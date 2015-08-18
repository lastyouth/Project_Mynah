package com.seven.mynah.globalmanager;

import java.util.ArrayList;

import android.content.Context;

import com.seven.mynah.artifacts.BusInfo;
import com.seven.mynah.database.DBManager;

public class TTSManager {

	
	public String BusTTS;
	public String SubwayTTS;
	public String WeatherTTS;
	public String CalTTS;
	public String GasTTS;
	
	private Context mContext;
	
	public TTSManager(Context context) {
		// TODO Auto-generated constructor stub
		
		mContext = context;
		BusTTS = "";
		SubwayTTS = "";
		WeatherTTS = "";
		CalTTS = "";
		GasTTS = "";
		
	}
	
	public void setBusTTS()
	{
		BusInfo binfo;
		ArrayList<BusInfo> array_binfo = DBManager.getManager(mContext).getBusDBbyLog();
		if(array_binfo.size() != 0)
		{
			//binfo = DBManager.getManager(mContext).getBusDB(array_binfo.get(0));
			//binfo.
		}
		
		
	}
	
	
	
}
