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

	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		
		if(mIsBackKeyPressed == false)
		{
			mIsBackKeyPressed = true;
			finish();
			overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
		}
	}

}