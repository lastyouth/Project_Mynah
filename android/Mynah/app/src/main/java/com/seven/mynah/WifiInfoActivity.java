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
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.seven.mynah.artifacts.SessionUserInfo;
import com.seven.mynah.database.DBManager;
import com.seven.mynah.globalmanager.GlobalVariable;
import com.seven.mynah.globalmanager.ServiceAccessManager;
import com.seven.mynah.network.AsyncHttpTask;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

public class WifiInfoActivity extends Activity {

    private static final String TAG = "WifiInfoActivity";

    private final long	FINSH_INTERVAL_TIME    = 2000;
    private long		backPressedTime        = 0;

    private Context mContext;

    //activity 관련

    private EditText etSSIDName;
    private EditText etSSIDPasswd;
    private TextView tvWifiNow;
    private Button btnWifiSave;

    private SharedPreferences p;


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
            }

            if (msg.what == 2) {
                //2일 떄 하는 일
                //핸들링 2일때 할 것
                System.out.println("handling 2 !");
                System.out.println("response : "+msg.obj);


            }

            if (msg.what == 3) {
                //핸들링 2일때 할 것 회원가입 서브밋
                System.out.println("response : "+msg.obj);

            }
        }
    };

    private void initWifiInfo()
    {
        String wifi_name = "";
        String wifi_passwd = "";
        String temp = "";

        etSSIDName = (EditText)findViewById(R.id.etSSIDName);
        etSSIDPasswd = (EditText)findViewById(R.id.etSSIDPasswd);

        btnWifiSave = (Button)findViewById(R.id.btnWifiSave);
        tvWifiNow = (TextView)findViewById(R.id.tvWifiNow);

        p = getSharedPreferences(ServiceAccessManager.WIFISTAT, MODE_PRIVATE);
        SharedPreferences.Editor ed = p.edit();

        wifi_name = p.getString("wifi_name","");
        wifi_passwd = p.getString("wifi_passwd","");


        if(!wifi_name.equalsIgnoreCase("")) {

            temp = "SSID : " + wifi_name;

            if(!wifi_passwd.equalsIgnoreCase("")) {
                temp += ", PASSWORD : " + wifi_passwd;
            }
        }

        if(temp.equalsIgnoreCase(""))
        {
            temp = "SSID와 PASSWD을 입력해주세요.";
        }
        tvWifiNow.setText(temp);


        btnWifiSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if(etSSIDName.equ)
                String ssid_name = etSSIDName.getText() + "";
                String ssid_passwd = etSSIDPasswd.getText() + "";

                setWifiInfo(ssid_name, ssid_passwd);

                //TODO 블루트스 통신이 되는지 확인하는 로직 필요
                sendWifiInfo();


                //TODO 이게 설정에서 오는 건지 아니면 애초에 초기 등록할 때 부분인지 확인할 것
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);



            }
        });

    }

    //TODO 블루투스 디바이스로 와이파이 전송용
    public void sendWifiInfo()
    {

    }

    private void setWifiInfo(String wifi_name, String wifi_passwd)
    {
        SharedPreferences.Editor ed = p.edit();
        ed.putString("wifi_name", wifi_name);
        ed.putString("wifi_passwd", wifi_passwd);
        ed.commit();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_info);

        mContext = getApplicationContext();

        initWifiInfo();

    }




    @Override
    public void onBackPressed() {
        long tempTime        = System.currentTimeMillis();
        long intervalTime    = tempTime - backPressedTime;

        if ( 0 <= intervalTime && FINSH_INTERVAL_TIME >= intervalTime ) {
            super.onBackPressed();
            //TODO 여기 프로세스 확인해놓을 것!
        }
        else {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(),"등록하지 않으면 사용하실 수 없습니다.\n" +
                    "'뒤로'버튼을 한번 더 누르시면 종료됩니다.",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
        //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
