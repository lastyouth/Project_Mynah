package com.seven.mynah.artifacts;

import java.util.ArrayList;


public class BusInfo {
	
	//�������� �������� Ư�� �ð��� ���� ���� ����� �˷���.
	//������ ���� �ϳ����� ������ ����.
	public String staOrd; // ������ ���� (�����ҿ� ���� ������ ��� �˰� �־����)
	public String dir; //�����(���δ���)
	public BusRouteInfo route;
	public BusStationInfo station;
	public ArrayList<TimeToBus> array_ttb;
	public String last_update;
	
	public BusInfo() {
		route = new BusRouteInfo();
		station = new BusStationInfo();
		array_ttb = new ArrayList<TimeToBus>();
	}
	
	
	
}
