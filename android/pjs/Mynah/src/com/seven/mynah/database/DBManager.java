package com.seven.mynah.database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.seven.mynah.artifacts.*;
import com.seven.mynah.globalmanager.GlobalVariable;

import android.R.array;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DBManager {

	private static DBManager instance;

	private DBHelper dbh;

	private static SimpleDateFormat defaultDateFormat;
	private static SimpleDateFormat printDateFormat;

	private static int log_get_max_counter = 5;

	// android 생성자
	private DBManager(Context context) {

		dbh = new DBHelper(context);
		dbh.open();
	}

	public static synchronized DBManager getManager(Context context) {
		if (instance == null) {
			instance = new DBManager(context);
			defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			printDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		}
		return instance;
	}

	public synchronized void setWeatherDB(WeatherInfo winfo) {
		ContentValues values;

		String sql = "delete from " + MynahDB._WEATHER_TABLE_NAME + " where "
				+ MynahDB._WEATHER_COL_DATETIME
				+ " < datetime('now','localtime');";
		dbh.mDB.execSQL(sql);

		// dbh.mDB.delete(MynahDB._WEATHER_TABLE_NAME,
		// MynahDB._WEATHER_COL_DATETIME + " < today()", null);

		for (int i = 0; i < winfo.array_ttw.size(); ++i) {
			sql = "select " + MynahDB._WEATHER_COL_CITY_CODE + " from "
					+ MynahDB._WEATHER_TABLE_NAME + " where "
					+ MynahDB._WEATHER_COL_CITY_CODE + "= '"
					+ winfo.location.city_code + "' and "
					+ MynahDB._WEATHER_COL_DATETIME + "= '"
					+ winfo.array_ttw.get(i).hour + "';";

			Cursor c = dbh.mDB.rawQuery(sql, null);

			values = new ContentValues();

			if (c.getCount() == 0) {
				values.put(MynahDB._WEATHER_COL_CITY_CODE,
						winfo.location.city_code);
				values.put(MynahDB._WEATHER_COL_CITY_NAME,
						winfo.location.city_name);
				values.put(MynahDB._WEATHER_COL_CITY_XPOS,
						winfo.location.city_xpos);
				values.put(MynahDB._WEATHER_COL_CITY_YPOS,
						winfo.location.city_ypos);
				values.put(MynahDB._WEATHER_COL_DATETIME,
						winfo.array_ttw.get(i).hour);
				values.put(MynahDB._WEATHER_COL_WFKOR,
						winfo.array_ttw.get(i).wfKor);
				values.put(MynahDB._WEATHER_COL_POP, winfo.array_ttw.get(i).pop);
				values.put(MynahDB._WEATHER_COL_REH, winfo.array_ttw.get(i).reh);
				values.put(MynahDB._WEATHER_COL_TEMPER,
						winfo.array_ttw.get(i).temp);
				values.put(MynahDB._WEATHER_COL_SKY, winfo.array_ttw.get(i).sky);

				// insert
				dbh.mDB.insert(MynahDB._WEATHER_TABLE_NAME, null, values);

			} else {

				values.put(MynahDB._WEATHER_COL_CITY_NAME,
						winfo.location.city_name);
				values.put(MynahDB._WEATHER_COL_CITY_XPOS,
						winfo.location.city_xpos);
				values.put(MynahDB._WEATHER_COL_CITY_YPOS,
						winfo.location.city_ypos);
				values.put(MynahDB._WEATHER_COL_WFKOR,
						winfo.array_ttw.get(i).wfKor);
				values.put(MynahDB._WEATHER_COL_POP, winfo.array_ttw.get(i).pop);
				values.put(MynahDB._WEATHER_COL_REH,
						winfo.array_ttw.get(i).temp);
				values.put(MynahDB._WEATHER_COL_TEMPER,
						winfo.array_ttw.get(i).temp);
				values.put(MynahDB._WEATHER_COL_SKY, winfo.array_ttw.get(i).sky);

				// update
				dbh.mDB.update(MynahDB._WEATHER_TABLE_NAME, values,
						MynahDB._WEATHER_COL_CITY_CODE + "= '"
								+ winfo.location.city_code + "' and "
								+ MynahDB._WEATHER_COL_DATETIME + "= '"
								+ winfo.array_ttw.get(i).hour + "'", null);

			}
		}
		System.out.println("날씨 셋 완료");
	}

	// 현재 기준으로 가장 최근의 로그 테이블의 설정치로 location 반환함(최근껄로)
	public synchronized ArrayList<WeatherLocationInfo> getWeatherDBbyLog() {
		ArrayList<WeatherLocationInfo> array_location = new ArrayList<WeatherLocationInfo>();

		String sql = "select * from " + MynahDB._WEATHER_LOG_TABLE_NAME
				+ " order by set_time desc;";
		
		Cursor c = dbh.mDB.rawQuery(sql, null);
		if (c != null && c.getCount() != 0)
			c.moveToFirst();

		WeatherLocationInfo location = new WeatherLocationInfo();

		int city_code_index = c.getColumnIndex(MynahDB._WEATHER_COL_CITY_CODE);
		int counter = 0;

		while (!c.isAfterLast()) {
			if (counter > log_get_max_counter)
				break;
			String code = c.getString(city_code_index);

			location = getWeatherLocationByCode(code);
			array_location.add(location);
			c.moveToNext();
			counter++;
		}

		return array_location;

	}

	// 로그테이블에 넣기 위한 정보. 현재 선택된 WeatherLocationInfo를 log테이블에 넣음.
	public synchronized void setWeatherLocationDBbyLog(
			WeatherLocationInfo location) {

		ContentValues values;

		values = new ContentValues();
		Date date = new Date();

		values.put(MynahDB._WEATHER_COL_CITY_CODE, location.city_code);
		values.put(MynahDB._WEATHER_LOG_SET_TIME,
				defaultDateFormat.format(date));

		dbh.mDB.insert(MynahDB._WEATHER_LOG_TABLE_NAME, null, values);

	}

	public synchronized WeatherInfo getWeatherDB(WeatherInfo winfo) {

		// city코드를 기준으로 현재 시간의 이후 정보를 하루치로 return함
		// 현재 시간에 대해서 기준 필요함..
		String sql = "select * from " + MynahDB._WEATHER_TABLE_NAME + " where "
				+ MynahDB._WEATHER_COL_CITY_CODE + "= '"
				+ winfo.location.city_code + "' and "
				+ MynahDB._WEATHER_COL_DATETIME
				+ " > datetime('now','localtime');";

		Cursor c = dbh.mDB.rawQuery(sql, null);

		winfo.array_ttw.clear();

		if (c != null && c.getCount() != 0)
			c.moveToFirst();

		if (c.getCount() == 0)
			return winfo;

		TimeToWeather ttw;

		int hour_index = c.getColumnIndex(MynahDB._WEATHER_COL_DATETIME);
		int pop_index = c.getColumnIndex(MynahDB._WEATHER_COL_POP);
		int reh_index = c.getColumnIndex(MynahDB._WEATHER_COL_REH);
		int temp_index = c.getColumnIndex(MynahDB._WEATHER_COL_TEMPER);
		int wfkor_index = c.getColumnIndex(MynahDB._WEATHER_COL_WFKOR);
		int city_code_index = c.getColumnIndex(MynahDB._WEATHER_COL_CITY_CODE);
		int city_name_index = c.getColumnIndex(MynahDB._WEATHER_COL_CITY_NAME);
		int city_x_index = c.getColumnIndex(MynahDB._WEATHER_COL_CITY_XPOS);
		int city_y_index = c.getColumnIndex(MynahDB._WEATHER_COL_CITY_YPOS);
		int mdl_name_index = c
				.getColumnIndex(MynahDB._WEATHER_COL_CITY_MDL_NAME);
		int top_name_index = c
				.getColumnIndex(MynahDB._WEATHER_COL_CITY_TOP_NAME);
		int sky_index = c.getColumnIndex(MynahDB._WEATHER_COL_SKY);

		winfo.location = getWeatherLocationByCode(c.getString(city_code_index));
		// winfo.location.city_code = c.getString(city_code_index);
		// winfo.location.city_name = c.getString(city_name_index);
		// winfo.location.city_xpos = c.getString(city_x_index);
		// winfo.location.city_ypos = c.getString(city_y_index);
		// winfo.location.top_name = c.getString(top_name_index);
		// winfo.location.mdl_name = c.getString(mdl_name_index);

		while (!c.isAfterLast()) {
			ttw = new TimeToWeather();
			ttw.hour = c.getString(hour_index);
			ttw.pop = c.getString(pop_index);
			ttw.reh = c.getString(reh_index);
			ttw.temp = c.getString(temp_index);
			ttw.wfKor = c.getString(wfkor_index);
			ttw.sky = c.getString(sky_index);

			winfo.array_ttw.add(ttw);

			c.moveToNext();
		}

		return winfo;

	}

	public synchronized void setWeatherLocationAll(
			ArrayList<WeatherLocationInfo> array_location) {

		ContentValues values;

		String sql = "delete from " + MynahDB._WEATHER_CITY_TABLE_NAME
				+ " where 1=1;";
		dbh.mDB.execSQL(sql);

		for (int i = 0; i < array_location.size(); ++i) {

			values = new ContentValues();

			values.put(MynahDB._WEATHER_COL_CITY_CODE,
					array_location.get(i).city_code);
			values.put(MynahDB._WEATHER_COL_CITY_NAME,
					array_location.get(i).city_name);
			values.put(MynahDB._WEATHER_COL_CITY_XPOS,
					array_location.get(i).city_xpos);
			values.put(MynahDB._WEATHER_COL_CITY_YPOS,
					array_location.get(i).city_ypos);
			values.put(MynahDB._WEATHER_COL_CITY_TOP_NAME,
					array_location.get(i).top_name);
			values.put(MynahDB._WEATHER_COL_CITY_MDL_NAME,
					array_location.get(i).mdl_name);

			dbh.mDB.insert(MynahDB._WEATHER_CITY_TABLE_NAME, null, values);
		}

	}

	public synchronized ArrayList<WeatherLocationInfo> getWeatherLocationByName(
			String _name) {

		ArrayList<WeatherLocationInfo> array_location = new ArrayList<WeatherLocationInfo>();
		
		
		
		String sql = "select * from " + MynahDB._WEATHER_CITY_TABLE_NAME
				+ " where " + MynahDB._WEATHER_COL_CITY_NAME + " like '%"
				+ _name + "%' " + " or " + MynahDB._WEATHER_COL_CITY_MDL_NAME
				+ " like '%" + _name + "%' ;";

		Cursor c = dbh.mDB.rawQuery(sql, null);

		if (c != null && c.getCount() != 0)
			c.moveToFirst();

		WeatherLocationInfo location;

		int city_code_index = c.getColumnIndex(MynahDB._WEATHER_COL_CITY_CODE);
		int city_name_index = c.getColumnIndex(MynahDB._WEATHER_COL_CITY_NAME);
		int city_x_index = c.getColumnIndex(MynahDB._WEATHER_COL_CITY_XPOS);
		int city_y_index = c.getColumnIndex(MynahDB._WEATHER_COL_CITY_YPOS);
		int mdl_name_index = c
				.getColumnIndex(MynahDB._WEATHER_COL_CITY_MDL_NAME);
		int top_name_index = c
				.getColumnIndex(MynahDB._WEATHER_COL_CITY_TOP_NAME);

		while (!c.isAfterLast()) {
			location = new WeatherLocationInfo();

			location.city_code = c.getString(city_code_index);
			location.city_name = c.getString(city_name_index);
			location.city_xpos = c.getString(city_x_index);
			location.city_ypos = c.getString(city_y_index);
			location.top_name = c.getString(top_name_index);
			location.mdl_name = c.getString(mdl_name_index);

			array_location.add(location);

			c.moveToNext();
		}

		return array_location;

	}

	public synchronized WeatherLocationInfo getWeatherLocationByCode(
			String city_code) {

		WeatherLocationInfo location;

		String sql = "select * from " + MynahDB._WEATHER_CITY_TABLE_NAME
				+ " where " + MynahDB._WEATHER_COL_CITY_CODE + " = '"
				+ city_code + "' ;";

		Cursor c = dbh.mDB.rawQuery(sql, null);

		if (c != null && c.getCount() != 0)
			c.moveToFirst();

		if (c == null)
			return null;

		location = new WeatherLocationInfo();

		int city_code_index = c.getColumnIndex(MynahDB._WEATHER_COL_CITY_CODE);
		int city_name_index = c.getColumnIndex(MynahDB._WEATHER_COL_CITY_NAME);
		int city_x_index = c.getColumnIndex(MynahDB._WEATHER_COL_CITY_XPOS);
		int city_y_index = c.getColumnIndex(MynahDB._WEATHER_COL_CITY_YPOS);
		int mdl_name_index = c
				.getColumnIndex(MynahDB._WEATHER_COL_CITY_MDL_NAME);
		int top_name_index = c
				.getColumnIndex(MynahDB._WEATHER_COL_CITY_TOP_NAME);

		location.city_code = c.getString(city_code_index);
		location.city_name = c.getString(city_name_index);
		location.city_xpos = c.getString(city_x_index);
		location.city_ypos = c.getString(city_y_index);
		location.top_name = c.getString(top_name_index);
		location.mdl_name = c.getString(mdl_name_index);

		return location;

	}

	public synchronized boolean isInitialUser() {
		String sql = "select id from " + MynahDB._USER_TABLE_NAME + " where "
				+ MynahDB._USER_COL_TYPE + "="
				+ String.valueOf(GlobalVariable.UserType.me) + " ;";

		Cursor c = dbh.mDB.rawQuery(sql, null);

		if (c != null && c.getCount() != 0) {
			return true;
		} else {
			return false;
		}
	}

	public synchronized FamilyInfo getFamilyDB() {
		FamilyInfo finfo = new FamilyInfo();
		UserProfile userProfile;

		String sql = "select * from " + MynahDB._USER_TABLE_NAME + " order by "
				+ MynahDB._USER_COL_TYPE + " ; ";

		Cursor c = dbh.mDB.rawQuery(sql, null);

		int id_index = c.getColumnIndex(MynahDB._USER_COL_ID);
		int passwd_index = c.getColumnIndex(MynahDB._USER_COL_PASSWD);
		int inout_index = c.getColumnIndex(MynahDB._USER_COL_INOUT);
		int name_index = c.getColumnIndex(MynahDB._USER_COL_NAME);
		int key_index = c.getColumnIndex(MynahDB._USER_COL_CERTI_KEY);
		int type_index = c.getColumnIndex(MynahDB._USER_COL_TYPE);
		int master_type_index = c.getColumnIndex(MynahDB._USER_COL_MASTER_TYPE);

		while (!c.isAfterLast()) {
			userProfile = new UserProfile();
			userProfile.id = c.getString(id_index);
			userProfile.passwd = c.getString(passwd_index);
			userProfile.inout = c.getInt(inout_index);
			userProfile.name = c.getString(name_index);
			userProfile.mac_address = c.getString(key_index);
			userProfile.usertype = c.getInt(type_index);
			userProfile.mastertype = c.getInt(master_type_index);

			finfo.members.add(userProfile);

			c.moveToNext();
		}

		return finfo;
	}

	public synchronized void setMemberDB(FamilyInfo finfo) {

		deleteFamilyExceptMe();

		ContentValues values;
		String sql;

		for (int i = 0; i < finfo.members.size(); ++i) {
			if (finfo.members.get(i).usertype != GlobalVariable.UserType.me) {

				sql = "select * from " + MynahDB._USER_TABLE_NAME + " where "
						+ MynahDB._USER_COL_ID + "= '"
						+ finfo.members.get(i).id + "';";

				Cursor c = dbh.mDB.rawQuery(sql, null);
				// insert
				values = new ContentValues();
				// DateFormat df = new DateFormat();
				// TODO 날짜 데이터포멧 변경해야할 것임..확인

				values.put(MynahDB._USER_COL_ID, finfo.members.get(i).id);
				values.put(MynahDB._USER_COL_PASSWD,
						finfo.members.get(i).passwd);
				values.put(MynahDB._USER_COL_NAME, finfo.members.get(i).name);
				values.put(MynahDB._USER_COL_TYPE,
						finfo.members.get(i).usertype);
				values.put(MynahDB._USER_COL_MASTER_TYPE,
						finfo.members.get(i).mastertype);
				values.put(MynahDB._USER_COL_INOUT, finfo.members.get(i).inout);
				values.put(MynahDB._USER_COL_CERTI_KEY,
						finfo.members.get(i).mac_address);

				if (c.getCount() == 0) {

					dbh.mDB.insert(MynahDB._USER_TABLE_NAME, null, values);

				} else {
					dbh.mDB.update(MynahDB._USER_TABLE_NAME, values,
							MynahDB._USER_COL_ID + "= '"
									+ finfo.members.get(i).id + "'", null);

				}
			}
		}
	}

	public synchronized void deleteFamilyExceptMe() {
		String sql = "delete from " + MynahDB._USER_TABLE_NAME + " where "
				+ MynahDB._USER_COL_TYPE + "<>" + GlobalVariable.UserType.me
				+ " ;";
		dbh.mDB.execSQL(sql);
	}

	public synchronized void setBusDB(BusInfo binfo) {

		ContentValues values;

		String sql = "delete from " + MynahDB._BUS_TABLE_NAME + " where "
				+ MynahDB._BUS_COL_ARR_TIME + " < datetime('now','localtime');";
		dbh.mDB.execSQL(sql);

		for (int i = 0; i < binfo.array_ttb.size(); ++i) {
			sql = "select " + MynahDB._BUS_COL_STATION_ID + ","
					+ MynahDB._BUS_COL_ROUTE_ID + ","
					+ MynahDB._BUS_COL_ARR_TIME + ","
					+ MynahDB._BUS_COL_STATION_ORD + " from "
					+ MynahDB._BUS_TABLE_NAME + " where "
					+ MynahDB._BUS_COL_STATION_ID + "= '" + binfo.station.stId
					+ "' and " + MynahDB._BUS_COL_ROUTE_ID + "= '"
					+ binfo.route.busRouteId + "' and "
					+ MynahDB._BUS_COL_STATION_ORD + "= '" + binfo.staOrd
					+ "' and " + MynahDB._BUS_COL_ARR_TIME + "= '"
					+ binfo.array_ttb.get(i).time + "';";

			Cursor c = dbh.mDB.rawQuery(sql, null);

			values = new ContentValues();

			if (c.getCount() == 0) {
				values.put(MynahDB._BUS_COL_ROUTE_ID, binfo.route.busRouteId);
				values.put(MynahDB._BUS_COL_ROUTE_NAME, binfo.route.busRouteNm);
				// values.put(MynahDB._BUS_COL_ROUTE_TYPE, binfo.route.bus);
				values.put(MynahDB._BUS_COL_STATION_ID, binfo.station.stId);
				values.put(MynahDB._BUS_COL_STATION_NAME, binfo.station.stNm);
				values.put(MynahDB._BUS_COL_STATION_ORD, binfo.staOrd);
				values.put(MynahDB._BUS_COL_ARR_TIME,
						binfo.array_ttb.get(i).time);

				// insert
				dbh.mDB.insert(MynahDB._BUS_TABLE_NAME, null, values);

			} else {

				values.put(MynahDB._BUS_COL_ROUTE_ID, binfo.route.busRouteId);
				values.put(MynahDB._BUS_COL_ROUTE_NAME, binfo.route.busRouteNm);
				// values.put(MynahDB._BUS_COL_ROUTE_TYPE, binfo.);
				values.put(MynahDB._BUS_COL_STATION_ID, binfo.station.stId);
				values.put(MynahDB._BUS_COL_STATION_NAME, binfo.station.stNm);
				values.put(MynahDB._BUS_COL_STATION_ORD, binfo.staOrd);
				values.put(MynahDB._BUS_COL_ARR_TIME,
						binfo.array_ttb.get(i).time);

				// update
				dbh.mDB.update(MynahDB._WEATHER_TABLE_NAME, values,
						MynahDB._BUS_COL_STATION_ID + "= '"
								+ binfo.station.stId + "' and "
								+ MynahDB._BUS_COL_ROUTE_ID + "= '"
								+ binfo.route.busRouteId + "' and "
								+ MynahDB._BUS_COL_ARR_TIME + "= '"
								+ binfo.array_ttb.get(i).time + "'", null);

			}
		}
		System.out.println("버스 셋 완료");
	}

	public synchronized ArrayList<BusInfo> getBusDBbyLog() {

		// 상위 3개정도의 버스 인포를 받음(설정한 것 기준으로)
		ArrayList<BusInfo> array_binfo = new ArrayList<BusInfo>();
		BusInfo binfo;

		String sql = "select * from " + MynahDB._BUS_LOG_TABLE_NAME
				+ " order by set_time desc;";

		Cursor c = dbh.mDB.rawQuery(sql, null);
		if (c != null && c.getCount() != 0)
			c.moveToFirst();

		int route_id_index = c.getColumnIndex(MynahDB._BUS_COL_ROUTE_ID);
		int station_id__index = c.getColumnIndex(MynahDB._BUS_COL_STATION_ID);
		int station_ord_index = c.getColumnIndex(MynahDB._BUS_COL_STATION_ORD);

		int counter = 0;

		while (!c.isAfterLast()) {
			if (counter > log_get_max_counter)
				break;

			binfo = new BusInfo();
			binfo.route.busRouteId = c.getString(route_id_index);
			binfo.station.stId = c.getString(station_id__index);
			binfo.staOrd = c.getString(station_ord_index);

			array_binfo.add(binfo);
			c.moveToNext();
			counter++;
		}

		return array_binfo;

	}

	public synchronized void setBusDBbyLog(BusInfo binfo) {

		ContentValues values;

		values = new ContentValues();
		Date date = new Date();

		values.put(MynahDB._BUS_COL_ROUTE_ID, binfo.route.busRouteId);
		values.put(MynahDB._BUS_COL_STATION_ID, binfo.station.stNm);
		values.put(MynahDB._BUS_COL_STATION_ORD, binfo.staOrd);
		values.put(MynahDB._BUS_LOG_SET_TIME, defaultDateFormat.format(date));

		dbh.mDB.insert(MynahDB._BUS_LOG_TABLE_NAME, null, values);

	}

	public synchronized BusInfo getBusDB(BusInfo binfo) {

		String sql = "select * from " + MynahDB._BUS_TABLE_NAME + " where "
				+ MynahDB._BUS_COL_STATION_ID + "= '" + binfo.station.stId
				+ "' and " + MynahDB._BUS_COL_ROUTE_ID + "= '"
				+ binfo.route.busRouteId + "' and "
				+ MynahDB._BUS_COL_STATION_ORD + "= '" + binfo.staOrd
				+ "' and " + MynahDB._BUS_COL_ARR_TIME
				+ " > datetime('now','localtime') ;";

		Cursor c = dbh.mDB.rawQuery(sql, null);

		binfo.array_ttb.clear();

		if (c != null && c.getCount() != 0)
			c.moveToFirst();

		if (c.getCount() == 0)
			return binfo; // error?

		TimeToBus ttb;

		int route_id_index = c.getColumnIndex(MynahDB._BUS_COL_ROUTE_ID);
		int route_name_index = c.getColumnIndex(MynahDB._BUS_COL_ROUTE_NAME);
		int route_type_index = c.getColumnIndex(MynahDB._BUS_COL_ROUTE_TYPE);
		int station_id__index = c.getColumnIndex(MynahDB._BUS_COL_STATION_ID);
		int station_name_index = c
				.getColumnIndex(MynahDB._BUS_COL_STATION_NAME);
		int station_ord_index = c.getColumnIndex(MynahDB._BUS_COL_STATION_ORD);
		int dir_index = c.getColumnIndex(MynahDB._BUS_COL_DIR);
		int arr_time_index = c.getColumnIndex(MynahDB._BUS_COL_ARR_TIME);

		binfo.route.busRouteId = c.getString(route_id_index);
		binfo.route.busRouteNm = c.getString(route_name_index);
		binfo.route.routeType = c.getString(route_type_index);

		binfo.station.stId = c.getString(station_id__index);
		binfo.station.stNm = c.getString(station_name_index);

		binfo.staOrd = c.getString(station_ord_index);
		binfo.dir = c.getString(dir_index);

		while (!c.isAfterLast()) {
			ttb = new TimeToBus();
			ttb.time = c.getString(arr_time_index);

			binfo.array_ttb.add(ttb);

			c.moveToNext();
		}

		return binfo;

	}

	public synchronized void setSubwayDBbyLog(SubwayInfo sinfo) {

		ContentValues values;

		values = new ContentValues();
		Date date = new Date();

		values.put(MynahDB._SUBWAY_COL_STATION_ID, sinfo.station.station_cd);
		values.put(MynahDB._SUBWAY_COL_INOUT_TAG, sinfo.station.inout_tag);

		values.put(MynahDB._SUBWAY_COL_WEEK_TAG, sinfo.week_tag);
		values.put(MynahDB._SUBWAY_LOG_SET_TIME, defaultDateFormat.format(date));

		dbh.mDB.insert(MynahDB._SUBWAY_LOG_TABLE_NAME, null, values);

	}

	public synchronized ArrayList<SubwayInfo> getSubwayDBbyLog() {

		ArrayList<SubwayInfo> array_sinfo = new ArrayList<SubwayInfo>();

		String sql = "select * from " + MynahDB._SUBWAY_LOG_TABLE_NAME
				+ " order by set_time desc;";

		Cursor c = dbh.mDB.rawQuery(sql, null);
		if (c != null && c.getCount() != 0)
			c.moveToFirst();

		SubwayInfo sinfo;

		int station_id_index = c.getColumnIndex(MynahDB._SUBWAY_COL_STATION_ID);
		int inout_tag_index = c.getColumnIndex(MynahDB._SUBWAY_COL_INOUT_TAG);
		int week_tag_index = c.getColumnIndex(MynahDB._SUBWAY_COL_WEEK_TAG);

		int counter = 0;

		while (!c.isAfterLast()) {
			if (counter > log_get_max_counter)
				break;

			sinfo = new SubwayInfo();

			sinfo.station.station_cd = c.getString(station_id_index);
			sinfo.station.inout_tag = c.getString(inout_tag_index);
			sinfo.week_tag = c.getString(week_tag_index);

			array_sinfo.add(sinfo);
			c.moveToNext();
			counter++;
		}

		return array_sinfo;
	}

	public synchronized void setSubwayDB(SubwayInfo swinfo) {

		ContentValues values;

		// delete는 필요없다..? 일단 추후 확인
		// String sql = "delete from " + MynahDB._BUS_TABLE_NAME + " where "
		// + MynahDB._BUS_COL_ARR_TIME + " < datetime('now','localtime');";
		// dbh.mDB.execSQL(sql);

		String sql;

		for (int i = 0; i < swinfo.array_tts.size(); ++i) {

			sql = "select " + MynahDB._SUBWAY_COL_STATION_ID + ","
					+ MynahDB._SUBWAY_COL_WEEK_TAG + ","
					+ MynahDB._SUBWAY_COL_INOUT_TAG + ","
					+ MynahDB._BUS_COL_ARR_TIME + " from "
					+ MynahDB._SUBWAY_TABLE_NAME + " where "
					+ MynahDB._SUBWAY_COL_STATION_ID + "= '"
					+ swinfo.station.station_cd + "' and "
					+ MynahDB._SUBWAY_COL_WEEK_TAG + "= '" + swinfo.week_tag
					+ "' and " + MynahDB._SUBWAY_COL_INOUT_TAG + "= '"
					+ swinfo.station.inout_tag + "' and "
					+ MynahDB._SUBWAY_COL_ARR_TIME + "= '"
					+ swinfo.array_tts.get(i).arr_time + "';";

			Cursor c = dbh.mDB.rawQuery(sql, null);

			values = new ContentValues();

			if (c.getCount() == 0) {

				values.put(MynahDB._SUBWAY_COL_STATION_ID,
						swinfo.station.station_cd);
				values.put(MynahDB._SUBWAY_COL_STATION_NAME,
						swinfo.station.station_nm);
				values.put(MynahDB._SUBWAY_COL_LINE_NUM,
						swinfo.station.line_num);
				values.put(MynahDB._SUBWAY_COL_WEEK_TAG, swinfo.week_tag);
				values.put(MynahDB._SUBWAY_COL_INOUT_TAG, swinfo.station.inout_tag);
				values.put(MynahDB._SUBWAY_COL_ARR_TIME,
						swinfo.array_tts.get(i).arr_time);
				values.put(MynahDB._SUBWAY_COL_END_STATION_NAME,
						swinfo.array_tts.get(i).subway_end_name);
				values.put(MynahDB._SUBWAY_COL_FL_FLAG,
						swinfo.array_tts.get(i).fl_flag);

				// insert
				dbh.mDB.insert(MynahDB._SUBWAY_TABLE_NAME, null, values);

			} else {

				values.put(MynahDB._SUBWAY_COL_STATION_ID,
						swinfo.station.station_cd);
				values.put(MynahDB._SUBWAY_COL_STATION_NAME,
						swinfo.station.station_nm);
				values.put(MynahDB._SUBWAY_COL_LINE_NUM,
						swinfo.station.line_num);
				values.put(MynahDB._SUBWAY_COL_WEEK_TAG, swinfo.week_tag);
				values.put(MynahDB._SUBWAY_COL_INOUT_TAG, swinfo.station.inout_tag);
				values.put(MynahDB._SUBWAY_COL_ARR_TIME,
						swinfo.array_tts.get(i).arr_time);
				values.put(MynahDB._SUBWAY_COL_END_STATION_NAME,
						swinfo.array_tts.get(i).subway_end_name);
				values.put(MynahDB._SUBWAY_COL_FL_FLAG,
						swinfo.array_tts.get(i).fl_flag);

				// update
				dbh.mDB.update(MynahDB._SUBWAY_TABLE_NAME, values,
						MynahDB._SUBWAY_COL_STATION_ID + "= '"
								+ swinfo.station.station_cd + "' and "
								+ MynahDB._SUBWAY_COL_WEEK_TAG + "= '"
								+ swinfo.week_tag + "' and "
								+ MynahDB._SUBWAY_COL_INOUT_TAG + "= '"
								+ swinfo.station.inout_tag + "' and "
								+ MynahDB._SUBWAY_COL_ARR_TIME + "= '"
								+ swinfo.array_tts.get(i).arr_time + "'", null);

			}
		}
		System.out.println("지하철 셋 완료");

	}

	public synchronized SubwayInfo getSubwayDB(SubwayInfo swinfo) {

		String sql = "select * from " + MynahDB._SUBWAY_TABLE_NAME + " where "
				+ MynahDB._SUBWAY_COL_STATION_ID + " = '"
				+ swinfo.station.station_cd + "' and "
				+ MynahDB._SUBWAY_COL_WEEK_TAG + " = '" + swinfo.week_tag
				+ "' and " + MynahDB._SUBWAY_COL_INOUT_TAG + " = '"
				+ swinfo.station.inout_tag + "' and " + MynahDB._SUBWAY_COL_ARR_TIME
				+ "  > time('now','localtime');";

		Cursor c = dbh.mDB.rawQuery(sql, null);

		swinfo.array_tts.clear();

		if (c != null && c.getCount() != 0)
			c.moveToFirst();

		if (c.getCount() == 0)
			return swinfo; // error?

		TimeToSubway tts;

		int station_cd_index = c.getColumnIndex(MynahDB._SUBWAY_COL_STATION_ID);
		int station_name_index = c
				.getColumnIndex(MynahDB._SUBWAY_COL_STATION_NAME);
		int line_num_index = c.getColumnIndex(MynahDB._SUBWAY_COL_LINE_NUM);
		int week_tag__index = c.getColumnIndex(MynahDB._SUBWAY_COL_WEEK_TAG);
		int inout_tag_index = c.getColumnIndex(MynahDB._SUBWAY_COL_INOUT_TAG);
		int arr_time_index = c.getColumnIndex(MynahDB._SUBWAY_COL_ARR_TIME);
		int end_station_index = c
				.getColumnIndex(MynahDB._SUBWAY_COL_END_STATION_NAME);
		int fl_flag_index = c.getColumnIndex(MynahDB._SUBWAY_COL_FL_FLAG);

		swinfo.station.station_cd = c.getString(station_cd_index);
		swinfo.station.station_nm = c.getString(station_name_index);
		swinfo.station.line_num = c.getString(line_num_index);

		swinfo.week_tag = c.getString(week_tag__index);
		swinfo.station.inout_tag = c.getString(inout_tag_index);

		while (!c.isAfterLast()) {
			tts = new TimeToSubway();
			tts.arr_time = c.getString(arr_time_index);
			tts.subway_end_name = c.getString(end_station_index);
			tts.fl_flag = c.getString(fl_flag_index);

			swinfo.array_tts.add(tts);

			c.moveToNext();
		}

		return swinfo;

	}

	public void setMainUserDB(UserProfile upf) {
		// default로 넣음.
		ContentValues values;

		if (upf.id != null)
			return;

		values = new ContentValues();
		values.put(MynahDB._USER_COL_ID, upf.id);
		values.put(MynahDB._USER_COL_PASSWD, upf.passwd);
		values.put(MynahDB._USER_COL_NAME, upf.name);
		values.put(MynahDB._USER_COL_TYPE, upf.usertype);
		values.put(MynahDB._USER_COL_MASTER_TYPE, upf.mastertype);
		values.put(MynahDB._USER_COL_INOUT, upf.inout);
		values.put(MynahDB._USER_COL_CERTI_KEY, upf.mac_address);

		// insert
		dbh.mDB.insert(MynahDB._USER_TABLE_NAME, null, values);

	}

	public UserProfile getMainUserDB() {

		UserProfile muser = new UserProfile();

		String sql = "select * from " + MynahDB._USER_TABLE_NAME + " where "
				+ MynahDB._USER_COL_TYPE + "= '" + GlobalVariable.UserType.me
				+ "' ;";

		Cursor c = dbh.mDB.rawQuery(sql, null);

		if (c != null && c.getCount() != 0)
			c.moveToFirst();

		if (c.getCount() == 0)
			return null; // error?

		int id_index = c.getColumnIndex(MynahDB._USER_COL_ID);
		int passwd_index = c.getColumnIndex(MynahDB._USER_COL_PASSWD);
		int name_index = c.getColumnIndex(MynahDB._USER_COL_NAME);
		int type__index = c.getColumnIndex(MynahDB._USER_COL_TYPE);
		int master_type_index = c.getColumnIndex(MynahDB._USER_COL_MASTER_TYPE);
		int inout_index = c.getColumnIndex(MynahDB._USER_COL_INOUT);
		int certi_index = c.getColumnIndex(MynahDB._USER_COL_CERTI_KEY);

		muser.id = c.getString(id_index);
		muser.passwd = c.getString(passwd_index);
		muser.name = c.getString(name_index);
		muser.usertype = c.getInt(type__index);
		muser.mastertype = c.getInt(master_type_index);
		muser.inout = c.getInt(inout_index);
		muser.mac_address = c.getString(certi_index);

		return muser;

	}

}
