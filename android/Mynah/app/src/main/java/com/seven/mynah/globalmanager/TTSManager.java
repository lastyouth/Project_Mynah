package com.seven.mynah.globalmanager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.util.Log;


import com.seven.mynah.artifacts.BusInfo;
import com.seven.mynah.database.DBManager;

public class TTSManager implements TextToSpeech.OnInitListener {


	private String TAG = "TTSManager";
	public static final int STOP = 1;
	//public static final int REC_ING = 2;
	public static final int PLAY_ING = 4;

	private Context mContext;
	private TextToSpeech tts;
	private String defaultExStoragePath;
	private MediaPlayer player = null;

	private int flag_state = STOP;

	public TTSManager(Context context) {
		// TODO Auto-generated constructor stub

		mContext = context;
		defaultExStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/mynah/";

		tts = new TextToSpeech(mContext,this);

	}

	@Override
	public void onInit(int status) {

		if (status == TextToSpeech.SUCCESS) {

			int result = tts.setLanguage(Locale.KOREA);

			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.d("TTS", "This Language is not supported");
			} else {

				//pitch는 음높이
				tts.setPitch((float)1.1);
				//rate는 말의 빠르기
				tts.setSpeechRate((float)1.3);
			}

		} else {
			Log.d("TTS", "Initilization Failed!");
		}

	}

	public void speakOut(String text)
	{
		if(tts == null)
		{
			return;
		}
		else
		{
			tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
		}
	}

	public void saveTTS(String text,String filename)
	{
		//유저 네임
		//default 유저 저장용 음성 내용

		HashMap<String, String> myHashRender = new HashMap<String, String>();
		myHashRender.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, text);

		File appTmpPath = new File(defaultExStoragePath);
		appTmpPath.mkdirs();

		//user name id로 파일명과 방식 지정.
		String tempDestFile = appTmpPath.getAbsolutePath() + "/" + filename;
		tts.synthesizeToFile(text, myHashRender, tempDestFile);

	}

	public void startPlaying(String filename)
	{
		if(player != null)
		{
			player.stop();
			player.release();
			player = null;
		}

		try{
			player = new MediaPlayer();
			//player.setDataSource(default_path + filename);

			File appTmpPath = new File(defaultExStoragePath);
			appTmpPath.mkdirs();
			String tempDestFile = appTmpPath.getAbsolutePath() + "/" + filename;

			player.setDataSource(tempDestFile);

			player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					flag_state = STOP;
				}
			});

			player.prepare();
			player.start();
			flag_state = PLAY_ING;
			Log.d(TAG,"녹음 재생 시작");

		} catch (Exception e)
		{
			Log.d(TAG,"입출력 에러");
		}
	}

	public void stopPlaying()
	{
		if(player == null)
		{
			return;
		}
		player.stop();
		player.release();
		flag_state = STOP;
		player = null;
		Log.d(TAG, "녹음 재생 끝");

	}

	public String getDefaultExStoragePath()
	{
		return defaultExStoragePath;
	}

}
