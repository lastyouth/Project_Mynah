package com.seven.mynah.custominterface;


import java.util.HashMap;

import com.seven.mynah.MainActivity;
import com.seven.mynah.R;
import com.seven.mynah.ScheduleListActivity;
import com.seven.mynah.globalmanager.GlobalVariable;

import android.os.Bundle;
import android.provider.Settings.Global;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView.FindListener;
import android.widget.GridLayout;
import android.widget.GridLayout.Spec;
import android.widget.LinearLayout;


public class CustomButtonsFragment extends Fragment{

	
	//일단 테스트용 내부 모습
	private int mNumColumns = 4;
	private int mNumRows = 5;
	private int mNumButtons = 0;
	
	private HashMap<String, CustomButton> HashButtons = new HashMap<String, CustomButton>();
	private DisplayMetrics metrics;
	private int padding_5dp;
	private int padding_5dp_toPixel;
	
	private View rootView;
	private GridLayout mainGridLayout;
	private String intentActionName;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		rootView = inflater.inflate(R.layout.fragment_custombuttons, container,false);
		mainGridLayout = (GridLayout)rootView.findViewById(R.id.mainGridLayout);
		//return super.onCreateView(inflater, container, savedInstanceState);
		
		
		//logging
		metrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay()
				.getMetrics(metrics);
		padding_5dp = (int) (3 * metrics.density * 0.5f);
		padding_5dp_toPixel = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 
				padding_5dp, getResources().getDisplayMetrics());
		
		Log.d(this.getClass().getSimpleName(),"This deview pixels X:" + metrics.widthPixels + " Y:"
				+ metrics.heightPixels);
		Log.d(this.getClass().getSimpleName(),"setGrid 직전");
		setGridLayoutDefault();
		
		return rootView;
	}
	
	//디폴트 모양 결정(추후 원하는 대로 변환)
	public void setGridLayoutDefault()
	{
		//onCreateView에서 호출
		mainGridLayout.setOrientation(GridLayout.HORIZONTAL);
		mainGridLayout.setRowCount(mNumRows);
		mainGridLayout.setColumnCount(mNumColumns);
		mNumButtons = 0;
		

		addButton(GlobalVariable.ShortcutType.typeScheduleShortcut, 0,0,4);
		addButton(GlobalVariable.ShortcutType.typeBusShortcut, 1,0,2);
		addButton(GlobalVariable.ShortcutType.typeSubwayShortcut, 1,2,2);		
		addButton(GlobalVariable.ShortcutType.typeWeatherShortcut, 2,0,2);
		addButton(GlobalVariable.ShortcutType.typeGasAlarmShortcut, 2,2,2);
		addButton(GlobalVariable.ShortcutType.typeFamilyShortcut, 3,0,4);
		addButton(GlobalVariable.ShortcutType.typeRefrash, 4,0,2);
		addButton(GlobalVariable.ShortcutType.typeVoice, 4,2,1);
		addButton(GlobalVariable.ShortcutType.typeSetting, 4,3,1);
	}
	
	
	public void addButton(int type, int row, int col, int colspan)
	{
		
		CustomButton cb = null;
		Spec _row = GridLayout.spec(row, 1);
		Spec _colspan = GridLayout.spec(col,colspan);
		//mainGridLayout.getDisplay().getSize(size);
		
		int gridmargin = (int) getResources().getDimension(R.dimen.grid_margin);
		
		int screenWidth = metrics.widthPixels - gridmargin * 2;
		int screenHeight = metrics.heightPixels - getStatusBarHeight() - gridmargin * 2;
		
		//MeasureSpec.EXACTLY : fill_parent, match_parent 로 외부에서 미리 크기가 지정되었다.
		//mainGridLayout.measure(MeasureSpec.EXACTLY, MeasureSpec.EXACTLY);
		//int screenWidth = mainGridLayout.getMeasuredWidth();
		//int screenHeight = mainGridLayout.getMeasuredHeight();
		
		switch(type) {
		case GlobalVariable.ShortcutType.typeScheduleShortcut:
			cb = new ScheduleShortcutLayout(this.getActivity(), this);
			break;
		case GlobalVariable.ShortcutType.typeBusShortcut:
			cb = new BusShortcutLayout(this.getActivity(), this);
			break;
		case GlobalVariable.ShortcutType.typeSubwayShortcut:
			cb = new SubwayShortcutLayout(this.getActivity(), this);
			break;
		case GlobalVariable.ShortcutType.typeWeatherShortcut:
			cb = new WeatherShortcutLayout(this.getActivity(), this);
			break;
		case GlobalVariable.ShortcutType.typeGasAlarmShortcut:
			cb = new GasAlarmShortcutLayout(this.getActivity());
			break;
		case GlobalVariable.ShortcutType.typeFamilyShortcut:
			cb = new FamilyShortcutLayout(this.getActivity());
			break;
		case GlobalVariable.ShortcutType.typeRefrash:
			cb = new RefreshLayout(this.getActivity());
			break;
		case GlobalVariable.ShortcutType.typeVoice:
			cb = new VoiceLayout(this.getActivity());
			break;
		case GlobalVariable.ShortcutType.typeSetting:
			cb = new SettingLayout(this.getActivity(), this);
			break;
		
		default:
			return;

		}
		
		GridLayout.LayoutParams lp = new GridLayout.LayoutParams(_row, _colspan);
		lp.width = (int) (screenWidth / mNumColumns) * colspan - padding_5dp_toPixel * 2 / mNumColumns / colspan;
		lp.height = (int) screenHeight / mNumRows - padding_5dp_toPixel ;
		//lp.setMargins(padding_5dp, padding_5dp, padding_5dp, padding_5dp);
        lp.setMargins(padding_5dp * 2, padding_5dp * 2, 0, 0);
		//lp.setGravity(Gravity.CENTER);
        
		cb.setLayoutParams(lp);
		mainGridLayout.addView(cb);
		HashButtons.put("", cb);
		
	}
	
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);		
	}
	
	
	public void startSettingActivity(String type)
	{
		intentActionName = "com.seven.mynah.";
		intentActionName += type;
		Intent intent = new Intent(intentActionName);
		startActivity(intent);
	}
	
	
	public void startTest()
	{
		Intent intent = new Intent(this.getActivity(),ScheduleListActivity.class);
		startActivity(intent);
	}
	

	public int getStatusBarHeight(){ 
	    
	    int statusHeight = 0;
	    int screenSizeType = (getResources().getConfiguration().screenLayout &
	            Configuration.SCREENLAYOUT_SIZE_MASK);
	 
	    if(screenSizeType != Configuration.SCREENLAYOUT_SIZE_XLARGE) {
	        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
	 
	        if (resourceId > 0) {
	            statusHeight = getResources().getDimensionPixelSize(resourceId);
	        }
	    }
	     
	    return statusHeight;
	}
	
	
}
