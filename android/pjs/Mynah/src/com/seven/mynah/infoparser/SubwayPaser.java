package com.seven.mynah.infoparser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.seven.mynah.artifacts.BusInfo;
import com.seven.mynah.artifacts.SubwayInfo;
import com.seven.mynah.artifacts.TimeToBus;
import com.seven.mynah.artifacts.TimeToSubway;
import com.seven.mynah.infoparser.BusPaser.ReceiveXml;

import android.util.Log;

public class SubwayPaser {
	
	
	
	private final String rssFeed = "http://openapi.seoul.go.kr:8088/sample/xml/SearchSTNTimeTableByIDService/2/2/0151/1/1/";
	private final String serviceKey = "7058616c627361793637446d4b5152"; //����Ű ������ ���� 
	
	
	public SubwayPaser() {
		
		
	}
	
	public void parserSubway_XML(SubwayInfo sinfo)
	{
		
		String station_cd =  sinfo.station_cd;
		String week_tag = String.valueOf(sinfo.week_tag);
		String inout_tag = String.valueOf(sinfo.inout_tag);
		
		int max_size = 0;
		
		
		/*		
		try {
			
			//first max size�� ����.
			
			XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = parserFactory.newPullParser();
			
			
			ReceiveXml rx = new ReceiveXml(
					new URL("http://openAPI.seoul.go.kr:8088/" + serviceKey
							+ "/xml/SearchSTNTimeTableByIDService/1/300/" + station_cd
							+ "/" + week_tag + "/" + inout_tag));
							
			
			rx.start();
			rx.join();
			
			parser.setInput(new StringReader(rx.getXml()));
			
			int eventType = parser.getEventType();
 
            boolean done = false;
			int i = -1;
            TimeToSubway tts = null;
			
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
            
            //db���ٰ� �����ϴ� ��������
            
            
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
		*/
		
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
