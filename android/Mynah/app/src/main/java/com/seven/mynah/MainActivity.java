package com.seven.mynah;


import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.seven.mynah.artifacts.BusInfo;
import com.seven.mynah.artifacts.ScheduleInfo;
import com.seven.mynah.artifacts.SessionUserInfo;
import com.seven.mynah.artifacts.SubwayInfo;
import com.seven.mynah.artifacts.TimeToBus;
import com.seven.mynah.artifacts.WeatherInfo;
import com.seven.mynah.artifacts.WeatherLocationInfo;
import com.seven.mynah.backgroundservice.GetInformationService;
import com.seven.mynah.calender.CalendarManager;
import com.seven.mynah.backgroundservice.RPiBluetoothConnectionManager;
import com.seven.mynah.database.DBManager;
import com.seven.mynah.globalmanager.GlobalGoogleCalendarManager;
import com.seven.mynah.globalmanager.GlobalVariable;
import com.seven.mynah.globalmanager.RECManager;
import com.seven.mynah.globalmanager.ServiceAccessManager;
import com.seven.mynah.globalmanager.TTSManager;
import com.seven.mynah.network.AsyncHttpTask;
import com.seven.mynah.network.AsyncHttpUpload;
import com.seven.mynah.summarize.InfoTextSummarizer;
import com.seven.mynah.util.DebugToast;
import com.seven.mynah.util.TransparentProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

//Google Calendar


public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    public static final int SIGNAL_UI_UPDATE = 0x10001001;

    GoogleCloudMessaging gcm;

    // for bk service connection
    GetInformationService infoService;
    boolean isServiceConnected = false;

    AtomicInteger msgId = new AtomicInteger();

    RPiBluetoothConnectionManager BTmanager;

    private BroadcastReceiver mReceiver = null;

    //GCM
    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "AIzaSyBo7pigCHSXysJD-qxKsE0H9YBGXmIvaVQ";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    //GCM project key
    private static final String SENDER_ID = "803082977332";

    //GCM 등록용 키(핸드폰 기준 1개)
    private String regid;

    public CustomLayoutSet layout;

    private CalendarManager mCalendarManager;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "MainActivity onCreate Start");

        mContext = getApplicationContext();

        SessionUserInfo suInfo = DBManager.getManager(getApplicationContext()).getSessionUserDB();
        Toast.makeText(getApplicationContext(), suInfo.userName + "님 환영합니다.", Toast.LENGTH_SHORT);
        

        ServiceAccessManager.getInstance().setMainPid(android.os.Process.myPid());

        Intent service = new Intent(this, GetInformationService.class);
        startService(service);



        requestWindowFeature(Window.FEATURE_NO_TITLE);

        layout = new CustomLayoutSet(this,MainActivity.this);
        layout.setAnimationDuration(400);
        layout.setInterpolator(new AccelerateDecelerateInterpolator());
        setContentView(layout);

        //setContentView(R.layout.activity_main);

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
        int record = GlobalVariable.RECORD;

        int status = p.getInt("status", schedule | bus | subway | weather | record);
        ed.putInt("status", status);
        ed.commit();

        updateStatus();

        Log.d(TAG, "onCreate Finish");
    }


    private void updateStatus()
    {

        SharedPreferences p = getSharedPreferences(ServiceAccessManager.TSTAT, MODE_PRIVATE);
        SharedPreferences.Editor ed = p.edit();
        int schedule = GlobalVariable.SCHEDULE;
        int bus = GlobalVariable.BUS;
        int subway = GlobalVariable.SUBWAY;
        int weather = GlobalVariable.WEATHER;
        int record = GlobalVariable.RECORD;

        int ttsStatus = p.getInt("status", schedule | bus | subway | weather | record);
        int ttsList[] = {schedule, bus, subway, weather, record};
        int ttsFlag = 0;
        int recFlag = 0;
        for(int i = 0; i < 4; i++)
        {
            int s = ttsStatus & ttsList[i];
            if(s != 0 )
            {
                ttsFlag = 1;
                break;
            }
        }
        int s = ttsStatus & ttsList[4];
        if( s != 0) recFlag = 1;

        JSONObject jobj = new JSONObject();

        try {
            jobj.put("messagetype", "update_status");
            jobj.put("user_id",DBManager.getManager(this).getSessionUserDB().deviceId);
            jobj.put("tts",ttsFlag);
            jobj.put("rec",recFlag);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //handler type 2 : 상태 업데이트용
        new AsyncHttpTask(getApplicationContext(), GlobalVariable.WEB_SERVER_IP, layout.getHandler(), jobj, 2, 0);

    }


    @Override
    protected void onRestart()
    {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {

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

    private void registerReceiver() {

        if(mReceiver != null)
            return;

        final IntentFilter theFilter = new IntentFilter();
        theFilter.addAction(GlobalVariable.BROADCAST_MESSAGE);
        this.mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //int strNewPostion = intent.getIntExtra("value", 0);
                if (intent.getAction().equals(GlobalVariable.BROADCAST_MESSAGE)) {
                    if(layout == null) return;
                    layout.changeStatusText();
                }
            }
        };
        this.registerReceiver(this.mReceiver, theFilter);
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
            layout.getHandler().sendEmptyMessage(SIGNAL_UI_UPDATE);
            //mHandler.sendEmptyMessage(SIGNAL_UI_UPDATE);
        }

        registerReceiver();

        Log.d(TAG, "onResume Finish");
    }

    public CustomLayoutSet.SendMassgeHandler getHandler()
    {
        return layout.getHandler();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        if(hasFocus){
            layout.startAnimationOnFocusChanged();
        }
    }

}