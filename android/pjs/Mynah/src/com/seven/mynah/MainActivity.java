package com.seven.mynah;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.seven.mynah.artifacts.BusInfo;
import com.seven.mynah.artifacts.WeatherInfo;
import com.seven.mynah.custominterface.CustomButtonsFragment;
import com.seven.mynah.infoparser.BusPaser;
import com.seven.mynah.infoparser.WeatherParser;



public class MainActivity extends Activity {

	
	private CustomButtonsFragment cbf;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Splash : LoadingActivity
        //onCreate���� splash�� �ϴ°�� �ڷΰ��⸦ ������ ����ȭ������ ���ƿ� > loading�� skip�� �� �ִ�.
        //startActivity(new Intent(this, LoadingActivity.class));
        
        
        if (savedInstanceState == null) {
            setDefaultFragment();
        }
        testSide();
    }
    
    

    private void setDefaultFragment()
	{
		FragmentManager fm = getFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		cbf = new CustomButtonsFragment();
		transaction.add(R.id.container, cbf);
		transaction.commit();
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void testSide()
    {
    	
    	WeatherInfo winfo = new WeatherInfo();
    	winfo.city_xpos = 57;
    	winfo.city_ypos = 121;
    	
    	
    	WeatherParser wp = new WeatherParser();
    	wp.parseWeather_XML(winfo);
    	
    	BusInfo binfo = new BusInfo();
    	//������ID stId : 35428
        //�뼱ID busRouteId : 4341100
    	binfo.stId = String.valueOf(35428);
    	binfo.busRouteId = String.valueOf(4341100);
    	
    	BusPaser bp = new BusPaser();
    	//bp.parseBus_XML(binfo);
    	
    	bp.parseStationInfo_XML("�����");
    			
    	
    }
    
    
    
}
