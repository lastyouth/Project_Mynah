package com.seven.mynah.backgroundservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class GetInformationService extends Service
{
    // local binder for Activity attaching

    private final IBinder mServicePointer = new LocalBinder();
    private boolean isBindWithActivity = false;
    RPiBluetoothConnectionManager mBluetoothManager;

	@Override
    public void onCreate() 
	{
		Toast.makeText(this, "Service onCreate", Toast.LENGTH_SHORT).show();
		super.onCreate();
    }

    public class LocalBinder extends Binder {
        public GetInformationService getService()
        {
            return GetInformationService.this;
        }
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) 
    {
        // TODO Auto-generated method stub
    	Toast.makeText(this, "Service onStartCommand",  Toast.LENGTH_SHORT).show();
    	return START_STICKY;
    	//return super.onStartCommand(intent, flags, startId);
    }
    
    @Override
    public IBinder onBind(Intent intent) 
    {
        // TODO Auto-generated method stub
    	return mServicePointer;
    }
    public void doTest(String target)
    {
        Toast.makeText(this,target,Toast.LENGTH_SHORT).show();
    }
    public void setBindStatus(boolean status)
    {
        isBindWithActivity = status;
    }
    public void onDestroy() 
    {
        // TODO Auto-generated method stub
    	Toast.makeText(this, "Service onDestroy", Toast.LENGTH_SHORT).show();
    	super.onDestroy();
    }
}
