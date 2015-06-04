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


        //���� ����ö ���� ���� �޾ƿ��� �ؼ� ���⼭ �Ľ� ?
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
