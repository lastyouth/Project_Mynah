package com.seven.mynah.globalmanager;


public final class GlobalVariable {

	
	
	public final static class BusConstant {

		public static final String cityNm[] = {"����","�λ�","�뱸","��õ","����","���","���","����","����","��õ/ȫõ","����","û��",
		  	   "õ��","�ƻ�","����","����","����","��õ","����","����","����","���","â��","����","�뿵",
		  	   "����","�о�","����","���"};
		
		public static final String cityCode[] = {"11","21","22","23","25","26","31","39","31250","32010","32020","33010","34010","34040",
					 "35010","35020","36020","36030","36060","37010","37050","37100","38010","38030","38050",
					 "38070","38080","38090","38100"};
		
		public static final String openUrl_Dev = "http://openapi.tago.go.kr/openapi/service";
		
		public static final String functionLv1_BusRt = "BusLcInfoInqireService";
		public static final String functionLv1_BusInfo = "BusRouteInfoInqireService";
		
		public static final String functionLv2_BusRtPosition = "getRouteAcctoBusLcList";
		public static final String functionLv2_BusNoList = "getRouteNoList";
		public static final String functionLv2_BusSttnList = "getRouteAcctoThrghSttnList";
		public static final String functionLv2_RouteInfoList = "getRouteInfoIem";
		public static final String serviceId_BusInfo_Dev = "SC-OA-26-12";
		public static final String serviceId_BusRt_Dev = "SC-OA-26-14";
		
		public static final String msg_busCd_empty = "������ȣ�� �Է��� �ֽʽÿ�.";
		public static final String msg_busListRslt_empty = "���� ��ȸ����� �������� �ʽ��ϴ�.";
		public static final String msg_infoListRslt_empty = "�����⺻���� ��ȸ����� �������� �ʽ��ϴ�.";
		public static final String msg_sttnListRslt_empty = "���������� ��ȸ����� �������� �ʽ��ϴ�.";
		public static final String msg_rtListRslt_empty = "�����ǽð����� ��ȸ����� �������� �ʽ��ϴ�.";
		public static final String msg_busCp_ok = "������ �̰��� �������� �ֽ��ϴ�.";
		
	}
	
	
	public final class ShortcutType {
		
		public static final int 
		typeScheduleShortcut  = 1,
		typeBusShortcut = 2,
		typeSubwayShortcut = 3,
		typeWeatherShortcut = 4,
		typeGasAlarmShortcut = 5,
		typeFamilyShortcut= 6,
		typeRefrash = 7,
		typeEmpty = 8,
		typeVoice = 9,
		typeSetting = 10;
		
	}
	
	public final class UserType {
		
		public static final int
		me = 1,
		other = 2;
		
		public static final int 
		master = 3,
		normal = 4;
	}


	
}
