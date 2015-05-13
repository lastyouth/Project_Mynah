package com.seven.mynah.custominterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.seven.mynah.R;
import com.seven.mynah.artifacts.GasAlarmInfo;
import com.seven.mynah.artifacts.WeatherInfo;
import com.seven.mynah.artifacts.WeatherLocationInfo;
import com.seven.mynah.database.DBManager;
import com.seven.mynah.infoparser.WeatherParser;

public class GasAlarmShortcutLayout extends CustomButton{
	
	private View view;
	private ImageView ivGasStatus;
	private TextView tvGasOnOff;
	private TextView tvGasTemperature;
	
	

	public GasAlarmShortcutLayout(Context context, CustomButtonsFragment _cbf) {
		super(context);
		// TODO Auto-generated constructor stub
		cbf = _cbf;
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
	
	
	
	public void setGasAlarmInfo(GasAlarmInfo ginfo)
	{
		
		if(ginfo.isFired)
		{
			tvGasOnOff.setText("ON");
			setImageOnOff(2);
		}
		else
		{
			tvGasOnOff.setText("OFF");
			setImageOnOff(1);
		}
		
	}
	
	
	private void setImageOnOff(int type)
	{
		switch (type)
		{
		case 1:
			ivGasStatus.setImageResource(R.drawable.ic_gas_warning);
			break;
				
		case 2:
			ivGasStatus.setImageResource(R.drawable.ic_gas_warning2);
			break;
		case 3:
			ivGasStatus.setImageResource(R.drawable.ic_gas);
			break;
		}
		
	}
	
	
	public void setuptest()
	{
		
		cbf.getActivity().runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub

				GasAlarmInfo ginfo = new GasAlarmInfo();
				
				ginfo.isFired = true;
				//ginfo.last_update = String.valueOf(new Date());
				//ginfo.time = String.valueOf(new Date());
				
				setGasAlarmInfo(ginfo);
				
		    	
			}
		});
		
		
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
				//setuptest();
				cbf.startBluetoothList_temp();
				
				return true;
			}
			return true;
		}
	}

}
