package com.seven.mynah;

import java.util.ArrayList;

import com.seven.mynah.artifacts.BusRouteInfo;
import com.seven.mynah.artifacts.BusStationInfo;
import com.seven.mynah.infoparser.BusPaser;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class BusSettingActivity extends Activity {
	
	private ImageView ivSearch;
	private ListView lvBusStop;
	private EditText etBusName;
	private BusStationAdapter adapter;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_bus);
        
        etBusName = (EditText)findViewById(R.id.etBusName);
        lvBusStop = (ListView)findViewById(R.id.lvBusStop);
        
        ivSearch = (ImageView)findViewById(R.id.ivSearch);
        ivSearch.setOnClickListener(new OnClickListener() {
        	@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
        		Editable bus_route = etBusName.getText();
   
        		Toast.makeText(getApplicationContext(), "ivSearch Clicked: " + bus_route, Toast.LENGTH_SHORT).show();
        		BusPaser bp = new BusPaser();
        		ArrayList<BusRouteInfo> array_route = new ArrayList<BusRouteInfo>();
        		ArrayList<BusStationInfo> array_station = new ArrayList<BusStationInfo>();
        		array_route = bp.getBusRouteList(bus_route.toString());
        		array_station = bp.getStaionsByRouteList(array_route.get(0).busRouteId);
        		
        		adapter = new BusStationAdapter(getApplicationContext(), R.layout.list_row, array_station);
        		lvBusStop.setAdapter(adapter);
        		adapter.notifyDataSetChanged();
        		
			}
        });    
    }
	
	

	
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



