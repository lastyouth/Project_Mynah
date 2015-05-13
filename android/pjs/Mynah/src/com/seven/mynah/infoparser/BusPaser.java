package com.seven.mynah.infoparser;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

import com.seven.mynah.artifacts.BusInfo;
import com.seven.mynah.artifacts.BusRouteInfo;
import com.seven.mynah.artifacts.BusStationInfo;
import com.seven.mynah.artifacts.TimeToBus;

public class BusPaser {


	//2���� �������� ������
	//����� ����
	//���䱳��� ����
	//���� 2�� �� Ű�� �߱޵Ǿ� �ִµ� ������ �ȵʿ�...
	//���⿡ ���°� 3������.
	
	
	private final String serviceKey = "0x3MtlITSbQrDntCBFU0A%2FKeCpVidWB9jeKt8acPuZ0ZHSTW%2FaMiRmuCAsifYUWyYn5jBZN0xEReaIKPMSJSWQ%3D%3D"; 
	private final String open_Url = "http://ws.bus.go.kr/api/rest";
	
	
	private final String option1_Arrive = "/arrive";
	private final String option1_Station = "/stationinfo";
	private final String option1_Route = "/busRouteInfo";
	
	
	//arrive
	private final String option2_GetArrInfoByRoute = "/getArrInfoByRoute";
	
	//station
	private final String option2_GetStationByName = "/getStationByName";
	private final String option2_GetStaionsByPosList = "/getStaionsByPosList";
	private final String option2_GetRouteByStationList = "/getRouteByStationList";
	private final String option2_GetStationByUid = "/getStationByUid";
	
	//route
	private final String option2_GetStaionsByRouteList = "/getStaionByRoute";
	private final String option2_GetBusRouteList = "/getBusRouteList";
	
	
	//�뼱��ȣ ��� ��ȸ�� �ϰ�!
	//�뼱 ����� ��µǸ� �� ������ busRouteld�� ���� �� �ִ�.
	//�� ��Ʈ ���̵� �������
	//�� ��Ʈ�� �����ϴ� ��� �������� ��θ� ��´�.
	//�����Ҹ� �����Ѵ�.
	//�׷� ������ �Ϸ�ǰ�
	//�� �����ҿ� �뼱�� �������� �������� ������ �ð��� ��� �޾ƿ´�.

	//�ð��� ������ �� ���� �����ϵ��� �����Ѵ�.

	
	public BusPaser() {
		
	}
	
	//�⺻ ���� �˻�... ���� �뼱�� ���������̼��� �������� �ð� ������ ������		
	public BusInfo getBusArrInfoByRoute(BusInfo binfo)
	{
		
		String stId =  binfo.station.stId;  // �����̼� ������ ���̵�
		//String arsId = binfo.station.arsId;
		String busRouteId = binfo.route.busRouteId; //�뼱 ���̵�
		String ord = binfo.staOrd;
		SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.KOREA);
		
		
		binfo.array_ttb.clear();
		
		
		try {
			XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = parserFactory.newPullParser();
			
			String str = open_Url + option1_Arrive + option2_GetArrInfoByRoute +
					"?ServiceKey=" + serviceKey + "&stId="
					+ stId + "&busRouteId=" + busRouteId 
					+ "&ord=" +  ord;
			
			URL url = new URL(str);
			ReceiveXml rx = new ReceiveXml(url);
			
			System.out.println(url.toString());
			
			rx.start();
			rx.join();
			
			parser.setInput(new StringReader(rx.getXml()));
			
			System.out.println(rx.getXml());
			
			int eventType = parser.getEventType();
 
            boolean done = false;
            TimeToBus ttb1 = null;
			TimeToBus ttb2 = null;
            
            while (eventType != XmlPullParser.END_DOCUMENT && !done){
                String name = null;
                String temp = null;
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        
                        break;
                    case XmlPullParser.START_TAG:
                    	
                        name = parser.getName();
                        if (name.equalsIgnoreCase("dir")){
                            binfo.dir = parser.nextText();
                        }else if (name.equalsIgnoreCase("kals1")){
                        	temp = parser.nextText();
                        	if(!temp.equalsIgnoreCase("0")){
                        		ttb1 = new TimeToBus();
                        		long now = System.currentTimeMillis();
                        		Date date = new Date(now);
                        		long temp_time = (date.getTime() + Long.valueOf(temp)*1000);
                        		date.setTime(temp_time);
                        		ttb1.time = CurDateFormat.format(date);
                        		//ttb1.time = temp;
                        	}
                        }else if (name.equalsIgnoreCase("kals2")){
                        	temp = parser.nextText();
                        	if(!temp.equalsIgnoreCase("0")){
                        		ttb2 = new TimeToBus();
                        		long now = System.currentTimeMillis();
                        		Date date = new Date(now);
                        		long temp_time = (date.getTime() + Long.valueOf(temp)*1000);
                        		date.setTime(temp_time);
                        		ttb2.time = CurDateFormat.format(date);
                        		//ttb2.time = temp;
                        	}
                        		
                        }else if (name.equalsIgnoreCase("mkTm")){
                        	binfo.last_update = parser.nextText();
                        }else if (name.equalsIgnoreCase("sectOrd1")){
                        	if(ttb1!=null) ttb1.sectNm = parser.nextText();
                        }else if (name.equalsIgnoreCase("sectOrd2")){
                        	if(ttb2!=null) ttb2.sectNm = parser.nextText();
                        }else if (name.equalsIgnoreCase("vehId1")){
                        	if(ttb1!=null) ttb1.vehId = parser.nextText();
                        }else if (name.equalsIgnoreCase("vehId2")){
                        	if(ttb2!=null) ttb2.vehId = parser.nextText();
                        }
                        break;
                    
                }
                eventType = parser.next();
                
            }
            
            if(ttb1!=null) binfo.array_ttb.add(ttb1);
            if(ttb2!=null) binfo.array_ttb.add(ttb2);
            
            
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
		
		
		return binfo;
		
	}
	
	//������ ������ȣ�� ���� �ش� ����ϴ� �뼱 ����� ���� ���� �˼�����..
	//���⼭ ord�� �����Ͽ� �ٽ� ����.
	public BusInfo getStationByUid(BusInfo binfo)
	{
		
		//String stId =  binfo.station.stId;  // �����̼� ������ ���̵�
		String arsId = binfo.station.arsId;
		//String busRouteId = binfo.route.busRouteId; //�뼱 ���̵�
		
		
		//clear �ϸ� �ȵ�.
		//binfo.array_ttb.clear();
		
		try {
			XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = parserFactory.newPullParser();
			
			String str = open_Url + option1_Station + option2_GetStationByUid +
					"?ServiceKey=" + serviceKey + "&arsId="
					+ arsId;
			
			URL url = new URL(str);
			ReceiveXml rx = new ReceiveXml(url);
			
			System.out.println(url.toString());
			
			rx.start();
			rx.join();
			
			parser.setInput(new StringReader(rx.getXml()));
			
			System.out.println(rx.getXml());
			
			int eventType = parser.getEventType();
 
            boolean done = false;
            boolean check = false;
			
            while (eventType != XmlPullParser.END_DOCUMENT && !done){
                String name = null;
         
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        
                        break;
                    case XmlPullParser.START_TAG:
                    	//���� ��������... ���� Ȯ���غ���...
                        name = parser.getName();
                        if (name.equalsIgnoreCase("busRouteId")){
                            if(parser.nextText().equalsIgnoreCase(binfo.route.busRouteId))
                            	check = true;
                            
                        }else if(name.equalsIgnoreCase("staOrd"))
                        {
                        	if(check) {
                        		binfo.staOrd = parser.nextText();
                        		done = true;
                        	}
                        }
                        break;
                    
                }
                eventType = parser.next();
                
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
		
		return binfo;
		
	}
	
	//������ ������ȣ �������� ��Ʈ ����
	public ArrayList<BusRouteInfo> getRouteByStationList(String arsId)
	{
		
		ArrayList<BusRouteInfo> array_rinfo = new ArrayList<BusRouteInfo>();
		
		try {
			
			
			XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = parserFactory.newPullParser();
			
			
			String str = open_Url + option1_Station + option2_GetRouteByStationList +
					"?ServiceKey=" + serviceKey + "&arsId=" + arsId;
			
			URL url = new URL(str);
			
			ReceiveXml rx = new ReceiveXml(url);
			
			System.out.println(url.toString());
			
			rx.start();
			rx.join();
			
			parser.setInput(new StringReader(rx.getXml()));
			
			System.out.println(rx.getXml());
			
			int eventType = parser.getEventType();
 
            int i = -1;
            boolean done = false;
            
            BusRouteInfo rinfo = null;
            
            while (eventType != XmlPullParser.END_DOCUMENT && !done){
                String name = null;
                //String temp;
         
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        
                        break;
                    case XmlPullParser.START_TAG:
                        // �±׸� �ĺ��� �� �±׿� �´� �۾� ����.
                    	//Ű ������� �����ڵ尡 ���� �� ���� ó��
                    	name = parser.getName();
                        if (name.equalsIgnoreCase("itemList")){
                            if (i != -1) {
                            	array_rinfo.add(rinfo);
                            }
                            rinfo = new BusRouteInfo();
                            i++;
                        }else if (name.equalsIgnoreCase("busRouteId")){
                        	rinfo.busRouteId = parser.nextText();
                        }else if (name.equalsIgnoreCase("busRouteNm")){
                        	rinfo.busRouteNm = parser.nextText();
                        }else if (name.equalsIgnoreCase("routeType")){
                        	rinfo.routeType = parser.nextText();
                        }else if (name.equalsIgnoreCase("stStationNm")){
                        	rinfo.stStationNm = parser.nextText();
                        }else if (name.equalsIgnoreCase("edStationNm")){
                        	rinfo.edStationNm = parser.nextText();
                        }
                        break;
                    
                }
                eventType = parser.next();
                
            }
            if (i != -1) {
            	array_rinfo.add(rinfo);
            }
            
            
            
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
		
		
		return array_rinfo;
	}
	
	//�˻��� �뼱 �̸��� ������� �뼱������ ����
	public ArrayList<BusRouteInfo> getBusRouteList(String _strSrch)
	{
		
		ArrayList<BusRouteInfo> array_rinfo = new ArrayList<BusRouteInfo>();
		
		try {
			
			//String strSrch =  URLEncoder.encode(_strSrch, "utf-8");
			
			XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = parserFactory.newPullParser();
			
			
			String str = open_Url + option1_Route + option2_GetBusRouteList +
					"?ServiceKey=" + serviceKey + "&strSrch=" + _strSrch;
			
			URL url = new URL(str);
			
			ReceiveXml rx = new ReceiveXml(url);
			
			System.out.println(url.toString());
			
			rx.start();
			rx.join();
			
			parser.setInput(new StringReader(rx.getXml()));
			
			System.out.println(rx.getXml());
			
			int eventType = parser.getEventType();
 
            int i = -1;
            boolean done = false;
            
            BusRouteInfo rinfo = null;
            
            while (eventType != XmlPullParser.END_DOCUMENT && !done){
                String name = null;
                //String temp;
         
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        
                        break;
                    case XmlPullParser.START_TAG:
                        // �±׸� �ĺ��� �� �±׿� �´� �۾� ����.
                    	//Ű ������� �����ڵ尡 ���� �� ���� ó��
                    	name = parser.getName();
                        if (name.equalsIgnoreCase("itemList")){
                            if (i != -1) {
                            	array_rinfo.add(rinfo);
                            }
                            rinfo = new BusRouteInfo();
                            i++;
                        }else if (name.equalsIgnoreCase("busRouteId")){
                        	rinfo.busRouteId = parser.nextText();
                        }else if (name.equalsIgnoreCase("busRouteNm")){
                        	rinfo.busRouteNm = parser.nextText();
                        }else if (name.equalsIgnoreCase("routeType")){
                        	rinfo.routeType = parser.nextText();
                        }else if (name.equalsIgnoreCase("stStationNm")){
                        	rinfo.stStationNm = parser.nextText();
                        }else if (name.equalsIgnoreCase("edStationNm")){
                        	rinfo.edStationNm = parser.nextText();
                        }
                        break;
                    
                }
                eventType = parser.next();
                
            }
            if (i != -1) {
            	array_rinfo.add(rinfo);
            }
            
            
            
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
		
		
		return array_rinfo;
	}
	
	
	public ArrayList<BusStationInfo> getStaionsByRouteList(String busRouteId)
	{
		
		ArrayList<BusStationInfo> array_sinfo = new ArrayList<BusStationInfo>();
		
		try {
			
			
			XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = parserFactory.newPullParser();
			
			
			String str = open_Url + option1_Route + option2_GetStaionsByRouteList +
					"?ServiceKey=" + serviceKey + "&busRouteId=" + busRouteId;
			
			URL url = new URL(str);
			
			ReceiveXml rx = new ReceiveXml(url);
			
			System.out.println(url.toString());
			
			rx.start();
			rx.join();
			
			parser.setInput(new StringReader(rx.getXml()));
			
			System.out.println(rx.getXml());
			
			int eventType = parser.getEventType();
 
            int i = -1;
            boolean done = false;
            
            BusStationInfo sinfo = null;
            
            while (eventType != XmlPullParser.END_DOCUMENT && !done){
                String name = null;
                //String temp;
         
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        
                        break;
                    case XmlPullParser.START_TAG:
                        // �±׸� �ĺ��� �� �±׿� �´� �۾� ����.
                    	//Ű ������� �����ڵ尡 ���� �� ���� ó��
                    	name = parser.getName();
                        if (name.equalsIgnoreCase("itemList")){
                            if (i != -1) {
                            	array_sinfo.add(sinfo);
                            }
                            sinfo = new BusStationInfo();
                            i++;
                        }else if (name.equalsIgnoreCase("stationNo")){
                        	sinfo.arsId = parser.nextText();
                        }else if (name.equalsIgnoreCase("station")){
                        	sinfo.stId = parser.nextText();
                        }else if (name.equalsIgnoreCase("stationNm")){
                        	sinfo.stNm = parser.nextText();
                        }else if (name.equalsIgnoreCase("gpsX")){
                        	sinfo.tmX = parser.nextText();
                        }else if (name.equalsIgnoreCase("gpsY")){
                        	sinfo.tmY = parser.nextText();
                        }
                        break;
                    
                }
                eventType = parser.next();
                
            }
            if (i != -1) {
            	array_sinfo.add(sinfo);
            }
            
            
            
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
		
		
		return array_sinfo;
	}
	
	//���� �̸� ������ �˻���.
	public ArrayList<BusStationInfo> getStationByNameList(String _stSrch)
	{
		
		ArrayList<BusStationInfo> array_sinfo = new ArrayList<BusStationInfo>();
		
		try {
			
			String stSrch =  URLEncoder.encode(_stSrch, "utf-8");
			
			XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = parserFactory.newPullParser();
			
			
			String str = open_Url + option1_Station + option2_GetStationByName +
					"?ServiceKey=" + serviceKey + "&stSrch=" + stSrch;
			
			URL url = new URL(str);
			
			ReceiveXml rx = new ReceiveXml(url);
			
			System.out.println(url.toString());
			
			rx.start();
			rx.join();
			
			parser.setInput(new StringReader(rx.getXml()));
			
			System.out.println(rx.getXml());
			
			int eventType = parser.getEventType();
 
            int i = -1;
            boolean done = false;
            
            BusStationInfo sinfo = null;
            
            while (eventType != XmlPullParser.END_DOCUMENT && !done){
                String name = null;
                //String temp;
         
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        
                        break;
                    case XmlPullParser.START_TAG:
                        // �±׸� �ĺ��� �� �±׿� �´� �۾� ����.
                    	//Ű ������� �����ڵ尡 ���� �� ���� ó��
                    	name = parser.getName();
                        if (name.equalsIgnoreCase("itemList")){
                            if (i != -1) {
                            	array_sinfo.add(sinfo);
                            }
                            sinfo = new BusStationInfo();
                            i++;
                        }else if (name.equalsIgnoreCase("arsId")){
                        	sinfo.arsId = parser.nextText();
                        }else if (name.equalsIgnoreCase("stId")){	
                        	sinfo.stId = parser.nextText();
                        }else if (name.equalsIgnoreCase("stNm")){
                        	sinfo.stNm = parser.nextText();
                        }else if (name.equalsIgnoreCase("tmX")){
                        	sinfo.tmX = parser.nextText();
                        }else if (name.equalsIgnoreCase("tmY")){
                        	sinfo.tmY = parser.nextText();
                        }
                        break;
                    
                }
                eventType = parser.next();
                
            }
            if (i != -1) {
            	array_sinfo.add(sinfo);
            }
            
            
            
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
		
		
		return array_sinfo;
		
	}
	
	
	public ArrayList<BusStationInfo> getStaionsByPosList(String tmX, String tmY, String radius)
	{
		
		ArrayList<BusStationInfo> array_sinfo = new ArrayList<BusStationInfo>();
		
		try {
			
			
			XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = parserFactory.newPullParser();
			
			
			String str = open_Url + option1_Station + option2_GetStaionsByPosList +
					"?ServiceKey=" + serviceKey + "&tmX=" + tmX + "&tmY=" + tmY + "&radius=" + radius;
			
			URL url = new URL(str);
			
			ReceiveXml rx = new ReceiveXml(url);
			
			System.out.println(url.toString());
			
			rx.start();
			rx.join();
			
			parser.setInput(new StringReader(rx.getXml()));
			
			System.out.println(rx.getXml());
			
			int eventType = parser.getEventType();
 
            int i = -1;
            boolean done = false;
            
            BusStationInfo sinfo = null;
            
            while (eventType != XmlPullParser.END_DOCUMENT && !done){
                String name = null;
                //String temp;
         
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        
                        break;
                    case XmlPullParser.START_TAG:
                        // �±׸� �ĺ��� �� �±׿� �´� �۾� ����.
                    	//Ű ������� �����ڵ尡 ���� �� ���� ó��
                    	name = parser.getName();
                        if (name.equalsIgnoreCase("itemList")){
                            if (i != -1) {
                            	array_sinfo.add(sinfo);
                            }
                            sinfo = new BusStationInfo();
                            i++;
                        }else if (name.equalsIgnoreCase("arsId")){
                        	sinfo.arsId = parser.nextText();
                        }else if (name.equalsIgnoreCase("stId")){	
                        	sinfo.stId = parser.nextText();
                        }else if (name.equalsIgnoreCase("stNm")){
                        	sinfo.stNm = parser.nextText();
                        }else if (name.equalsIgnoreCase("tmX")){
                        	sinfo.tmX = parser.nextText();
                        }else if (name.equalsIgnoreCase("tmY")){
                        	sinfo.tmY = parser.nextText();
                        }
                        break;
                    
                }
                eventType = parser.next();
                
            }
            if (i != -1) {
            	array_sinfo.add(sinfo);
            }
            
            
            
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
		
		
		return array_sinfo;
		
	}
	
}