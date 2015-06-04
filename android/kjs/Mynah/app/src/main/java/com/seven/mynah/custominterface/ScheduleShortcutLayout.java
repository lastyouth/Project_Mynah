package com.seven.mynah.custominterface;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import android.provider.Settings;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.seven.mynah.LoadingActivity;
import com.seven.mynah.MainActivity;
import com.seven.mynah.R;
import com.seven.mynah.artifacts.ScheduleInfo;
import com.seven.mynah.calender.CalendarManager;
import com.seven.mynah.globalmanager.GlobalGoogleCalendarManager;

public class ScheduleShortcutLayout extends CustomButton{
	
	private static int maxSchedules = 10;
	private static int maxPreparations = 10;
	private View view;
	private LinearLayout layoutSchedule;
	private LinearLayout layoutPreparation;
	private TextView tvSchedules[];
	private TextView tvPreparation;

	private CalendarManager calendarManager;
	private ArrayList<ScheduleInfo> scheduleInfo;
	private String today;

	public ScheduleShortcutLayout(Context context, CustomButtonsFragment _cbf) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		cbf = _cbf;
		initView();
	}
	
	private void initView() 
	{
		view = inflate(getContext(), R.layout.layout_button_schedule, null);
		layoutSchedule = (LinearLayout)view.findViewById(R.id.layoutSchedule);
		layoutPreparation = (LinearLayout)view.findViewById(R.id.layoutPreparation);
		tvSchedules = new TextView[maxSchedules];
		tvPreparation = new TextView(context);

		calendarManager = GlobalGoogleCalendarManager.calendarManager;
		if(calendarManager == null)
		{

		}
		else
		{
			setInfo();
		}
		/*
		for(int i = 0; i < 2; i++)
		{
			tvSchedules[i] = new TextView(context);
			tvSchedules[i].setTextColor(Color.parseColor("#ffffff"));
			tvSchedules[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			layoutSchedule.addView(tvSchedules[i]);
		}
		tvSchedules[0].setText("09:00 외주업체 미팅");
		tvSchedules[1].setText("12:30 점심약속");

		tvPreparation.setText("준비물 : USB, 보고서");
		tvPreparation.setTextColor(Color.parseColor("#ffffff"));
		tvPreparation.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		layoutPreparation.addView(tvPreparation);
		*/
		//setInfo();
		//추후 이부분은 다 xml로 넘길것
		view.setOnTouchListener(new ScheduleTouchListener());
		addView(view);
	}
	
	private final class ScheduleTouchListener extends Activity implements OnTouchListener {
		public boolean onTouch(View view, MotionEvent motionEvent) {
			
			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
				view.setAlpha((float) 0.8);
				return true;
			} else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
				return true;
			} else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
				view.setAlpha((float) 1.0);
				//원하는 실행 엑티비티!

				//cbf.startSettingActivity("Schedule");
				cbf.startSettingActivity("Calendar");
				return true;	

			}
			return true;
		}
	}
	
	public void refresh() {
		setInfo();
	}

	public void setInfo()
	{
		calendarManager = GlobalGoogleCalendarManager.calendarManager;

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = sdf.format(date);

		scheduleInfo = new ArrayList<ScheduleInfo>();
		scheduleInfo = calendarManager.getScheduleOnDate(strDate);

		int size = scheduleInfo.size();
		for(int i = 0; i < size; i++)
		{
			tvSchedules[i] = new TextView(context);
			tvSchedules[i].setTextColor(Color.parseColor("#ffffff"));
			tvSchedules[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			String str = scheduleInfo.get(i).scheduleTime + " " + scheduleInfo.get(i).scheduleName;
			tvSchedules[i].setText(str);
			layoutSchedule.addView(tvSchedules[i]);
		}

	}
}
