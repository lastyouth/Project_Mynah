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

    Button btn;
    EditText etUserId;
    EditText etUserPassword;

    //클래스 안에 선언해놓을 것
    protected Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            // IF Sucessfull no timeout

            //시발아
            //여기서는 이런식으로 what에 헨들링 넘버 넣어놨으니까 그거에 맞는 동작하면 됨.
            System.out.println("in handler");
            if (msg.what == -1) {
                //   BreakTimeout();
                //ConnectionError();
            }


            if (msg.what == 1) {
                //핸들링 1일때 할 것
                System.out.println("response : "+msg.obj);
                try{
                    JSONObject jobj = new JSONObject(msg.obj+"");
                    String messageType = jobj.get("messagetype") + "";
                    String result = jobj.get("result")+"";

                    System.out.println("MT : "+messageType);
                    System.out.println("RT : "+result);

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


                           Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
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

            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUserId = (EditText) findViewById(R.id.etUserId);

        etUserPassword = (EditText) findViewById(R.id.etUserPassword);

        //new AsyncHttpTask(this, "192.168.35.75", mHandler, jobj, 1, 0);

        //세션 데이터 지우는 부분 들어가야 할 것 같아

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
               // startActivity(intent);
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
