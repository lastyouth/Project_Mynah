package com.seven.mynah;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.seven.mynah.artifacts.BusInfo;
import com.seven.mynah.artifacts.ScheduleInfo;
import com.seven.mynah.artifacts.SessionUserInfo;
import com.seven.mynah.artifacts.SubwayInfo;
import com.seven.mynah.artifacts.WeatherInfo;
import com.seven.mynah.artifacts.WeatherLocationInfo;
import com.seven.mynah.backgroundservice.GetInformationService;
import com.seven.mynah.calender.CalendarManager;
import com.seven.mynah.backgroundservice.RPiBluetoothConnectionManager;
import com.seven.mynah.database.DBManager;
import com.seven.mynah.globalmanager.GlobalFunction;
import com.seven.mynah.globalmanager.GlobalGoogleCalendarManager;
import com.seven.mynah.globalmanager.GlobalVariable;
import com.seven.mynah.globalmanager.ServiceAccessManager;
import com.seven.mynah.util.TransparentProgressDialog;

//Google Calendar


public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    public static final int SIGNAL_UI_UPDATE = 0x10001001;

    public class SendMassgeHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                switch (msg.what) {
                    case SIGNAL_UI_UPDATE:
                        Log.d(TAG, "UI UPDATE");
                        new doAllRefresh(MainActivity.this).execute();

                        break;
                    default:
                        break;
                }
            }catch(NullPointerException npe)
            {
                Log.d(TAG,"NullPointerException");
            }

        }
    };
    SendMassgeHandler mHandler = new SendMassgeHandler();
    GoogleCloudMessaging gcm;
    // for bk service connection
    GetInformationService infoService;
    boolean isServiceConnected = false;

    AtomicInteger msgId = new AtomicInteger();

    RPiBluetoothConnectionManager BTmanager;

    //GCM
    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "AIzaSyBo7pigCHSXysJD-qxKsE0H9YBGXmIvaVQ";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    //GCM project key
    private static final String SENDER_ID = "803082977332";
    //GCM 등록용 키(핸드폰 기준 1개)
    String regid;

    private CalendarManager mCalendarManager;

    private Context mContext;
    //By JS
    LinearLayout llSchedule, llBus, llSubway, llWeather, llRecord, llRefresh, llSetting, llProgressbar;

    //for Schedule
    private static int maxSchedules = 10;
    private static int maxPreparations = 10;
    private LinearLayout layoutSchedule;
    private LinearLayout layoutPreparation;
    private TextView tvSchedules[];
    private TextView tvPreparation;
    private CalendarManager calendarManager;
    private ArrayList<ScheduleInfo> scheduleInfo;
    private String today;

    //for Bus
    private ImageView ivBusImage;
    private TextView tvBusRoute;
    private TextView tvBusStopName;
    private TextView tvBusDirName;
    private TextView tvBusNextTime;
    private TextView tvBusNextTime2;
    private TextView tvCurrentBusRoute;
    private ArrayList<BusInfo> busArrayList;
    private String bRoute;
    private String bDir;
    private String bStation;
    private String time1;
    private String time2;

    //for Subway
    private ImageView ivSubwayImage;
    private TextView tvSubwayName;
    private TextView tvSubwayStopName;
    private TextView tvSubwayDirName;
    private TextView tvSubwayNextTime;
    private TextView tvSubwayDirName2;
    private TextView tvSubwayNextTime2;
    private ArrayList<SubwayInfo> subwayArrayList;


    //for Weather
    private ImageView ivWeatherImage;
    private TextView tvWeatherType;
    private TextView tvPlace;
    private TextView tvPlace2;
    private TextView tvTemper;
    private TextView tvReh; // 습도
    private TextView tvPop; // 강수확률
    private TextView tvUpdateTime;
    private TextView tvHour;
    private ArrayList<WeatherLocationInfo> weatherArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "MainActivity onCreate Start");

        SessionUserInfo suInfo = DBManager.getManager(getApplicationContext()).getSessionUserDB();
        Toast.makeText(getApplicationContext(), suInfo.userName + "님 환영합니다.", Toast.LENGTH_SHORT);

        ServiceAccessManager.getInstance().setMainPid(android.os.Process.myPid());

        Intent service = new Intent(this, GetInformationService.class);
        startService(service);

        mContext = getApplicationContext();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //By JS
        inflateButtonLayout();
        scheduleInitView();
        busInitView();
        subwayInitView();
        weatherInitView();


        ServiceAccessManager mServiceAccessManager = ServiceAccessManager.getInstance();
        mServiceAccessManager.setContext(this);
        mServiceAccessManager.prepareService();
        //mServiceAccessManager.

        SharedPreferences p = getSharedPreferences(ServiceAccessManager.TSTAT, MODE_PRIVATE);
        SharedPreferences.Editor ed = p.edit();
        int schedule = GlobalVariable.SCHEDULE;
        int bus = GlobalVariable.BUS;
        int subway = GlobalVariable.SUBWAY;
        int weather = GlobalVariable.WEATHER;

        int status = p.getInt("status", schedule | bus | subway | weather);
        ed.putInt("status", status);
        ed.commit();

        Log.d(TAG, "onCreate Finish");
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        Log.d(TAG, "onDestroy Start");
        super.onDestroy();
//        Toast.makeText(this,"MainActivity OnDestory",Toast.LENGTH_SHORT).show();

        //ScheduleInfo sinfo = infoService.getNumber();
        //infoService.setBindStatus(false);
        //unbindService(mBkServiceConnection);

        ServiceAccessManager.getInstance().releaseService();

        //BTmanager.stopBTConnection();
        Log.d(TAG, "onDestro.commit()");
    }

    public void startSettingActivity(String type)
    {
        String intentActionName = "com.seven.mynah.";
        intentActionName += type;
        Intent intent = new Intent(intentActionName);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

    public SendMassgeHandler getHandler()
    {
        return mHandler;
    }


    public void startBluetoothActivity_temp()
    {
        Intent intent = new Intent("com.seven.mynah.Bluetooth");
        //Intent intent = new Intent(this,DeviceListActivity.class);
        startActivity(intent);
        //this.overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    /**
     * Stores the registration ID and the app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param context application's context.
     * @param regId registration ID
     */
    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGcmPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    /**
     * Gets the current registration ID for application on GCM service, if there is one.
     * <p>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     *         registration ID.
     */
    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGcmPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.equals("")) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        Log.i(TAG, "Registration id : " + registrationId);
        return registrationId;
    }


    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(mContext);
                    }
                    regid = gcm.register(SENDER_ID);
                    msg = "Device registered, registration ID=" + regid;

                    // You should send the registration ID to your server over HTTP, so it
                    // can use GCM/HTTP or CCS to send messages to your app.
                    sendRegistrationIdToBackend();

                    // For this demo: we don't need to send it because the device will send
                    // upstream messages to a server that echo back the message using the
                    // 'from' address in the message.

                    // Persist the regID - no need to register again.
                    storeRegistrationId(mContext, regid);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                //mDisplay.append(msg + "\n");
            }
        }.execute(null, null, null);
    }

    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    /**
     * @return Application's {@code SharedPreferences}.
     */
    private SharedPreferences getGcmPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.
        return getSharedPreferences(MainActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }

    /**
     * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP or CCS to send
     * messages to your app. Not needed for this demo since the device sends upstream messages
     * to a server that echoes back the message using the 'from' address in the message.
     */
    private void sendRegistrationIdToBackend() {
        // Your implementation here.
    }



    @Override
    protected void onResume() {
        Log.d(TAG, "onResume Start");
        // TODO Auto-generated method stub
        super.onResume();
        boolean flag = ServiceAccessManager.getInstance().getDeleteFlag();
        Log.d(TAG,"OnResume flag : "+flag);
        if(flag)
        {
            Intent service = new Intent(this, GetInformationService.class);
            stopService(service);
            ServiceAccessManager.getInstance().setDeleteFlag(false);
            finish();
            return;
        }

        mCalendarManager = GlobalGoogleCalendarManager.calendarManager;
        if(mCalendarManager != null)
        {
            if(mCalendarManager.getCalendarCredential().getSelectedAccountName() != null)
            {
                mCalendarManager.asyncSchedule();
            }
        }

        if(ServiceAccessManager.getInstance().checkServiceConnected())
        {
            mHandler.sendEmptyMessage(SIGNAL_UI_UPDATE);
        }


        Log.d(TAG, "onResume Finish");
    }

    private void inflateButtonLayout()
    {
        llSchedule = (LinearLayout)findViewById(R.id.llSchedule);
        llBus = (LinearLayout)findViewById(R.id.llBus);
        llSubway = (LinearLayout)findViewById(R.id.llSubway);
        llWeather = (LinearLayout)findViewById(R.id.llWeather);
        llRecord = (LinearLayout)findViewById(R.id.llRecord);
        llRefresh = (LinearLayout)findViewById(R.id.llRefresh);
        llSetting = (LinearLayout)findViewById(R.id.llSetting);
        llProgressbar = (LinearLayout)findViewById(R.id.llProgressbar);

        llRefresh.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    llRefresh.setAlpha((float) 0.8);
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    llRefresh.setAlpha((float) 1.0);
                    //allRefresh();
                    new doAllRefresh(MainActivity.this).execute();
                    return true;
                }
                return true;
            }
        });

        llSetting.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    llSetting.setAlpha((float) 0.8);
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    llSetting.setAlpha((float) 1.0);
                    Intent intent = new Intent(mContext, GlobalSettingActivity.class);
                    startActivity(intent);
                    return true;
                }
                return true;
            }
        });

    }

    //SCHEDULE
    public void scheduleInitView()
    {
        layoutSchedule = (LinearLayout)findViewById(R.id.layoutSchedule);
        tvSchedules = new TextView[maxSchedules];
        tvPreparation = new TextView(mContext);

        calendarManager = GlobalGoogleCalendarManager.calendarManager;
        int tableCount = DBManager.getManager(mContext).getSchedulesCount();

        if(tableCount == 0)
        {
            layoutSchedule.removeAllViews();
            tvSchedules[0] = new TextView(mContext);
            tvSchedules[0].setTextColor(Color.parseColor("#ffffff"));
            tvSchedules[0].setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            tvSchedules[0].setText("터치하여 구글캘린더와 연동을 시작하세요.");

            layoutSchedule.setGravity(Gravity.CENTER);
            layoutSchedule.addView(tvSchedules[0]);
        }

        llSchedule.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    llSchedule.setAlpha((float) 0.8);
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    llSchedule.setAlpha((float) 1.0);
                    Intent intent = new Intent(mContext, CalendarActivity.class);
                    startActivity(intent);
                    return true;
                }
                return true;
            }
        });
    }

    public void scheduleRefresh()
    {
        //Request service to get schedule information (through Mynah DB)
        ArrayList<ScheduleInfo> scheduleInfos = ServiceAccessManager.getInstance().getService().getScheduleInfo();
        if(scheduleInfos == null)
        {
            scheduleInfos = new ArrayList<ScheduleInfo>();
        }
        setScheduleInfo(scheduleInfos);
    }

    public void setScheduleInfo(ArrayList<ScheduleInfo> scheduleInfos)
    {
        int size = scheduleInfos.size();
        layoutSchedule.removeAllViews();
        layoutSchedule.setGravity(Gravity.NO_GRAVITY);
        if(size == 0)
        {
            tvSchedules[0] = new TextView(mContext);
            tvSchedules[0].setTextColor(Color.parseColor("#ffffff"));
            tvSchedules[0].setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            tvSchedules[0].setText("등록된 스케줄이 없습니다.");

            layoutSchedule.setGravity(Gravity.CENTER);
            layoutSchedule.addView(tvSchedules[0]);
        }

        for(int i = 0; i < size; i++)
        {
            tvSchedules[i] = new TextView(mContext);
            tvSchedules[i].setTextColor(Color.parseColor("#ffffff"));
            tvSchedules[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            String str = scheduleInfos.get(i).scheduleTime + "    " + scheduleInfos.get(i).scheduleName;
            tvSchedules[i].setText(str);

            tvSchedules[0].setEllipsize(TextUtils.TruncateAt.MARQUEE);
            tvSchedules[0].setSelected(true);
            tvSchedules[0].setSingleLine();

            layoutSchedule.addView(tvSchedules[i]);
        }
    }

    //BUS
    private void busInitView() {

        ivBusImage = (ImageView)findViewById(R.id.ivBusImage);
        tvBusRoute = (TextView)findViewById(R.id.tvBusRoute);

        // (TextView)view.findViewById(R.id.tvCurrentBusRoute);
        tvBusStopName = (TextView)findViewById(R.id.tvBusStopName);
        tvBusNextTime = (TextView)findViewById(R.id.tvBusNextTime);
        tvBusNextTime2 = (TextView)findViewById(R.id.tvBusNextTime2);
        tvBusDirName = (TextView)findViewById(R.id.tvBusDirName);

        llBus.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    llBus.setAlpha((float) 0.8);
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    llBus.setAlpha((float) 1.0);

                    Intent intent = new Intent(mContext, BusSettingActivity.class);
                    startActivity(intent);

                    return true;
                }
                return true;
            }
        });
    }

    public void busRefresh() {
        //Request service to get bus Information
        BusInfo bInfo = ServiceAccessManager.getInstance().getService().getBusInfo();
        setBusInfo(bInfo);
    }

    private void setBusInfo(BusInfo binfo)  {
        if (binfo == null)
        {
            bRoute = "";
            bStation = "";
            bDir = "터치해서 정보를 입력하세요";
            time1 = "";
            time2 = "";
        }
        else
        {
            bRoute = binfo.route.busRouteNm + " 버스";
            bStation = binfo.station.stNm;
            bDir = binfo.dir + "행\n";

            if (binfo.array_ttb.size() == 0)
            {
                time1 = "차가 없음";
            }
            else if (binfo.array_ttb.size() == 1)
            {
                time1 = getBusArriveTime(binfo, 0);
            }
            else
            {
                time1 = getBusArriveTime(binfo, 0);
                time2 = getBusArriveTime(binfo, 1);
            }
        }

        ivBusImage.setImageResource(R.drawable.ic_bus);
        tvBusRoute.setText(bRoute);
        tvBusStopName.setText(bStation);
        tvBusStopName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tvBusStopName.setSelected(true);
        tvBusStopName.setSingleLine();
        tvBusDirName.setText(bDir);
        tvBusNextTime.setText(time1);
        tvBusNextTime2.setText(time2);
    }
    private String getBusArriveTime(BusInfo binfo, int pos)
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
            time = "접근중";
        }
        else
        {
            time = arriveMinute + "분 전";
        }
        return time;
    }

    //SUBWAY
    private void subwayInitView() {
        ivSubwayImage = (ImageView)findViewById(R.id.ivSubwayImage);
        tvSubwayName = (TextView)findViewById(R.id.tvSubwayName);
        tvSubwayStopName = (TextView)findViewById(R.id.tvSubwayStopName);
        tvSubwayDirName = (TextView)findViewById(R.id.tvSubwayDirName);
        tvSubwayNextTime = (TextView)findViewById(R.id.tvSubwayNextTime);
        tvSubwayDirName2 = (TextView)findViewById(R.id.tvSubwayDirName2);
        tvSubwayNextTime2 = (TextView)findViewById(R.id.tvSubwayNextTime2);

        ivSubwayImage.setImageResource(R.drawable.ic_subway);

        llSubway.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    llSubway.setAlpha((float) 0.8);
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    llSubway.setAlpha((float) 1.0);
                    Intent intent = new Intent(mContext, SubwaySettingActivity.class);
                    startActivity(intent);
                    return true;
                }
                return true;
            }
        });
    }

    public void subwayRefresh() {
        // Request service to get subway information
        SubwayInfo sInfo = ServiceAccessManager.getInstance().getService().getSubwayInfo();
        setSubwayInfo(sInfo);
    }

    private void setSubwayInfo(SubwayInfo sinfo) {
        if (sinfo == null) {
            // 초기화
            tvSubwayDirName.setText("터치해서 정보를 입력하세요");
            return;
        }
        String line_num = sinfo.station.line_num;
        tvSubwayName.setText(GlobalFunction.SubwayDecode(line_num));
        tvSubwayName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tvSubwayName.setSelected(true);
        tvSubwayName.setSingleLine();

        tvSubwayStopName.setText(sinfo.station.station_nm);
        tvSubwayStopName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tvSubwayStopName.setSelected(true);
        tvSubwayStopName.setSingleLine();

        Date curTime = new Date();
        SimpleDateFormat cur_format = new SimpleDateFormat("HH:mm", Locale.KOREA);

        try {
            curTime = cur_format.parse(cur_format.format(curTime));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        if (sinfo.array_tts.size() == 0)
        {
            tvSubwayNextTime.setText("");
            tvSubwayNextTime2.setText("");
        }
        else
        {
            long tt = 0;
            try {
                tt = cur_format.parse(sinfo.array_tts.get(0).arr_time).getTime() - curTime.getTime();
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            tt = tt/1000/60;
            if(tt >= 1000)
            {
                tvSubwayNextTime.setText("종착역입니다");
                tvSubwayDirName.setText(sinfo.array_tts.get(0).subway_end_name + "행");
                tvSubwayNextTime.setTextSize(12);
                tvSubwayDirName2.setText("");
                tvSubwayNextTime2.setText("");
                return;
            }
            String time1 = tt + "분 전";
            tvSubwayDirName.setText(sinfo.array_tts.get(0).subway_end_name + "행");
            tvSubwayNextTime.setText(time1);

            if (sinfo.array_tts.size() == 1)
            {
                tvSubwayNextTime2.setText("");
            }
            else
            {
                long tt2 = 0;
                try {
                    tt2 = cur_format.parse(sinfo.array_tts.get(1).arr_time).getTime() - curTime.getTime();
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                tt2 = tt2/1000/60;
                String time2 = tt2 + "분 전";
                tvSubwayDirName2.setText(sinfo.array_tts.get(1).subway_end_name + "행");
                tvSubwayNextTime2.setText(time2);

            }
        }
    }

    //WEATHER
    private void weatherInitView() {
        Log.d(TAG, "weatherInitView Start");

        ivWeatherImage = (ImageView)findViewById(R.id.ivWeatherImage);
        tvWeatherType = (TextView)findViewById(R.id.tvWeatherType);

        tvPlace = (TextView)findViewById(R.id.tvWeatherPlace);
        tvPlace2 = (TextView)findViewById(R.id.tvWeatherPlace2);
        tvTemper = (TextView)findViewById(R.id.tvWeatherTemper);

        tvPop = (TextView)findViewById(R.id.tvPop);

        setButtonsMarquee();

        llWeather.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    llWeather.setAlpha((float) 0.8);
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    llWeather.setAlpha((float) 1.0);
                    Intent intent = new Intent(mContext, WeatherSettingActivity.class);
                    startActivity(intent);
                    return true;
                }
                return true;
            }
        });

        Log.d(TAG, "weatherInitView End");
    }

    public void weatherRefresh() {
        Log.d(TAG, "weatherRefresh Start");

        // Request service to get weather Information
        WeatherInfo wInfo = ServiceAccessManager.getInstance().getService().getWeatherInfo();
        setWeatherInfo(wInfo);

        Log.d(TAG, "weatherRefresh End");
    }

    private void setWeatherInfo(WeatherInfo winfo) {
        Log.d(TAG, "setWeatherInfo Start");

        if (winfo == null) {
            // 초기화
            tvPlace2.setText("터치해서 정보를 입력하세요");
            setWeatherImage(5);
            return;
        }

        tvPlace.setText(winfo.location.city_name);
        tvPlace2.setText(winfo.location.mdl_name + "\n");
        tvTemper.setText(winfo.array_ttw.get(0).temp + " °C");
        tvPop.setText("강수확률 : " + winfo.array_ttw.get(0).pop + "%");
        tvWeatherType.setText(winfo.array_ttw.get(0).wfKor);

        // Set weather image type
        setWeatherImage(Integer.valueOf(winfo.array_ttw.get(0).sky));
        Log.d(TAG, "setWeatherInfo End");
    }

    private void setWeatherImage(int type) {

        switch (type) {
            case 1:
                ivWeatherImage.setImageResource(R.drawable.ic_sunny);
                break;
            case 2:
                //구름조금
                ivWeatherImage.setImageResource(R.drawable.ic_cloud2);
                break;

            case 3:
                //구름 많음
                ivWeatherImage.setImageResource(R.drawable.ic_cloud1);
                break;

            case 4:
                //흐림  /비
                ivWeatherImage.setImageResource(R.drawable.ic_cloud1);
                break;
            case 5:
                ivWeatherImage.setImageResource(R.drawable.ic_question);
        }
    }

    private void setButtonsMarquee()
    {
        tvPlace.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tvPlace.setSelected(true);
        tvPlace.setSingleLine();

        tvPlace2.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tvPlace2.setSelected(true);
        tvPlace2.setSingleLine();

        tvTemper.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tvTemper.setSelected(true);
        tvTemper.setSingleLine();

        tvPop.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tvPop.setSelected(true);
        tvPop.setSingleLine();
    }

    private void allRefresh()
    {
        scheduleRefresh();
        busRefresh();
        subwayRefresh();
        weatherRefresh();
    }

    class doAllRefresh extends AsyncTask<Void, Void, Void>{

        private Context mContext;
        private Boolean result = false;
        private TransparentProgressDialog progressDialog;
        //private ProgressDialog progressDialog;

        public doAllRefresh(Context context) {
            mContext = context;
            progressDialog = new TransparentProgressDialog(mContext);
            //progressDialog = new ProgressDialog(mContext, R.style.TransparentDialog);
        }

        @Override
        protected void onPreExecute() {
            //progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            //progressDialog.setMessage("로딩중입니다...");
            //progressDialog.setCancelable(false);
            //progressDialog.show();
            progressDialog = TransparentProgressDialog.show(mContext, "", ".", true, false, null);
        }

        @Override
        protected Void doInBackground(Void... params) {

            //TODO Test >> Do work like communication with SQLITE, API REQUEST
            try {
                Thread.sleep(500);
            }
            catch (InterruptedException e)
            {
                Log.d(TAG, e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //TODO Do just changing UI by data from doInBackground
            allRefresh();
            if(progressDialog.isShowing())
            {
                progressDialog.dismiss();
            }
        }
    }
}