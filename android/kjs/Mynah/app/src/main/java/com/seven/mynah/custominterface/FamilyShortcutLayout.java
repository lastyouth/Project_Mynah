package com.seven.mynah.custominterface;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.seven.mynah.R;
import com.seven.mynah.artifacts.SessionUserInfo;
import com.seven.mynah.database.DBManager;
import com.seven.mynah.globalmanager.GlobalVariable;
import com.seven.mynah.network.AsyncHttpTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FamilyShortcutLayout extends CustomButton{

	private View view;
	private LinearLayout layoutFamilyName;
	private LinearLayout layoutFamilyTime;
	private LinearLayout layoutFamilyInOut;
	private TextView tvFamilyName[];
	private TextView tvFamilyTime[];
	private TextView tvFamilyInOut[];

	private ArrayList<SessionUserInfo> suInfoList;

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
				//핸들링 1일때 할 것 가족등 상태를 받아와야지
				System.out.println("response : "+msg.obj);
				try{
					JSONObject jobj = new JSONObject(msg.obj+"");
					String messageType = jobj.get("messagetype") + "";
					String result = jobj.get("result")+"";
					String attach = jobj.get("attach")+"";

					System.out.println("MT : "+messageType);
					System.out.println("RT : "+result);
					System.out.println("AT : "+attach);

					if(messageType.equals("get_family_inout")){
						if(result.equals("GET_FAMILY_INOUT_FAIL")){
							Toast.makeText(getContext(), "Get Family Inout Fail", Toast.LENGTH_SHORT);
						}
						else if(result.equals("GET_FAMILY_INOUT_SUCCESS")) {
							Toast.makeText(getContext(), "Get Family Inout SUCCESS", Toast.LENGTH_SHORT);
							JSONArray userJarray = new JSONArray(jobj.get("attach")+"");
							//System.out.println("ghgh " + userJarray);

							SessionUserInfo suinfo;
							suInfoList = new ArrayList<SessionUserInfo>();
							for(int i=0; i<userJarray.length(); i++){
								//System.out.println("jarr : " + userJarray.get(i));
								JSONObject userJobj = new JSONObject(userJarray.get(i)+"");

								suinfo = new SessionUserInfo();
								suinfo.userName = userJobj.get("user_name")+"";
								suinfo.inoutTime = ((userJobj.get("inout_time")+"").replace('T', ' ')).replace('Z', ' ');
								suinfo.inHomeFlag = userJobj.get("in_home_flag")+"";

								suInfoList.add(suinfo);
							}
							System.out.println("suinfo ArrayList 추가 성공");
							setFamilyInoutStatus();
						}
						else if(result.equals("GET_FAMILY_INOUT_ERROR")) {
							Toast.makeText(getContext(), "Get Family Inout ERROR", Toast.LENGTH_SHORT);
						}
						else{
							Toast.makeText(getContext(), "Wrong Get Family Inout", Toast.LENGTH_SHORT);
						}
					}
					else {
						Toast.makeText(getContext(), "Wrong Get Family Inout", Toast.LENGTH_SHORT);
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

	//가족 구성원수
	private int numOfFamily = 2;
	
	public FamilyShortcutLayout(Context context, CustomButtonsFragment _cbf) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		cbf = _cbf;
		initView();
	}

	//받아온 정보 숏컷으로
	private void setFamilyInoutStatus(){
		layoutFamilyName = (LinearLayout)view.findViewById(R.id.layoutFamilyName);
		layoutFamilyTime = (LinearLayout)view.findViewById(R.id.layoutFamilyTime);
		layoutFamilyInOut = (LinearLayout)view.findViewById(R.id.layoutFamilyInOut);

		layoutFamilyName.removeAllViews();
		layoutFamilyTime.removeAllViews();
		layoutFamilyInOut.removeAllViews();

		tvFamilyName = new TextView[suInfoList.size()];
		tvFamilyTime = new TextView[suInfoList.size()];
		tvFamilyInOut = new TextView[suInfoList.size()];

		for(int i = 0; i < suInfoList.size(); i++)
		{
			tvFamilyName[i] = new TextView(context);
			tvFamilyTime[i] = new TextView(context);
			tvFamilyInOut[i] = new TextView(context);

			tvFamilyName[i].setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			tvFamilyTime[i].setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			tvFamilyInOut[i].setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

			tvFamilyName[i].setGravity(Gravity.CENTER);
			tvFamilyTime[i].setGravity(Gravity.CENTER);
			tvFamilyInOut[i].setGravity(Gravity.CENTER);

			tvFamilyName[i].setTextColor(Color.parseColor("#ffffff"));
			tvFamilyTime[i].setTextColor(Color.parseColor("#ffffff"));
			tvFamilyInOut[i].setTextColor(Color.parseColor("#ffffff"));

			tvFamilyName[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			tvFamilyTime[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			tvFamilyInOut[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

			layoutFamilyName.addView(tvFamilyName[i]);
			layoutFamilyTime.addView(tvFamilyTime[i]);
			layoutFamilyInOut.addView(tvFamilyInOut[i]);

			tvFamilyName[i].setText(suInfoList.get(i).userName);
			tvFamilyTime[i].setText((suInfoList.get(i).inoutTime).substring(11, 16));

			if("1".equals(suInfoList.get(i).inHomeFlag)){
				tvFamilyInOut[i].setText("IN");
			}
			else{
				tvFamilyInOut[i].setText("OUT");
			}
		}
	}
	
	private void initView() 
	{
		view = inflate(getContext(), R.layout.layout_button_family, null);
		
		getFamilyInOutStatus();
		
		//추후 이부분은 다 xml로 넘길것
		view.setOnTouchListener(new FamilyTouchListener());
		addView(view);
	}

	//서버로 product id 보내서 가족정보 받아오기
	private void getFamilyInOutStatus(){
		System.out.println("getFamilyInOutStatus");
		SessionUserInfo suinfo = new SessionUserInfo();
		suinfo = DBManager.getManager(getContext()).getSessionUserDB();
		System.out.println("Product ID : " + suinfo.productId);

		JSONObject jobj = new JSONObject();

		try {
			jobj.put("messagetype", "get_family_inout");
			jobj.put("product_id", suinfo.productId);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (NullPointerException e){
			e.printStackTrace();
		}

		//AsyncHttpTask aht = new AsyncHttpTask("https://192.168.35.75",jobj);

		new AsyncHttpTask(getContext(), GlobalVariable.WEB_SERVER_IP, mHandler, jobj, 1, 0);
	}
	
	private final class FamilyTouchListener implements OnTouchListener {
		public boolean onTouch(View view, MotionEvent motionEvent) {
			
			Log.d("Touch", "motionEvent.getAction()" + motionEvent.getAction());
			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
				view.setAlpha((float) 0.8);
				return true;
			} else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
				return true;
			} else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
				//test
				Toast.makeText(getContext(), "가족 정보가 클릭되었음.", Toast.LENGTH_SHORT).show();
				view.setAlpha((float) 1.0);
				//원하는 실행 엑티비티!

				getFamilyInOutStatus();

				return true;
			}
			return true;
		}
	}
	
	
	public void refresh() {
		Toast.makeText(getContext(), "Family onRestart()", 1).show();
	}
}
