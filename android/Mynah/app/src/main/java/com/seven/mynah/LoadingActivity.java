package com.seven.mynah;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.seven.mynah.R;
import com.seven.mynah.artifacts.SessionUserInfo;
import com.seven.mynah.artifacts.UserProfile;
import com.seven.mynah.artifacts.WeatherLocationInfo;
import com.seven.mynah.backgroundservice.GetInformationService;
import com.seven.mynah.calender.CalendarManager;
import com.seven.mynah.database.DBManager;
import com.seven.mynah.globalmanager.GlobalVariable;
import com.seven.mynah.infoparser.WeatherParser;
import com.seven.mynah.network.AsyncHttpTask;

import org.json.JSONException;
import org.json.JSONObject;

public class LoadingActivity extends Activity{
	
	RelativeLayout rlayout;

	private String deviceID;
	private static String TAG = "LOADING";

	private TextView tvState;

	private static int handler_tag = 0; //서버 닫겨있을 경우 대비 핸들러 플래그 추가

	public static Activity activity;

	//클래스 안에 선언해놓을 것
	protected Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			// IF Sucessfull no timeout
			handler_tag = 1;
			System.out.println("in handler");
			if (msg.what == -1) {
				//   BreakTimeout();
				//ConnectionError();
				System.out.println("handler error");
			}


			if (msg.what == 1) {
				//핸들링 1일때 할 것
				System.out.println("response : "+msg.obj);

				try{
					JSONObject jobj = new JSONObject(msg.obj+"");
					String messageType = jobj.get("messagetype") + "";
					String result = jobj.get("result")+"";

					System.out.println("MT : "+messageType);
					System.out.println("RT : "+result);

					if(messageType.equals("member_check")){
						if(result.equals("IS_NOT_MEMBER")){
//							Toast.makeText(getApplicationContext(), "회원이 아닙니다", Toast.LENGTH_SHORT).show();
							//회원가입 창으로
							Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
							startActivity(intent);
							//여기서 finish 하기 되면 가입을 하더라도 정보 업데이트 안된 상태로 문제가 생김.

						}
						else if(result.equals("IS_MEMBER")) {
//							Toast.makeText(getApplicationContext(), "회원입니다.", Toast.LENGTH_SHORT).show();
							//SQLITE에 레코드 넣고 메인액티비티로
							//내부 세션 유지 테이블에 insert 해야될거같애여

							JSONObject jobj2 = new JSONObject();

							try {
								jobj2.put("messagetype", "get_user_info");
								jobj2.put("device_id", deviceID);
							} catch (JSONException e) {
								e.printStackTrace();
							}

							new AsyncHttpTask(getApplicationContext(), GlobalVariable.WEB_SERVER_IP, mHandler, jobj2, 2, 1);
						}
						else if(result.equals("MEMBER_CHECK_ERROR")) {
							Toast.makeText(getApplicationContext(), "MEMBER CHECK ERRORR", Toast.LENGTH_SHORT).show();
						}
						else {
							Toast.makeText(getApplicationContext(), "WRONG MEMBER CHECK", Toast.LENGTH_SHORT).show();
						}
					}
					else {
						Toast.makeText(getApplicationContext(), "WRONG MEMBER CHECK", Toast.LENGTH_SHORT).show();
					}
				}catch(JSONException e){
					e.printStackTrace();
					handler_tag = 0;
				}

			}

			if (msg.what == 2) {
				//핸들링 2일때 할 것
				//회원인 것이 확인 되면 SQLITE에 회원정보 넣고 MAIn으로
				System.out.println("response : "+msg.obj);

				try {
					JSONObject jobj = new JSONObject(msg.obj + "");
					String messageType = jobj.get("messagetype") + "";
					String result = jobj.get("result") + "";
					String attach = jobj.get("attach") + "";
					System.out.println("MT : " + messageType);
					System.out.println("RT : " + result);
					System.out.println("AT : " + attach);

					if (messageType.equals("get_user_info")) {
						if (result.equals("GET_USER_INFO_FAIL")) {
							Toast.makeText(getApplicationContext(), "Get user info Fail", Toast.LENGTH_SHORT).show();
						}
						else if(result.equals("GET_USER_INFO_SUCCESS")){
							//System.out.println("ja sal gak");
//							Toast.makeText(getApplicationContext(), "Get user info success", Toast.LENGTH_SHORT).show();

							//user info 받은거 세션 테이블로 집어넣기 ㄱㄱ
							JSONObject user_jobj = new JSONObject(jobj.get("attach")+"");

							SessionUserInfo suinfo = new SessionUserInfo();
							suinfo.userId = user_jobj.get("user_id")+"";
							suinfo.productId = user_jobj.get("product_id")+"";
							suinfo.registrationId = user_jobj.get("registration_id")+"";
							suinfo.userName = user_jobj.get("user_name")+"";
							suinfo.genderFlag = user_jobj.get("gender_flag")+"";
							suinfo.representativeFlag = user_jobj.get("representative_flag")+"";
							suinfo.inHomeFlag = user_jobj.get("in_home_flag")+"";
							suinfo.deviceId = user_jobj.get("device_id")+"";
							suinfo.inoutTime = ((user_jobj.get("inout_time")+"").replace('Z', ' ')).replace('T', ' ');

							DBManager.getManager(getApplicationContext()).setSessionUserDB(suinfo);
							System.out.println("세션 저장 성공");

							//Toast.makeText(getApplicationContext(), suinfo.userName + "님 고마 어서오이소", Toast.LENGTH_SHORT).show();

							Loading();
							//Intent intent = new Intent(getApplicationContext(), MainActivity.class);
							//startActivity(intent);
							//finish();
						}
						else if(result.equals("GET_USER_INFO_ERROR")){
							Toast.makeText(getApplicationContext(), "Get user info Error", Toast.LENGTH_SHORT).show();
						}
						else{
							Toast.makeText(getApplicationContext(), "Wrong get user info", Toast.LENGTH_SHORT).show();
						}
					}
					else{
						Toast.makeText(getApplicationContext(), "Wrong get user info", Toast.LENGTH_SHORT).show();
					}
				}
				catch(JSONException e){
					e.printStackTrace();
					handler_tag = 0;
				}
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		activity = LoadingActivity.this;

		setContentView(R.layout.activity_logo);

		tvState = (TextView)findViewById(R.id.tvState);

		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

	@Override
	protected void onResume() {
		super.onResume();
		//세션 데이터 지우는 부분 들어가야 할 것 같아
		DBManager.getManager(getApplicationContext()).deleteSessionUser();
		System.out.println("세션 비워야지");

		//서버에 접근해서 회원인지 물어보는 부분

		//device id 받아오기
		deviceID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
		//dummy
		//deviceID = "test_device_id_2";
		Log.d(TAG, "DEVICE ID : " + deviceID);

		JSONObject jobj = new JSONObject();

		try{
			jobj.put("messagetype", "member_check");
			jobj.put("device_id", deviceID);
		}catch(JSONException e){
			e.printStackTrace();
		}

		tvState.setText("서버에 접속 중 입니다...");
		new AsyncHttpTask(getApplicationContext(), GlobalVariable.WEB_SERVER_IP, mHandler, jobj, 1, 0);
		Handler tempHandler = new Handler()
		{
			public void handleMessage(Message msg)
			{
				if(handler_tag == 0)
				{
					Log.d(TAG, "handler 작동 X 서버 timeout 추측 (5초)");
					tvState.setText("서버에 응답이 없어 임시 ID로 접속합니다.");
					Toast.makeText(getApplicationContext(), "서버에 응답이 없어 임시 ID로 접속합니다.", Toast.LENGTH_SHORT).show();

					GlobalVariable.isServerOn = false;

					try {
						Thread.sleep(500);
					}
					catch (InterruptedException e)
					{
						Log.d(TAG, e.getMessage());
					}
					Loading();
				}

			}

		};
		tempHandler.sendEmptyMessageDelayed(0,3500);
	}

	public void Loading()
	{
		SessionUserInfo suinfo = DBManager.getManager(getApplicationContext()).getSessionUserDB();

		if(suinfo.userId == null)
		{
			createTempUser();
		}

		loadWeatherLocation();

		Handler handler = new Handler()
		{
			public void handleMessage(Message msg)
			{
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				//Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
				startActivity(intent);
				finish();
			}
		};
		tvState.setText("완료하였습니다.");
		handler.sendEmptyMessageDelayed(0, 500);

	}
	
	private void createTempUser()
	{
//		UserProfile up = new UserProfile();
//		up.id = "pika";
//		up.passwd = "";
//		up.name = "김진성";
//		up.inout = 1;
//		up.mac_address = "";
//		up.usertype = 1;
//		up.usertype = GlobalVariable.UserType.me;
//		up.mastertype = GlobalVariable.UserType.master;
//
//		DBManager.getManager(this).setMainUserDB(up);
//

		tvState.setText("임시 유저를 생성합니다.");
		try {
			Thread.sleep(500);
		}
		catch (InterruptedException e)
		{
			Log.d(TAG, e.getMessage());
		}
		SessionUserInfo suinfo = new SessionUserInfo();
		suinfo.userId = "tempid";
		suinfo.productId = "temp_product_id";
		suinfo.registrationId = "temp_registration_id";
		suinfo.userName = "pika";
		suinfo.genderFlag = "";
		suinfo.representativeFlag = "";
		suinfo.inHomeFlag = "in_home_flag";
		suinfo.deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
		suinfo.inoutTime = "";

		DBManager.getManager(getApplicationContext()).setSessionUserDB(suinfo);


	}
	
	private void loadWeatherLocation()
	{
		tvState.setText("날씨 지역 정보를 불러옵니다.");
		try {
			Thread.sleep(500);
		}
		catch (InterruptedException e)
		{
			Log.d(TAG, e.getMessage());
		}
		if(DBManager.getManager(this).isSetWeatherLocation())
		{
			//지역정보 설정되어 있음.
		}
		else
		{
			tvState.setText("날씨 지역 정보를 다운로드합니다.");
			try {
				Thread.sleep(500);
			}
			catch (InterruptedException e)
			{
				Log.d(TAG, e.getMessage());
			}
			WeatherParser wp = new WeatherParser();
	    	ArrayList<WeatherLocationInfo> array_location; 
	    	array_location = wp.getAllLocationInfo();
	    	DBManager.getManager(this).setWeatherLocationAll(array_location);
		}
		
	}
}
