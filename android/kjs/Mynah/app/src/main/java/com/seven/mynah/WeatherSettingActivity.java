package com.seven.mynah;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.seven.mynah.artifacts.WeatherInfo;
import com.seven.mynah.artifacts.WeatherLocationInfo;
import com.seven.mynah.database.DBManager;

public class WeatherSettingActivity extends Activity{

	private ImageView ivCityNameSearch;
	private ListView lvCityName;
	private EditText etCityName;
	private TextView tvCurrentWeatherLocation;
	private CityNameAdapter adapter;
	
	//for tvCurrentWeatherLocation
	private WeatherLocationInfo wlinfo;
	private ArrayList<WeatherLocationInfo> weatherArrayList;
	private WeatherInfo winfo;
	
	private boolean mIsBackKeyPressed = false;
	
	private static String TAG = "WeatherSettingActivity";
	
	protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_weather);
        
        ivCityNameSearch = (ImageView)findViewById(R.id.ivCityNameSearch);
        lvCityName = (ListView)findViewById(R.id.lvCityName);
        etCityName = (EditText)findViewById(R.id.etCityName);
        tvCurrentWeatherLocation = (TextView)findViewById(R.id.tvCurrentWeatherLocation);
        
        weatherArrayList = new ArrayList<WeatherLocationInfo>();
        weatherArrayList = DBManager.getManager(getApplicationContext()).getWeatherDBbyLog();
        
        if(weatherArrayList.size() != 0)
        {
        	wlinfo = weatherArrayList.get(0);
            tvCurrentWeatherLocation.setText(wlinfo.city_name);
        }
        
        
        ivCityNameSearch.setOnClickListener(new OnClickListener() {
        	@Override
			public void onClick(View v) {
        		Log.d(TAG, "onClick Start");
        		
				// TODO Auto-generated method stub
        		String cityName = etCityName.getText().toString().trim();        		
        		
        		//DB Transaction
        		ArrayList<WeatherLocationInfo> array_location = new ArrayList<WeatherLocationInfo>();
        		array_location = DBManager.getManager(getApplicationContext()).getWeatherLocationByName(cityName);
        		
        		adapter = new CityNameAdapter(getApplicationContext(), R.layout.list_row, array_location);
        		lvCityName.setAdapter(adapter);
        		adapter.notifyDataSetChanged();
        		
        		Log.d(TAG, "onClick End");
			}
        });
        
        lvCityName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO 
            	//DB
            	Log.d(TAG, "onItemClick Start");
            	WeatherInfo winfo = new WeatherInfo();
            	ViewHolder vh = (ViewHolder)view.getTag();
                
            	DBManager.getManager(getApplicationContext()).setWeatherLocationDBbyLog(vh.weatherLocationInfo);
                
                finish();
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                
                Log.d(TAG, "onItemClick End");
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
