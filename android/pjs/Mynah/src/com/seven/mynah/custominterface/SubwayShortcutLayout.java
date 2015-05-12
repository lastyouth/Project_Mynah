package com.seven.mynah.custominterface;

import java.util.ArrayList;
import java.util.Date;

import com.seven.mynah.R;
import com.seven.mynah.artifacts.BusInfo;
import com.seven.mynah.artifacts.GasAlarmInfo;
import com.seven.mynah.artifacts.SubwayInfo;
import com.seven.mynah.artifacts.SubwayStationInfo;
import com.seven.mynah.artifacts.WeatherInfo;
import com.seven.mynah.artifacts.WeatherLocationInfo;
import com.seven.mynah.database.DBManager;
import com.seven.mynah.infoparser.SubwayPaser;
import com.seven.mynah.infoparser.WeatherParser;

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
		tvSubwayName.setText("1ȣ�� ����");
		tvSubwayStopName.setText("û������\n");
		tvSubwayNextTime.setText("2��");
		tvSubwayNextTime2.setText("5��");
		
		//���� �̺κ��� �� xml�� �ѱ��
		view.setOnTouchListener(new SubwayTouchListener());
		addView(view);
	}
	
	
	private void setSubwayInfo(SubwayInfo sinfo)
	{
		if(sinfo==null) {	
			System.out.println("�ڷ����.");
			return;
			
		}
		tvSubwayName.setText(sinfo.station.line_num + "ȣ�� ");
		tvSubwayStopName.setText(sinfo.station.station_nm);
		
		tvSubwayNextTime.setText(sinfo.array_tts.get(0).arr_time
				+ " " + sinfo.array_tts.get(0).subway_end_name + "��");
		tvSubwayNextTime2.setText(sinfo.array_tts.get(1).arr_time
				+ " " + sinfo.array_tts.get(1).subway_end_name + "��");
		
		
		
	}
	
	
	public void setuptest()
	{
		
		cbf.getActivity().runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				SubwayInfo sinfo = new SubwayInfo();
		    	
				
				ArrayList<SubwayStationInfo> array_ssinfo = new ArrayList<SubwayStationInfo>();
				
				
				//�ϳ��� ��� ������ ��� ���� �ּ� ����
		    	SubwayPaser sp = new SubwayPaser();
		    	array_ssinfo =  sp.getStationInfoByName("����");
		    	sinfo.inout_tag = "1";
		    	sinfo.week_tag  = "1";
		    	sinfo.station = array_ssinfo.get(0);
		    	sinfo = sp.getTimeTableByID(sinfo);
		    
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
				//���ϴ� ���� ��Ƽ��Ƽ!
				//cbf.startSettingActivity("Subway");
				setuptest();
				
				return true;
			}
			return true;
		}
	}

}
