package com.seven.mynah.globalmanager;


public final class GlobalVariable {


	//public static final String WEB_SERVER_IP = "https://192.168.35.75"; //우리집 내부아이피 테스트용
	public static final String WEB_SERVER_IP = "https://1.227.248.51"; //외부아이피.
	public static final int HTTP_PORT = 13337;
	public static final int HTTPS_PORT = 13337;
	public static boolean isScheduleDBUpdated = false;

	public static final int SCHEDULE = 1;
	public static final int BUS = 2;
	public static final int SUBWAY = 4;
	public static final int WEATHER = 8;
	public static final int RECORD = 16;


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

	public final class GCMConstant {

		public static final String
				PROJECT_REG_ID = "AIzaSyBo7pigCHSXysJD-qxKsE0H9YBGXmIvaVQ";

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