package com.seven.mynah.globalmanager;


public final class GlobalVariable {

	
<<<<<<< HEAD
	//public static final String WEB_SERVER_IP = "https://192.168.35.75"; //¿ì¸®Áý ³»ºÎ¾ÆÀÌÇÇ Å×½ºÆ®¿ë
	public static final String WEB_SERVER_IP = "https://1.227.248.51"; //¿ÜºÎ¾ÆÀÌÇÇ. À¯µ¿ÀÌ¶ó °¡²û ¹Ù²î³× ½Ã¹ß
=======
	public static final String WEB_SERVER_IP = "https://1.227.248.51";
>>>>>>> sbh
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
			week_normal = "1", //ï¿½ï¿½ï¿½ï¿½
			week_saturday = "2", //ï¿½ï¿½ï¿½ï¿½ï¿½
			week_holiday_sunday = "3"; //ï¿½ï¿½ï¿½ï¿½/ï¿½Ï¿ï¿½ï¿½ï¿½
		
		public static final String
			up_in_line = "1", //ï¿½ï¿½ï¿½ï¿½/ï¿½ï¿½ï¿½ï¿½
			down_out_line ="2"; //ï¿½ï¿½ï¿½ï¿½/ï¿½Ü¼ï¿½
		
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
			sky_clear = 1, //ï¿½ï¿½ï¿½ï¿½
			sky_partly_cloudy = 2, //ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½
			sky_mostly_cloudy = 3, //ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½
			sky_cloudy = 4; //ï¿½å¸²
		
		public static final int 
			pty_clear = 0,
			pty_rain = 1,
			pty_snow_rain = 2,
			pty_rain_snow = 3,
			pty_snow = 4;
		
		
		
	}
	
	public final class UserType {
		
		public static final int
		me = 1, // ï¿½ï¿½
		other = 2; //ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½
		
		//master type checkï¿½ï¿½
		public static final int 
		master = 3, //ï¿½ï¿½ï¿½ï¿½ï¿½ß¿ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½Ìµï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½
		normal = 4; //ï¿½ï¿½ï¿½ï¿½ï¿½ß¿ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½Ìµï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½
	}


	
}
