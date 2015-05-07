package com.seven.mynah.artifacts;

import java.util.ArrayList;


public class BusInfo {
	
	//정류장을 기준으로 특정 시간의 버스 도착 목록을 알려줌.
	public String stId; //스테이션 아이디
	public String stNm; //스테이션 이름
	public String busRouteId;  //버스 노선 정보 예를 들어 1112 같은 식으로
	public String rtNm; //버스 노선 이름
	public String routeType; // 버스 노선 유형 (1:공항, 3:간선, 4:지선, 5:순환, 6:광역, 7:인천, 8:경기, 9:폐지, 0:공용)
	
	//public TimeToBus[] ttb;
	
	public ArrayList<TimeToBus> array_ttb;
	
	
	public BusInfo() {
		array_ttb = new ArrayList<TimeToBus>();
	}
	
	
	
}
