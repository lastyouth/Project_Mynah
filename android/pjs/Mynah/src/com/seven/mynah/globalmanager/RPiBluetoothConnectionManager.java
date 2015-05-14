package com.seven.mynah.globalmanager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.array;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.provider.Settings.Secure;
import android.util.Base64;
import android.util.Log;

public class RPiBluetoothConnectionManager {
	private final UUID uuid = UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49daae"); //Standard SerialPortService ID

	private BluetoothSocket btSocket;
	private BluetoothDevice targetBTDevice;
	private final String TAG = "RPI_BT_MGR";
	private boolean isInitialize;
	private BluetoothAdapter btAdapter;
	private final String TARGET_MAC_ADDR = "00:1A:7D:DA:71:07";
	
	private boolean isWatchThreadActivated;
	private boolean isServeThreadActivated;
	
	private WatcherThread wtThread;
	private ServeThread svThread;
	
	private String deviceID;
	
	
	private ArrayList<String> array_TTS;
	
	// sendtype
	
	public static final int SEND_TYPE_RSSI = 0x30001001;
	public static final int SEND_TYPE_TTS = 0x30001002;
	public static final int SEND_TYPE_INIT = 0x30001003;
	
	// settings
	private final int MAX_WAIT_FOR_RECONNECT = 3000;
	private final int MAX_WAIT_FOR_ALIVE = 10000;
	
	// messages
	
	public static final int ERROR_BT_NOT_SUPPORTED = 0x10001001;
	public static final int ERROR_TARGET_DEVICE_NOT_REGISTERED = 0x10001002;
	
	public static final int SUCCESS_INITIALIZE = 0x20001001;
	
	private class ServeThread extends Thread
	{
		public void run()
		{
			while(isServeThreadActivated)
			{
				if(btSocket.isConnected())
				{
					try {
						//OutputStream mmOutputStream = socket.getOutputStream();
						InputStream tis = btSocket.getInputStream();
						byte[] b = new byte[1024];
						int len;
						len = tis.read(b,0,1024);
						Log.i(TAG,"len : "+len);
						String str = new String(b,0,len);

						Log.i(TAG,str);
						
						if(str.equals("tts"))
						{
							if(array_TTS.size() != 0)
							{
								sendTo(SEND_TYPE_TTS, array_TTS.get(0));
							}
							
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						Log.e(TAG,"Error in reading from Server !");
						try {
							btSocket.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

				}

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private class WatcherThread extends Thread
	{
		public void run()
		{
			while(isWatchThreadActivated)
			{
				if(!btSocket.isConnected())
				{
					connectInner();
				}
				else
				{
					try {
						Thread.sleep(MAX_WAIT_FOR_ALIVE);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	private void connectInner()
	{
		try
		{
			btSocket = targetBTDevice.createRfcommSocketToServiceRecord(uuid);
			btSocket.connect();
		}catch(IOException e)
		{
			Log.e(TAG,"BTCONNECT - IO ERROR");
		}
		try {
			Thread.sleep(MAX_WAIT_FOR_RECONNECT);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sendTo(SEND_TYPE_INIT,"HITHERE");
	}
	
	
	public RPiBluetoothConnectionManager(String deviceID)
	{
		isWatchThreadActivated = false;
		isServeThreadActivated = false;
		btAdapter = BluetoothAdapter.getDefaultAdapter();
		this.deviceID = deviceID;
		isInitialize = false;
		array_TTS = new ArrayList<String>();
		array_TTS.add("요약 정보가 준비되지 않았습니다.");
	}
	
	public int initializeBTConnection()
	{
		if(btAdapter == null)
		{
			return ERROR_BT_NOT_SUPPORTED;
		}
		
		targetBTDevice = btAdapter.getRemoteDevice(TARGET_MAC_ADDR);
		
		if(targetBTDevice == null)
		{
			return ERROR_TARGET_DEVICE_NOT_REGISTERED;
		}
		
		connectInner();
		
		wtThread = new WatcherThread();
		
		isWatchThreadActivated = true;
		
		wtThread.start();
		
		svThread = new ServeThread();
		
		isServeThreadActivated = true;
		
		svThread.start();
		
		isInitialize = true;
		return SUCCESS_INITIALIZE;
	}
	public void stopBTConnection()
	{
		isWatchThreadActivated = false;
		isServeThreadActivated = false;
		if(btSocket.isConnected())
		{
			try {
				btSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			wtThread.join();
			svThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean sendTo(int type,String data)
	{
		JSONObject json = new JSONObject();
		
		try {
			json.put("id",this.deviceID);
			//json.put("rssi", Long.toString(rssi));
			switch(type)
			{
			case SEND_TYPE_TTS:
				json.put("type", "tts");
				break;
			case SEND_TYPE_INIT:
				json.put("type","init");
				break;
			default:
				json.put("type", "bad");	
			}
			json.put("data", data);
			
			String encodedjson = Base64.encodeToString(json.toString().getBytes(), Base64.DEFAULT);
			
			OutputStream os = btSocket.getOutputStream();
			
			os.write(encodedjson.getBytes());
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return false;
		} catch (IOException ioe)
		{
			Log.e(TAG,"IOException BT");
			try {
				btSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			return false;
		}
		return true;
	}
	public boolean isInitialize()
	{
		return this.isInitialize;
	}
	
	public void setTTS(ArrayList<String> tts)
	{
		array_TTS = tts;
	}
	
	
}
