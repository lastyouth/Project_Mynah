package com.seven.mynah.backgroundservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.seven.mynah.artifacts.SessionUserInfo;
import com.seven.mynah.database.DBManager;
import com.seven.mynah.globalmanager.GlobalVariable;
import com.seven.mynah.globalmanager.RECManager;
import com.seven.mynah.globalmanager.TTSManager;
import com.seven.mynah.network.AsyncHttpUpload;
import com.seven.mynah.summarize.InfoTextSummarizer;

public class AlarmReceiver extends BroadcastReceiver {

    public static String ACTION_ALARM = "com.seven.mynah.backgroundservice.AlarmReceiver";

    private TTSManager mTTSManager;
    private Context mCtx;

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
    public void onReceive(Context context, Intent intent) {
        // For our recurring task, we'll just display a message

        Log.i("Alarm Receiver", "Entered");
        Toast.makeText(context, "Entered", Toast.LENGTH_SHORT).show();
        mCtx = context;

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
            mTTSManager.saveTTS(tts, suInfo.userId + "_tts.mp4");
            RECManager.getInstance().startPlaying(suInfo.userId + "_tts.mp4");
            //Toast.makeText(mCtx, "Task : tts 생성 period 성공", Toast.LENGTH_SHORT).show();
            //필요하다면 전송
            new AsyncHttpUpload(mCtx.getApplicationContext(), GlobalVariable.WEB_SERVER_IP, mhHandler,
                    RECManager.getInstance().getDefaultExStoragePath() + "tts.mp3", 1, AsyncHttpUpload.TYPE_TTS);
        }
        else
        {
            //Toast.makeText(mCtx, "Task : 동일 tts 확인 미전송 시퀸스", Toast.LENGTH_SHORT).show();
        }
    }


}