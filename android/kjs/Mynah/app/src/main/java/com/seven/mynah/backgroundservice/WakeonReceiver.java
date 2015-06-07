package com.seven.mynah.backgroundservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class WakeonReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Intent service = new Intent(context, GetInformationService.class);
		context.startService(service);
	}

	
	
}
