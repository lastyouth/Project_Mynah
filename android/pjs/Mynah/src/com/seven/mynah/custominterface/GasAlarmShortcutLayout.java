package com.seven.mynah.custominterface;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.seven.mynah.R;

public class GasAlarmShortcutLayout extends CustomButton{
	
	private View view;
	private ImageView ivGasStatus;
	private TextView tvGasOnOff;
	private TextView tvGasTemperature;
	
	
	public GasAlarmShortcutLayout(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}
	
	private void initView() 
	{
		view = inflate(getContext(), R.layout.layout_button_gas, null);
		ivGasStatus = (ImageView)view.findViewById(R.id.ivGasImage);
		tvGasOnOff = (TextView)view.findViewById(R.id.tvGasOnOff);
		tvGasTemperature = (TextView)view.findViewById(R.id.tvGasTemperature);
		
		//if gas is on then ic_gas_warning2, tvGasonOff.setText("ON");
		ivGasStatus.setImageResource(R.drawable.ic_gas);
		tvGasOnOff.setText("OFF");
		tvGasTemperature.setText("15 °C");
		
		
		view.setOnTouchListener(new GasAlarmTouchListener());
		addView(view);
	}
	
	
	
	private final class GasAlarmTouchListener implements OnTouchListener {
		public boolean onTouch(View view, MotionEvent motionEvent) {
			
			Log.d("Touch", "motionEvent.getAction()" + motionEvent.getAction());
			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
				view.setAlpha((float) 0.8);
				return true;
			} else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
				return true;
			} else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

				//Toast.makeText(getContext(), "가스 정보가 클릭되었음.", Toast.LENGTH_SHORT).show();
				view.setAlpha((float) 1.0);
				//원하는 실행 엑티비티
				
				return true;
			}
			return true;
		}
	}

}
