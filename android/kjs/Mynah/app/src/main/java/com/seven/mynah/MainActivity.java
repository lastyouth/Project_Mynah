package com.seven.mynah;


import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.seven.mynah.artifacts.SessionUserInfo;
import com.seven.mynah.backgroundservice.GetInformationService;
import com.seven.mynah.calender.CalendarManager;
import com.seven.mynah.custominterface.CustomButtonsFragment;
import com.seven.mynah.backgroundservice.RPiBluetoothConnectionManager;
import com.seven.mynah.database.DBManager;
import com.seven.mynah.globalmanager.GlobalFunction;
import com.seven.mynah.globalmanager.GlobalGoogleCalendarManager;
import com.seven.mynah.globalmanager.GlobalVariable;
import com.seven.mynah.globalmanager.ServiceAccessManager;

//Google Calendar


public class MainActivity extends Activity {

    private CustomButtonsFragment cbf;
    private View view;

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
                        cbf.refresh("Bus");
                        cbf.refresh("Subway");
                        cbf.refresh("Weather");
                        cbf.refresh("Schedule");
                        cbf.refresh("Gas");
                        cbf.refresh("Family");
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
    Context mContext;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "MainActivity onCreate Start");

        SessionUserInfo suInfo = DBManager.getManager(getApplicationContext()).getSessionUserDB();
        Toast.makeText(getApplicationContext(), suInfo.userName + "님 환영합니다.", Toast.LENGTH_SHORT);

        ServiceAccessManager.getInstance().setMainPid(android.os.Process.myPid());

        Intent service = new Intent(this, GetInformationService.class);
        startService(service);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();

        if (savedInstanceState == null) {
            setDefaultFragment();
        }
        //Intent t = new Intent(this, GetInformationService.class);
        //bindService(t,mBkServiceConnection,Context.BIND_AUTO_CREATE);

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
        int gas = GlobalVariable.GAS;

        int status = p.getInt("status", schedule | bus | subway | weather | gas);
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
        Log.d(TAG, "onDestroy Start");
    }

    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        cbf = new CustomButtonsFragment();
        transaction.add(R.id.container, cbf);
        transaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
            mCalendarManager.asyncSchedule();
        }

        if(ServiceAccessManager.getInstance().checkServiceConnected())
        {
            mHandler.sendEmptyMessage(SIGNAL_UI_UPDATE);
        }


        Log.d(TAG, "onResume Finish");
    }
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        calendarManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }*/
}