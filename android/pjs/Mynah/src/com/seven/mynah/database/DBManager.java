package com.seven.mynah.database;

import com.seven.mynah.artifacts.BusInfo;
import com.seven.mynah.artifacts.FamilyInfo;
import com.seven.mynah.artifacts.SubwayInfo;
import com.seven.mynah.artifacts.TimeToWeather;
import com.seven.mynah.artifacts.UserProfile;
import com.seven.mynah.artifacts.WeatherInfo;
import com.seven.mynah.globalmanager.GlobalVariable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.format.DateFormat;


public class DBManager {
	
	private static DBManager instance;
	
	private DBHelper dbh;
	
	
	//android 생성자
	public DBManager(Context context) {
		
		dbh = new DBHelper(context);
		dbh.open();
	}

	
	public static synchronized DBManager getManager(Context context)
    {
        if (instance == null)
            instance = new DBManager(context);

        return instance;
    }
	
	public void setWeatherDB(WeatherInfo winfo)
	{
		ContentValues values;
		
		String sql = "delete from " + MynahDB._WEATHER_TABLE_NAME + " where "
				+ MynahDB._WEATHER_COL_DATETIME + " < today();";
		dbh.mDB.execSQL(sql);
		
		//dbh.mDB.delete(MynahDB._WEATHER_TABLE_NAME, MynahDB._WEATHER_COL_DATETIME + " < today()", null);
		
		for(int i = 0; i<winfo.array_ttw.size(); ++i)
		{
			sql = "select " + MynahDB._WEATHER_COL_CITY_CODE + " from " + MynahDB._WEATHER_TABLE_NAME +
					" where " + MynahDB._WEATHER_COL_CITY_CODE + "=" + winfo.city_code
					+ "and " + MynahDB._WEATHER_COL_DATETIME + "=" + winfo.array_ttw.get(i).hour + ";";
					
			Cursor c = dbh.mDB.rawQuery(sql, null);
			if (c.getCount() == 0) 
			{
				//insert
				values = new ContentValues();
				//DateFormat df = new DateFormat();
				// TODO 날짜 데이터포멧 변경해야할 것임..확인
				
				values.put(MynahDB._WEATHER_COL_CITY_CODE, winfo.city_code);
				values.put(MynahDB._WEATHER_COL_CITY_NAME, winfo.city_name);
				values.put(MynahDB._WEATHER_COL_CITY_XPOS, winfo.city_xpos);
				values.put(MynahDB._WEATHER_COL_CITY_YPOS, winfo.city_ypos);
				values.put(MynahDB._WEATHER_COL_DATETIME, winfo.array_ttw.get(i).hour);
				values.put(MynahDB._WEATHER_COL_WFKOR, winfo.array_ttw.get(i).wfKor);
				values.put(MynahDB._WEATHER_COL_POP, winfo.array_ttw.get(i).pop);
				values.put(MynahDB._WEATHER_COL_REH, winfo.array_ttw.get(i).temp);
				values.put(MynahDB._WEATHER_COL_TEMPER, winfo.array_ttw.get(i).temp);
				
				dbh.mDB.insert(MynahDB._WEATHER_TABLE_NAME, null, values);
				
			}
			else
			{
				values = new ContentValues();
				//DateFormat df = new DateFormat();
				// TODO 날짜 데이터포멧 변경해야할 것임..확인
				
				values.put(MynahDB._WEATHER_COL_CITY_CODE, winfo.city_code);
				values.put(MynahDB._WEATHER_COL_CITY_NAME, winfo.city_name);
				values.put(MynahDB._WEATHER_COL_CITY_XPOS, winfo.city_xpos);
				values.put(MynahDB._WEATHER_COL_CITY_YPOS, winfo.city_ypos);
				values.put(MynahDB._WEATHER_COL_DATETIME, winfo.array_ttw.get(i).hour);
				values.put(MynahDB._WEATHER_COL_WFKOR, winfo.array_ttw.get(i).wfKor);
				values.put(MynahDB._WEATHER_COL_POP, winfo.array_ttw.get(i).pop);
				values.put(MynahDB._WEATHER_COL_REH, winfo.array_ttw.get(i).temp);
				values.put(MynahDB._WEATHER_COL_TEMPER, winfo.array_ttw.get(i).temp);
				
				dbh.mDB.update(MynahDB._WEATHER_TABLE_NAME, values, 
						MynahDB._WEATHER_COL_CITY_CODE + "=" + winfo.city_code
						+ "and " + MynahDB._WEATHER_COL_DATETIME + "=" + winfo.array_ttw.get(i).hour, null);
				
			}
//			
		}
		
	}
	
	public WeatherInfo getWeatherDB(WeatherInfo winfo)
	{
		
		//city코드를 기준으로 현재 시간의 이후 정보를 하루치로 return함.
		
		String sql = "select * from " + MynahDB._WEATHER_TABLE_NAME +
				" where " + MynahDB._WEATHER_COL_CITY_CODE + "=" + winfo.city_code
				+ "and " + MynahDB._WEATHER_COL_DATETIME + "=" + winfo.date + ";";
		
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
		
		winfo.city_code = c.getString(city_code_index);
		winfo.city_name = c.getString(city_name_index);
		winfo.city_xpos = c.getInt(city_x_index);
		winfo.city_ypos = c.getInt(city_y_index);
	
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
	
	
	public boolean isInitialUser()
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
	
	public FamilyInfo getFamilyDB()
	{
		FamilyInfo finfo = new FamilyInfo();
		UserProfile userProfile;
		
		String sql = "select * from " + MynahDB._USER_TABLE_NAME + " " +
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
	
	
	//아예 타입으로 엎어버릴까 아님 개별로 작동시킬까...
	public void setFamilyDB(FamilyInfo finfo)
	{
		
		deleteFamilyExceptMe();
		
		String sql;
		
		for(int i = 0; i<finfo.members.size(); ++i)
		{
			if(finfo.members.get(i).usertype != GlobalVariable.UserType.me)
			{
				
				
			}
		}
	}
	
	
	public void deleteFamilyExceptMe()
	{
		String sql = "delete from " + MynahDB._USER_TABLE_NAME + " where "
				+ MynahDB._USER_COL_TYPE + "<>" + GlobalVariable.UserType.me
				+ " ;";
		dbh.mDB.execSQL(sql);
	}
	
	
	public void setBusDB(BusInfo binfo)
	{
		
	}
	
	public BusInfo getBusDB(BusInfo binfo)
	{
		
		return binfo;
	}
	
	
	public void setSubwayDB(SubwayInfo swinfo)
	{
		
		
	}
	
	public SubwayInfo getSubwayDB(SubwayInfo swinfo)
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
