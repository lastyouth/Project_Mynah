package com.seven.mynah.artifacts;

import java.util.ArrayList;

public class SubwayInfo {


	public SubwayStationInfo station;
	public String week_tag; //����
	public String last_update; //�ǹ̾���

	public ArrayList<TimeToSubway> array_tts;
	
	public SubwayInfo() {
		station = new SubwayStationInfo();
		array_tts = new ArrayList<TimeToSubway>();
	}
	
}
