package com.seven.mynah;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.seven.mynah.adapter.SettingTTSAdapter;
import com.seven.mynah.adapter.ViewHolder;
import com.seven.mynah.database.DBManager;
import com.seven.mynah.globalmanager.GlobalVariable;
import com.seven.mynah.globalmanager.ServiceAccessManager;
import com.seven.mynah.network.AsyncHttpTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by KimJS on 2015-06-11.
 */
public class ChooseTTSActivity extends Activity {

    private boolean mIsBackKeyPressed = false;
    private ArrayList<String[]> mArrayList;
    private ListView lvSettingTTS;
    private SettingTTSAdapter mAdapter;

    private int SCHEDULE = GlobalVariable.SCHEDULE;
    private int BUS = GlobalVariable.BUS;
    private int SUBWAY= GlobalVariable.SUBWAY;
    private int WEATHER = GlobalVariable.WEATHER;
    private int RECORD = GlobalVariable.RECORD;
    private int ttsList[] = {SCHEDULE, BUS, SUBWAY, WEATHER,RECORD};
    private int ttsStatus;
    private SharedPreferences p;

    protected Handler mhHandler = new Handler() {
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

            }

            if (msg.what == 2) {
                //핸들링 2일때 할 것
                System.out.println("handling 2 !");
                System.out.println("response : "+msg.obj);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_tts);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        mArrayList = new ArrayList<String[]>();

        p = getSharedPreferences(ServiceAccessManager.TSTAT, MODE_PRIVATE);
        SharedPreferences.Editor ed = p.edit();
        ttsStatus = p.getInt("status", 31);
        String sList[] = {"일정", "버스", "지하철", "날씨","녹음"};

        for(int i = 0; i < sList.length; i++)
        {
            int s = ttsStatus & ttsList[i];
            String flag;
            if(s == 0)
            {
                flag = "false";
            }
            else
            {
                flag = "true";
            }

            String tmp[] = {sList[i], flag};
            mArrayList.add(tmp);
        }

        mAdapter = new SettingTTSAdapter(getApplicationContext(), R.layout.list_row_setting_tts, mArrayList);
        lvSettingTTS = (ListView)findViewById(R.id.lvSettingTTS);
        lvSettingTTS.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        lvSettingTTS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ViewHolder vh = (ViewHolder)view.getTag();
                CheckBox cb = vh.cbSettingTTS;
                ttsStatus = p.getInt("status", 15);
                Log.d("TTSActivity","Before Bit Value : " +ttsStatus);
                boolean status = cb.isChecked();
                Log.d("TTSActivity","Position : "+position+" Value : "+status);
                if(!status)
                {
                    ttsStatus = ttsStatus | (ttsList[position]);
                }
                else
                {
                    ttsStatus = ttsStatus & (~ttsList[position]);
                }
                Log.d("TTSActivity","After Bit Value : " +ttsStatus);
                SharedPreferences.Editor ed = p.edit();
                ed.putInt("status", ttsStatus);
                ed.commit();
                cb.setChecked(!status);
            }
        });
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
            jobj.put("user_id", DBManager.getManager(this).getSessionUserDB().deviceId);
            jobj.put("tts",ttsFlag);
            jobj.put("rec",recFlag);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //handler type 2 : 상태 업데이트용
        new AsyncHttpTask(getApplicationContext(), GlobalVariable.WEB_SERVER_IP, mhHandler, jobj, 2, 0);




    }

    @Override
    public void finish() {
        super.finish();

        updateStatus();
        //overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }
}
