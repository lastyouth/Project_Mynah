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
import android.widget.Toast;

import com.seven.mynah.artifacts.SubwayInfo;
import com.seven.mynah.artifacts.SubwayStationInfo;
import com.seven.mynah.artifacts.WeatherInfo;
import com.seven.mynah.database.DBManager;
import com.seven.mynah.globalmanager.GlobalVariable;
import com.seven.mynah.infoparser.BusPaser;
import com.seven.mynah.infoparser.SubwayPaser;

public class SubwaySettingActivity extends Activity{
	
	private ImageView ivSubwayStationSearch;
	private ListView lvSubwayStation;
	private EditText etSubwayStation;
	private SubwayStationAdapter adapter;
	
	private SubwayStationInfo subwayStationInfo;
	
	private boolean mIsBackKeyPressed = false;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_subway);
        
        ivSubwayStationSearch = (ImageView)findViewById(R.id.ivSubwayStationSearch);
        lvSubwayStation = (ListView)findViewById(R.id.lvSubwayStation);
        etSubwayStation = (EditText)findViewById(R.id.etSubwayStationName);
        
        ivSubwayStationSearch.setOnClickListener(new OnClickListener() {
        	@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
        		String subwayName = etSubwayStation.getText().toString().trim();        		
        		
        		//cityname string栏肺 weatherLocationInfo 按眉 积己
        		Toast.makeText(getApplicationContext(), "ivSearch Clicked: " + subwayName, Toast.LENGTH_SHORT).show();

        		//DB Transaction
        		ArrayList<SubwayStationInfo> array_line = new ArrayList<SubwayStationInfo>();
        		
        		//array_line = DBManager.getManager(getApplicationContext()).getWeatherLocationByName(cityName);
        		SubwayPaser sp = new SubwayPaser();
        		array_line = sp.getStationInfoByName(subwayName);
        		
        		
        		adapter = new SubwayStationAdapter(getApplicationContext(), R.layout.list_row, array_line);
        		lvSubwayStation.setAdapter(adapter);
        		adapter.notifyDataSetChanged();
			}
        });
        
        lvSubwayStation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO 
            	//DB
            	
            	SubwayInfo sinfo = new SubwayInfo();
            	ViewHolder vh = (ViewHolder)view.getTag();
                sinfo.station = vh.subwayStationInfo;

            	sinfo.station.inout_tag = GlobalVariable.SubwayConstant.up_in_line; //惑青
            	sinfo.week_tag = GlobalVariable.SubwayConstant.week_normal; //乞老

                
                DBManager.getManager(getApplicationContext()).setSubwayDBbyLog(sinfo);
                
                Toast.makeText(getApplicationContext(), "item Clicked: " + vh.tvSubwayStationListRow.getText().toString(), Toast.LENGTH_SHORT).show();
                
                finish();
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
            }
        });
        
        
    }
}
