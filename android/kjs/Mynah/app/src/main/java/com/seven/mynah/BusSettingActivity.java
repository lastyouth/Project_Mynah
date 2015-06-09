package com.seven.mynah;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.seven.mynah.artifacts.BusInfo;
import com.seven.mynah.artifacts.BusRouteInfo;
import com.seven.mynah.artifacts.BusStationInfo;
import com.seven.mynah.database.DBManager;
import com.seven.mynah.infoparser.BusPaser;

public class BusSettingActivity extends Activity {
   
   private ImageView ivBusNameSearch;
   private ListView lvBusStop;
   private EditText etBusName;
   private TextView tvCurrentBusRoute;
   private BusStationAdapter adapter;
   
  
   private BusRouteInfo busRouteInfo;
   
   //for tvCurrentBusRoute
   private BusInfo binfo;
   private ArrayList<BusInfo> busArrayList;
   
   private boolean mIsBackKeyPressed = false;
   
   private String TAG = "BusSettingActivity";
   
   protected void onCreate(Bundle savedInstanceState) 
   {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_bus);
        
        etBusName = (EditText)findViewById(R.id.etBusName);
        lvBusStop = (ListView)findViewById(R.id.lvBusStop);
        tvCurrentBusRoute = (TextView)findViewById(R.id.tvCurrentBusRoute);
        
        busArrayList = new ArrayList<BusInfo>();
        busArrayList = DBManager.getManager(getApplicationContext()).getBusDBbyLog();
        
        
        if(busArrayList.size() != 0)
        {
        	binfo = busArrayList.get(0);
            binfo = DBManager.getManager(getApplicationContext()).getBusDB(binfo);

            if (binfo.array_ttb.size() == 0)
            {
                BusPaser bp = new BusPaser();
                binfo = bp.getBusArrInfoByRoute(binfo);
                DBManager.getManager(getApplicationContext()).setBusDB(binfo);
                binfo = DBManager.getManager(getApplicationContext()).getBusDB(binfo);
            }
            String str = binfo.route.busRouteNm;
           
            tvCurrentBusRoute.setText(str);

        }
        
        
        
        ivBusNameSearch = (ImageView)findViewById(R.id.ivBusNameSearch);
        ivBusNameSearch.setOnClickListener(new OnClickListener() {
           @Override
         public void onClick(View v) {
            // TODO Auto-generated method stub
              String bus_route = etBusName.getText().toString().trim();

               if(bus_route.equals(""))
               {
                   bus_route = tvCurrentBusRoute.getText().toString();
               }

              BusPaser bp = new BusPaser();
              ArrayList<BusRouteInfo> array_route = new ArrayList<BusRouteInfo>();
              ArrayList<BusStationInfo> array_station = new ArrayList<BusStationInfo>();
              array_route = bp.getBusRouteList(bus_route);

              //if there is no such bus route
              if(array_route.size() == 0)
              {
                  showSearchError();
              }
              else
              {
                  busRouteInfo = array_route.get(0);
                  array_station = bp.getStaionsByRouteList(busRouteInfo.busRouteId);

                  adapter = new BusStationAdapter(getApplicationContext(), R.layout.list_row, array_station);
                  lvBusStop.setAdapter(adapter);
                  adapter.notifyDataSetChanged();
              }
           }
        });
        
        lvBusStop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                
                // TODO 아이템 클릭시에 구현할 내용은 여기에.
                BusInfo binfo = new BusInfo();
                
                ViewHolder vh = (ViewHolder)view.getTag();
                
                binfo.route = busRouteInfo;
                binfo.station = vh.busStationInfo;
                
                String stNm = vh.tvBusStopNameListRow.getText().toString();
                DBManager.getManager(getApplicationContext()).setBusDBbyLog(binfo);
                
                finish();
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
            }
        });
        
    }
   
   @Override
   public void onBackPressed() {
      // TODO Auto-generated method stub
      super.onBackPressed();
      
      if(mIsBackKeyPressed == false)
      {
         mIsBackKeyPressed = true;
         finish();
         overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
         
         Log.d(TAG, "onBackPressed");
      }
   }
   
   public void showSearchError()
   {
       String bus_route = etBusName.getText().toString();
       AlertDialog alertDialog = new AlertDialog.Builder(this).create();
       alertDialog.setTitle("");
       alertDialog.setMessage(bus_route + "버스를 찾을수 없습니다.");
       alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "확인", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int which)
           {
               etBusName.setText("");
               dialog.dismiss();
           }
       });

       alertDialog.show();
   }

   /*
   @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		finish();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);

	}*/
   

}


