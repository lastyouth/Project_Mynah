package com.seven.mynah;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;


public class GlobalSettingActivity extends Activity{
	
	private boolean mIsBackKeyPressed = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_app);
		
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		
		if(mIsBackKeyPressed == false)
		{
			mIsBackKeyPressed = true;
			finish();
		}
	}
}
