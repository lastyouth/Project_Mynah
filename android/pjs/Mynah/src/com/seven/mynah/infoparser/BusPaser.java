package com.seven.mynah.infoparser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.text.style.StyleSpan;
import android.util.Log;
import android.util.Xml.Encoding;

import com.seven.mynah.artifacts.BusInfo;
import com.seven.mynah.artifacts.TimeToBus;
import com.seven.mynah.artifacts.TimeToWeather;
import com.seven.mynah.artifacts.WeatherInfo;
import com.seven.mynah.infoparser.WeatherParser.ReceiveXml;

public class BusPaser {


	//여기에 들어가는게 3가지임.
	private final String rssFeed = "http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRoute?stId=%s&busRouteId=%s&ord=%s";
	private final String serviceKey = "0x3MtlITSbQrDntCBFU0A%2FKeCpVidWB9jeKt8acPuZ0ZHSTW%2FaMiRmuCAsifYUWyYn5jBZN0xEReaIKPMSJSWQ%3D%3D"; 
	//나중에 발급받으면 거기다가 넣을 것
	
	
	
	public BusPaser() {
		
	}
	
	public void parseBus_XML(BusInfo binfo)
	{
		
		//
		String stId =  binfo.stId;
		String busRouteId = binfo.busRouteId;
		String ord = binfo.ord; //일단 임시로
		
		
		try {
			XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = parserFactory.newPullParser();
			
			
			URL url = new URL("http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRoute?ServiceKey=" 
					+ serviceKey + "&stId=" + stId + "&busRouteId=" + busRouteId
					+ "&ord=" + ord);
			ReceiveXml rx = new ReceiveXml(url);
			
			System.out.println(url.toString());
			
			rx.start();
			rx.join();
			
			parser.setInput(new StringReader(rx.getXml()));
			
			System.out.println(rx.getXml());
			
			int eventType = parser.getEventType();
 
            boolean done = false;
			int i = -1;
            TimeToBus ttb = null;
			
            while (eventType != XmlPullParser.END_DOCUMENT && !done){
                String name = null;
                //String temp;
         
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        
                        break;
                    case XmlPullParser.START_TAG:
                        // 태그를 식별한 뒤 태그에 맞는 작업을 수행합니다.
                        name = parser.getName();
                        if (name.equalsIgnoreCase("data")){
                            //currentMessage = new Message();
                            //temp = parser.getAttributeValue("", "seq");
                            if (i != -1) {
                            	binfo.array_ttb.add(ttb);
                            }
                            ttb = new TimeToBus();
                            i++;
                        }else if (name.equalsIgnoreCase("tm")){
                        	//winfo.last_update = parser.nextText();
                        }else if (name.equalsIgnoreCase("wfKor")){
                        	//ttw.wfKor = parser.nextText();
                        }else if (name.equalsIgnoreCase("pop")){
                        	//ttw.pop = parser.nextText();
                        }else if (name.equalsIgnoreCase("reh")){
                        	//ttw.reh = parser.nextText();
                        }else if (name.equalsIgnoreCase("hour")){
                        	//ttw.hour = parser.nextText();
                        }else if (name.equalsIgnoreCase("temp")){
                        	//ttw.temp = parser.nextText();
                        }
                        break;
                    
                }
                eventType = parser.next();
                
            }

            binfo.array_ttb.add(ttb);
            
            //db에다가 저장하는 내용으로
            
            
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("Mynah", e.toString());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	/**
	 * 도시코드와 버스아이디로 버스정류소정보를 조회한다
	 **/
	public void parseStationInfo_XML(String _name)
	{
			
		try {
			
			String stSrch =  URLEncoder.encode(_name, "utf-8");
			
			
			XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = parserFactory.newPullParser();
			
			
			URL url = new URL("http://openapi.tago.go.kr/openapi/service/BusSttnInfoInqireService/getStationByName"
					+ "?ServiceKey=" + URLEncoder.encode(serviceKey, "UTF-8")
					+ "?stSrch=" + stSrch);
			
			ReceiveXml rx = new ReceiveXml(url);
			
			System.out.println(url.toString());
			
			rx.start();
			rx.join();
			
			parser.setInput(new StringReader(rx.getXml()));
			
			System.out.println(rx.getXml());
			
			
			int eventType = parser.getEventType();
 
            boolean done = false;
			int i = -1;
            TimeToBus ttb = null;
			
            while (eventType != XmlPullParser.END_DOCUMENT && !done){
                String name = null;
                //String temp;
         
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        
                        break;
                    case XmlPullParser.START_TAG:
                        // 태그를 식별한 뒤 태그에 맞는 작업을 수행합니다.
                        
                        break;
                    
                }
                eventType = parser.next();
                
            }

            //db에다가 저장하는 내용으로
            
            
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("Mynah", e.toString());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void parseStationInfo_XML(double x_pos, double y_pos)
	{
		
		
		
	}
	
	
	public void parseStationInfo_XML()
	{
		
		
		
	}
	
	
	
	class ReceiveXml extends Thread {
        

		private URL url;
		private String output;
		
		
		public ReceiveXml(URL url) {
			this.url = url;
		}
		
		public String getXml() {
			return output;
		}
		
		public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        url.openStream(), "UTF-8"));
                output = in.readLine();
                while(true){
                    String temp;
                    temp = in.readLine();
                    if(temp==null)
                        break;
                    output += temp;
                }
            } catch (Exception e) {
            	// TODO Auto-generated catch block
    			e.printStackTrace();
    			Log.d(getName(), e.toString());
            }
        }
    }
	
}
