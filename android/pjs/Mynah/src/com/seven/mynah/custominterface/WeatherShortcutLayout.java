package com.seven.mynah.custominterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.w3c.dom.Text;

import android.content.Context;
import android.os.DeadObjectException;
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
	private TextView tvReh; // ����
	private TextView tvPop; // ����Ȯ��
	private TextView tvUpdateTime;
	private TextView tvHour;
	
	SimpleDateFormat printDateFormat;
	SimpleDateFormat defaultDateFormat;
	
	
	public WeatherShortcutLayout(Context context, CustomButtonsFragment _cbf) {
		super(context);
		// TODO Auto-generated constructor stub
		cbf = _cbf;
		printDateFormat = new SimpleDateFormat("HH:mm");
		defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		initView();
	}
	
	
	private void initView() {
		
		view = inflate(getContext(), R.layout.layout_button_weather, null);
		ivWeatherImage = (ImageView)view.findViewById(R.id.ivWeatherImage);
		tvWeatherType = (TextView)view.findViewById(R.id.tvWeatherType);
		
		tvPlace = (TextView)view.findViewById(R.id.tvWeatherPlace);
		tvPlace2 = (TextView)view.findViewById(R.id.tvWeatherPlace2);
		tvTemper = (TextView)view.findViewById(R.id.tvWeatherTemper);
		
		tvReh = (TextView)view.findViewById(R.id.tvReh);
		
		tvPop = (TextView)view.findViewById(R.id.tvPop);
		tvUpdateTime = (TextView)view.findViewById(R.id.tvUpdateTime);
		
		tvHour = (TextView)view.findViewById(R.id.tvHour);
		
		
		//ivWeatherImage.setImageResource(R.drawable.ic_umbrella);
		setWeatherImage(4);
		
		tvWeatherType.setText("��(�帲)");
		tvPlace.setText("���빮��");
		tvTemper.setText("12��C");
		tvPop.setText("89%");
		
		
		//���� �̺κ��� �� xml�� �ѱ��
		view.setOnTouchListener(new WeatherTouchListener());
		addView(view);
		
	}
	
	public void refresh()
	{
		
	}
	
	
	private void setWeatherInfo(WeatherInfo winfo)
	{
		Date date = new Date();
		if(winfo==null) {
			System.out.println("�ڷ����.");
			return;
			
		}
		
		tvPlace.setText(winfo.location.city_name);
		tvPlace2.setText(winfo.location.top_name + " " + winfo.location.mdl_name);
		tvTemper.setText(winfo.array_ttw.get(0).temp + "��C");
		tvPop.setText(winfo.array_ttw.get(0).pop + "%");
		tvReh.setText("���� : " + winfo.array_ttw.get(0).reh + "%"); 
		tvWeatherType.setText(winfo.array_ttw.get(0).wfKor);
		
		try {
			date = defaultDateFormat.parse(winfo.array_ttw.get(0).hour);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tvHour.setText(printDateFormat.format(date));
		//tvUpdateTime.setText("������Ʈ : " + winfo.last_update);
		//�̹��� Ÿ�� �ֱ�
		setWeatherImage(Integer.valueOf(winfo.array_ttw.get(0).sky));
		
	}
	
	public void setuptest()
	{
		
		cbf.getActivity().runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				WeatherInfo winfo = new WeatherInfo();
//		    	
//		    	
//		    	WeatherParser wp = new WeatherParser();
//		    	winfo = wp.getWeatherInfo(winfo);
//		    	
//		    	
		    	ArrayList<WeatherLocationInfo> array_location; 
//		    	ArrayList<WeatherLocationInfo> array_location2; 
//		    	
//		    	
//		    	WeatherLocationInfo location2;
//		    	WeatherLocationInfo location3;
//		    	
//		    	//�ҷ�����κ�
//		    	//array_location = wp.getAllLocationInfo();
//		    	//db ����
//		    	//DBManager.getManager(this).setWeatherLocationAll(array_location);
//		    	array_location = DBManager.getManager(cbf.getActivity()).getWeatherLocationByName("����");
//		    	
//		    	winfo.location = array_location.get(0);
//		    	
//		    	
//		    	winfo = wp.getWeatherInfo(winfo);
//		    	
//		    	DBManager.getManager(cbf.getActivity()).setWeatherDB(winfo);
//		    	
//		    	winfo = new WeatherInfo();
//		    	winfo.location = array_location.get(0);
//		    	
//		    	winfo = DBManager.getManager(cbf.getActivity()).getWeatherDB(winfo);
//		    
//		    	
//		    	//DBManager.getManager(cbf.getActivity()).setWeatherLocationDBbyLog(arr)
//		    	
//		    	array_location2 = DBManager.getManager(cbf.getActivity()).getWeatherDBbyLog();
//		    	
//		    	
				array_location = DBManager.getManager(cbf.getActivity()).getWeatherLocationByName("����");
				
				DBManager.getManager(cbf.getActivity()).setWeatherLocationDBbyLog(array_location.get(1));
				
		    	winfo = DBManager.getManager(cbf.getActivity()).getWeatherDB(winfo);
		    	
		    	setWeatherInfo(winfo);
			}
		});
		
	}
	
	private void setWeatherImage(int type) 
	{
		
		switch(type) {
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
				//���ϴ� ���� ��Ƽ��Ƽ!
				//cbf.startSettingActivity("Weather");
				//cbf.startSettingActivity_temp("Weather");
				setuptest();
				return true;
			}
			return true;
		}
	}
	

}
