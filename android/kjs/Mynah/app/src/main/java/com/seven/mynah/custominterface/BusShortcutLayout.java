package com.seven.mynah.custominterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.seven.mynah.R;
import com.seven.mynah.artifacts.BusInfo;
import com.seven.mynah.database.DBManager;
import com.seven.mynah.globalmanager.ServiceAccessManager;
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

		view.setOnTouchListener(new BusTouchListener());
		addView(view);
	}

	public void refresh() {
		//Request service to get bus Information
		BusInfo bInfo = ServiceAccessManager.getInstance().getService().getBusInfo();
		setBusInfo(bInfo);
	}

	private void setBusInfo(BusInfo binfo)  {
		if (binfo == null)
		{
			bRoute = "";
			bStation = "";
			bDir = "터치해서 정보를 입력하세요";
			time1 = "";
			time2 = "";
		}
		else
		{
			bRoute = binfo.route.busRouteNm + " 버스";
			bStation = binfo.station.stNm;
			bDir = binfo.dir + "행\n";

			if (binfo.array_ttb.size() == 0)
			{
				time1 = "차가 없음";
			}
			else if (binfo.array_ttb.size() == 1)
			{
				time1 = getArriveTime(binfo, 0);
			}
			else
			{
				time1 = getArriveTime(binfo, 0);
				time2 = getArriveTime(binfo, 1);
			}
		}

		ivBusImage.setImageResource(R.drawable.ic_bus);
		tvBusRoute.setText(bRoute);
		tvBusStopName.setText(bStation);
		tvBusStopName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
		tvBusStopName.setSelected(true);
		tvBusStopName.setSingleLine();
		tvBusDirName.setText(bDir);
		tvBusNextTime.setText(time1);
		tvBusNextTime2.setText(time2);
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

				cbf.startSettingActivity("Bus");
				return true;
			}
			return true;
		}
	}

	private String getArriveTime(BusInfo binfo, int pos)
	{
		long curTime = System.currentTimeMillis();

		String time = binfo.array_ttb.get(pos).time;
		Date date = new Date();
		SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd hh:mm");

		try {
			date = date_format.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long arriveTime = date.getTime();
		long arriveMinute = (arriveTime - curTime)/1000/60;
		if(arriveMinute == 0)
		{
			time = "접근중";
		}
		else
		{
			time = arriveMinute + "분 전";
		}
		return time;
	}

}