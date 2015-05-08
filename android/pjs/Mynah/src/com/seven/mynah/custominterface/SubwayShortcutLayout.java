package com.seven.mynah.custominterface;

import com.seven.mynah.R;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SubwayShortcutLayout extends CustomButton{

	private View view;
	private ImageView ivSubwayImage;
	private TextView tvSubwayName;
	private TextView tvSubwayStopName;
	private TextView tvSubwayNextTime;
	private TextView tvSubwayNextTime2;
	
	public SubwayShortcutLayout(Context context, CustomButtonsFragment _cbf) {
		super(context);
		// TODO Auto-generated constructor stub
		cbf = _cbf;
		initView();
	}
	
	private void initView() {
		view = inflate(getContext(), R.layout.layout_button_subway, null);
		
		ivSubwayImage = (ImageView)view.findViewById(R.id.ivSubwayImage);
		tvSubwayName = (TextView)view.findViewById(R.id.tvSubwayName);
		tvSubwayStopName = (TextView)view.findViewById(R.id.tvSubwayStopName);
		tvSubwayNextTime = (TextView)view.findViewById(R.id.tvSubwayNextTime);
		tvSubwayNextTime2 = (TextView)view.findViewById(R.id.tvSubwayNextTime2);
		
		ivSubwayImage.setImageResource(R.drawable.ic_subway);
		tvSubwayName.setText("1호선 하행");
		tvSubwayStopName.setText("청량리역\n");
		tvSubwayNextTime.setText("2분");
		tvSubwayNextTime2.setText("5분");
		
		//추후 이부분은 다 xml로 넘길것
		view.setOnTouchListener(new SubwayTouchListener());
		addView(view);
	}
	
	
	
	private final class SubwayTouchListener implements OnTouchListener {
		public boolean onTouch(View view, MotionEvent motionEvent) {
			
			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
				view.setAlpha((float) 0.8);
				return true;
			} else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
				return true;
			} else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
				view.setAlpha((float) 1.0);
				//원하는 실행 엑티비티!
				cbf.startSettingActivity("Subway");
				return true;
			}
			return true;
		}
	}

}
