package com.seven.mynah;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.seven.mynah.artifacts.SessionUserInfo;
import com.seven.mynah.database.DBManager;
import com.seven.mynah.globalmanager.GlobalVariable;
import com.seven.mynah.globalmanager.ServiceAccessManager;
import com.seven.mynah.network.AsyncHttpTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class GlobalSettingActivity extends Activity{
	
	private boolean mIsBackKeyPressed = false;
	private ListView lvSetting;
	private ArrayList<String[]> mArrayList;
	private SettingListAdapter mAdapter;

	private int SCHEDULE = GlobalVariable.SCHEDULE;
	private int BUS = GlobalVariable.BUS;
	private int SUBWAY= GlobalVariable.SUBWAY;
	private int WEATHER = GlobalVariable.WEATHER;
	private int GAS = GlobalVariable.GAS;
	private int ttsList[] = {SCHEDULE, BUS, SUBWAY, WEATHER, GAS};
	private int ttsStatus;
	private SharedPreferences p;

	//클래스 안에 선언해놓을 것
	protected Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			// IF Sucessfull no timeout
			System.out.println("in handler");
			if (msg.what == -1) {
				//   BreakTimeout();
				//ConnectionError();
				System.out.println("handler error");
			}


			if (msg.what == 1) {
				//핸들링 1일때 할 것
				//회원탈퇴가 잘 되었는지 확인
				System.out.println("response : "+msg.obj);
				try{
					JSONObject jobj = new JSONObject(msg.obj+"");
					String messageType = jobj.get("messagetype") + "";
					String result = jobj.get("result")+"";
					String attach = jobj.get("attach")+"";

					System.out.println("MT : "+messageType);
					System.out.println("RT : "+result);
					System.out.println("AT : "+attach);

					if(messageType.equals("delete_user")){
						if(result.equals("DELETE_USER_FAIL")){
							Toast.makeText(getApplicationContext(), "회원 탈퇴를 실패했습니다.", Toast.LENGTH_SHORT).show();
						}
						else if(result.equals("DELETE_USER_SUCCESS")) {
							Toast.makeText(getApplicationContext(), "회원 탈퇴를 성공했습니다.", Toast.LENGTH_SHORT).show();
							SharedPreferences pref = getSharedPreferences(ServiceAccessManager.PREF,MODE_PRIVATE);
							SharedPreferences.Editor editor = pref.edit();

							editor.putString("RPI_UUID","NULL");
							editor.putString("RPI_MAC", "NULL");

							editor.commit();

							ServiceAccessManager.getInstance().setDeleteFlag(true);

							finish();

							//앱 끄기

						}
						else if(result.equals("DELETE_USER_ERROR")) {
							Toast.makeText(getApplicationContext(), "회원탈퇴간 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
						}
						else {
							Toast.makeText(getApplicationContext(), "잘못된 회원탈퇴 접근입니다.", Toast.LENGTH_SHORT).show();
						}
					}
					else {
						Toast.makeText(getApplicationContext(), "잘못된 회원탈퇴 접근입니다.", Toast.LENGTH_SHORT).show();
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_app);

	}

	@Override
	protected void onResume() {
		super.onResume();

		lvSetting = (ListView)findViewById(R.id.lvSetting);

		mArrayList = new ArrayList<String[]>();
		String setting1[] = {"TTS 설정", "현재 선택된 TTS 정보"};
		String setting2[] = {"제품 연결 해제", "product2"};

		ArrayList<String> currentSetting = getCurrentTTSSetting();
		String settingTTS = "";
		for(int i = 0; i < currentSetting.size(); i++)
		{
			settingTTS += currentSetting.get(i) + " ";
		}
		if(settingTTS.trim().equals(""))
		{
			setting1[1] = "선택된 TTS 정보가 없습니다.";
		}
		else
		{
			setting1[1] = settingTTS;
		}


		mArrayList.add(setting1);
		mArrayList.add(setting2);

		mAdapter = new SettingListAdapter(getApplicationContext(), R.layout.list_row_setting, mArrayList);
		lvSetting.setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();

		lvSetting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(position == 0)
				{
					Intent intent = new Intent(getApplicationContext(), ChooseTTSActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
				}
				else if(position == 1)
				{
					//Dialog Are you sure?
					DialogConfirm();
					//Request unregister to server
					//deleteUser();

				}
			}
		});
	}

	private void deleteUser(){
		SessionUserInfo suInfo = DBManager.getManager(getApplicationContext()).getSessionUserDB();

		JSONObject jobj = new JSONObject();

		try {
			jobj.put("messagetype", "delete_user");
			jobj.put("device_id", suInfo.deviceId);
		}
		catch(JSONException e){
			e.printStackTrace();
		}
		new AsyncHttpTask(getApplicationContext(), GlobalVariable.WEB_SERVER_IP, mHandler, jobj, 1, 0);
	}

	public ArrayList<String> getCurrentTTSSetting()
	{
		p = getSharedPreferences(ServiceAccessManager.TSTAT, MODE_PRIVATE);
		SharedPreferences.Editor ed = p.edit();
		ttsStatus = p.getInt("status", 31);
		String sList[] = {"일정", "버스", "지하철", "날씨", "가스불"};
		ArrayList<String> setting = new ArrayList<String>();

		for(int i = 0; i < 5; i++)
		{
			int s = ttsStatus & ttsList[i];
			String flag;
			if(s != 0)
			{
				setting.add(sList[i]);
			}
		}

		return setting;
	}

	private void DialogConfirm(){
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
		alt_bld.setMessage("정말 해제하시겠습니까?").setCancelable(
				false).setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// Action for 'Yes' Button
						deleteUser();
					}
				}).setNegativeButton("No",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// Action for 'NO' Button
						dialog.cancel();
					}
				});
		AlertDialog alert = alt_bld.create();
		alert.show();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		
		if(mIsBackKeyPressed == false)
		{
			mIsBackKeyPressed = true;
			finish();
			overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
		}
	}

}