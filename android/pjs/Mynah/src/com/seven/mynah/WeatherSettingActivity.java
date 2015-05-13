package com.seven.mynah;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.seven.mynah.artifacts.WeatherInfo;
import com.seven.mynah.artifacts.WeatherLocationInfo;
import com.seven.mynah.database.DBManager;

public class WeatherSettingActivity extends Activity{

	private ImageView ivCityNameSearch;
	private ListView lvCityName;
	private EditText etCityName;
	private CityNameAdapter adapter;
	
	private WeatherLocationInfo weatherInfo;
	
	private boolean mIsBackKeyPressed = false;
	
	protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_weather);
        
        ivCityNameSearch = (ImageView)findViewById(R.id.ivCityNameSearch);
        lvCityName = (ListView)findViewById(R.id.lvCityName);
        etCityName = (EditText)findViewById(R.id.etCityName);
        
        ivCityNameSearch.setOnClickListener(new OnClickListener() {
        	@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
        		String cityName = etCityName.getText().toString().trim();        		
        		
        		//cityname string으로 weatherLocationInfo 객체 생성
        		Toast.makeText(getApplicationContext(), "ivSearch Clicked: " + cityName, Toast.LENGTH_SHORT).show();

        		//DB Transaction
        		ArrayList<WeatherLocationInfo> array_location = new ArrayList<WeatherLocationInfo>();
        		array_location = DBManager.getManager(getApplicationContext()).getWeatherLocationByName(cityName);

        		adapter = new CityNameAdapter(getApplicationContext(), R.layout.list_row, array_location);
        		lvCityName.setAdapter(adapter);
        		adapter.notifyDataSetChanged();
			}
        });
        
        lvCityName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO 
            	//DB
            	
            	WeatherInfo winfo = new WeatherInfo();
            	//winfo.location.city_code = 
            	ViewHolder vh = (ViewHolder)view.getTag();
                
                DBManager.getManager(getApplicationContext()).setWeatherLocationDBbyLog(vh.weatherLocationInfo);
                
                Toast.makeText(getApplicationContext(), "item Clicked: " + vh.tvCityNameListRow.getText().toString(), Toast.LENGTH_SHORT).show();
                
                finish();
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
            }
        });
        
    }
	
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		
		if(mIsBackKeyPressed == false)
		{
			mIsBackKeyPressed = true;
			finish();
			overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
		}
	}

}
