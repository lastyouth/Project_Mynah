package com.seven.mynah;


import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

public class AppInfoActivity extends Activity {
	
	private boolean mIsBackKeyPressed = false;


	private TextView tvMynahVersion;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_info);
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

		initAppinfo();

	}

	private void initAppinfo()
	{

		tvMynahVersion = (TextView) findViewById(R.id.tvMynahVersion);
		String version = "";
		try {
			PackageInfo i = getApplication().getPackageManager().getPackageInfo(getApplication().getPackageName(), 0);
			version = i.versionName;
		} catch(PackageManager.NameNotFoundException e) {
			version = "Ver1.0";
		}

		tvMynahVersion.setText("Ver"+version);

	}


	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}
}