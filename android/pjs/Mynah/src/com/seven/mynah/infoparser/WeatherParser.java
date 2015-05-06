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





//자체가 쓰레딩하지 않도록 함(다운받는곳 제외)
//파서를 호출하는 클래스에서 쓰레딩
public class WeatherParser {

	//파싱한 자료를 DB에 넣는것까지만 하는 것으로 한다.
	//현 시점을 기준으로 업데이트한다.
	//DB 내부 클래스의 경우 중복되거나 새로 추가되야하는 부분은 
	//그쪽에서 다 처리한다.
	
	private final String rssFeed = "http://www.kma.go.kr/wid/queryDFS.jsp?gridx=%s&gridy=%s";
	
	//private SimpleDateFormat formater = new SimpleDateFormat("YYYYMMDDHH");
	
	public WeatherParser() {
		
	}
    
	public void parserWeather_XML(WeatherInfo winfo)
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
                        // 태그를 식별한 뒤 태그에 맞는 작업을 수행합니다.
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
		
		
			//여기에서 내용을 파싱하고 그 내용을 바탕으로 
			// winfo 에다가 내용을 넣음.
			
			
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
	
	
	
	
	

	private InputStream downloadUrl(String urlString) throws IOException {
	    URL url = new URL(urlString);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setReadTimeout(10000 /* milliseconds */);
	    conn.setConnectTimeout(15000 /* milliseconds */);
	    conn.setRequestMethod("GET");
	    conn.setDoInput(true);
	    // Starts the query
	    conn.connect();
	    return conn.getInputStream();
	}
	
	
	//receiveXML
	//얘가 별로면 httpClient를 사용한다.
	//이것조차 별로이면 XmlFullparser를 응용한다.
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
