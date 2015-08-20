package com.seven.mynah;


import android.app.Activity;
import android.os.Bundle;

public class AppInfoActivity extends Activity {
	
	private boolean mIsBackKeyPressed = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_info);
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}
}