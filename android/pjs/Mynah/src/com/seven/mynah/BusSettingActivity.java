package com.seven.mynah;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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
   private BusStationAdapter adapter;
   
   private BusRouteInfo busRouteInfo;
   
   private boolean mIsBackKeyPressed = false;
   
   protected void onCreate(Bundle savedInstanceState) 
   {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_bus);
        
        etBusName = (EditText)findViewById(R.id.etBusName);
        lvBusStop = (ListView)findViewById(R.id.lvBusStop);
        
        ivBusNameSearch = (ImageView)findViewById(R.id.ivBusNameSearch);
        ivBusNameSearch.setOnClickListener(new OnClickListener() {
           @Override
         public void onClick(View v) {
            // TODO Auto-generated method stub
              String bus_route = etBusName.getText().toString().trim();
              
              Toast.makeText(getApplicationContext(), "ivSearch Clicked: " + bus_route, Toast.LENGTH_SHORT).show();
              
              BusPaser bp = new BusPaser();
              ArrayList<BusRouteInfo> array_route = new ArrayList<BusRouteInfo>();
              ArrayList<BusStationInfo> array_station = new ArrayList<BusStationInfo>();
              array_route = bp.getBusRouteList(bus_route);
              
              busRouteInfo = array_route.get(0);
              array_station = bp.getStaionsByRouteList(busRouteInfo.busRouteId);
              
              adapter = new BusStationAdapter(getApplicationContext(), R.layout.list_row, array_station);
              lvBusStop.setAdapter(adapter);
              adapter.notifyDataSetChanged();
              
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
                Toast.makeText(getApplicationContext(), stNm, Toast.LENGTH_LONG).show();
                
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
      }
   }
   
   
   /*
   @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		finish();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);

	}*/
   
   

   
   private class setBusNameListener implements OnClickListener
   {

      @Override
      public void onClick(View v) {
         // TODO Auto-generated method stub
         /*Call Dialog
         FragmentManager fm = getFragmentManager();
         BusNameDialogFragment dialogFragment = new BusNameDialogFragment();
         dialogFragment.show(fm, "dialogFragment");
         */
      }
      
   }
}


