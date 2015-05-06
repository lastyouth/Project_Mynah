package com.seven.mynah.database;

import android.provider.BaseColumns;

public class MynahDB {
	
	
	//공용 컬럼
	public static final String _USER_TABLE_NAME = "user";
	public static final String _USER_COL_ID = "id";
	public static final String _USER_COL_PASSWD = "passwd";
	public static final String _USER_COL_INOUT= "inout";
	public static final String _USER_COL_NAME = "name";
	public static final String _USER_COL_CERTI_KEY = "certification_key";
	public static final String _USER_COL_TYPE = "type";
	public static final String _USER_COL_UPDATE = "last_update";
	
	
	public static final String _WEATHER_TABLE_NAME = "weather";
	public static final String _WEATHER_COL_CITY_CODE = "city_code";
	public static final String _WEATHER_COL_CITY_NAME = "city_name";
	public static final String _WEATHER_COL_CITY_XPOS = "pos_x";
	public static final String _WEATHER_COL_CITY_YPOS = "pos_y";
	public static final String _WEATHER_COL_DATETIME = "datetime";
	public static final String _WEATHER_COL_REH = "reh";
	public static final String _WEATHER_COL_TEMPER = "temp";
	public static final String _WEATHER_COL_POP = "pop";
	public static final String _WEATHER_COL_WFKOR = "wfkor";
	
	public static final String _WEATHER_CITY_TABLE_NAME = "weather_city";
	
	
	public static final String _GAS_TABLE_NAME = "gas";
	public static final String _GAS_COL_ID = "id";
	public static final String _GAS_COL_DATETIME = "datetime";
	public static final String _GAS_COL_ON_OFF = "on_off";
	
	
	public static final String _SUBWAY_TABLE_NAME = "subway";
	public static final String _SUBWAY_COL_STATION_ID = "station_id";
	public static final String _SUBWAY_COL_STATION_NAME = "station_name";
	public static final String _SUBWAY_COL_PASS_TYPE = "pass_type";
	public static final String _SUBWAY_COL_TIME = "time";

	
	public static final String _BUS_TABLE_NAME = "bus";
	public static final String _BUS_COL_STATION_ID = "station_id";
	public static final String _BUS_COL_STATION_NAME = "station_name";
	public static final String _BUS_COL_BUS_ID = "bus_id";
	public static final String _BUS_COL_BUS_NAME = "bus_name";
	public static final String _BUS_COL_LINE_ID = "line_id";
	public static final String _BUS_COL_TIME = "datetime";
	
	
	
	
	

	//디비 생성용
	public static final class CreateDB implements BaseColumns{
		
		public static final String _CREATE_USER_TABLE = "create table " + _USER_TABLE_NAME
				+ " (" + _USER_COL_ID + " text, "
				+ _USER_COL_PASSWD + " text not null, "
				+ _USER_COL_TYPE + " text not null. "
				+ _USER_COL_INOUT + " text not null, "
				+ _USER_COL_CERTI_KEY + " text, "
				+ " primary key(" + _USER_COL_ID + "); ";
		
		public static final String _CREATE_WEATHER_TABLE = "create table " + _USER_TABLE_NAME
				+ " (" + _WEATHER_COL_CITY_CODE + " text, "
				+ _WEATHER_COL_CITY_NAME + " text, "
				+ _WEATHER_COL_CITY_XPOS + " integer, "
				+ _WEATHER_COL_CITY_YPOS + " integer, "
				+ _WEATHER_COL_DATETIME + " datetime, "
				+ _WEATHER_COL_TEMPER + " text, "
				+ _WEATHER_COL_REH + " integer, "
				+ _WEATHER_COL_POP + " integer, "
				+ _WEATHER_COL_WFKOR + " text, "
				+ " primary key(" + _WEATHER_COL_CITY_CODE + "," 
				+ _WEATHER_COL_DATETIME + ") );";
		
		public static final String _CREATE_WEATHER_CITY_TABLE = "";
		
		public static final String _CREATE_BUS_TABLE = "create table " + _BUS_TABLE_NAME
				+ " ( " + _BUS_COL_BUS_ID + " text not null, "
				+ _BUS_COL_BUS_NAME + " text, "
				+ _BUS_COL_LINE_ID + " text not null, "
				+ _BUS_COL_STATION_ID + " text not null, "
				+ _BUS_COL_STATION_NAME + " text, "
				+ _BUS_COL_TIME + " datetime, "
				+ " primary key(" + _BUS_COL_STATION_ID + ","
				+ _BUS_COL_TIME + ") );";

		public static final String _CREATE_SUBWAY_TABLE = "create table " + _SUBWAY_TABLE_NAME
				+ " ( " + _SUBWAY_COL_STATION_ID + " text not null, "
				+ _SUBWAY_COL_STATION_NAME + " text, "
				+ _SUBWAY_COL_PASS_TYPE + " text, "
				+ _SUBWAY_COL_TIME + " time, "
				+ " primary key(" + _SUBWAY_COL_STATION_ID + ","
				+ _SUBWAY_COL_PASS_TYPE + ","
				+ _SUBWAY_COL_TIME + ") );";
		
		
		public static final String _CREATE_GAS_TABLE = "create table " + _GAS_TABLE_NAME
				+ " ( " + _GAS_COL_ID + " text, "
				+ _GAS_COL_DATETIME + " datetime, "
				+ _GAS_COL_ON_OFF + " text,"
				+ " primary key(" + _GAS_COL_ID + ","
				+ _GAS_COL_DATETIME + ") );";
		
	}
	
	
}
