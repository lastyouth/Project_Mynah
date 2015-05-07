package com.seven.mynah.custominterface;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.seven.mynah.ScheduleListActivity;

public class ScheduleShortcutLayout extends CustomButton{
	
	private static int maxSchedules = 10;
	private static int maxPreparations = 10;
	private View view;
	private LinearLayout layoutSchedule;
	private LinearLayout layoutPreparation;
	private TextView tvSchedules[];
	private TextView tvPreparation;
	
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
		
		for(int i = 0; i < 2; i++)
		{
			tvSchedules[i] = new TextView(context);
			tvSchedules[i].setTextColor(Color.parseColor("#ffffff"));
			tvSchedules[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			layoutSchedule.addView(tvSchedules[i]);
		}
		tvSchedules[0].setText("09:00 ���־�ü ����");
		tvSchedules[1].setText("12:30 ���ɾ��");
		
		
		
		tvPreparation.setText("�غ� : USB, ����");
		tvPreparation.setTextColor(Color.parseColor("#ffffff"));
		tvPreparation.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		layoutPreparation.addView(tvPreparation);
		
		//���� �̺κ��� �� xml�� �ѱ��
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
				//���ϴ� ���� ��Ƽ��Ƽ!
				cbf.startSettingActivity("Schedule");
				return true;	
			}
			return true;
		}
	}
}
