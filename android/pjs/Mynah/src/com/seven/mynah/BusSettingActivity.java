package com.seven.mynah;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.Toast;
import android.content.Context;

public class BusSettingActivity extends Activity {
	
	private ImageView ivSearch;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_bus);
        
        ivSearch = (ImageView)findViewById(R.id.ivSearch);
        ivSearch.setOnClickListener(new OnClickListener() {
        	@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
        		Toast.makeText(getApplicationContext(), "ivSearch Clicked", Toast.LENGTH_SHORT).show();
        		
        		
        		
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



