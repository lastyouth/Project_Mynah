package com.seven.mynah.artifacts;

import java.util.ArrayList;

public class SubwayInfo {


	public SubwayStationInfo station;
	public int week_tag; //요일
	public int inout_tag; //상/하행선
	public String last_update; //의미없음

	public ArrayList<TimeToSubway> array_tts;
	
	public SubwayInfo() {
		station = new SubwayStationInfo();
		array_tts = new ArrayList<TimeToSubway>();
	}
	
}
