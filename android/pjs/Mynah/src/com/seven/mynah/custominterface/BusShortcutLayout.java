package com.seven.mynah.custominterface;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.seven.mynah.R;

public class BusShortcutLayout extends CustomButton {
	
	private View view;
	private ImageView ivBusImage;
	private TextView tvBusName;
	private TextView tvBusStopName;
	private TextView tvBusNextTime;
	private TextView tvBusNextTime2;
	
	public BusShortcutLayout(Context context, CustomButtonsFragment _cbf) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		cbf = _cbf;
		initView();
	}
	
	private void initView() 
	{
		view = inflate(getContext(), R.layout.layout_button_bus, null);
		
		ivBusImage = (ImageView)view.findViewById(R.id.ivBusImage);
		tvBusName = (TextView)view.findViewById(R.id.tvBusName);
		tvBusStopName = (TextView)view.findViewById(R.id.tvBusStopName);
		tvBusNextTime = (TextView)view.findViewById(R.id.tvBusNextTime);
		tvBusNextTime2 = (TextView)view.findViewById(R.id.tvBusNextTime2);
		
		ivBusImage.setImageResource(R.drawable.ic_bus);
		tvBusName.setText("121 ����");
		tvBusStopName.setText("����ø����\n");
		tvBusNextTime.setText("3��");
		tvBusNextTime2.setText("7��");
		
		
		//���� �̺κ��� �� xml�� �ѱ��
		view.setOnTouchListener(new ScheduleTouchListener());
		addView(view);
	}
	
	
	
	private final class ScheduleTouchListener implements OnTouchListener {
		public boolean onTouch(View view, MotionEvent motionEvent) {
			
			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
				view.setAlpha((float) 0.8);
				return true;
			} else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
				return true;
			} else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
				view.setAlpha((float) 1.0);
				//���ϴ� ���� ��Ƽ��Ƽ!
				cbf.startSettingActivity_temp("Bus");
				return true;
			}
			return true;
		}
	}
}
