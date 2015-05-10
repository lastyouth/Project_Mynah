package com.seven.mynah.custominterface;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.seven.mynah.R;

public class WeatherShortcutLayout extends CustomButton {

	private View view;
	private ImageView ivWeatherImage;
	private TextView tvWeatherType;
	private TextView tvPlace;
	private TextView tvTemper;
	private TextView tvWeatherHighAndLow;
	private TextView tvWeatherProbability;
	
	public WeatherShortcutLayout(Context context, CustomButtonsFragment _cbf) {
		super(context);
		// TODO Auto-generated constructor stub
		cbf = _cbf;
		initView();
	}
	
	
	private void initView() {
		view = inflate(getContext(), R.layout.layout_button_weather, null);
		ivWeatherImage = (ImageView)view.findViewById(R.id.ivWeatherImage);
		tvWeatherType = (TextView)view.findViewById(R.id.tvWeatherType);
		
		tvPlace = (TextView)view.findViewById(R.id.tvWeatherPlace);
		tvTemper = (TextView)view.findViewById(R.id.tvWeatherTemper);
		tvWeatherHighAndLow = (TextView)view.findViewById(R.id.tvWeatherHighAndLow);
		tvWeatherProbability = (TextView)view.findViewById(R.id.tvWeatherProbability);
		
		ivWeatherImage.setImageResource(R.drawable.ic_umbrella);
		tvWeatherType.setText("��(�帲)");
		tvPlace.setText("���빮��");
		tvTemper.setText("12��C");
		tvWeatherHighAndLow.setText("17��/10��");
		tvWeatherProbability.setText("89%");
		
		
		//���� �̺κ��� �� xml�� �ѱ��
		view.setOnTouchListener(new WeatherTouchListener());
		addView(view);
		
	}
	
	public void refresh()
	{
		
	}
	
	public void setWeatherImage(int type) 
	{
		
		switch(type) {
		case 1:
			
			break;
		case 2:
			
			break;
			
		case 3:
			
			break;
			
		case 4:
			
			break;
			
		default:
			
			break;
		}
	}
	
	private final class WeatherTouchListener implements OnTouchListener {
		public boolean onTouch(View view, MotionEvent motionEvent) {
			
			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
				view.setAlpha((float) 0.8);
				return true;
			} else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
				return true;
			} else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
				view.setAlpha((float) 1.0);
				//���ϴ� ���� ��Ƽ��Ƽ!
				cbf.startSettingActivity("Weather");
				return true;
			}
			return true;
		}
	}
	

}
