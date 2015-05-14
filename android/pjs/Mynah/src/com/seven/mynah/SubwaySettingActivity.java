package com.seven.mynah;

import java.util.ArrayList;

import android.app.Activity;
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
			tvCurrentSubwayStation.setText(sinfo.station.station_nm + " " + sinfo.station.line_num + "호선");
			
		}
		
		ivSubwayStationSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String subwayName = etSubwayStation.getText().toString().trim();

				Toast.makeText(getApplicationContext(),
						"ivSearch Clicked: " + subwayName, Toast.LENGTH_SHORT)
						.show();

				// DB Transaction
				ArrayList<SubwayStationInfo> array_line = new ArrayList<SubwayStationInfo>();

				SubwayPaser sp = new SubwayPaser();
				array_line = sp.getStationInfoByName(subwayName);

				adapter = new SubwayStationAdapter(getApplicationContext(),
						R.layout.list_row, array_line);
				lvSubwayStation.setAdapter(adapter);
				adapter.notifyDataSetChanged();
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

						sinfo.station.inout_tag = GlobalVariable.SubwayConstant.up_in_line; // 상행
						sinfo.week_tag = GlobalVariable.SubwayConstant.week_normal; // 평일

						DBManager.getManager(getApplicationContext())
								.setSubwayDBbyLog(sinfo);

						Toast.makeText(
								getApplicationContext(),
								"item Clicked: "
										+ vh.tvSubwayStationListRow.getText()
												.toString(), Toast.LENGTH_SHORT)
								.show();

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
}
