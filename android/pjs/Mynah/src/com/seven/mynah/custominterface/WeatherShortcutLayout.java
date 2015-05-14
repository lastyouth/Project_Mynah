package com.seven.mynah.custominterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.seven.mynah.R;
import com.seven.mynah.artifacts.WeatherInfo;
import com.seven.mynah.artifacts.WeatherLocationInfo;
import com.seven.mynah.database.DBManager;
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

		// tvReh = (TextView)view.findViewById(R.id.tvReh);

		tvPop = (TextView) view.findViewById(R.id.tvPop);

		refresh();
		// 추후 이부분은 다 xml로 넘길것
		view.setOnTouchListener(new WeatherTouchListener());
		addView(view);
		Log.d(TAG, "initView End");
	}

	private void setWeatherInfo(WeatherInfo winfo) {
		Log.d(TAG, "setWeatherInfo Start");
		
		if (winfo == null) {
			// 초기화
			tvPlace2.setText("터치해서 정보를 입력하세요");
			return;
		}
		
		tvPlace.setText(winfo.location.city_name);
		tvPlace2.setText(winfo.location.mdl_name + "\n");
		tvTemper.setText(winfo.array_ttw.get(0).temp + "°C");
		tvPop.setText(winfo.array_ttw.get(0).pop + "%");
		// tvReh.setText("습도 : " + winfo.array_ttw.get(0).reh + "%");
		tvWeatherType.setText(winfo.array_ttw.get(0).wfKor);


		// tvHour.setText(printDateFormat.format(date));
		// tvUpdateTime.setText("업데이트 : " + winfo.last_update);
		// 이미지 타입 넣기
		//setWeatherImage(Integer.valueOf(winfo.array_ttw.get(0).sky));
		setWeatherImage(3);
		Log.d(TAG, "setWeatherInfo End");
	}

	public void refresh() {
		Log.d(TAG, "refresh Start");
		weatherArrayList = new ArrayList<WeatherLocationInfo>();
		weatherArrayList = DBManager.getManager(cbf.getActivity()).getWeatherDBbyLog();
		WeatherInfo winfo = new WeatherInfo();
		
		if(weatherArrayList.size() == 0)
		{
			setWeatherInfo(null);
		}
		else
		{
			WeatherLocationInfo wlinfo = weatherArrayList.get(0);
			winfo.location = wlinfo;
			winfo = DBManager.getManager(getContext()).getWeatherDB(winfo);
			if(winfo.array_ttw.size() == 0)
			{
				WeatherParser wp = new WeatherParser();
				winfo = wp.getWeatherInfo(winfo);
			}
			setWeatherInfo(winfo);
		}
		Log.d(TAG, "refresh End");
	}

	private void setWeatherImage(int type) {

		switch (type) {
		case 1:

			break;
		case 2:

			break;

		case 3:
			ivWeatherImage.setImageResource(R.drawable.ic_umbrella);
			break;

		case 4:
			ivWeatherImage.setImageResource(R.drawable.ic_umbrella);
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
				// 원하는 실행 엑티비티!
				cbf.startSettingActivity("Weather");
				// refresh();
				return true;
			}
			return true;
		}
	}

}
