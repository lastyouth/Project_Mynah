package com.seven.mynah;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BusSettingActivity extends Activity{
	
	private View view;
	private ListView listView;
	private CustomAdapter listAdapter;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_bus);
        
        
        //listAdapter = new ArrayAdapter<String>(this, R.layout.custom_list_bus_setting);
        listAdapter = new CustomAdapter();
        
        listView = (ListView)findViewById(R.id.lvBusSetting);
        listView.setAdapter(listAdapter);
        listView.setDividerHeight(4);
        
        listAdapter.add("버스 번호 입력");
        listAdapter.add("버스 정류장 입력");
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



