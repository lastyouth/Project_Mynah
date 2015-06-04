package com.seven.mynah;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.seven.mynah.globalmanager.GlobalVariable;
import com.seven.mynah.network.AsyncHttpTask;

import android.provider.Settings.Secure;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by HJHOME on 2015-06-01.
 */
public class SignUpActivity extends Activity {

    private static final String TAG = "LoginActivity";

    GoogleCloudMessaging gcm;
    Context mContext;

    //GCM
    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "AIzaSyBo7pigCHSXysJD-qxKsE0H9YBGXmIvaVQ";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    //GCM project key
    private static final String SENDER_ID = "803082977332";
    //GCM 등록용 키(핸드폰 기준 1개)
    String regid;

    //device id
    String deviceID;

    //activity 관련
    Button btn;
    EditText etProductId;
    EditText etNewUserId;
    EditText etNewUserPassword;
    EditText etNewUserRePassword;
    EditText etNewUserName;
    Boolean productCheck;
    Boolean idDupCheck;

    //클래스 안에 선언해놓을 것
    protected Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            // IF Sucessfull no timeout

            //여기서는 이런식으로 what에 헨들링 넘버 넣어놨으니까 그거에 맞는 동작하면 됨.
            System.out.println("in handler");
            if (msg.what == -1) {
                //   BreakTimeout();
                //ConnectionError();
            }


            if (msg.what == 1) {
                //핸들링 1일때 할 것 product 존재 확인
                System.out.println("response : "+msg.obj);
                try{
                    JSONObject jobj = new JSONObject(msg.obj+"");
                    String messageType = jobj.get("messagetype") + "";
                    String result = jobj.get("result")+"";

                    System.out.println("MT : "+messageType);
                    System.out.println("RT : "+result);

                    if(messageType.equals("product_check")){
                        if(result.equals("PRODUCT_NOT_EXIST")){
                            Toast.makeText(getApplicationContext(), "Product Not Exist", Toast.LENGTH_SHORT).show();
                        }
                        else if(result.equals("PRODUCT_EXIST")) {
                            Toast.makeText(getApplicationContext(), "Product Exist", Toast.LENGTH_SHORT).show();
                            productCheck = true;
                        }
                        else if(result.equals("PRODUCT_ERROR")) {
                            Toast.makeText(getApplicationContext(), "Product Check Error", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Wrong Product Check Attempt", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Wrong Product Check Attempt", Toast.LENGTH_SHORT).show();
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }

            }

            if (msg.what == 2) {
                //핸들링 2일때 할 것 id 중복확인
                System.out.println("response : "+msg.obj);
                try{
                    JSONObject jobj = new JSONObject(msg.obj+"");
                    String messageType = jobj.get("messagetype") + "";
                    String result = jobj.get("result")+"";

                    System.out.println("MT : "+messageType);
                    System.out.println("RT : "+result);

                    if(messageType.equals("id_duplicate_check")){
                        if(result.equals("ID_DUPLICATE")){
                            Toast.makeText(getApplicationContext(), "id 중복여", Toast.LENGTH_SHORT).show();
                        }
                        else if(result.equals("ID_NOT_DUPLICATE")) {
                            Toast.makeText(getApplicationContext(), "id 안중복 써도 돼", Toast.LENGTH_SHORT).show();
                            idDupCheck = true;
                        }
                        else if(result.equals("ID_DUPLICATE_ERROR")) {
                            Toast.makeText(getApplicationContext(), "중복체크 에러", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Wrong ID Duplicate Check Attempt", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Wrong Id Duplicate Check Attempt", Toast.LENGTH_SHORT).show();
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }

            }

            if (msg.what == 3) {
                //핸들링 2일때 할 것 회원가입 서브밋
                System.out.println("response : "+msg.obj);
                try{
                    JSONObject jobj = new JSONObject(msg.obj+"");
                    String messageType = jobj.get("messagetype") + "";
                    String result = jobj.get("result")+"";

                    System.out.println("MT : "+messageType);
                    System.out.println("RT : "+result);

                    if(messageType.equals("signup")){
                        if(result.equals("SIGNUP_FAIL")){
                            Toast.makeText(getApplicationContext(), "회원가입 실패 에러", Toast.LENGTH_SHORT).show();
                        }
                        else if(result.equals("SIGNUP_SUCCESS")) {
                            Toast.makeText(getApplicationContext(), "회원가입 성공인듯", Toast.LENGTH_SHORT).show();
                            finish();
                            //idDupCheck = true;
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Wrong sign up Attempt", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Wrong sign up Attempt", Toast.LENGTH_SHORT).show();
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mContext = getApplicationContext();

        //Check device for Play Services APK. If check succeeds, proceed with GCM registration.
        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(this);
            regid = getRegistrationId(mContext);

            if (regid.equals("")) {
                registerInBackground();
            }
            Toast.makeText(this, "등록 id = " + regid, 1).show();
            Log.d(TAG,regid);

            //토스트에서 알려주자!
        } else {
            Log.i(TAG, "No valid Google Play Services APK found.");
        }

        gcm = GoogleCloudMessaging.getInstance(this);
        regid = getRegistrationId(mContext);

        if (regid.equals("")) {
            registerInBackground();
        }
        Toast.makeText(this, "등록 id = " + regid, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "REG ID : " + regid);

        //device id 받아오기
        deviceID = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);
        Log.d(TAG, "DEVICE ID : " + deviceID);


        //activity 관련
        etProductId = (EditText)findViewById(R.id.etProductId);
        etNewUserId = (EditText)findViewById(R.id.etNewUserId);
        etNewUserPassword = (EditText)findViewById(R.id.etNewUserPassword);
        etNewUserRePassword = (EditText)findViewById(R.id.etNewUserRePassword);
        etNewUserName = (EditText)findViewById(R.id.etNewUserName);

        productCheck = false; //기계 존재유무 확인
        idDupCheck = false; //id 중복 확인

        //라즈베리파이 id 인증 버튼
        btn = (Button) findViewById(R.id.btnProductPermission);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String strProductId = etProductId.getText() + "";

                JSONObject jobj = new JSONObject();

                try{
                    jobj.put("messagetype", "product_check");
                    jobj.put("product_id", strProductId);
                }
                catch(JSONException e){
                    e.printStackTrace();
                }
                new AsyncHttpTask(getApplicationContext(), GlobalVariable.WEB_SERVER_IP, mHandler, jobj, 1, 0);
            }
        });

        //아이디 중복 검사 버튼
        btn = (Button) findViewById(R.id.btnIdDupCheck);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String strNewUserId = etNewUserId.getText() + "";

                JSONObject jobj = new JSONObject();

                try{
                    jobj.put("messagetype", "id_duplicate_check");
                    jobj.put("user_id", strNewUserId);
                }
                catch(JSONException e){
                    e.printStackTrace();
                }
                new AsyncHttpTask(getApplicationContext(), GlobalVariable.WEB_SERVER_IP, mHandler, jobj, 2, 1);
            }
        });

        //회원가입 커밋
        btn = (Button) findViewById(R.id.btnNewSignup);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String strProductId = etProductId.getText() + "";
                final String strNewUserId = etNewUserId.getText() + "";
                final String strNewUserPassword = etNewUserPassword.getText() + "";
                final String strNewUserRePassword = etNewUserRePassword.getText() + "";
                final String strNewUserName = etNewUserName.getText()+"";
                final String strRegId = regid;
                final String strDeviceId = deviceID;
                final Boolean isMale = true;
                final Boolean isRepresentative = true;
                final Boolean isInHome = true;

                if(!productCheck){
                    Toast.makeText(getApplicationContext(), "기계 확인 안됬어", 1).show();
                }
                else if(!idDupCheck){
                    Toast.makeText(getApplicationContext(), "id 중복확인 안됬어", 1).show();
                }
                else if(!strNewUserPassword.equals(strNewUserRePassword)){
                    Toast.makeText(getApplicationContext(), "비밀번호 같은걸로 입력해야해", 1).show();
                }
                else {
                    JSONObject jobj = new JSONObject();

                    try {
                        jobj.put("messagetype", "signup");
                        jobj.put("user_id", strNewUserId);
                        jobj.put("product_id", strProductId);
                        jobj.put("registration_id", strRegId);
                        jobj.put("user_name", strNewUserName);
                        jobj.put("gender_flag", isMale);
                        jobj.put("representative_flag", isRepresentative);
                        jobj.put("in_home_flag", isInHome);
                        jobj.put("device_id", strDeviceId);
                        jobj.put("password", strNewUserPassword);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    new AsyncHttpTask(getApplicationContext(), GlobalVariable.WEB_SERVER_IP, mHandler, jobj, 3, 2);
                }
            }
        });
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
}
