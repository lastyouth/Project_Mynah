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
		
		public static final String
			week_normal = "1", //평일
			week_saturday = "2", //토요일
			week_holiday_sunday = "3"; //휴일/일요일
		
		public static final String
			up_in_line = "1", //상행/내선
			down_out_line ="2"; //하행/외선
		
		public static final String
			tpye_I = "I",
			type_K = "K";
	}
	
	public final class BusConstant {
		
		
		
	}
	
	public final class WeatherConstant {
		
		public static final int 
			sky_clear = 1, //맑음
			sky_partly_cloudy = 2, //구름조금
			sky_mostly_cloudy = 3, //구름 많음
			sky_cloudy = 4; //흐림
		
		public static final int 
			pty_clear = 0,
			pty_rain = 1,
			pty_snow_rain = 2,
			pty_rain_snow = 3,
			pty_snow = 4;
		
		
		
	}
	
	public final class UserType {
		
		public static final int
		me = 1, // 나
		other = 2; //나 제외
		
		//master type check용
		public static final int 
		master = 3, //가족중에서 메인 아이디인 경우
		normal = 4; //가족중에서 서브 아이디인 경우
	}


	
}
