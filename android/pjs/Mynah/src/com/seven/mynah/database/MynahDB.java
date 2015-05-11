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
	public static final String _USER_COL_MASTER_TYPE = "master_type";
	public static final String _USER_COL_UPDATE = "last_update";
	
	
	public static final String _WEATHER_TABLE_NAME = "weather";
	public static final String _WEATHER_COL_CITY_CODE = "city_code";
	public static final String _WEATHER_COL_CITY_TOP_NAME = "top_name";
	public static final String _WEATHER_COL_CITY_MDL_NAME = "mdl_name";
	public static final String _WEATHER_COL_CITY_NAME = "city_name";
	public static final String _WEATHER_COL_CITY_XPOS = "pos_x";
	public static final String _WEATHER_COL_CITY_YPOS = "pos_y";
	public static final String _WEATHER_COL_DATETIME = "datetime";
	public static final String _WEATHER_COL_REH = "reh";
	public static final String _WEATHER_COL_TEMPER = "temp";
	public static final String _WEATHER_COL_POP = "pop";
	public static final String _WEATHER_COL_WFKOR = "wfkor";
	public static final String _WEATHER_COL_SKY = "sky";
	
	public static final String _WEATHER_CITY_TABLE_NAME = "weather_city";
	
	public static final String _WEATHER_LOG_TABLE_NAME = "weather_log";
	public static final String _WEATHER_LOG_SET_TIME = "set_time";
	
	
	
	public static final String _GAS_TABLE_NAME = "gas";
	public static final String _GAS_COL_ID = "id";
	public static final String _GAS_COL_DATETIME = "datetime";
	public static final String _GAS_COL_ON_OFF = "on_off";
	
	
	public static final String _SUBWAY_TABLE_NAME = "subway";
	public static final String _SUBWAY_COL_STATION_ID = "station_id";
	public static final String _SUBWAY_COL_STATION_NAME = "station_name";
	public static final String _SUBWAY_COL_LINE_NUM = "line_num";
	public static final String _SUBWAY_COL_PASS_TYPE = "pass_type";
	public static final String _SUBWAY_COL_WEEK_TAG = "week_tag";
	public static final String _SUBWAY_COL_INOUT_TAG = "inout_tag";
	public static final String _SUBWAY_COL_ARR_TIME = "arr_time";
	public static final String _SUBWAY_COL_FL_FLAG = "fl_flag";
	public static final String _SUBWAY_COL_END_STATION_NAME = "end_station_name";
	
	
	public static final String _SUBWAY_LOG_TABLE_NAME = "subway_log";
	public static final String _SUBWAY_LOG_SET_TIME = "set_time";
	
	
	public static final String _BUS_TABLE_NAME = "bus";
	public static final String _BUS_COL_STATION_ID = "station_id";
	public static final String _BUS_COL_STATION_NAME = "station_name";
	public static final String _BUS_COL_ROUTE_ID = "route_id";
	public static final String _BUS_COL_ROUTE_NAME = "route_name";
	public static final String _BUS_COL_ROUTE_TYPE = "route_type";
	public static final String _BUS_COL_STATION_ORD = "sta_ord";
	public static final String _BUS_COL_DIR = "dir";
	public static final String _BUS_COL_ARR_TIME = "arr_time";
	
	
	public static final String _BUS_LOG_TABLE_NAME = "bus_log";
	public static final String _BUS_LOG_SET_TIME = "set_time";
	
	

	//디비 생성용
	public static final class CreateDB implements BaseColumns{
		
		public static final String _CREATE_USER_TABLE = "create table " + _USER_TABLE_NAME
				+ " (" + _USER_COL_ID + " text, "
				+ _USER_COL_PASSWD + " text, "
				+ _USER_COL_NAME + " text, "
				+ _USER_COL_TYPE + " integer not null, "
				+ _USER_COL_MASTER_TYPE + " integer not null, "
				+ _USER_COL_INOUT + " integer not null, "
				+ _USER_COL_CERTI_KEY + " text, "
				+ " primary key(" + _USER_COL_ID + ") ); ";
		
		
		public static final String _CREATE_WEATHER_TABLE = "create table " + _WEATHER_TABLE_NAME
				+ " (" + _WEATHER_COL_CITY_CODE + " text, "
				+ _WEATHER_COL_CITY_NAME + " text, "
				+ _WEATHER_COL_CITY_TOP_NAME + " text, "
				+ _WEATHER_COL_CITY_MDL_NAME + " text, "
				+ _WEATHER_COL_CITY_XPOS + " text, "
				+ _WEATHER_COL_CITY_YPOS + " text, "
				+ _WEATHER_COL_DATETIME + " datetime, "
				+ _WEATHER_COL_TEMPER + " text, "
				+ _WEATHER_COL_REH + " text, "
				+ _WEATHER_COL_POP + " text, "
				+ _WEATHER_COL_WFKOR + " text, "
				+ _WEATHER_COL_SKY + " text, "
				+ " primary key(" + _WEATHER_COL_CITY_CODE + "," 
				+ _WEATHER_COL_DATETIME + ") ); ";
		
		
		public static final String _CREATE_WEATHER_CITY_TABLE = "create table " + _WEATHER_CITY_TABLE_NAME
				+ " ( " + _WEATHER_COL_CITY_CODE + " text,"
				+ _WEATHER_COL_CITY_NAME + " text, "
				+ _WEATHER_COL_CITY_TOP_NAME + " text, "
				+ _WEATHER_COL_CITY_MDL_NAME + " text, "
				+ _WEATHER_COL_CITY_XPOS + " text, "
				+ _WEATHER_COL_CITY_YPOS + " text, "
				+ " primary key(" + _WEATHER_COL_CITY_CODE + ") ); ";
		
		
		public static final String _CREATE_WEATHER_LOG_TABLE = "create table " + _WEATHER_LOG_TABLE_NAME
				+ " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "  
				+ _WEATHER_COL_CITY_CODE + " text,"
				+ _WEATHER_LOG_SET_TIME + " datetime "
				+ " );";
		
		
		public static final String _CREATE_BUS_TABLE = "create table " + _BUS_TABLE_NAME
				+ " ( " + _BUS_COL_ROUTE_ID + " text not null, "
				+ _BUS_COL_ROUTE_NAME + " text, "
				+ _BUS_COL_ROUTE_TYPE + " text, "
				+ _BUS_COL_STATION_ID + " text not null, "
				+ _BUS_COL_STATION_NAME + " text, "
				+ _BUS_COL_STATION_ORD + " text, "
				+ _BUS_COL_DIR + " text, "
				+ _BUS_COL_ARR_TIME + " datetime, "
				+ " primary key(" + _BUS_COL_STATION_ID + "," 
				+ _BUS_COL_ROUTE_ID + "," 
				+ _BUS_COL_ARR_TIME + ") );";
		
		public static final String _CREATE_BUS_LOG_TABLE = "create table " + _BUS_LOG_TABLE_NAME
				+ " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ _BUS_COL_STATION_ID + " text, "
				+ _BUS_COL_ROUTE_ID + " text, "
				+ _BUS_COL_STATION_ORD + " text, "
				+ _BUS_LOG_SET_TIME + " datetime "
				+ " );";

		public static final String _CREATE_SUBWAY_TABLE = "create table " + _SUBWAY_TABLE_NAME
				+ " ( " + _SUBWAY_COL_STATION_ID + " text not null, "
				+ _SUBWAY_COL_STATION_NAME + " text, "
				+ _SUBWAY_COL_LINE_NUM + " text, "
				+ _SUBWAY_COL_WEEK_TAG + " text, "
				+ _SUBWAY_COL_INOUT_TAG + " text, "
				+ _SUBWAY_COL_ARR_TIME + " time, "
				+ " primary key(" + _SUBWAY_COL_STATION_ID + ","
				+ _SUBWAY_COL_WEEK_TAG + ","
				+ _SUBWAY_COL_INOUT_TAG + ","
				+ _SUBWAY_COL_ARR_TIME + ") );";
		
		
		public static final String _CREATE_SUBWAY_LOG_TABLE = "create table " + _SUBWAY_LOG_TABLE_NAME
				+ " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ _SUBWAY_COL_STATION_ID + " text, "
				+ _SUBWAY_COL_INOUT_TAG + " text, "
				+ _SUBWAY_COL_WEEK_TAG + " text, "
				+ _SUBWAY_LOG_SET_TIME + " datetime "
				+ " );";
		
		
		public static final String _CREATE_GAS_TABLE = "create table " + _GAS_TABLE_NAME
				+ " ( " + _GAS_COL_ID + " text, "
				+ _GAS_COL_DATETIME + " datetime, "
				+ _GAS_COL_ON_OFF + " text,"
				+ " primary key(" + _GAS_COL_ID + ","
				+ _GAS_COL_DATETIME + ") );";
		
	}
	
	
}
