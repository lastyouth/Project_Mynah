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
import com.seven.mynah.globalmanager.ServiceAccessManager;
import com.seven.mynah.infoparser.WeatherParser;

public class GasAlarmShortcutLayout extends CustomButton{

	private View view;
	private ImageView ivGasStatus;
	private TextView tvGasOnOff;
	private TextView tvGasTemperature;

	private final int GAS_OFF = 1;
	private final int GAS_WARNING = 2;
	private final int GAS_WARNING2 = 3;

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

		view.setOnTouchListener(new GasAlarmTouchListener());
		addView(view);
	}

	public void refresh() {
		// Request service to get temperature
		int temp = ServiceAccessManager.getInstance().getService().getTempData();
		setGasAlarmInfo(temp);
	}

	public void setGasAlarmInfo(int temp)
	{
		if(temp == 0)
		{
			tvGasTemperature.setText("받아오는 중입니다.");
			tvGasTemperature.setTextSize(14);
			return;
		}
		tvGasTemperature.setText(temp + "°C");
		if(isFired(temp))
		{
			tvGasOnOff.setText("ON");
			setImageOnOff(GAS_WARNING);
		}
		else
		{
			tvGasOnOff.setText("OFF");
			setImageOnOff(GAS_OFF);
		}
	}

	public boolean isFired(int temp)
	{
		if(temp >= 30)
			return true;
		else
			return false;
	}

	private void setImageOnOff(int type)
	{
		switch (type)
		{
			case GAS_OFF:
				ivGasStatus.setImageResource(R.drawable.ic_gas);
				break;

			case GAS_WARNING:
				ivGasStatus.setImageResource(R.drawable.ic_gas_warning2);
				break;
			case GAS_WARNING2:
				ivGasStatus.setImageResource(R.drawable.ic_gas_warning);
				break;
		}

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

				refresh();
				return true;
			}
			return true;
		}
	}



}
