package com.seven.mynah.custominterface;

import com.seven.mynah.R;
import com.seven.mynah.artifacts.WeatherInfo;
import com.seven.mynah.globalmanager.GlobalVariable;

import android.R.color;
import android.content.ClipData;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.View.DragShadowBuilder;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WeatherShortcutLayout extends CustomButton {

	private View view;
	private TextView tvWeatherType;
	private TextView tvPlace;
	private TextView tvTemper;
	private TextView tvOption;
	private TextView tvOption2;
	private Animation anim;
	
	public WeatherShortcutLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
		setAnimation();
	}
	
	
	private void initView() {
		view = inflate(getContext(), R.layout.layout_weather, null);
		tvWeatherType = (TextView)view.findViewById(R.id.tvWeatherType);
		tvPlace = (TextView)view.findViewById(R.id.tvPlace);
		tvTemper = (TextView)view.findViewById(R.id.tvTemper);
		tvOption = (TextView)view.findViewById(R.id.tvOption);
		tvOption2 = (TextView)view.findViewById(R.id.tvOption2);
		
		//���� �̺κ��� �� xml�� �ѱ��
		view.setOnTouchListener(new WeatherTouchListener());
		addView(view);
		
	}
	
	
	public void setWeatherInfo(WeatherInfo wInfo)
	{
		
		//������Ʈ �ð��� ��� �˷���?
		
	}
	
	
	private void setAnimation() {
		anim = new AnimationUtils().loadAnimation(getContext(), R.anim.slide_in_from_bottom);
	}
	
	
	public void refresh()
	{
		
	}
	
	public void setWeatherImage(int type) {
		
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
			
			Log.d("Touch", "motionEvent.getAction()" + motionEvent.getAction());
			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
				view.setAlpha((float) 0.8);
				//view.startAnimation(anim);
				return true;
			} else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
				return true;
			} else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
				//test
				Toast.makeText(getContext(), "���� ������ Ŭ���Ǿ���.", Toast.LENGTH_SHORT).show();
				view.setAlpha((float) 1.0);
				//���ϴ� ���� ��Ƽ��Ƽ!
				
				return true;
			}
			return true;
		}
	}
	

}
