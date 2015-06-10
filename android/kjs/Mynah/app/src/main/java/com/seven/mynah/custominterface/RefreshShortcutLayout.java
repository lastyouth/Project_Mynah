package com.seven.mynah.custominterface;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.seven.mynah.MainActivity;
import com.seven.mynah.R;

public class RefreshShortcutLayout extends CustomButton{
	
	private View view;
	private TextView tvRefreshTime;
	private Date date;
	
	public RefreshShortcutLayout(Context context, CustomButtonsFragment _cbf) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		cbf = _cbf;
		initView();
	}
	
	private void initView() {
		view = inflate(getContext(), R.layout.layout_button_refresh, null);
		
		tvRefreshTime = (TextView)view.findViewById(R.id.tvRefreshTime);
		setCurrentTime();

		view.setOnTouchListener(new RefreshTouchListener());
		addView(view);
	}
	
	private final class RefreshTouchListener implements OnTouchListener {
		public boolean onTouch(View view, MotionEvent motionEvent) {
			
			Log.d("Touch", "motionEvent.getAction()" + motionEvent.getAction());
			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
				view.setAlpha((float) 0.8);
				return true;
			} else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
				return true;
			} else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
				view.setAlpha((float) 1.0);

				refreshAll();
				return true;
			}
			return true;
		}
	}
	
	public void refreshAll()
	{
		cbf.refresh("Bus");
		cbf.refresh("Subway");
		cbf.refresh("Weather");
		cbf.refresh("Schedule");
		setCurrentTime();
	}
	
	public void setCurrentTime()
	{
		SimpleDateFormat date_format = new SimpleDateFormat("MM/dd hh:mm");
		date = new Date();
		
		tvRefreshTime.setText(date_format.format(date));

		tvRefreshTime.setEllipsize(TextUtils.TruncateAt.MARQUEE);
		tvRefreshTime.setSelected(true);
		tvRefreshTime.setSingleLine();
	}
}
