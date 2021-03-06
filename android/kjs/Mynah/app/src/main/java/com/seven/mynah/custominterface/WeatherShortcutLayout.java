package com.seven.mynah.custominterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.seven.mynah.R;
import com.seven.mynah.artifacts.WeatherInfo;
import com.seven.mynah.artifacts.WeatherLocationInfo;
import com.seven.mynah.database.DBManager;
import com.seven.mynah.globalmanager.ServiceAccessManager;
import com.seven.mynah.infoparser.WeatherParser;

public class WeatherShortcutLayout extends CustomButton {

	private View view;
	private ImageView ivWeatherImage;
	private TextView tvWeatherType;
	private TextView tvPlace;
	private TextView tvPlace2;
	private TextView tvTemper;
	private TextView tvReh; // 습도
	private TextView tvPop; // 강수확률
	private TextView tvUpdateTime;
	private TextView tvHour;
	
	private ArrayList<WeatherLocationInfo> weatherArrayList;
	
	private static String TAG = "WeatherShortcutLayout";
	
	public WeatherShortcutLayout(Context context, CustomButtonsFragment _cbf) {
		super(context);
		// TODO Auto-generated constructor stub
		cbf = _cbf;
		initView();
	}

	private void initView() {
		Log.d(TAG, "initView Start");
		view = inflate(getContext(), R.layout.layout_button_weather, null);
		ivWeatherImage = (ImageView) view.findViewById(R.id.ivWeatherImage);
		tvWeatherType = (TextView) view.findViewById(R.id.tvWeatherType);

		tvPlace = (TextView) view.findViewById(R.id.tvWeatherPlace);
		tvPlace2 = (TextView) view.findViewById(R.id.tvWeatherPlace2);
		tvTemper = (TextView) view.findViewById(R.id.tvWeatherTemper);

		tvPop = (TextView) view.findViewById(R.id.tvPop);

		setButtonsMarquee();

		view.setOnTouchListener(new WeatherTouchListener());
		addView(view);
		Log.d(TAG, "initView End");
	}

	public void refresh() {
		Log.d(TAG, "refresh Start");

		// Request service to get weather Information
		WeatherInfo wInfo = ServiceAccessManager.getInstance().getService().getWeatherInfo();
		setWeatherInfo(wInfo);

		Log.d(TAG, "refresh End");
	}

	private void setWeatherInfo(WeatherInfo winfo) {
		Log.d(TAG, "setWeatherInfo Start");
		
		if (winfo == null) {
			// 초기화
			tvPlace2.setText("터치해서 정보를 입력하세요");
			setWeatherImage(5);
			return;
		}
		
		tvPlace.setText(winfo.location.city_name);
		tvPlace2.setText(winfo.location.mdl_name + "\n");
		tvTemper.setText(winfo.array_ttw.get(0).temp + " °C");
		tvPop.setText("강수확률 : " + winfo.array_ttw.get(0).pop + "%");
		tvWeatherType.setText(winfo.array_ttw.get(0).wfKor);

		// Set weather image type
		setWeatherImage(Integer.valueOf(winfo.array_ttw.get(0).sky));
		Log.d(TAG, "setWeatherInfo End");
	}

	private void setWeatherImage(int type) {

		switch (type) {
		case 1:
			ivWeatherImage.setImageResource(R.drawable.ic_sunny);
			break;
		case 2:
			//구름조금
			ivWeatherImage.setImageResource(R.drawable.ic_cloud2);
			break;

		case 3:
			//구름 많음
			ivWeatherImage.setImageResource(R.drawable.ic_cloud1);
			break;

		case 4:
			//흐림  /비
			ivWeatherImage.setImageResource(R.drawable.ic_cloud1);
			break;
		case 5:
			ivWeatherImage.setImageResource(R.drawable.ic_question);
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
				cbf.startSettingActivity("Weather");
				return true;
			}
			return true;
		}
	}

	private void setButtonsMarquee()
	{
		tvPlace.setEllipsize(TextUtils.TruncateAt.MARQUEE);
		tvPlace.setSelected(true);
		tvPlace.setSingleLine();

		tvPlace2.setEllipsize(TextUtils.TruncateAt.MARQUEE);
		tvPlace2.setSelected(true);
		tvPlace2.setSingleLine();

		tvTemper.setEllipsize(TextUtils.TruncateAt.MARQUEE);
		tvTemper.setSelected(true);
		tvTemper.setSingleLine();

		tvPop.setEllipsize(TextUtils.TruncateAt.MARQUEE);
		tvPop.setSelected(true);
		tvPop.setSingleLine();
	}

}
