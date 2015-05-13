package com.seven.mynah.globalmanager;


public final class GlobalVariable {

	
	
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
	
	
	public final class SubwayConstant {
		
		public static final int
			week_normal = 1, //����
			week_saturday = 2, //�����
			week_holiday_sunday = 3; //����/�Ͽ���
		
		public static final int
			up_in_line = 1, //����/����
			down_out_line =2; //����/�ܼ�
		
		public static final String
			K = "�߾Ӽ�";
	}
	
	public final class BusConstant {
		
		
		
	}
	
	public final class GCMConstant {
		
		public static final String 
		PROJECT_REG_ID = "AIzaSyBo7pigCHSXysJD-qxKsE0H9YBGXmIvaVQ";
		
	}
	
	public final class WeatherConstant {
		
		public static final int 
			sky_clear = 1, //����
			sky_partly_cloudy = 2, //��������
			sky_mostly_cloudy = 3, //���� ����
			sky_cloudy = 4; //�帲
		
		public static final int 
			pty_clear = 0,
			pty_rain = 1,
			pty_snow_rain = 2,
			pty_rain_snow = 3,
			pty_snow = 4;
		
		
		
	}
	
	public final class UserType {
		
		public static final int
		me = 1, // ��
		other = 2; //�� ����
		
		//master type check��
		public static final int 
		master = 3, //�����߿��� ���� ���̵��� ���
		normal = 4; //�����߿��� ���� ���̵��� ���
	}


	
}
