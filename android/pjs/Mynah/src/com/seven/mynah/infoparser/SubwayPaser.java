package com.seven.mynah.infoparser;


import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.seven.mynah.artifacts.SubwayInfo;
import com.seven.mynah.artifacts.TimeToSubway;
import com.seven.mynah.artifacts.SubwayStationInfo;


public class SubwayPaser {
	
	
	
	private final String open_Url = "http://openAPI.seoul.go.kr:8088/";
	private final String serviceKey = "7058616c627361793637446d4b5152";  
	
	
	public SubwayPaser() {
		
		
	}
	
	public SubwayInfo getTimeTableByID(SubwayInfo sinfo)
	{
		
		String station_cd =  sinfo.station.station_cd;
		String week_tag = String.valueOf(sinfo.week_tag);
		String inout_tag = String.valueOf(sinfo.inout_tag);
		
		int start = 1;
		int end = 200;
		int i = 0;
		
		try {
			
			XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = parserFactory.newPullParser();
			
			
			while(i < end)
			{
				String str = open_Url + serviceKey + "/xml/SearchSTNTimeTableByIDService/"
						+ String.valueOf(start) + "/" + String.valueOf(end) + "/"
						+ station_cd + "/" + week_tag + "/" + inout_tag;
				
				URL url = new URL(str);
				
				System.out.println(url.toString());
				
				ReceiveXml rx = new ReceiveXml(url);
								
				rx.start();
				rx.join();
				
				parser.setInput(new StringReader(rx.getXml()));
				
				int eventType = parser.getEventType();
	 
	            boolean done = false;
	            TimeToSubway tts = null;
				
	            while (eventType != XmlPullParser.END_DOCUMENT && !done){
	            	String name = null;
	            	
	                switch (eventType){
	                    case XmlPullParser.START_DOCUMENT:
	                        
	                        break;
	                    case XmlPullParser.START_TAG:
	                        // 태그를 식별한 뒤 태그에 맞는 작업을 수행합니다.
	                        name = parser.getName();
	                        if (name.equalsIgnoreCase("row")){
	                            tts = new TimeToSubway();
	                            i++;
	                        }else if (name.equalsIgnoreCase("TRAIN_NO")){
	                        	tts.train_no = parser.nextText();
	                        }else if (name.equalsIgnoreCase("LEFTTIME")){
	                        	tts.arr_time = parser.nextText();
	                        }else if (name.equalsIgnoreCase("SUBWAYSNAME")){
	                        	tts.subway_start_name = parser.nextText();
	                        }else if (name.equalsIgnoreCase("SUBWAYENAME")){
	                        	tts.subway_end_name = parser.nextText();
	                        }else if (name.equalsIgnoreCase("FL_FLAG")){
	                        	tts.fl_flag = parser.nextText();
	                        	sinfo.array_tts.add(tts);
	                        }else if (name.equalsIgnoreCase("list_total_count")){
	                        	end = Integer.valueOf(parser.nextText());
	                        }
	                        break;
	                    
	                }
	                eventType = parser.next();
	                
	            }
	            
	            if(i < end) start = i + 1;
				
				
			}
			
			
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sinfo;
		
	}
	
	
	public ArrayList<SubwayStationInfo> getStationInfoByName(String station_nm) {
		
		ArrayList<SubwayStationInfo> array_station = new ArrayList<SubwayStationInfo>();
		
		
		int start = 1;
		int end = 200;
		int i = 0;
		
		try {
			
			XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = parserFactory.newPullParser();
			
			String _station_nm =  URLEncoder.encode(station_nm, "utf-8");
			
			while(i < end)
			{
				String str = open_Url + serviceKey + "/xml/SearchInfoBySubwayNameService/"
						+ String.valueOf(start) + "/" + String.valueOf(end) + "/"
						+ _station_nm;
				
				URL url = new URL(str);
				
				System.out.println(url.toString());
				
				ReceiveXml rx = new ReceiveXml(url);
								
				rx.start();
				rx.join();
				
				parser.setInput(new StringReader(rx.getXml()));
				
				int eventType = parser.getEventType();
	 
	            boolean done = false;
	            SubwayStationInfo station = null;
				
	            while (eventType != XmlPullParser.END_DOCUMENT && !done){
	            	String name = null;
	            	
	                switch (eventType){
	                    case XmlPullParser.START_DOCUMENT:
	                        
	                        break;
	                    case XmlPullParser.START_TAG:
	                        // 태그를 식별한 뒤 태그에 맞는 작업을 수행합니다.
	                        name = parser.getName();
	                        if (name.equalsIgnoreCase("row")){
	                            station = new SubwayStationInfo();
	                            i++;
	                        }else if (name.equalsIgnoreCase("STATION_CD")){
	                        	station.station_cd = parser.nextText();
	                        }else if (name.equalsIgnoreCase("STATION_NM")){
	                        	station.station_nm = parser.nextText();
	                        }else if (name.equalsIgnoreCase("LINE_NUM")){
	                        	station.line_num = parser.nextText();
	                        }else if (name.equalsIgnoreCase("FR_CODE")){
	                        	station.fr_code = parser.nextText();
	                        	array_station.add(station);
	                        }else if (name.equalsIgnoreCase("list_total_count")){
	                        	end = Integer.valueOf(parser.nextText());
	                        }
	                        break;
	                    
	                }
	                eventType = parser.next();
	                
	            }
	            
	            if(i < end) start = i + 1;
				
				
			}
			
			
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return array_station;
		
	}

}
