package com.seven.mynah.backgroundservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class GetInformationService extends Service
{
	@Override
    public void onCreate() 
	{
		Toast.makeText(this, "Service onCreate", 1).show();
		super.onCreate();
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
