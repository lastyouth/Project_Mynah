package com.seven.mynah;

import java.text.NumberFormat;
import java.util.StringTokenizer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.seven.mynah.R;
import com.seven.mynah.backgroundservice.GetInformationService;
import com.seven.mynah.database.DBManager;

public class LoadingActivity extends Activity{
	
	RelativeLayout rlayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logo);
		startService();
		Loading();
		
	}
	public void startService()
	{
		Intent service = new Intent(this, GetInformationService.class);
		startService(service);
		 
	}
	public void Loading()
	{
		
		checkInitUser();
		
		Handler handler = new Handler()
		{
			public void handleMessage(Message msg)
			{
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(intent);
				finish();
			}
		};
		
		handler.sendEmptyMessageDelayed(0, 1000);
	}
	
	private void checkInitUser()
	{
		if (DBManager.getManager(this).isInitialUser())
		{
			Toast.makeText(this, "유저정보 확인되었음", 1).show();
		}
		else 
		{
			Toast.makeText(this, "등록된 유저가 없습니다.", 1).show();
		}
			
	}
	
	//사전 구동에 필요한 내용을 로드하는 함수.
	public void loadInitialSetup() {
		
		
		//모두 로드하고 난 뒤에. 상황에 맞게 로드.
		//Intent intent = new Intent(LogoActivity.this, );
		//startActivity(intent);
		finish();
	}
	
	
	// 현 상황에서 필요없음.. A->B에서 B->A로 갈때 다이얼로그 사용할 때 사용.
	@Override 	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		
		
	}
	
}
