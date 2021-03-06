package com.seven.mynah.custominterface;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

import com.seven.mynah.R;

public class SettingShortcutLayout extends CustomButton{
private View view;
	
	public SettingShortcutLayout(Context context, CustomButtonsFragment _cbf) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		cbf = _cbf;
		initView();
	}
	
	private void initView() 
	{
		view = inflate(getContext(), R.layout.layout_button_setting, null);
		
		//추후 이부분은 다 xml로 넘길것
		view.setOnTouchListener(new SettingTouchListener());
		addView(view);
	}
	
	private final class SettingTouchListener implements OnTouchListener {
		public boolean onTouch(View view, MotionEvent motionEvent) {
			
			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
				view.setAlpha((float) 0.8);
				return true;
			} else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
				return true;
			} else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
				view.setAlpha((float) 1.0);
				//원하는 실행 엑티비티!
				cbf.startSettingActivity("Setting");
				return true;
			}
			return true;
		}
	}
	
	public void refresh() {
		Toast.makeText(getContext(), "Setting onRestart()", 1).show();
	}
}
