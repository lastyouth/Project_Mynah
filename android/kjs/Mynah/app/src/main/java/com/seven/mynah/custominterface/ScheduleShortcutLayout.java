package com.seven.mynah.custominterface;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
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
import com.seven.mynah.database.DBManager;
import com.seven.mynah.globalmanager.GlobalGoogleCalendarManager;
import com.seven.mynah.globalmanager.ServiceAccessManager;

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
		tvSchedules = new TextView[maxSchedules];
		tvPreparation = new TextView(context);

		calendarManager = GlobalGoogleCalendarManager.calendarManager;
		int tableCount = DBManager.getManager(getContext()).getSchedulesCount();

		if(tableCount == 0)
		{
			layoutSchedule.removeAllViews();
			tvSchedules[0] = new TextView(context);
			tvSchedules[0].setTextColor(Color.parseColor("#ffffff"));
			tvSchedules[0].setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			tvSchedules[0].setText("터치하여 구글캘린더와 연동을 시작하세요.");

			layoutSchedule.setGravity(Gravity.CENTER);
			layoutSchedule.addView(tvSchedules[0]);
		}

		view.setOnTouchListener(new ScheduleTouchListener());
		addView(view);
	}
	
	public void refresh()
	{
		//Request service to get schedule information (through Mynah DB)
		ArrayList<ScheduleInfo> scheduleInfos = ServiceAccessManager.getInstance().getService().getScheduleInfo();
		if(scheduleInfos == null)
		{
			scheduleInfos = new ArrayList<ScheduleInfo>();
		}
		setInfo(scheduleInfos);
	}

	public void setInfo(ArrayList<ScheduleInfo> scheduleInfos)
	{
		int size = scheduleInfos.size();
		layoutSchedule.removeAllViews();
		layoutSchedule.setGravity(Gravity.NO_GRAVITY);
		if(size == 0)
		{
			tvSchedules[0] = new TextView(context);
			tvSchedules[0].setTextColor(Color.parseColor("#ffffff"));
			tvSchedules[0].setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			tvSchedules[0].setText("등록된 스케줄이 없습니다.");

			layoutSchedule.setGravity(Gravity.CENTER);
			layoutSchedule.addView(tvSchedules[0]);
		}

		for(int i = 0; i < size; i++)
		{
			tvSchedules[i] = new TextView(context);
			tvSchedules[i].setTextColor(Color.parseColor("#ffffff"));
			tvSchedules[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
			String str = scheduleInfos.get(i).scheduleTime + "    " + scheduleInfos.get(i).scheduleName;
			tvSchedules[i].setText(str);

			tvSchedules[0].setEllipsize(TextUtils.TruncateAt.MARQUEE);
			tvSchedules[0].setSelected(true);
			tvSchedules[0].setSingleLine();

			layoutSchedule.addView(tvSchedules[i]);
		}

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

				cbf.startSettingActivity("Calendar");
				return true;
			}
			return true;
		}
	}
}
