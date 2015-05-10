package com.seven.mynah.custominterface;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.seven.mynah.R;

public class RefreshShortcutLayout extends CustomButton{
	
	private View view;
	private TextView tvRefreshTime;
	
	public RefreshShortcutLayout(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}
	
	private void initView() {
		view = inflate(getContext(), R.layout.layout_button_refresh, null);
		
		tvRefreshTime = (TextView)view.findViewById(R.id.tvRefreshTime);
		tvRefreshTime.setText("05/07 15:32");
		
		
		//���� �̺κ��� �� xml�� �ѱ��
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
				//test
				Toast.makeText(getContext(), "refresh ��ư�� Ŭ���Ǿ���.", Toast.LENGTH_SHORT).show();
				view.setAlpha((float) 1.0);
				//���ϴ� ���� ��Ƽ��Ƽ!
				
				return true;
			}
			return true;
		}
	}
}
