package com.seven.mynah.artifacts;

import java.util.ArrayList;

public class WeatherInfo {

	//�Ϸ� ����
	public WeatherLocationInfo location;
	public String date;
	public String last_update;
	public String description;
	
	public ArrayList<TimeToWeather> array_ttw;
	
	public WeatherInfo() {
		location = new WeatherLocationInfo();
		array_ttw = new ArrayList<TimeToWeather>();
	}
	
}
