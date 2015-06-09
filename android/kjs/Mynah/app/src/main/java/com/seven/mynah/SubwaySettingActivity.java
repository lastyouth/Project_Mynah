package com.seven.mynah;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.seven.mynah.artifacts.SubwayInfo;
import com.seven.mynah.artifacts.SubwayStationInfo;
import com.seven.mynah.database.DBManager;
import com.seven.mynah.globalmanager.GlobalFunction;
import com.seven.mynah.globalmanager.GlobalVariable;
import com.seven.mynah.infoparser.SubwayPaser;

public class SubwaySettingActivity extends Activity {

	private ImageView ivSubwayStationSearch;
	private ListView lvSubwayStation;
	private EditText etSubwayStation;
	private TextView tvCurrentSubwayStation;
	private SubwayStationAdapter adapter;

	private SubwayStationInfo subwayStationInfo;
	
	//for tvCurrentSubwayStation
	private ArrayList<SubwayInfo> subwayArrayList;
	private SubwayInfo sinfo;
	
	private boolean mIsBackKeyPressed = false;
	private String subway_station;
	private String line_num;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_subway);

		ivSubwayStationSearch = (ImageView) findViewById(R.id.ivSubwayStationSearch);
		lvSubwayStation = (ListView) findViewById(R.id.lvSubwayStation);
		etSubwayStation = (EditText) findViewById(R.id.etSubwayStationName);
		tvCurrentSubwayStation = (TextView)findViewById(R.id.tvCurrentSubwayStation);
		
		subwayArrayList = new ArrayList<SubwayInfo>();
		subwayArrayList = DBManager.getManager(getApplicationContext()).getSubwayDBbyLog();


		
		if(subwayArrayList.size() != 0)
		{
			sinfo = subwayArrayList.get(0);
			sinfo = DBManager.getManager(getApplicationContext()).getSubwayDB(sinfo);
			//SubwayPaser sp = new SubwayPaser();
			//sinfo = sp.getTimeTableByID(sinfo);
			line_num = sinfo.station.line_num;
			tvCurrentSubwayStation.setText(sinfo.station.station_nm + " " + GlobalFunction.SubwayDecode(line_num));
		}
		
		ivSubwayStationSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				subway_station = etSubwayStation.getText().toString().trim();

				if(subway_station.matches(".*역"))
				{
					int i = subway_station.indexOf("역");
					subway_station = subway_station.substring(0, i);
				}
				if(subway_station.equals(""))
				{
					subway_station = sinfo.station.station_nm;
				}

				// DB Transaction
				ArrayList<SubwayStationInfo> array_line = new ArrayList<SubwayStationInfo>();

				SubwayPaser sp = new SubwayPaser();
				array_line = sp.getStationInfoByName(subway_station);

				if(array_line.size() == 0)
				{
					showSearchError();
				}
				else
				{
					adapter = new SubwayStationAdapter(getApplicationContext(),
							R.layout.list_row, array_line);
					lvSubwayStation.setAdapter(adapter);
					adapter.notifyDataSetChanged();
				}

			}
		});

		lvSubwayStation
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO
						// DB

						SubwayInfo sinfo = new SubwayInfo();
						ViewHolder vh = (ViewHolder) view.getTag();
						sinfo.station = vh.subwayStationInfo;

						sinfo.station.inout_tag = vh.subwayStationInfo.inout_tag;
						sinfo.week_tag = GlobalVariable.SubwayConstant.week_normal; // 평일

						DBManager.getManager(getApplicationContext())
								.setSubwayDBbyLog(sinfo);

						finish();
						overridePendingTransition(R.anim.slide_in_from_left,
								R.anim.slide_out_to_right);
					}
				});

	}

	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		if (mIsBackKeyPressed == false) {
			mIsBackKeyPressed = true;
			finish();
			overridePendingTransition(R.anim.slide_in_from_left,
					R.anim.slide_out_to_right);
		}
	}

	public void showSearchError()
	{
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("");
		alertDialog.setMessage(subway_station + "을 찾을수 없습니다.");
		alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "확인", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which)
			{
				etSubwayStation.setText("");
				dialog.dismiss();
			}
		});

		alertDialog.show();
	}
}
