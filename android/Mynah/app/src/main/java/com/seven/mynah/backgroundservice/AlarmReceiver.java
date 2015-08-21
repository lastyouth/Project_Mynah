package com.seven.mynah.backgroundservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.seven.mynah.artifacts.SessionUserInfo;
import com.seven.mynah.database.DBManager;
import com.seven.mynah.globalmanager.RECManager;
import com.seven.mynah.globalmanager.TTSManager;
import com.seven.mynah.summarize.InfoTextSummarizer;

public class AlarmReceiver extends BroadcastReceiver {

    public static String ACTION_ALARM = "com.seven.mynah.backgroundservice.AlarmReceiver";

    private TTSManager mTTSManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        // For our recurring task, we'll just display a message

        Log.i("Alarm Receiver", "Entered");
        Toast.makeText(context, "Entered", Toast.LENGTH_SHORT).show();

        Bundle bundle = intent.getExtras();
        String action = bundle.getString(ACTION_ALARM);
        if (action.equals(ACTION_ALARM)) {
            Log.i("Alarm Receiver", "If loop");

            Task(context);
        }
        else
        {
            Log.i("Alarm Receiver", "Else loop");
            Toast.makeText(context, "Else loop", Toast.LENGTH_SHORT).show();
        }

    }

    private void Task(Context mCtx)
    {
        String tts = InfoTextSummarizer.getInstance(mCtx).makeTotalTTS();
        if(InfoTextSummarizer.getInstance(mCtx).isUpdate())
        {
            SessionUserInfo suInfo = DBManager.getManager(mCtx.getApplicationContext()).getSessionUserDB();
            mTTSManager.saveTTS(tts,suInfo.userId + "_tts.mp4");
            RECManager.getInstance().startPlaying(suInfo.userId + "_tts.mp4");
            Toast.makeText(mCtx, "Task : tts 생성 period 성공", Toast.LENGTH_SHORT).show();
            //필요하다면 전송
            //new AsyncHttpUpload(this,)
        }
        else
        {
            Toast.makeText(mCtx, "Task : 동일 tts 확인 미전송 시퀸스", Toast.LENGTH_SHORT).show();
        }
    }


}