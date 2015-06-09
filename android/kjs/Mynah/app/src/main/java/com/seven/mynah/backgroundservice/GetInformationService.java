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

import com.seven.mynah.artifacts.BusInfo;
import com.seven.mynah.artifacts.ScheduleInfo;
import com.seven.mynah.artifacts.SubwayInfo;
import com.seven.mynah.artifacts.WeatherInfo;
import com.seven.mynah.artifacts.WeatherLocationInfo;
import com.seven.mynah.database.DBManager;
import com.seven.mynah.infoparser.BusPaser;
import com.seven.mynah.infoparser.SubwayPaser;
import com.seven.mynah.infoparser.WeatherParser;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GetInformationService extends Service
{
    // local binder for Activity attaching

    private final IBinder mServicePointer = new LocalBinder();
    private boolean isBindWithActivity = false;
    private String deviceID;
    private RPiBluetoothConnectionManager mBluetoothManager;
    private Context mCtx;
    private int mTempdata;

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
        public void onRequestOutTTSWithRSSI() {
            Log.d(TAG, "onRequestOutTTSWithRSSI");
            String data = new String("안녕하세요 기미중님 사랑합니다.~");

            Log.d(TAG,"Send Data from GetInformationService : "+data);

            mBluetoothManager.sendTTSWithRSSI(RPiBluetoothConnectionManager.SEND_TYPE_OUTTTS,data);

        }

        @Override
        public void onRequestInTTSWithRSSI() {
            Log.d(TAG, "onRequestInTTSWithRSSI");
            String data = new String("어서오세요 기미중님 오늘 하루도 수고하셨습니다.");

            Log.d(TAG,"Send Data from GetInformationService : "+data);

            mBluetoothManager.sendTTSWithRSSI(RPiBluetoothConnectionManager.SEND_TYPE_INTTS,data);
        }

        @Override
        public void onTempDataArrived(int temp) {
            Log.d(TAG,"Temparture : "+temp);
            mTempdata = temp;
        }
    };

	@Override
    public void onCreate() 
	{
        mTempdata = 0;
        Log.d(TAG, "onCreate Start");
		//Toast.makeText(this, "Service onCreate", Toast.LENGTH_SHORT).show();
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
        Log.d(TAG, "onCreate Finish");
    }

    private void processErrorHandler(int ret)
    {
        if(ret == RPiBluetoothConnectionManager.ERROR_BT_NOT_SUPPORTED)
        {
            // Bluetooth is NOT Supported
            Log.d(TAG,"Shit");
        }else if(ret == RPiBluetoothConnectionManager.ERROR_CALLBACK_IS_NOT_REGISTERED)
        {

        }else if(ret == RPiBluetoothConnectionManager.ERROR_TARGET_DEVICE_NOT_REGISTERED)
        {

        }else if(ret == RPiBluetoothConnectionManager.ERROR_BT_IS_NOT_ENABLED)
        {
            Log.d(TAG,"BT is Not Enabled");
        }else if(ret == RPiBluetoothConnectionManager.SUCCESS_INITIALIZE)
        {
            Log.d(TAG,"BT initialized Successfully");
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
    	//Toast.makeText(this, "Service onStartCommand",  Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onStartCommand Start");

    	Toast.makeText(this, "Service onStartCommand",  Toast.LENGTH_SHORT).show();
        if(!mBluetoothManager.isInitialize())
        {
            int ret = mBluetoothManager.initializeBTConnection();
            processErrorHandler(ret);
        }
        //Log.d("GetInformationService", "Context : " + getBaseContext() + "Service context : " + this);
        Log.d(TAG, "onStartCommand Finish");
    	return START_STICKY;

    	//return super.onStartCommand(intent, flags, startId);
    }
    
    @Override
    public IBinder onBind(Intent intent) 
    {
        // TODO Auto-generated method stub
        Log.d(TAG, "onBind Start");
        Log.d(TAG, "onBind Finish");
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

    public int getTempData(){
        return mTempdata;
    }
    public void onDestroy()
    {
        Log.d(TAG, "onDestroy Start");
        // release receiver
        unregisterReceiver(bluetoothReceiver);
        // TODO Auto-generated method stub

    	if(mBluetoothManager != null && mBluetoothManager.isInitialize())
        {
            mBluetoothManager.stopBTConnection();
        }
        Toast.makeText(this, "Service onDestroy", Toast.LENGTH_SHORT).show();

        Log.d(TAG, "onDestroy Finish");
    	super.onDestroy();
    }

    public BusInfo getBusInfo()
    {
        BusInfo bInfo;
        ArrayList<BusInfo> busArrayList;

        // get current saved information from DB
        busArrayList = DBManager.getManager(this).getBusDBbyLog();

        if (busArrayList.size() == 0)
        {
            return null;
        }
        else
        {
            bInfo = busArrayList.get(0);
            bInfo = DBManager.getManager(this).getBusDB(bInfo);

            if (bInfo.array_ttb.size() == 0)
            {
                BusPaser bp = new BusPaser();
                bInfo = bp.getBusArrInfoByRoute(bInfo);
                DBManager.getManager(this).setBusDB(bInfo);
                bInfo = DBManager.getManager(this).getBusDB(bInfo);
            }
            return bInfo;
        }
    }

    public SubwayInfo getSubwayInfo()
    {
        SubwayInfo sInfo;
        ArrayList<SubwayInfo> subwayArrayList;

        // get current saved information from DB
        subwayArrayList = DBManager.getManager(this).getSubwayDBbyLog();

        if (subwayArrayList.size() == 0)
        {
            return null;
        }
        else
        {
            sInfo = subwayArrayList.get(0);
            sInfo = DBManager.getManager(this).getSubwayDB(sInfo);

            if (sInfo.array_tts.size() == 0)
            {
                SubwayPaser sp = new SubwayPaser();
                sInfo = sp.getTimeTableByID(sInfo);
                DBManager.getManager(this).setSubwayDB(sInfo);
                sInfo = DBManager.getManager(this).getSubwayDB(sInfo);
            }
            return sInfo;
        }
    }

    public WeatherInfo getWeatherInfo()
    {
        ArrayList<WeatherLocationInfo> weatherArrayList = DBManager.getManager(this).getWeatherDBbyLog();
        WeatherInfo wInfo = new WeatherInfo();

        if(weatherArrayList.size() == 0)
        {
            return null;
        }
        else
        {
            WeatherLocationInfo wLocationInfo = weatherArrayList.get(0);
            wInfo.location = wLocationInfo;
            wInfo = DBManager.getManager(this).getWeatherDB(wInfo);
            if(wInfo.array_ttw.size() == 0)
            {
                WeatherParser wp = new WeatherParser();
                wInfo = wp.getWeatherInfo(wInfo);
            }
            return wInfo;
        }
    }

    public ArrayList<ScheduleInfo> getScheduleInfo()
    {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sdf.format(date);

        // Request to get Schedule Information through Mynah DB
        ArrayList<ScheduleInfo> scheduleInfo;
        scheduleInfo = DBManager.getManager(this).getSchedulesByDateTimeDB(strDate).scheduleList;

        return scheduleInfo;
    }
}
