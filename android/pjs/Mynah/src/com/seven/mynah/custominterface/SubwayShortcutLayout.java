package com.seven.mynah.custominterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.seven.mynah.MainActivity;
import com.seven.mynah.R;
import com.seven.mynah.artifacts.BusInfo;
import com.seven.mynah.artifacts.GasAlarmInfo;
import com.seven.mynah.artifacts.SubwayInfo;
import com.seven.mynah.artifacts.SubwayStationInfo;
import com.seven.mynah.artifacts.WeatherInfo;
import com.seven.mynah.artifacts.WeatherLocationInfo;
import com.seven.mynah.database.DBManager;
import com.seven.mynah.infoparser.BusPaser;
import com.seven.mynah.infoparser.SubwayPaser;
import com.seven.mynah.infoparser.WeatherParser;

import android.R.array;
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

public class SubwayShortcutLayout extends CustomButton {

	private View view;
	private ImageView ivSubwayImage;
	private TextView tvSubwayName;
	private TextView tvSubwayStopName;
	private TextView tvSubwayDirName;
	private TextView tvSubwayNextTime;
	private TextView tvSubwayDirName2;
	private TextView tvSubwayNextTime2;

	private ArrayList<SubwayInfo> subwayArrayList;

	public SubwayShortcutLayout(Context context, CustomButtonsFragment _cbf) {
		super(context);
		// TODO Auto-generated constructor stub
		cbf = _cbf;
		initView();
	}

	private void initView() {
		view = inflate(getContext(), R.layout.layout_button_subway, null);

		ivSubwayImage = (ImageView) view.findViewById(R.id.ivSubwayImage);
		tvSubwayName = (TextView) view.findViewById(R.id.tvSubwayName);
		tvSubwayStopName = (TextView) view.findViewById(R.id.tvSubwayStopName);
		tvSubwayDirName = (TextView) view.findViewById(R.id.tvSubwayDirName);
		tvSubwayNextTime = (TextView) view.findViewById(R.id.tvSubwayNextTime);
		tvSubwayDirName2 = (TextView) view.findViewById(R.id.tvSubwayDirName2);
		tvSubwayNextTime2 = (TextView) view .findViewById(R.id.tvSubwayNextTime2);

		ivSubwayImage.setImageResource(R.drawable.ic_subway);
		
		// 추후 이부분은 다 xml로 넘길것
		refresh();
		view.setOnTouchListener(new SubwayTouchListener());
		addView(view);
	}

	private void setSubwayInfo(SubwayInfo sinfo) {
		if (sinfo == null) {
			// 초기화
			tvSubwayDirName.setText("터치해서 정보를 입력하세요");
			return;
		}
		tvSubwayName.setText(sinfo.station.line_num + "호선 ");
		tvSubwayStopName.setText(sinfo.station.station_nm + "\n");
		
		
		Date curTime = new Date();
		SimpleDateFormat cur_format = new SimpleDateFormat("HH:mm",Locale.KOREA);
		SimpleDateFormat print_format = new SimpleDateFormat("mm",Locale.KOREA);
		
		try {
			curTime = cur_format.parse(cur_format.format(curTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if (sinfo.array_tts.size() == 0) {
			tvSubwayNextTime.setText("");
			tvSubwayNextTime2.setText("");

		} else {
			
			long tt = 0;
			try {
				tt = cur_format.parse(sinfo.array_tts.get(0).arr_time).getTime() - curTime.getTime();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tt = tt/1000/60;
			String time1 = tt + "분 전";
			tvSubwayDirName.setText(sinfo.array_tts.get(0).subway_end_name + "행");
			tvSubwayNextTime.setText(time1);

			
			if (sinfo.array_tts.size() == 1) {
				tvSubwayNextTime2.setText("");
			} else {
				
				long tt2 = 0;
				try {
					tt2 = cur_format.parse(sinfo.array_tts.get(1).arr_time).getTime() - curTime.getTime();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				tt2 = tt2/1000/60;
				String time2 = tt2 + "분 전";
				tvSubwayDirName2.setText(sinfo.array_tts.get(1).subway_end_name + "행");
				tvSubwayNextTime2.setText(time2);

			}

		}

	}

	public void refresh() {

		// get current saved information from DB
		subwayArrayList = new ArrayList<SubwayInfo>();
		subwayArrayList = DBManager.getManager(getContext()).getSubwayDBbyLog();

		if (subwayArrayList.size() == 0) 
		{
			setSubwayInfo(null);
		} 
		else 
		{
			SubwayInfo sinfo = subwayArrayList.get(0);
			sinfo = DBManager.getManager(getContext()).getSubwayDB(sinfo);

			if (sinfo.array_tts.size() == 0) 
			{
				SubwayPaser sp = new SubwayPaser();
				sinfo = sp.getTimeTableByID(sinfo);
				DBManager.getManager(getContext()).setSubwayDB(sinfo);
				sinfo = DBManager.getManager(getContext()).getSubwayDB(sinfo);
			}
			setSubwayInfo(sinfo);
		}

	}

	public void setuptest() {

		cbf.getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				SubwayInfo sinfo = new SubwayInfo();

				ArrayList<SubwayStationInfo> array_ssinfo;
				ArrayList<SubwayInfo> array_sinfo;

				SubwayPaser sp = new SubwayPaser();

				array_sinfo = DBManager.getManager(cbf.getActivity())
						.getSubwayDBbyLog();

				if (array_sinfo.size() != 0) {
					sinfo = array_sinfo.get(0);
				} else {
					array_ssinfo = sp.getStationInfoByName("석계");
					sinfo.week_tag = "1";
					sinfo.station = array_ssinfo.get(0);
					sinfo.station.inout_tag = "1";

				}

				sinfo = sp.getTimeTableByID(sinfo);
				DBManager.getManager(cbf.getActivity()).setSubwayDB(sinfo);
				// 이미 저장되어 있으면 불러옴..
				sinfo = DBManager.getManager(cbf.getActivity()).getSubwayDB(
						sinfo);

				setSubwayInfo(sinfo);
			}
		});

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
				// 원하는 실행 엑티비티!
				cbf.startSettingActivity("Subway");
				// setuptest();

				return true;
			}
			return true;
		}
	}

}
