package com.seven.mynah.artifacts;

import java.util.ArrayList;

public class WeatherInfo {

	//하루 기준
	public String city_code;
	public String city_name;
	public int city_xpos;
	public int city_ypos;
	
	public String date;
	
	public String last_update;
	public String description;
	
	//public TimeToWeather[] ttw;
	public ArrayList<TimeToWeather> array_ttw;
	
	public WeatherInfo() {
		//ttw = new TimeToWeather[size];
		array_ttw = new ArrayList<TimeToWeather>();
	}
	
	
	
}
