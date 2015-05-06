package com.seven.mynah.artifacts;


public class BusInfo {
	
	//정류장을 기준으로 특정 시간의 버스 도착 목록을 알려줌.
	public String station_id;
	public String station_name;
	public String line_id;
	
	public TimeToBus[] ttb;
	
	public BusInfo() {
		
	}
	
	
	public class TimeToBus {
		public String bus_id;
		public String bus_name;
		public String time;
		
		public TimeToBus() {
			
		}
	}
	
}
