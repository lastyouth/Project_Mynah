package com.seven.mynah.backgroundservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.seven.mynah.artifacts.BusInfo;
import com.seven.mynah.artifacts.SubwayInfo;
import com.seven.mynah.database.DBManager;

import android.view.View;
import android.content.Context;

import java.util.ArrayList;

public class GetInformationService extends Service
{
    ArrayList<BusInfo> busArrayList;
    ArrayList<SubwayInfo> subwayArrayList;
    ArrayList weatherArrayList;

	@Override
    public void onCreate() 
	{
		Toast.makeText(this, "Service onCreate", 1).show();
		super.onCreate();


    }

    void getInformation(){


        //버스 지하철 날씨 정보 받아오게 해서 여기서 파싱 ?
        busArrayList = new ArrayList<BusInfo>();
        //busArrayList = DBManager.getManager(getContext()).getBusDBbyLog();
        subwayArrayList = new ArrayList<SubwayInfo>();
        //subwayArrayList = DBManager.getManager(getContext()).getSubwayDBbyLog();
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) 
    {
        // TODO Auto-generated method stub
    	Toast.makeText(this, "Service onStartCommand", 1).show();
    	return START_STICKY;
    	//return super.onStartCommand(intent, flags, startId);
    }
    
    @Override
    public IBinder onBind(Intent intent) 
    {
        // TODO Auto-generated method stub
    	return null;
    }
    
    @Override
    public void onDestroy() 
    {
        // TODO Auto-generated method stub
    	Toast.makeText(this, "Service onDestroy", 1).show();
    	super.onDestroy();
    }
}
