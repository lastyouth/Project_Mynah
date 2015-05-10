package com.seven.mynah.custominterface;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.seven.mynah.R;

public class FamilyShortcutLayout extends CustomButton{

	private View view;
	private LinearLayout layoutFamilyName;
	private LinearLayout layoutFamilyTime;
	private LinearLayout layoutFamilyInOut;
	private TextView tvFamilyName[];
	private TextView tvFamilyTime[];
	private TextView tvFamilyInOut[];
	
	//가족 구성원수
	private int numOfFamily = 2;
	
	public FamilyShortcutLayout(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}
	
	private void initView() 
	{
		view = inflate(getContext(), R.layout.layout_button_family, null);
		
		layoutFamilyName = (LinearLayout)view.findViewById(R.id.layoutFamilyName);
		layoutFamilyTime = (LinearLayout)view.findViewById(R.id.layoutFamilyTime);
		layoutFamilyInOut = (LinearLayout)view.findViewById(R.id.layoutFamilyInOut);
		
		tvFamilyName = new TextView[numOfFamily];
		tvFamilyTime = new TextView[numOfFamily];
		tvFamilyInOut = new TextView[numOfFamily];
		
		for(int i = 0; i < numOfFamily; i++)
		{
			tvFamilyName[i] = new TextView(context);
			tvFamilyTime[i] = new TextView(context);
			tvFamilyInOut[i] = new TextView(context);
			
			tvFamilyName[i].setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			tvFamilyTime[i].setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			tvFamilyInOut[i].setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			
			tvFamilyName[i].setGravity(Gravity.CENTER);
			tvFamilyTime[i].setGravity(Gravity.CENTER);
			tvFamilyInOut[i].setGravity(Gravity.CENTER);
			
			tvFamilyName[i].setTextColor(Color.parseColor("#ffffff"));
			tvFamilyTime[i].setTextColor(Color.parseColor("#ffffff"));
			tvFamilyInOut[i].setTextColor(Color.parseColor("#ffffff"));
			
			tvFamilyName[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			tvFamilyTime[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			tvFamilyInOut[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			
			layoutFamilyName.addView(tvFamilyName[i]);
			layoutFamilyTime.addView(tvFamilyTime[i]);
			layoutFamilyInOut.addView(tvFamilyInOut[i]);
		}
		
		tvFamilyName[0].setText("김진성");
		tvFamilyTime[0].setText("08:11");
		tvFamilyInOut[0].setText("IN");
		
		tvFamilyName[1].setText("김희중");
		tvFamilyTime[1].setText("19:35");
		tvFamilyInOut[1].setText("OUT");
		
		//추후 이부분은 다 xml로 넘길것
		view.setOnTouchListener(new FamilyTouchListener());
		addView(view);
	}
	
	private final class FamilyTouchListener implements OnTouchListener {
		public boolean onTouch(View view, MotionEvent motionEvent) {
			
			Log.d("Touch", "motionEvent.getAction()" + motionEvent.getAction());
			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
				view.setAlpha((float) 0.8);
				return true;
			} else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
				return true;
			} else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
				//test
				Toast.makeText(getContext(), "가족 정보가 클릭되었음.", Toast.LENGTH_SHORT).show();
				view.setAlpha((float) 1.0);
				//원하는 실행 엑티비티!
				
				return true;
			}
			return true;
		}
	}
}
