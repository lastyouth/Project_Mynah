package com.seven.mynah.custominterface;


import java.util.HashMap;

import com.seven.mynah.MainActivity;
import com.seven.mynah.R;
import com.seven.mynah.globalmanager.GlobalVariable;

import android.os.Bundle;
import android.provider.Settings.Global;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.content.Context;
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
	private int mNumColumns = 2;
	private int mNumRows = 4;
	
	private int mNumButtons = 0;
	
	private HashMap<String, CustomButton> HashButtons = new HashMap<String, CustomButton>();
	private DisplayMetrics metrics;
	private int padding_5dp;
	private int padding_5dp_toPixel;
	
	private View rootView;
	private GridLayout mainGridLayout;
	
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
		mainGridLayout.setOrientation(GridLayout.VERTICAL);
		mainGridLayout.setRowCount(mNumRows);
		mainGridLayout.setColumnCount(mNumColumns);
		mNumButtons = 0;
		

		addButton(GlobalVariable.ShortcutType.Weather, 0,0,1);
		addButton(GlobalVariable.ShortcutType.Weather, 1,0,2);
		//addButton(GlobalVariable.typeWeatherShortcut, 1,1,1);		
		addButton(GlobalVariable.ShortcutType.Weather, 2,0,2);
		addButton(GlobalVariable.ShortcutType.Weather, 3,0,1);
		
		
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
		case GlobalVariable.ShortcutType.Weather:
			cb = new WeatherShortcutLayout(this.getActivity());
			break;
		case GlobalVariable.ShortcutType.Schedule:
			break;
		case GlobalVariable.ShortcutType.Subway:
			cb = new SubwayShortcutLayout(this.getActivity());
			break;
		default:
			
			return;
			
		}
		
		GridLayout.LayoutParams lp = new GridLayout.LayoutParams(_row, _colspan);
		lp.width = (int) screenWidth / mNumColumns * colspan - padding_5dp_toPixel * 2;
		lp.height = (int) screenHeight / mNumRows - padding_5dp_toPixel * 2;
		lp.setMargins(padding_5dp, padding_5dp, padding_5dp, padding_5dp);
        lp.setGravity(Gravity.CENTER);
        
		cb.setLayoutParams(lp);
		mainGridLayout.addView(cb);
		HashButtons.put("", cb);
		
	}
	
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
				
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
