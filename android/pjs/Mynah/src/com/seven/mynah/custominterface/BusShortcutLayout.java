package com.seven.mynah.custominterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.seven.mynah.R;
import com.seven.mynah.artifacts.BusInfo;
import com.seven.mynah.database.DBManager;
import com.seven.mynah.infoparser.BusPaser;

public class BusShortcutLayout extends CustomButton {

	private View view;
	private ImageView ivBusImage;
	private TextView tvBusRoute;
	private TextView tvBusStopName;
	private TextView tvBusDirName;
	private TextView tvBusNextTime;
	private TextView tvBusNextTime2;
	private TextView tvCurrentBusRoute;

	private ArrayList<BusInfo> busArrayList;

	private String bRoute;
	private String bDir;
	private String bStation;
	private String time1;
	private String time2;

	public BusShortcutLayout(Context context, CustomButtonsFragment _cbf) {
		super(context);
		// TODO Auto-generated constructor stub
		cbf = _cbf;
		// refresh();
		initView();
	}

	private void initView() {
		view = inflate(getContext(), R.layout.layout_button_bus, null);

		ivBusImage = (ImageView) view.findViewById(R.id.ivBusImage);
		tvBusRoute = (TextView) view.findViewById(R.id.tvBusRoute);

		// (TextView)view.findViewById(R.id.tvCurrentBusRoute);
		tvBusStopName = (TextView) view.findViewById(R.id.tvBusStopName);
		tvBusNextTime = (TextView) view.findViewById(R.id.tvBusNextTime);
		tvBusNextTime2 = (TextView) view.findViewById(R.id.tvBusNextTime2);
		tvBusDirName = (TextView) view.findViewById(R.id.tvBusDirName);

		refresh();
		// 추후 이부분은 다 xml로 넘길것
		view.setOnTouchListener(new BusTouchListener());
		addView(view);
	}

	private void setBusInfo(BusInfo binfo) {
		if (binfo == null) {
			// 초기화
			bRoute = "";
			bStation = "";
			bDir = "터치해서 정보를 입력하세요";
			time1 = "";
			time2 = "";
		} else {
			bRoute = binfo.route.busRouteNm + " 버스";
			bStation = binfo.station.stNm;
			bDir = binfo.dir + "행\n";
			
			//bStation = binfo.station.stNm;
			
			Date date = new Date();
			SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			long curTime = System.currentTimeMillis();
			long arriveTime;
			
			
			if (binfo.array_ttb.size() == 0) {
				time1 = "차가 없음";
				time2 = "";
			} else if (binfo.array_ttb.size() == 1) {
				time1 = binfo.array_ttb.get(0).time;
				try {
					date = date_format.parse(time1);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				arriveTime = date.getTime();
				time1 = ((arriveTime - curTime)/1000/60) + "분 전";
				
			} else {
				time1 = binfo.array_ttb.get(0).time;
				try {
					date = date_format.parse(time1);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				arriveTime = date.getTime();
				time1 = ((arriveTime - curTime)/1000/60) + "분 전";
				
				time2 = binfo.array_ttb.get(1).time;
				try {
					date = date_format.parse(time2);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				arriveTime = date.getTime();
				time2 = ((arriveTime - curTime)/1000/60) + "분 전";
			}
		}
		ivBusImage.setImageResource(R.drawable.ic_bus);
		tvBusRoute.setText(bRoute);
		tvBusStopName.setText(bStation);
		tvBusDirName.setText(bDir);
		tvBusNextTime.setText(time1);
		tvBusNextTime2.setText(time2);
	}

	public void refresh() {

		// get current saved information from DB
		busArrayList = new ArrayList<BusInfo>();
		busArrayList = DBManager.getManager(getContext()).getBusDBbyLog();

		if (busArrayList.size() == 0) 
		{
			setBusInfo(null);
		}
		else 
		{
			BusInfo binfo = busArrayList.get(0);
			binfo = DBManager.getManager(getContext()).getBusDB(binfo);

			if (binfo.array_ttb.size() == 0) 
			{
				BusPaser bp = new BusPaser();
				binfo = bp.getBusArrInfoByRoute(binfo);
				DBManager.getManager(getContext()).setBusDB(binfo);
				binfo = DBManager.getManager(getContext()).getBusDB(binfo);
			}
			setBusInfo(binfo);
		}

	}

	public View getBusView() {
		return view;
	}

	
	private final class BusTouchListener implements OnTouchListener {
		public boolean onTouch(View view, MotionEvent motionEvent) {

			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
				view.setAlpha((float) 0.8);
				return true;
			} else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
				return true;
			} else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
				view.setAlpha((float) 1.0);
				// 원하는 실행 엑티비티!
				// refresh();
				cbf.startSettingActivity("Bus");
				return true;
			}
			return true;
		}
	}
	
}
