package com.seven.mynah.infoparser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;

public class WeatherCityParser {

	//http://www.kma.go.kr/wid/queryDFS.jsp?gridx=59&gridy=125
    
	private String rssCityTop = "http://www.kma.go.kr/DFSROOT/POINT/DATA/top.json.txt";
	private String rssCityMdl = "http://www.kma.go.kr/DFSROOT/POINT/DATA/mdl.%s.json.txt";
	private String rssCityleft = "http://www.kma.go.kr/DFSROOT/POINT/DATA/leaf.%s.json.txt";
	private String rssFeed = "http://www.kma.go.kr/wid/queryDFS.jsp?gridx=%s&gridy=%s";
	
	ReceiveXml rx;
	ReceiveXml rx2;
	ReceiveXml rx3;
	
	
	public WeatherCityParser() {
		
		createCityInfoAll();
	}
	
	private void createCityInfoAll()  {
	
		
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
						//여깄는 것들을 DB에다가 넣는 로직을 함...혹은 csv 전환
						
						
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
            }
        }
    }
	
	
}
