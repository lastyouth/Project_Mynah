package com.seven.mynah.backgroundservice;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;

import android.os.Looper;
import android.os.Message;
import android.provider.Settings;

import android.util.Log;
import android.widget.Toast;

import com.seven.mynah.MainActivity;
import com.seven.mynah.artifacts.BusInfo;
import com.seven.mynah.artifacts.ScheduleInfo;
import com.seven.mynah.artifacts.SessionUserInfo;
import com.seven.mynah.artifacts.SubwayInfo;
import com.seven.mynah.artifacts.WeatherInfo;
import com.seven.mynah.artifacts.WeatherLocationInfo;
import com.seven.mynah.database.DBManager;
import com.seven.mynah.globalmanager.GlobalVariable;
import com.seven.mynah.globalmanager.RECManager;
import com.seven.mynah.globalmanager.ServiceAccessManager;
import com.seven.mynah.globalmanager.TTSManager;
import com.seven.mynah.infoparser.BusPaser;
import com.seven.mynah.infoparser.SubwayPaser;
import com.seven.mynah.infoparser.WeatherParser;
import com.seven.mynah.network.AsyncHttpTask;
import com.seven.mynah.network.AsyncHttpUpload;
import com.seven.mynah.summarize.InfoTextSummarizer;
import com.seven.mynah.util.DebugToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.spec.RSAOtherPrimeInfo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.prefs.Preferences;

public class GetInformationService extends Service
{
    // local binder for Activity attaching

    private final IBinder mServicePointer = new LocalBinder();
    private boolean isBindWithActivity = false;
    private String deviceID;
    private RPiBluetoothConnectionManager mBluetoothManager;
    private Context mCtx;

    private SharedPreferences pref;

    private boolean isBluetoothEnabled;


    //public static final long NOTIFY_INTERVAL = 3 * 60 * 1000; //3분
    public static final long NOTIFY_INTERVAL = 1 * 60 * 1000; //1분

    //public static final long BLUETOOTH_NOTIFY_INTERVAL = 3 * 1000;

    private Timer mTimer = null;
    private Handler mTimeTaskHandler = new Handler(); // for new thread
    private TTSManager mTTSManager = null;
    public static final String TAG = "GetInfomationService";


    protected Handler mhHandler = new Handler() {
        public void handleMessage(Message msg) {
            // IF Sucessfull no timeout
            System.out.println("in handler");
            if (msg.what == -1) {
                //   BreakTimeout();
                //ConnectionError();
                //System.out.println("handler error");
                Log.d(TAG, "handler error");
            }

            if (msg.what == 1) {
                //핸들링 1일때 할 것
                //System.out.println("response : "+msg.obj);
                Log.d(TAG, "handling 1 -> response : " + msg.obj);
            }

            if (msg.what == 2) {
                //핸들링 2일때 할 것
                //System.out.println("handling 2 !");
                //System.out.println("response : "+msg.obj);
                Log.d(TAG, "handling 2 -> response : " + msg.obj);
            }

            if (msg.what == 3)
            {
                JSONObject jobj = null;
                try {
                    jobj = new JSONObject(msg.obj+"");
                    String messageType = jobj.get("messagetype") + "";

                    if(messageType.equals("get_uuid")) {
                        String result = jobj.get("result") + "";


                        String data[] = result.split("//");

                        if(data[0].equals("GET_UUID_SUCCESS")) {
                            SharedPreferences pref = getSharedPreferences(ServiceAccessManager.PREF, MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("RPI_MAC", data[1]);
                            editor.putString("RPI_UUID", data[2]);

                            if(!mBluetoothManager.isInitialize())
                            {
                                int ret = mBluetoothManager.initializeBTConnection(data[2],data[1]);
                                processErrorHandler(ret);
                            }

                            editor.commit();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }
    };

    private BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if(action.equals(BluetoothAdapter.ACTION_STATE_CHANGED))
            {
                int type = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
                if(type == BluetoothAdapter.STATE_OFF)
                {
                    DebugToast.makeText(mCtx, "Bluetooth is Off", Toast.LENGTH_SHORT).show();
                    isBluetoothEnabled = false;
                }
                else if(type == BluetoothAdapter.STATE_ON)
                {
                    DebugToast.makeText(mCtx,"Bluetooth is On",Toast.LENGTH_SHORT).show();
                    isBluetoothEnabled = true;
                }
            }
        }
    };



    //블투 콜백에 대한 tts 대응 필요없음
    private BluetoothRequestCallback btCallback = new BluetoothRequestCallback() {
        @Override
        public void onRequestOutTTSWithRSSI() {
            Log.d(TAG, "onRequestOutTTSWithRSSI");

            //String tts = InfoTextSummarizer.getInstance(mCtx).makeTotalTTS();

            //mBluetoothManager.sendTTSWithRSSI(RPiBluetoothConnectionManager.SEND_TYPE_OUTTTS,tts);
            mBluetoothManager.sendTTSWithRSSI(RPiBluetoothConnectionManager.SEND_TYPE_OUTTTS,"");
            Log.d(TAG, "Send Data from GetInformationService : " + "");

        }

        @Override
        public void onRequestInTTSWithRSSI() {
            Log.d(TAG, "onRequestInTTSWithRSSI");

            SessionUserInfo sInfo = DBManager.getManager(getApplicationContext()).getSessionUserDB();
            String userName = sInfo.userName;
            String tts = "어서오세요, " + userName + "님, 오늘 하루도 수고하셨습니다.";

            //mBluetoothManager.sendTTSWithRSSI(RPiBluetoothConnectionManager.SEND_TYPE_INTTS, tts);
            mBluetoothManager.sendTTSWithRSSI(RPiBluetoothConnectionManager.SEND_TYPE_INTTS, "");
            Log.d(TAG, "Send Data from GetInformationService : " + tts);
        }

        @Override
        public void onTempDataArrived(int temp) {
            Log.d(TAG, "Temparture : " + temp);

            //mTempdata = temp;
            InfoTextSummarizer.getInstance(mCtx).setTempData(temp);
        }
        @Override
        public void onConnected()
        {
            //TODO 메인에서 호출하는 변경 내용
            GlobalVariable.isBluetoothOn = true;
            Intent intent = new Intent(GlobalVariable.BROADCAST_MESSAGE);
            sendBroadcast(intent);


        }
        @Override
        public void onDisconnected()
        {
            //TODO 접속 종료시
            GlobalVariable.isBluetoothOn = false;
            Intent intent = new Intent(GlobalVariable.BROADCAST_MESSAGE);
            sendBroadcast(intent);
        }

        @Override
        public void onNewWifiResult(boolean flag) {
            if(flag)
            {
                Intent intent = new Intent(GlobalVariable.BROADCAST_WIFI);
                intent.putExtra("WIFI",1);
                sendBroadcast(intent);
            }
            else
            {
                Intent intent = new Intent(GlobalVariable.BROADCAST_WIFI);
                intent.putExtra("WIFI",0);
                sendBroadcast(intent);
            }
        }
    };

	@Override
    public void onCreate()
	{
        //mTempdata = 0;
        Log.d(TAG, "onCreate Start");
		//Toast.makeText(this, "Service onCreate", Toast.LENGTH_SHORT).show();

        //timer 내용 포함
        if(mTimer != null){
            mTimer.cancel();
        }
        else {
            mTimer = new Timer();
        }

        mTimer.scheduleAtFixedRate(new SendTTSTimerTask(), 3000, NOTIFY_INTERVAL);
        mTTSManager = new TTSManager(this);

        mCtx = this;
		//Toast.makeText(this, "Service onCreate", Toast.LENGTH_SHORT).show();
        DebugToast.makeText(this,"Service onCreate", Toast.LENGTH_SHORT).show();


        // register broadcast receiver
        IntentFilter btfilter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(bluetoothReceiver,btfilter);

        // Get Device Id
        deviceID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        isBluetoothEnabled = false;// BluetoothAdapter.getDefaultAdapter().isEnabled();

        mBluetoothManager = new RPiBluetoothConnectionManager(deviceID);
        mBluetoothManager.registerCallback(btCallback);

        pref = getSharedPreferences(ServiceAccessManager.PREF,MODE_PRIVATE);

        String tuuid = pref.getString("RPI_UUID", "NULL");

        String tmac = pref.getString("RPI_MAC","NULL");

        // initialize bt connection
        int ret = mBluetoothManager.initializeBTConnection(tuuid,tmac);

        processErrorHandler(ret);
		super.onCreate();
        Log.d(TAG, "onCreate Finish");
    }

    public boolean sendData(int type, String str)
    {
        //RPiBluetoothConnectionManager.SEND_TYPE_NEWWIFI , str //로 구분해서 다 보낼것
        //RPiBluetoothConnectionManager.SEND_TYPE_POWEROFF  str // ""으로 본낼것
        //type
        boolean flag = mBluetoothManager.sendTTSWithRSSI(type,str);
        if(flag)
        {
            DebugToast.makeText(this,"블루투스 전송 성공 " + str,Toast.LENGTH_SHORT).show();
        }else
        {
            DebugToast.makeText(this,"블루투스 전송 실패 " + str,Toast.LENGTH_SHORT).show();
        }
        return flag;
    }


    private void processErrorHandler(int ret)
    {
        if(ret == RPiBluetoothConnectionManager.ERROR_BT_NOT_SUPPORTED)
        {
            // Bluetooth is NOT Supported
            Log.d(TAG,"Shit");
        }
        else if(ret == RPiBluetoothConnectionManager.ERROR_CALLBACK_IS_NOT_REGISTERED)
        {

        }else if(ret == RPiBluetoothConnectionManager.ERROR_TARGET_UUID_NOT_REGISTERED)
        {
            // if uuid is not exist, then re-load it
            JSONObject jobj = new JSONObject();
            try{
                jobj.put("messagetype", "get_uuid");
                jobj.put("device_id",Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));
                //jobj.put("product_id", strProductId);
            }catch(JSONException e){
                e.printStackTrace();
            }

            new AsyncHttpTask(getApplicationContext(), GlobalVariable.WEB_SERVER_IP, mhHandler,jobj, 3, 2);
        }
        else if(ret == RPiBluetoothConnectionManager.ERROR_TARGET_DEVICE_NOT_REGISTERED)
        {
            Toast.makeText(getApplicationContext(),"디바이스와 최초 페어링이 되지 않았습니다. Mynah-Device 기능을 사용할 수 없습니다",Toast.LENGTH_LONG).show();
        }
        else if(ret == RPiBluetoothConnectionManager.ERROR_BT_IS_NOT_ENABLED)
        {
            Log.d(TAG,"BT is Not Enabled");
        }
        else if(ret == RPiBluetoothConnectionManager.SUCCESS_INITIALIZE)
        {
            Log.d(TAG,"BT initialized Successfully");
        }
        else
        {
            DebugToast.makeText(this, "Unknown return value from RPiBluetoothConnectionManager", Toast.LENGTH_SHORT).show();
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
        Log.d(TAG, "onStartCommand Start");
        DebugToast.makeText(this,"GetInformation Service onStartCommand",  Toast.LENGTH_SHORT).show();

        String tuuid = pref.getString("RPI_UUID", "NULL");
        String tmac = pref.getString("RPI_MAC","NULL");

        if(!mBluetoothManager.isInitialize())
        {
            int ret = mBluetoothManager.initializeBTConnection(tuuid,tmac);
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

    public void setBindStatus(boolean status)
    {
        isBindWithActivity = status;
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
        DebugToast.makeText(this, "Service onDestroy", Toast.LENGTH_SHORT).show();

        Log.d(TAG, "onDestroy Finish");
    	super.onDestroy();
    }

    public int requestBTInit(String uuid, String mac)
    {
        if(!mBluetoothManager.isInitialize()) {
            return mBluetoothManager.initializeBTConnection(uuid,mac);
        }
        return RPiBluetoothConnectionManager.SUCCESS_INITIALIZE;
    }

    public Timer getTimer()
    {
        return mTimer;
    }


    public void sendTTSNow()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendTTS(false);
                //DebugToast.makeText(mCtx,"즉시 tts 생성 시퀸스 완료",Toast.LENGTH_SHORT).show();
            }
        }).start();
    }


    public void sendTTS(boolean toastOn)
    {
        String tts = InfoTextSummarizer.getInstance(mCtx).makeTotalTTS();
        if(InfoTextSummarizer.getInstance(mCtx).isUpdate())
        {
            SessionUserInfo suInfo = DBManager.getManager(getApplicationContext()).getSessionUserDB();

            try {
                mTTSManager.saveTTS(tts,"tts.mp3");
                if(toastOn) DebugToast.makeText(mCtx, TAG + ": tts 생성 period 성공 , id : " + suInfo.userId + " data : " + tts, Toast.LENGTH_SHORT).show();
                //주기적으로 파일 전송
                new AsyncHttpUpload(getApplicationContext(), GlobalVariable.WEB_SERVER_IP, mhHandler,
                        RECManager.getInstance().getDefaultExStoragePath() + "tts.mp3", 1, AsyncHttpUpload.TYPE_TTS);
            } catch (Exception e)
            {
                e.printStackTrace();
                return;
            }
        }
        else
        {
            if(toastOn) DebugToast.makeText(mCtx, TAG + ": 동일 tts 확인 미전송 시퀸스", Toast.LENGTH_SHORT).show();
        }
    }

    class SendTTSTimerTask extends TimerTask {

        @Override
        public void run() {
            mTimeTaskHandler.post(new Runnable() {
                @Override
                public void run() {
                    sendTTS(true);
                }
            });
        }
    }

}
