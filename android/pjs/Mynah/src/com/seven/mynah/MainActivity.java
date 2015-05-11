package com.seven.mynah;


import java.util.ArrayList;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.seven.mynah.artifacts.BusInfo;
import com.seven.mynah.artifacts.BusRouteInfo;
import com.seven.mynah.artifacts.BusStationInfo;
import com.seven.mynah.artifacts.SubwayInfo;
import com.seven.mynah.artifacts.WeatherInfo;
import com.seven.mynah.artifacts.WeatherLocationInfo;
import com.seven.mynah.custominterface.CustomButtonsFragment;
import com.seven.mynah.database.DBManager;
import com.seven.mynah.infoparser.BusPaser;
import com.seven.mynah.infoparser.SubwayPaser;
import com.seven.mynah.infoparser.WeatherParser;



public class MainActivity extends Activity {

	
	private CustomButtonsFragment cbf;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Splash : LoadingActivity
        //onCreate에서 splash를 하는경우 뒤로가기를 누를시 메인화면으로 돌아옴 > loading을 skip할 수 있다.
        //startActivity(new Intent(this, LoadingActivity.class));
        
        
        if (savedInstanceState == null) {
            setDefaultFragment();
        }
        testSide();
    }
    
    

    private void setDefaultFragment()
	{
		FragmentManager fm = getFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		cbf = new CustomButtonsFragment();
		transaction.add(R.id.container, cbf);
		transaction.commit();
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void testSide()
    {
    	
    	
    	//날씨부분 
    	WeatherInfo winfo = new WeatherInfo();
    	
    	
    	WeatherParser wp = new WeatherParser();
    	winfo = wp.getWeatherInfo(winfo);
    	
    	
    	ArrayList<WeatherLocationInfo> array_location; 
    	
    	//불러오기부분
    	array_location = wp.getAllLocationInfo();
    	//db 저장
    	DBManager.getManager(this).setWeatherLocationAll(array_location);
    	
    	array_location = DBManager.getManager(this).getWeatherLocationByName("월계");
    	
    	
//    	
//    	winfo.location = array_location.get(0);
//    	
//    	winfo = wp.getWeatherInfo(winfo);
//    	
//    	
//    	
//    	
//    	BusInfo binfo = new BusInfo();
//    	BusPaser bp = new BusPaser();
//    	
//    	
//    	
//    	ArrayList<BusRouteInfo> array_rinfo;
//    	ArrayList<BusStationInfo> array_sinfo;
//    	
//    	array_rinfo = bp.getBusRouteList("121");
//    	
//    	array_sinfo = bp.getStaionsByRouteList(array_rinfo.get(1).busRouteId);
//    	
//    	//array_sinfo = bp.getStationByNameList("광운대");
//    	
//    	binfo.route = array_rinfo.get(1);
//    	binfo.station = array_sinfo.get(5);
//    	
//    	binfo = bp.getStationByUid(binfo);
//    	bp.getBusArrInfoByRoute(binfo);
//    	
//    	
//    	SubwayPaser sp = new SubwayPaser();
//    	
//    	SubwayInfo sinfo = new SubwayInfo();
//    	
//    	sinfo.inout_tag = 2;
//    	sinfo.station.station_cd = "1006";
//    	sinfo.week_tag = 1;
//    	
//    	//sp.getTimeTableByID(sinfo);
//    	
//    	sp.getStationInfoByName("청량리");
//    	
//    	//bp.parseBus_XML(binfo);
    	
    }
    
    public void startSettingActivity_temp(String type)
    {
    	String intentActionName = "com.seven.mynah.";
		intentActionName += type;
		Intent intent = new Intent(intentActionName);
		startActivity(intent);
		this.overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top);
		
    }
    
    
}
