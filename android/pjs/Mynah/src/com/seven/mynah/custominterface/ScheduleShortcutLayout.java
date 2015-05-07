package com.seven.mynah.custominterface;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.seven.mynah.R;
import com.seven.mynah.ScheduleListActivity;
import com.seven.mynah.MainActivity;

public class ScheduleShortcutLayout extends CustomButton{

	private View view;
	
	public ScheduleShortcutLayout(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}
	
	private void initView() 
	{
		view = inflate(getContext(), R.layout.layout_schedule, null);
		
		//추후 이부분은 다 xml로 넘길것
		view.setOnTouchListener(new ScheduleTouchListener());
		addView(view);
	}
	
	private final class ScheduleTouchListener extends Activity implements OnTouchListener {
		public boolean onTouch(View view, MotionEvent motionEvent) {
			
			Log.d("Touch", "motionEvent.getAction()" + motionEvent.getAction());
			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
				view.setAlpha((float) 0.8);
				return true;
			} else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
				return true;
			} else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
				//test
				Toast.makeText(getContext(), "스케줄 정보가 클릭되었음.", Toast.LENGTH_SHORT).show();
				view.setAlpha((float) 1.0);
				//원하는 실행 엑티비티!
				try {
					cbf.startTest();
					
				}
				catch(Exception e) {
					Log.d("activity error", e.toString());
				}
				
				
				return true;
			}
			return true;
		}
	}
}
