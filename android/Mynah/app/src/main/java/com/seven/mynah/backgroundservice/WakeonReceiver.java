package com.seven.mynah.backgroundservice;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class WakeonReceiver extends BroadcastReceiver{


	public static final long NOTIFY_INTERVAL = 1 * 60 * 1000; //3분
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
		// on Boot
		if(action.equals("android.intent.action.BOOT_COMPLETED")) {
			Log.d("WakeonReceiver", "Boot complete signal has been received");
			Intent service = new Intent(context, GetInformationService.class);
			context.startService(service);

			startSchedule(context);

		}
	}

	public void startSchedule(Context context) {

		try {
			AlarmManager alarms = (AlarmManager) context
					.getSystemService(Context.ALARM_SERVICE);

			Intent intent = new Intent(context.getApplicationContext(),
					AlarmReceiver.class);
			intent.putExtra(AlarmReceiver.ACTION_ALARM,
					AlarmReceiver.ACTION_ALARM);

			final PendingIntent pIntent = PendingIntent.getBroadcast(context,
					1234567, intent, PendingIntent.FLAG_UPDATE_CURRENT);

			alarms.setRepeating(AlarmManager.RTC_WAKEUP,
					System.currentTimeMillis(), NOTIFY_INTERVAL, pIntent);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void cancelSchedules(Context context) {

		Intent intent = new Intent(context.getApplicationContext(),
				AlarmReceiver.class);
		intent.putExtra(AlarmReceiver.ACTION_ALARM,
				AlarmReceiver.ACTION_ALARM);

		final PendingIntent pIntent = PendingIntent.getBroadcast(context, 1234567,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);

		AlarmManager alarms = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		alarms.cancel(pIntent);

	}

	
	
}
