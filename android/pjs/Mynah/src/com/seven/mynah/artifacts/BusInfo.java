package com.seven.mynah.artifacts;


public class BusInfo {
	
	//�������� �������� Ư�� �ð��� ���� ���� ����� �˷���.
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
