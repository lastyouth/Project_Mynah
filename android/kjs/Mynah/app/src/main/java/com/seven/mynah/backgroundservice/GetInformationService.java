package com.seven.mynah.backgroundservice;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

public class GetInformationService extends Service
{
    // local binder for Activity attaching

    private final IBinder mServicePointer = new LocalBinder();
    private boolean isBindWithActivity = false;
    private String deviceID;
    private RPiBluetoothConnectionManager mBluetoothManager;
    private Context mCtx;

    public static final String TAG = "GetInfomationService";

    private BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if(action.equals(BluetoothAdapter.ACTION_STATE_CHANGED))
            {
                int type = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
                if(type == BluetoothAdapter.STATE_OFF)
                {
                    Toast.makeText(mCtx,"Bluetooth is Off",Toast.LENGTH_SHORT).show();
                }else if(type == BluetoothAdapter.STATE_ON)
                {
                    Toast.makeText(mCtx,"Bluetooth is On",Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private BluetoothRequestCallback btCallback = new BluetoothRequestCallback() {
        @Override
        public void onRequestTTSWithRSSI() {
            Log.d(TAG, "onRequestTTSWithRSSI");
            String data = new String("안녕하세요 서보훈님 오늘도 좋은하루 되시길 바라며 화이팅입니다!! 안녕하세요 서보훈님 오늘도 좋은하루 되시길 바라며 화이팅입니다!! 안녕하세요 서보훈님 오늘도 좋은하루 되시길 바라며 화이팅입니다!!");

            Log.d(TAG,"Send Data from GetInformationService : "+data);

            mBluetoothManager.sendTTSWithRSSI(data);

        }
    };


	@Override
    public void onCreate() 
	{
        mCtx = this;
		Toast.makeText(this, "Service onCreate", Toast.LENGTH_SHORT).show();
        // register broadcast receiver
        IntentFilter btfilter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(bluetoothReceiver,btfilter);

        // Get Device Id
        deviceID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        mBluetoothManager = new RPiBluetoothConnectionManager(deviceID);

        mBluetoothManager.registerCallback(btCallback);

        // initialize bt connection
        int ret = mBluetoothManager.initializeBTConnection();

        processErrorHandler(ret);

		super.onCreate();
    }

    private void processErrorHandler(int ret)
    {
        if(ret == RPiBluetoothConnectionManager.ERROR_BT_NOT_SUPPORTED)
        {

        }else if(ret == RPiBluetoothConnectionManager.ERROR_CALLBACK_IS_NOT_REGISTERED)
        {

        }else if(ret == RPiBluetoothConnectionManager.ERROR_TARGET_DEVICE_NOT_REGISTERED)
        {

        }else if(ret == RPiBluetoothConnectionManager.ERROR_BT_IS_NOT_ENABLED)
        {
            Log.d(TAG,"BT is Not Enabled");
        }else if(ret == RPiBluetoothConnectionManager.SUCCESS_INITIALIZE)
        {

        }else
        {
            Toast.makeText(this,"Unknown return value from RPiBluetoothConnectionManager",Toast.LENGTH_SHORT).show();
        }
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
        if(!mBluetoothManager.isInitialize())
        {
            int ret = mBluetoothManager.initializeBTConnection();
            processErrorHandler(ret);
        }
        //Log.d("GetInformationService", "Context : " + getBaseContext() + "Service context : " + this);
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
        // release receiver
        unregisterReceiver(bluetoothReceiver);
        // TODO Auto-generated method stub
    	if(mBluetoothManager != null && mBluetoothManager.isInitialize())
        {
            mBluetoothManager.stopBTConnection();
        }
        Toast.makeText(this, "Service onDestroy", Toast.LENGTH_SHORT).show();

    	super.onDestroy();
    }
}
