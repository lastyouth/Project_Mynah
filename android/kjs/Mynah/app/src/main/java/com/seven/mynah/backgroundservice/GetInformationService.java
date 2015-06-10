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
import com.seven.mynah.globalmanager.GlobalFunction;
import com.seven.mynah.infoparser.BusPaser;
import com.seven.mynah.infoparser.SubwayPaser;
import com.seven.mynah.infoparser.WeatherParser;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
                }
                else if(type == BluetoothAdapter.STATE_ON)
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
            String tts = makeTotalTTS();
            mBluetoothManager.sendTTSWithRSSI(RPiBluetoothConnectionManager.SEND_TYPE_OUTTTS,tts);
            Log.d(TAG, "Send Data from GetInformationService : " + tts);

        }

        @Override
        public void onRequestInTTSWithRSSI() {
            Log.d(TAG, "onRequestInTTSWithRSSI");
            String tts = new String("어서오세요 김진성님 오늘 하루도 수고하셨습니다.");
            mBluetoothManager.sendTTSWithRSSI(RPiBluetoothConnectionManager.SEND_TYPE_INTTS, tts);
            Log.d(TAG, "Send Data from GetInformationService : " + tts);
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
        }
        else if(ret == RPiBluetoothConnectionManager.ERROR_CALLBACK_IS_NOT_REGISTERED)
        {

        }
        else if(ret == RPiBluetoothConnectionManager.ERROR_TARGET_DEVICE_NOT_REGISTERED)
        {

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

    public String getScheduleInfoTTS(ArrayList<ScheduleInfo> sInfoList)
    {
        if(sInfoList == null)
        {
            return "";
        }
        String tts = "";
        int maxScheduleCount = 3;
        int cnt = 0;

        ArrayList<String> timeList = new ArrayList<String>();
        ArrayList<String> summaryList = new ArrayList<String>();

        for(ScheduleInfo sInfo : sInfoList)
        {
            String time = sInfo.scheduleDate + " " + sInfo.scheduleTime;
            String summary = sInfo.scheduleName;

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");

            long curTime = System.currentTimeMillis();
            long scheduleTime = 0;
            try {
                Date date = format.parse(time);
                scheduleTime =date.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long remainTime = (scheduleTime - curTime)/1000/60;

            // past schedule info
            if(remainTime < 0)
            {
                continue;
            }
            else
            {
                timeList.add(time);
                summaryList.add(summary);
                time = sInfo.scheduleTime;
                tts += time + ", " + summary + ", ";
                cnt ++;
                if(cnt == 3)
                {
                    break;
                }
            }
        }
        return tts;
    }

    public String getBusInfoTTS(BusInfo bInfo)
    {
        if (bInfo == null)
        {
            return "";
        }

        String tts = "";
        String time = "";
        String bRoute = "";

        bRoute = bInfo.route.busRouteNm;
        if (bInfo.array_ttb.size() == 0)
        {
            time = " 운행종료, ";
        }
        else if (bInfo.array_ttb.size() == 1)
        {
            time = getRemainedBusTime(bInfo, 0);
        }
        else
        {
            time = getRemainedBusTime(bInfo, 0);
            if(time.equals("접근중, "))
            {
                time = getRemainedBusTime(bInfo, 1);
            }
        }
        //예시 : 문장없음 / 121번 버스 운행종료 / 121번 버스 4분전 / 121번 버스 접근중
        tts = bRoute + "번 버스으, " + time;
        return tts;
    }

    private String getRemainedBusTime(BusInfo binfo, int pos)
    {
        long curTime = System.currentTimeMillis();

        String time = binfo.array_ttb.get(pos).time;
        Date date = new Date();
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        try {
            date = date_format.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        long arriveTime = date.getTime();
        long arriveMinute = (arriveTime - curTime)/1000/60;
        if(arriveMinute == 0)
        {
            time = "접근중, ";
        }
        else
        {
            time = arriveMinute + "분 전, ";
        }

        return time;
    }

    public String getSubwayInfoTTS(SubwayInfo sInfo)
    {
        if(sInfo == null)
        {
            return "";
        }

        String tts = "";
        String time = "";
        String lineNum = sInfo.station.line_num;
        lineNum = GlobalFunction.SubwayDecode(lineNum);

        String station = sInfo.station.station_nm;
        String dir = "";

        if(sInfo.array_tts.size() == 0)
        {
            time = "운행종료, ";
        }
        else if(sInfo.array_tts.size() == 1)
        {
            time = getRemainedSubwayTime(sInfo, 0);
            dir = sInfo.array_tts.get(0).subway_end_name;
        }
        else
        {
            time = getRemainedSubwayTime(sInfo, 0);
            dir = sInfo.array_tts.get(0).subway_end_name;
            if(time.equals("접근중, "))
            {
                time = getRemainedSubwayTime(sInfo, 1);
                dir = sInfo.array_tts.get(1).subway_end_name;
            }
        }
        //예시 : 문장 없음 / 청량리역 1호선 용산행 운행종료 / 청량리역 1호선 용산행 3분전 / 청량리역 1호선 용산행 접근중
        tts = station + ", " + lineNum + ", " + dir + "행, " + time;
        return tts;
    }

    private String getRemainedSubwayTime(SubwayInfo sInfo, int pos)
    {
        Date curTime = new Date();
        SimpleDateFormat cur_format = new SimpleDateFormat("HH:mm", Locale.KOREA);
        try {
            curTime = cur_format.parse(cur_format.format(curTime));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        long tt = 0;
        try {
            tt = cur_format.parse(sInfo.array_tts.get(pos).arr_time).getTime() - curTime.getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        tt = tt/1000/60;
        String remain;
        if(tt == 0)
        {
            remain = "접근중, ";
        }
        else
        {
            remain = tt + "분 전, ";
        }

        return remain;
    }

    public String getWeatherInfoTTS(WeatherInfo wInfo)
    {
        if(wInfo == null)
        {
            return "";
        }

        String tts;
        String temper = wInfo.array_ttw.get(0).temp;
        String rainfallProp = wInfo.array_ttw.get(0).pop;

        //예시 : 현재 기온 23.9도, 강수확률 32 퍼센트
        tts = "현재 기온, " + temper + "도, " + "강수확률, " + rainfallProp + "퍼센트.";

        return tts;
    }

    public String getGasTemperatureTTS()
    {
        String tts = "가스불, 주변 온도, " + mTempdata + " 도, ";

        return tts;
    }

    public String makeTotalTTS()
    {
        String tts = "";

        String schedule = getScheduleInfoTTS(getScheduleInfo());
        String bus = getBusInfoTTS(getBusInfo());
        String subway = getSubwayInfoTTS(getSubwayInfo());
        String weather = getWeatherInfoTTS(getWeatherInfo());
        String gas = getGasTemperatureTTS();

        tts = gas + bus + subway + weather + " /// " + schedule;
        return tts;
    }

}
