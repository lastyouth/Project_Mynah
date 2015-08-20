package com.seven.mynah.backgroundservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class WakeonReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
		// on Boot
		if(action.equals("android.intent.action.BOOT_COMPLETED")) {
			Log.d("WakeonReceiver", "Boot complete signal has been received");
			Intent service = new Intent(context, GetInformationService.class);
			context.startService(service);
		}
	}

	
	
}
