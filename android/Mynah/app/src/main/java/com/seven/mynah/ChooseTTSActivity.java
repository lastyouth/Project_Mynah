package com.seven.mynah;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.seven.mynah.adapter.SettingTTSAdapter;
import com.seven.mynah.adapter.ViewHolder;
import com.seven.mynah.globalmanager.GlobalVariable;
import com.seven.mynah.globalmanager.ServiceAccessManager;

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
    private int ttsList[] = {SCHEDULE, BUS, SUBWAY, WEATHER};
    private int ttsStatus;
    private SharedPreferences p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_tts);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        mArrayList = new ArrayList<String[]>();

        p = getSharedPreferences(ServiceAccessManager.TSTAT, MODE_PRIVATE);
        SharedPreferences.Editor ed = p.edit();
        ttsStatus = p.getInt("status", 15);
        String sList[] = {"일정", "버스", "지하철", "날씨"};

        for(int i = 0; i < 4; i++)
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

    @Override
    public void finish() {
        super.finish();

        //overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
