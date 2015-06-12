package com.seven.mynah.custominterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.seven.mynah.R;
import com.seven.mynah.artifacts.GasAlarmInfo;
import com.seven.mynah.artifacts.SessionUserInfo;
import com.seven.mynah.artifacts.WeatherInfo;
import com.seven.mynah.artifacts.WeatherLocationInfo;
import com.seven.mynah.database.DBManager;
import com.seven.mynah.globalmanager.GlobalVariable;
import com.seven.mynah.globalmanager.ServiceAccessManager;
import com.seven.mynah.infoparser.WeatherParser;
import com.seven.mynah.network.AsyncHttpTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GasAlarmShortcutLayout extends CustomButton{

	private View view;
	private ImageView ivGasStatus;
	private TextView tvGasOnOff;
	private TextView tvGasTemperature;

	private final int GAS_OFF = 1;
	private final int GAS_WARNING = 2;
	private final int GAS_WARNING2 = 3;

	private int gasTemperature;

	//클래스 안에 선언해놓을 것
	protected Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			// IF Sucessfull no timeout

			//여기서는 이런식으로 what에 헨들링 넘버 넣어놨으니까 그거에 맞는 동작하면 됨.
			System.out.println("in handler");
			if (msg.what == -1) {
				//   BreakTimeout();
				//ConnectionError();
			}


			if (msg.what == 1) {
				//핸들링 1일때 할 것 온도
				System.out.println("response : "+msg.obj);
				try{
					JSONObject jobj = new JSONObject(msg.obj+"");
					String messageType = jobj.get("messagetype") + "";
					String result = jobj.get("result")+"";
					String attach = jobj.get("attach")+"";

					System.out.println("MT : "+messageType);
					System.out.println("RT : "+result);
					System.out.println("AT : "+attach);

					if(messageType.equals("get_temperature")){
						if(result.equals("GET_TEMPERATURE_FAIL")){
							Toast.makeText(getContext(), "온도 참조 실패", Toast.LENGTH_SHORT);
						}
						else if(result.equals("GET_TEMPERATURE_SUCCESS")) {
							Toast.makeText(getContext(), "온도 참조 성공", Toast.LENGTH_SHORT);
							JSONObject tmpJobj = new JSONObject(jobj.get("attach")+"");

							gasTemperature = Integer.parseInt(tmpJobj.get("temperature")+"");
							refreshByServer(gasTemperature);
							//System.out.println("ghgh " + userJarray);
						}
						else if(result.equals("GET_TEMPERATURE_ERROR")) {
							Toast.makeText(getContext(), "온도 참조 에러", Toast.LENGTH_SHORT);
						}
						else{
							Toast.makeText(getContext(), "잘못된 온도 참조", Toast.LENGTH_SHORT);
						}
					}
					else {
						Toast.makeText(getContext(), "잘못된 온도 참조", Toast.LENGTH_SHORT);
					}
				}catch(JSONException e){
					e.printStackTrace();
				}

			}

			if (msg.what == 2) {
				//핸들링 2일때 할 것
			}
		}
	};

	public GasAlarmShortcutLayout(Context context, CustomButtonsFragment _cbf) {
		super(context);
		// TODO Auto-generated constructor stub
		cbf = _cbf;
		initView();
	}

	private void initView()
	{
		view = inflate(getContext(), R.layout.layout_button_gas, null);
		ivGasStatus = (ImageView)view.findViewById(R.id.ivGasImage);
		tvGasOnOff = (TextView)view.findViewById(R.id.tvGasOnOff);
		tvGasTemperature = (TextView)view.findViewById(R.id.tvGasTemperature);

		//if gas is on then ic_gas_warning2, tvGasonOff.setText("ON");
		ivGasStatus.setImageResource(R.drawable.ic_gas);

		view.setOnTouchListener(new GasAlarmTouchListener());
		addView(view);
	}

	public void refresh() {
		// Request service to get temperature
		getTemperature();
		/*
		int temp = ServiceAccessManager.getInstance().getService().getTempData();
		setGasAlarmInfo(temp);*/
	}

	public void refreshByServer(int temp){
		setGasAlarmInfo(temp);
	}

	public void setGasAlarmInfo(int temp)
	{
		if(temp == 0)
		{
			tvGasTemperature.setText("받아오는 중입니다.");
			tvGasTemperature.setTextSize(14);
			return;
		}
		tvGasTemperature.setTextSize(30);
		tvGasTemperature.setText(temp + "°C");
		if(isFired(temp))
		{
			tvGasOnOff.setText("ON");
			setImageOnOff(GAS_WARNING);
		}
		else
		{
			tvGasOnOff.setText("OFF");
			setImageOnOff(GAS_OFF);
		}
	}

	public boolean isFired(int temp)
	{
		if(temp >= 35)
			return true;
		else
			return false;
	}

	private void setImageOnOff(int type)
	{
		switch (type)
		{
			case GAS_OFF:
				ivGasStatus.setImageResource(R.drawable.ic_gas);
				break;

			case GAS_WARNING:
				ivGasStatus.setImageResource(R.drawable.ic_gas_warning2);
				break;
			case GAS_WARNING2:
				ivGasStatus.setImageResource(R.drawable.ic_gas_warning);
				break;
		}

	}

	//서버에서 온도정보를 받아와 by product_id
	private void getTemperature(){
		System.out.println("getTemperature");
		SessionUserInfo suinfo = new SessionUserInfo();
		suinfo = DBManager.getManager(getContext()).getSessionUserDB();
		System.out.println("Product ID : " + suinfo.productId);

		JSONObject jobj = new JSONObject();

		try {
			jobj.put("messagetype", "get_temperature");
			jobj.put("product_id", suinfo.productId);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (NullPointerException e){
			e.printStackTrace();
		}

		new AsyncHttpTask(getContext(), GlobalVariable.WEB_SERVER_IP, mHandler, jobj, 1, 0);
	}

	private final class GasAlarmTouchListener implements OnTouchListener {
		public boolean onTouch(View view, MotionEvent motionEvent) {

			Log.d("Touch", "motionEvent.getAction()" + motionEvent.getAction());
			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
				view.setAlpha((float) 0.8);
				return true;
			} else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
				return true;
			} else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
				//Toast.makeText(getContext(), "가스 정보가 클릭되었음.", Toast.LENGTH_SHORT).show();
				view.setAlpha((float) 1.0);
				getTemperature();

				//refresh();
				return true;
			}
			return true;
		}
	}



}
