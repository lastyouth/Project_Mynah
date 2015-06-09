package com.seven.mynah.globalmanager;


public final class GlobalVariable {
	//public static final String WEB_SERVER_IP = "https://192.168.35.75"; //우리집 내부아이피 테스트용
	public static final String WEB_SERVER_IP = "https://1.227.248.51"; //외부아이피. 유동이라 가끔 바뀌네 시발
    public static final int HTTP_PORT = 13337;
    public static final int HTTPS_PORT = 13337;
	public static boolean isScheduleDBUpdated = false;
	
	public final class ShortcutType {
		
		public static final int 
		typeScheduleShortcut  = 1,
		typeBusShortcut = 2,
		typeSubwayShortcut = 3,
		typeWeatherShortcut = 4,
		typeGasAlarmShortcut = 5,
		typeFamilyShortcut= 6,
		typeRefrash = 7,
		typeVoice = 8,
		typeSetting = 9;
		
	}
	
	
	public final class SubwayConstant {
		
		public static final String
			week_normal = "1", //占쏙옙占쏙옙
			week_saturday = "2", //占쏙옙占쏙옙占�
			week_holiday_sunday = "3"; //占쏙옙占쏙옙/占싹울옙占쏙옙
		
		public static final String
			up_in_line = "1", //占쏙옙占쏙옙/占쏙옙占쏙옙
			down_out_line ="2"; //占쏙옙占쏙옙/占쌤쇽옙
		
		public static final String
			tpye_I = "I",
			type_K = "K";
	}
	
	public final class BusConstant {
		
		
		
	}
	
	public final class GCMConstant {
		
		public static final String 
		PROJECT_REG_ID = "AIzaSyBo7pigCHSXysJD-qxKsE0H9YBGXmIvaVQ";
		
	}
	
	
	public final class WeatherConstant {
		
		public static final int 
			sky_clear = 1, //占쏙옙占쏙옙
			sky_partly_cloudy = 2, //占쏙옙占쏙옙占쏙옙占쏙옙
			sky_mostly_cloudy = 3, //占쏙옙占쏙옙 占쏙옙占쏙옙
			sky_cloudy = 4; //占썲림
		
		public static final int 
			pty_clear = 0,
			pty_rain = 1,
			pty_snow_rain = 2,
			pty_rain_snow = 3,
			pty_snow = 4;
		
		
		
	}
	
	public final class UserType {
		
		public static final int
		me = 1, // 占쏙옙
		other = 2; //占쏙옙 占쏙옙占쏙옙
		
		//master type check占쏙옙
		public static final int 
		master = 3, //占쏙옙占쏙옙占쌩울옙占쏙옙 占쏙옙占쏙옙 占쏙옙占싱듸옙占쏙옙 占쏙옙占�
		normal = 4; //占쏙옙占쏙옙占쌩울옙占쏙옙 占쏙옙占쏙옙 占쏙옙占싱듸옙占쏙옙 占쏙옙占�
	}


	
}
