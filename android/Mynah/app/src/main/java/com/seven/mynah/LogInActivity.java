package com.seven.mynah;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.seven.mynah.artifacts.SessionUserInfo;
import com.seven.mynah.database.DBManager;
import com.seven.mynah.globalmanager.GlobalVariable;
import com.seven.mynah.network.AsyncHttpTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by HJHOME on 2015-06-01.
 */
public class LogInActivity extends Activity{

    private Button btn;
    private EditText etUserId;
    private EditText etUserPassword;


    //클래스 안에 선언해놓을 것
    protected Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            // IF Sucessfull no timeout
            System.out.println("in handler");
            if (msg.what == -1) {
                //   BreakTimeout();
                //ConnectionError();
                System.out.println("handler error");
            }

            if (msg.what == 1) {
                //핸들링 1일때 할 것
                System.out.println("response : "+msg.obj);
                try{
                    JSONObject jobj = new JSONObject(msg.obj+"");
                    String messageType = jobj.get("messagetype") + "";
                    String result = jobj.get("result")+"";
                    String attach = jobj.get("attach")+"";

                    System.out.println("MT : "+messageType);
                    System.out.println("RT : "+result);
                    System.out.println("AT : "+attach);

                    if(messageType.equals("login")){
                        if(result.equals("LOGIN_FAIL_ID")){
                            Toast.makeText(getApplicationContext(), "No ID", Toast.LENGTH_SHORT).show();
                        }
                        else if(result.equals("LOGIN_FAIL_PASSWD")) {
                            Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_SHORT).show();
                        }
                        else if(result.equals("LOGIN_SUCCESS")) {
                            Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_SHORT).show();

                            //성공했으니 서버로부터 계정 정보 받아와서
                            //내부 세션 유지 테이블에 insert 해야될거같애여

                            final String strUserId = etUserId.getText() + "";

                            JSONObject jobj2 = new JSONObject();

                            try {
                                jobj2.put("messagetype", "get_user_info");
                                jobj2.put("user_id", strUserId);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            new AsyncHttpTask(getApplicationContext(), GlobalVariable.WEB_SERVER_IP, mHandler, jobj2, 2, 1);

                            //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            //startActivity(intent);
                            //finish();
                        }
                        else if(result.equals("LOGIN_ERROR")) {
                            Toast.makeText(getApplicationContext(), "Login Error", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Wrong Login Attempt", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Wrong Login Attempt", Toast.LENGTH_SHORT).show();
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }

            if (msg.what == 2) {
                //핸들링 2일때 할 것
                System.out.println("handling 2 !");
                System.out.println("response : "+msg.obj);

                try {
                    JSONObject jobj = new JSONObject(msg.obj + "");
                    String messageType = jobj.get("messagetype") + "";
                    String result = jobj.get("result") + "";
                    String attach = jobj.get("attach") + "";
                    System.out.println("MT : " + messageType);
                    System.out.println("RT : " + result);
                    System.out.println("AT : " + attach);

                    if (messageType.equals("get_user_info")) {
                        if (result.equals("GET_USER_INFO_FAIL")) {
                            Toast.makeText(getApplicationContext(), "Get user info Fail", Toast.LENGTH_SHORT).show();
                        }
                        else if(result.equals("GET_USER_INFO_SUCCESS")){
                            //System.out.println("ja sal gak");
                            Toast.makeText(getApplicationContext(), "Get user info success", Toast.LENGTH_SHORT).show();

                            //user info 받은거 세션 테이블로 집어넣기 ㄱㄱ
                            JSONObject user_jobj = new JSONObject(jobj.get("attach")+"");

                            SessionUserInfo suinfo = new SessionUserInfo();
                            suinfo.userId = user_jobj.get("user_id")+"";
                            suinfo.productId = user_jobj.get("product_id")+"";
                            suinfo.registrationId = user_jobj.get("registration_id")+"";
                            suinfo.userName = user_jobj.get("user_name")+"";
                            suinfo.genderFlag = user_jobj.get("gender_flag")+"";
                            suinfo.representativeFlag = user_jobj.get("representative_flag")+"";
                            suinfo.inHomeFlag = user_jobj.get("in_home_flag")+"";
                            suinfo.deviceId = user_jobj.get("device_id")+"";
                            suinfo.inoutTime = ((user_jobj.get("inout_time")+"").replace('Z', ' ')).replace('T', ' ');

                            DBManager.getManager(getApplicationContext()).setSessionUserDB(suinfo);
                            System.out.println("세션 저장 성공");

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else if(result.equals("GET_USER_INFO_ERROR")){
                            Toast.makeText(getApplicationContext(), "Get user info Error", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Wrong get user info", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Wrong get user info", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(JSONException e){
                    e.printStackTrace();
                }
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_login);
        etUserId = (EditText) findViewById(R.id.etUserId);

        etUserPassword = (EditText) findViewById(R.id.etUserPassword);

        //세션 데이터 지우는 부분 들어가야 할 것 같아
        DBManager.getManager(getApplicationContext()).deleteSessionUser();
        System.out.println("세션 비워야지");
        //login button listener
        btn = (Button) findViewById(R.id.btnLogin);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                final String strUserId = etUserId.getText() + "";
                final String strUserPassword = etUserPassword.getText() + "";

                JSONObject jobj = new JSONObject();

                try {
                    jobj.put("messagetype", "login");
                    jobj.put("user_id", strUserId);
                    jobj.put("password", strUserPassword);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //AsyncHttpTask aht = new AsyncHttpTask("https://192.168.35.75",jobj);

                new AsyncHttpTask(getApplicationContext(), GlobalVariable.WEB_SERVER_IP, mHandler, jobj, 1, 0);

                //aht.execute();
                //startActivity(intent);
                //finish();
            }
        });

        //signup button listener
        btn = (Button) findViewById(R.id.btnSignup);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

}
