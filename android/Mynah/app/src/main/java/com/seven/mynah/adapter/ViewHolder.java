package com.seven.mynah.adapter;

import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.seven.mynah.artifacts.BusStationInfo;
import com.seven.mynah.artifacts.SubwayStationInfo;
import com.seven.mynah.artifacts.WeatherLocationInfo;

public class ViewHolder {
	//Bus
	public TextView tvBusStopNameListRow;	
	public BusStationInfo busStationInfo;
	
	//Weather
	public TextView tvCityNameListRow;
	public WeatherLocationInfo weatherLocationInfo;
	
	//Subway
	public TextView tvSubwayStationListRow;
	public SubwayStationInfo subwayStationInfo;

	//Calendar
	public TextView tvScheduleName;
	public TextView tvScheduleTime;
	public ImageView ivAdd;
	public String createdDate;
	public String scheduleDate;

	//Global Settin List
	public TextView tvSettingList;
	public TextView tvSettingStatus;

	//Setting TTS
	public TextView tvSettingTTS;
	public CheckBox cbSettingTTS;
}
