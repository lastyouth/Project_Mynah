package com.seven.mynah.artifacts;

import java.util.ArrayList;


public class BusInfo {
	
	//�������� �������� Ư�� �ð��� ���� ���� ����� �˷���.
	public String stId; //�����̼� ���̵�
	public String stNm; //�����̼� �̸�
	public String busRouteId;  //���� �뼱 ���� ���� ��� 1112 ���� ������
	public String rtNm; //���� �뼱 �̸�
	public String routeType; // ���� �뼱 ���� (1:����, 3:����, 4:����, 5:��ȯ, 6:����, 7:��õ, 8:���, 9:����, 0:����)
	
	//public TimeToBus[] ttb;
	
	public ArrayList<TimeToBus> array_ttb;
	
	
	public BusInfo() {
		array_ttb = new ArrayList<TimeToBus>();
	}
	
	
	
}
