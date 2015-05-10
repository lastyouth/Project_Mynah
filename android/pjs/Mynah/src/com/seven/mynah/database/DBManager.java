package com.seven.mynah.database;

import java.util.ArrayList;

import com.seven.mynah.artifacts.BusInfo;
import com.seven.mynah.artifacts.FamilyInfo;
import com.seven.mynah.artifacts.SubwayInfo;
import com.seven.mynah.artifacts.TimeToWeather;
import com.seven.mynah.artifacts.UserProfile;
import com.seven.mynah.artifacts.WeatherInfo;
import com.seven.mynah.artifacts.WeatherLocationInfo;
import com.seven.mynah.globalmanager.GlobalVariable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.format.DateFormat;


public class DBManager {
	
	private static DBManager instance;
	
	private static DBHelper dbh;
	
	
	//android 생성자
	private DBManager(Context context) {
		
		dbh = new DBHelper(context);
		dbh.open();
	}

	
	public static synchronized DBManager getManager(Context context)
    {
        if (instance == null)
            instance = new DBManager(context);

        return instance;
    }
	
	public synchronized void setWeatherDB(WeatherInfo winfo)
	{
		ContentValues values;
		
		String sql = "delete from " + MynahDB._WEATHER_TABLE_NAME + " where "
				+ MynahDB._WEATHER_COL_DATETIME + " < today();";
		dbh.mDB.execSQL(sql);
		
		//dbh.mDB.delete(MynahDB._WEATHER_TABLE_NAME, MynahDB._WEATHER_COL_DATETIME + " < today()", null);
		
		for(int i = 0; i<winfo.array_ttw.size(); ++i)
		{
			sql = "select " + MynahDB._WEATHER_COL_CITY_CODE + " from " + MynahDB._WEATHER_TABLE_NAME +
					" where " + MynahDB._WEATHER_COL_CITY_CODE + "= '" + winfo.location.city_code
					+ "' and " + MynahDB._WEATHER_COL_DATETIME + "= '" + winfo.array_ttw.get(i).hour + "';";
					
			Cursor c = dbh.mDB.rawQuery(sql, null);
			
			values = new ContentValues();
			
			values.put(MynahDB._WEATHER_COL_CITY_CODE, winfo.location.city_code);
			values.put(MynahDB._WEATHER_COL_CITY_NAME, winfo.location.city_name);
			values.put(MynahDB._WEATHER_COL_CITY_XPOS, winfo.location.city_xpos);
			values.put(MynahDB._WEATHER_COL_CITY_YPOS, winfo.location.city_ypos);
			values.put(MynahDB._WEATHER_COL_DATETIME, winfo.array_ttw.get(i).hour);
			values.put(MynahDB._WEATHER_COL_WFKOR, winfo.array_ttw.get(i).wfKor);
			values.put(MynahDB._WEATHER_COL_POP, winfo.array_ttw.get(i).pop);
			values.put(MynahDB._WEATHER_COL_REH, winfo.array_ttw.get(i).temp);
			values.put(MynahDB._WEATHER_COL_TEMPER, winfo.array_ttw.get(i).temp);
			
			if (c.getCount() == 0) 
			{
				//insert
				dbh.mDB.insert(MynahDB._WEATHER_TABLE_NAME, null, values);
				
			}
			else
			{
				//update
				dbh.mDB.update(MynahDB._WEATHER_TABLE_NAME, values, 
						MynahDB._WEATHER_COL_CITY_CODE + "=" + winfo.location.city_code
						+ "and " + MynahDB._WEATHER_COL_DATETIME + "=" + winfo.array_ttw.get(i).hour, null);
				
			}		
		}
		
	}
	
	//현재 기준으로 가장 최근의 로그 테이블의 설정치로 기본 winfo를 반환함
	public synchronized WeatherInfo getWeatherStandard()
	{
		WeatherInfo winfo = new WeatherInfo();
		
		
		
		return winfo;
		
	}
	
	
	public synchronized void setWeatherStandard()
	{
		
		
	}
	
	
	public synchronized WeatherInfo getWeatherDB(WeatherInfo winfo)
	{
		
		//city코드를 기준으로 현재 시간의 이후 정보를 하루치로 return함
		String sql = "select * from " + MynahDB._WEATHER_TABLE_NAME +
				" where " + MynahDB._WEATHER_COL_CITY_CODE + "= '" + winfo.location.city_code
				+ "' and " + MynahDB._WEATHER_COL_DATETIME + " > '" + winfo.date + "';";
		
		Cursor c = dbh.mDB.rawQuery(sql, null);
		
		if(c != null && c.getCount() != 0)
			c.moveToFirst();
		
		
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
		int mdl_name_index = c.getColumnIndex(MynahDB._WEATHER_COL_CITY_MDL_NAME);
		int top_name_index = c.getColumnIndex(MynahDB._WEATHER_COL_CITY_TOP_NAME);
		
		winfo.location.city_code = c.getString(city_code_index);
		winfo.location.city_name = c.getString(city_name_index);
		winfo.location.city_xpos = c.getString(city_x_index);
		winfo.location.city_ypos = c.getString(city_y_index);
		winfo.location.top_name = c.getString(top_name_index);
		winfo.location.mdl_name = c.getString(mdl_name_index);
	
		while(!c.isAfterLast())
		{
			ttw = new TimeToWeather();
			ttw.hour = c.getString(hour_index);
			ttw.pop = c.getString(pop_index);
			ttw.reh = c.getString(reh_index);
			ttw.temp = c.getString(temp_index);
			ttw.wfKor = c.getString(wfkor_index);
			
			winfo.array_ttw.add(ttw);
			
			c.moveToNext();
		}
		
		return winfo;
		
	}
	
	
	public synchronized void setWeatherLocationAll(ArrayList<WeatherLocationInfo> array_location)
	{
		
		ContentValues values;
		
		String sql = "delete from " + MynahDB._WEATHER_CITY_TABLE_NAME + " where 1=1;";
		dbh.mDB.execSQL(sql);
		
		for(int i = 0; i<array_location.size(); ++i)
		{
			
			values = new ContentValues();
		
			values.put(MynahDB._WEATHER_COL_CITY_CODE, array_location.get(i).city_code);
			values.put(MynahDB._WEATHER_COL_CITY_NAME, array_location.get(i).city_name);
			values.put(MynahDB._WEATHER_COL_CITY_XPOS, array_location.get(i).city_xpos);
			values.put(MynahDB._WEATHER_COL_CITY_YPOS, array_location.get(i).city_ypos);
			values.put(MynahDB._WEATHER_COL_CITY_TOP_NAME, array_location.get(i).top_name);
			values.put(MynahDB._WEATHER_COL_CITY_MDL_NAME, array_location.get(i).mdl_name);
				
			dbh.mDB.insert(MynahDB._WEATHER_CITY_TABLE_NAME, null, values);		
		}
		
	}
	
	
	public synchronized ArrayList<WeatherLocationInfo> getWeatherLocationByName(String _name)
	{
		
		ArrayList<WeatherLocationInfo> array_location = new ArrayList<WeatherLocationInfo>();
		
		String sql = "select * from " + MynahDB._WEATHER_CITY_TABLE_NAME +
				" where " + MynahDB._WEATHER_COL_CITY_NAME + " like '%" + _name + "%' ;";
		
		Cursor c = dbh.mDB.rawQuery(sql, null);
		
		if(c != null && c.getCount() != 0)
			c.moveToFirst();
		
		WeatherLocationInfo location;
		
		int city_code_index = c.getColumnIndex(MynahDB._WEATHER_COL_CITY_CODE);
		int city_name_index = c.getColumnIndex(MynahDB._WEATHER_COL_CITY_NAME);
		int city_x_index = c.getColumnIndex(MynahDB._WEATHER_COL_CITY_XPOS);
		int city_y_index = c.getColumnIndex(MynahDB._WEATHER_COL_CITY_YPOS);
		int mdl_name_index = c.getColumnIndex(MynahDB._WEATHER_COL_CITY_MDL_NAME);
		int top_name_index = c.getColumnIndex(MynahDB._WEATHER_COL_CITY_TOP_NAME);
		
		while(!c.isAfterLast())
		{
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
	
	
	public synchronized boolean isInitialUser()
	{
		String sql = "select id from " + MynahDB._USER_TABLE_NAME + 
				" where " + MynahDB._USER_COL_TYPE + "=" + String.valueOf(GlobalVariable.UserType.me) 
				+ " ;";
		
		Cursor c = dbh.mDB.rawQuery(sql, null);
		
		if(c != null && c.getCount() != 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public synchronized FamilyInfo getFamilyDB()
	{
		FamilyInfo finfo = new FamilyInfo();
		UserProfile userProfile;
		
		String sql = "select * from " + MynahDB._USER_TABLE_NAME + 
				" order by " + MynahDB._USER_COL_TYPE + " ; ";
		
		Cursor c = dbh.mDB.rawQuery(sql, null);
		
		int id_index = c.getColumnIndex(MynahDB._USER_COL_ID);
		int passwd_index = c.getColumnIndex(MynahDB._USER_COL_PASSWD);
		int inout_index = c.getColumnIndex(MynahDB._USER_COL_INOUT);
		int name_index = c.getColumnIndex(MynahDB._USER_COL_NAME);
		int key_index = c.getColumnIndex(MynahDB._USER_COL_CERTI_KEY);
		int type_index = c.getColumnIndex(MynahDB._USER_COL_TYPE);
		int master_type_index = c.getColumnIndex(MynahDB._USER_COL_MASTER_TYPE);
	
		while(!c.isAfterLast())
		{
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
	
	
	
	public synchronized void setMemberDB(FamilyInfo finfo)
	{
		
		deleteFamilyExceptMe();
		
		ContentValues values;
		String sql;
		
		
		for(int i = 0; i<finfo.members.size(); ++i)
		{
			if(finfo.members.get(i).usertype != GlobalVariable.UserType.me)
			{
				
				sql = "select * from " + MynahDB._USER_TABLE_NAME +
						" where " + MynahDB._USER_COL_ID + "= '" + finfo.members.get(i).id
						+ "';";
						
				Cursor c = dbh.mDB.rawQuery(sql, null);
				//insert
				values = new ContentValues();
				//DateFormat df = new DateFormat();
				// TODO 날짜 데이터포멧 변경해야할 것임..확인
				
				values.put(MynahDB._USER_COL_ID, finfo.members.get(i).id);
				values.put(MynahDB._USER_COL_PASSWD, finfo.members.get(i).passwd);
				values.put(MynahDB._USER_COL_NAME, finfo.members.get(i).name);
				values.put(MynahDB._USER_COL_TYPE, finfo.members.get(i).usertype);
				values.put(MynahDB._USER_COL_MASTER_TYPE, finfo.members.get(i).mastertype);
				values.put(MynahDB._USER_COL_INOUT, finfo.members.get(i).inout);
				values.put(MynahDB._USER_COL_CERTI_KEY, finfo.members.get(i).mac_address);
				
				
				if (c.getCount() == 0) 
				{
					
					dbh.mDB.insert(MynahDB._USER_TABLE_NAME, null, values);
					
				}
				else
				{
					dbh.mDB.update(MynahDB._USER_TABLE_NAME, values, 
							MynahDB._USER_COL_ID+ "= '" + finfo.members.get(i).id + "'", null);
					
				}
			}
		}
	}
	
	
	public synchronized void deleteFamilyExceptMe()
	{
		String sql = "delete from " + MynahDB._USER_TABLE_NAME + " where "
				+ MynahDB._USER_COL_TYPE + "<>" + GlobalVariable.UserType.me
				+ " ;";
		dbh.mDB.execSQL(sql);
	}
	
	public synchronized void setBusDB(BusInfo binfo)
	{
		
	}
	
	public synchronized BusInfo getBusDB(BusInfo binfo)
	{
		
		return binfo;
	}
	
	
	public synchronized void setSubwayDB(SubwayInfo swinfo)
	{
		
		
	}
	
	public synchronized SubwayInfo getSubwayDB(SubwayInfo swinfo)
	{
		
		return swinfo;
	}
	
	
	public void setMainUserDB(UserProfile upf)
	{
		
	}
	
	public UserProfile getMainUserDB()
	{
		UserProfile muser = new UserProfile();
		
		return muser;
	}
	
	
	

}
