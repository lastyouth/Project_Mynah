package com.seven.mynah.infoparser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.zip.DataFormatException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


import android.text.format.DateFormat;
import android.util.Log;
import android.util.Xml;

import com.seven.mynah.artifacts.*;



//��ü�� ���������� �ʵ��� ��(�ٿ�޴°� ����)
//�ļ��� ȣ���ϴ� Ŭ�������� ������


public class WeatherParser {

	//�Ľ��� �ڷḦ DB�� �ִ°ͱ����� �ϴ� ������ �Ѵ�.
	//�� ������ �������� ������Ʈ�Ѵ�.
	//DB ���� Ŭ������ ��� �ߺ��ǰų� ���� �߰��Ǿ��ϴ� �κ��� 
	//���ʿ��� �� ó���Ѵ�.
	
	private final String rssCityTop = "http://www.kma.go.kr/DFSROOT/POINT/DATA/top.json.txt";
	private final String rssCityMdl = "http://www.kma.go.kr/DFSROOT/POINT/DATA/mdl.%s.json.txt";
	private final String rssCityleft = "http://www.kma.go.kr/DFSROOT/POINT/DATA/leaf.%s.json.txt";
	
	private final String rssFeed = "http://www.kma.go.kr/wid/queryDFS.jsp?gridx=%s&gridy=%s";
	
	//private SimpleDateFormat formater = new SimpleDateFormat("YYYYMMDDHH");
	
	public WeatherParser() {
		
	}
    
	public void parseWeather_XML(WeatherInfo winfo)
	{
		
		String xpos = String.valueOf(winfo.city_xpos); 
		String ypos = String.valueOf(winfo.city_ypos);
		
		try {
			XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = parserFactory.newPullParser();
			
			//URL url = new URL("http://www.kma.go.kr/wid/queryDFS.jsp?gridx=" + xpos + "&gridy=" + ypos);
			//InputStream in = url.openConnection().getInputStream();
			
			//InputStream in = downloadUrl("http://www.kma.go.kr/wid/queryDFS.jsp?gridx=" + xpos + "&gridy=" + ypos);
			
			//parser.setInput(in,null);
			ReceiveXml rx = new ReceiveXml(
					new URL("http://www.kma.go.kr/wid/queryDFS.jsp?gridx=" + xpos + "&gridy=" + ypos));
			
			rx.start();
			rx.join();
			
			parser.setInput(new StringReader(rx.getXml()));
			
			int eventType = parser.getEventType();
 
            boolean done = false;
			int i = -1;
            TimeToWeather ttw = null;
			
            while (eventType != XmlPullParser.END_DOCUMENT && !done){
                String name = null;
                //String temp;
         
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        
                        break;
                    case XmlPullParser.START_TAG:
                        // �±׸� �ĺ��� �� �±׿� �´� �۾��� �����մϴ�.
                        name = parser.getName();
                        if (name.equalsIgnoreCase("data")){
                            //currentMessage = new Message();
                            //temp = parser.getAttributeValue("", "seq");
                            if (i != -1) {
                            	//winfo.ttw[i] = ttw;
                            	winfo.array_ttw.add(ttw);
                            }
                            ttw = new TimeToWeather();
                            i++;
                        }else if (name.equalsIgnoreCase("tm")){
                        	winfo.last_update = parser.nextText();
                        }else if (name.equalsIgnoreCase("wfKor")){
                        	ttw.wfKor = parser.nextText();
                        }else if (name.equalsIgnoreCase("pop")){
                        	ttw.pop = parser.nextText();
                        }else if (name.equalsIgnoreCase("reh")){
                        	ttw.reh = parser.nextText();
                        }else if (name.equalsIgnoreCase("hour")){
                        	ttw.hour = parser.nextText();
                        }else if (name.equalsIgnoreCase("temp")){
                        	ttw.temp = parser.nextText();
                        }
                        break;
                    
                }
                eventType = parser.next();
                
            }
            //winfo.ttw[i] = ttw;
            winfo.array_ttw.add(ttw);
            
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
	
	
	private void parseCityInfo_JSON()  {
	
		ReceiveXml rx;
		ReceiveXml rx2;
		ReceiveXml rx3;
		
		String temp, temp2, temp3;
		URL url;
		
		try {
			url = new URL(rssCityTop);
			rx = new ReceiveXml(url);
			JSONObject jso = new JSONObject();
			JSONArray jsa;
			rx.start();
			rx.join();
			jsa = new JSONArray(rx.getXml()); 
			
			for(int i=0;i<jsa.length();++i)
			{
				jso = jsa.getJSONObject(i);
				temp = jso.names().getString(0);
				
				
				url = new URL("http://www.kma.go.kr/DFSROOT/POINT/DATA/mdl." + temp + ".json.txt");
				rx2 = new ReceiveXml(url);
				JSONObject jso2 = new JSONObject();
				JSONArray jsa2;
				rx2.start();
				rx2.join();
				jsa2 = new JSONArray(rx2.getXml());
				
				for(int j=0;j<jsa2.length();++j)
				{
					jso2 = jsa2.getJSONObject(j);
					temp2 = jso2.names().getString(0);
					
					url = new URL("http://www.kma.go.kr/DFSROOT/POINT/DATA/leaf." + temp + ".json.txt");
					rx3 = new ReceiveXml(url);
					JSONObject jso3 = new JSONObject();
					JSONArray jsa3;
					rx3.start();
					rx3.join();
					jsa3 = new JSONArray(rx3.getXml());
					
					for (int k=0;k<jsa3.length();++k)
					{
						jso3 = jsa3.getJSONObject(k);
						//temp3 = jso3.names().getString(0);
						//������ �͵��� DB���ٰ� �ִ� ������ ��...Ȥ�� csv ��ȯ
						
						
					}
					
				}
				
			}
			

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void parseWeather_JSON(WeatherInfo winfo)
	{
		
		
		String xpos = String.valueOf(winfo.city_xpos); 
		String ypos = String.valueOf(winfo.city_ypos);
		
		try {
			ReceiveXml rx = new ReceiveXml(
					new URL("http://www.kma.go.kr/wid/queryDFS.jsp?gridx=" + xpos + "&gridy=" + ypos));
			
			JSONObject jso = new JSONObject();
			JSONArray jsa;
			rx.start();
			rx.join();
			jsa = new JSONArray(rx.getXml()); 
			
			jso = jsa.getJSONObject(0);
		
		
			//���⿡�� ������ �Ľ��ϰ� �� ������ �������� 
			// winfo ���ٰ� ������ ����.
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d(getClass().toString(),e.getMessage());
		}
		
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
