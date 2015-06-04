package com.seven.mynah;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.seven.mynah.R;
import com.seven.mynah.artifacts.UserProfile;
import com.seven.mynah.artifacts.WeatherLocationInfo;
import com.seven.mynah.backgroundservice.GetInformationService;
import com.seven.mynah.database.DBManager;
import com.seven.mynah.globalmanager.GlobalVariable;
import com.seven.mynah.infoparser.WeatherParser;

public class LoadingActivity extends Activity{
	
	RelativeLayout rlayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logo);
		startService();
		Loading();
		
	}
	public void startService()
	{
		Intent service = new Intent(this, GetInformationService.class);
		startService(service);
		 
	}
	public void Loading()
	{
		
		createTempUser();
		checkInitUser();
		loadWeatherLocation();
		
		Handler handler = new Handler()
		{
			public void handleMessage(Message msg)
			{
				//Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
				startActivity(intent);
				finish();
			}
		};
		
		handler.sendEmptyMessageDelayed(0, 1000);
	}
	
	private void checkInitUser()
	{
		
		if (DBManager.getManager(this).isInitialUser())
		{
			UserProfile up =  DBManager.getManager(this).getMainUserDB();
			Toast.makeText(this, "김진성님 환영합니다.", 1).show();
		}
		else 
		{
			Toast.makeText(this, "등록된 유저가 없습니다.", 1).show();
		}
			
	}
	
	private void createTempUser()
	{
		UserProfile up = new UserProfile();
		up.id = "pika";
		up.passwd = "";
		up.name = "김진성";
		up.inout = 1;
		up.mac_address = "";
		up.usertype = 1;
		up.usertype = GlobalVariable.UserType.me;
		up.mastertype = GlobalVariable.UserType.master;
		
		DBManager.getManager(this).setMainUserDB(up);
		
		
	}
	
	private void loadWeatherLocation()
	{
		if(DBManager.getManager(this).isSetWeatherLocation())
		{
			//지역정보 설정되어 있음.
		}
		else
		{
			WeatherParser wp = new WeatherParser();
	    	ArrayList<WeatherLocationInfo> array_location; 
	    	array_location = wp.getAllLocationInfo();
	    	DBManager.getManager(this).setWeatherLocationAll(array_location);
		}
		
	}
	
	
	// 현 상황에서 필요없음.. A->B에서 B->A로 갈때 다이얼로그 사용할 때 사용.
	@Override 	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		
		
	}
	
	
	
}
