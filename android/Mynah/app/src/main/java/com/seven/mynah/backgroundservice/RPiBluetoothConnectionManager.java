package com.seven.mynah.backgroundservice;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Base64;
import android.util.Log;

public class RPiBluetoothConnectionManager {
   private UUID uuid; //Standard SerialPortService ID
   //private String struuid = "94f39d29-7d6d-437d-973b-fba39e49daae";
   private BluetoothSocket btSocket;
   private BluetoothDevice targetBTDevice;
   private final String TAG = "RPI_BT";
   private boolean isInitialize;
   private BluetoothAdapter btAdapter;
   //private String TARGET_MAC_ADDR = "00:1A:7D:DA:71:07";

   //private final String PREF = "mynah_rpi";

   private boolean isWatchThreadActivated;
   private boolean isServeThreadActivated;

   private WatcherThread wtThread;
   private ServeThread svThread;
   private int currentRSSI;
   private String deviceID;

   private BluetoothRequestCallback mCallback;

   @SuppressLint("NewApi")
   private LeScanCallback lecallback = new LeScanCallback(){

      @Override
      public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
         // TODO Auto-generated method stub
         currentRSSI = rssi;
      }};



   //private ArrayList<String> array_TTS;

   // sendtype

   public static final int SEND_TYPE_RSSI = 0x30001001;
   public static final int SEND_TYPE_OUTTTS = 0x30001002;
   public static final int SEND_TYPE_INTTS = 0x30001005;
   public static final int SEND_TYPE_INIT = 0x30001003;
   public static final int SEND_TYPE_TEMP = 0x30001004;

   // settings
   private final int MAX_WAIT_FOR_RECONNECT = 1500;
   private final int MAX_WAIT_FOR_ALIVE = 10000;

   // messages

   public static final int ERROR_BT_NOT_SUPPORTED = 0x10001001;
   public static final int ERROR_TARGET_DEVICE_NOT_REGISTERED = 0x10001002;
   public static final int ERROR_CALLBACK_IS_NOT_REGISTERED = 0x10001003;
   public static final int ERROR_BT_IS_NOT_ENABLED = 0x10001004;
   public static final int ERROR_TARGET_UUID_NOT_REGISTERED = 0x10001005;

   public static final int SUCCESS_INITIALIZE = 0x20001001;

   private class ServeThread extends Thread
   {
      public void run()
      {
         while(isServeThreadActivated)
         {
            if(btSocket != null && btSocket.isConnected())
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

                  if(str.startsWith("outtts"))
                  {
                     mCallback.onRequestOutTTSWithRSSI();
                  }else if(str.startsWith("intts"))
                  {
                     mCallback.onRequestInTTSWithRSSI();
                  }
                  else if(str.startsWith("temp"))
                  {
                     str = str.replace("temp : ","");

                     mCallback.onTempDataArrived(Integer.parseInt(str));
                  }
               } catch (Exception e) {
                  // TODO Auto-generated catch block
                  Log.e(TAG,"Error in reading from Server !");
                  try {
                     btSocket.close();
                     mCallback.onDisconnected();
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
            if(btSocket == null || !btSocket.isConnected())
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
         mCallback.onConnected();
         sendTo(SEND_TYPE_INIT,"HITHERE");
      }catch(IOException e)
      {
         Log.e(TAG,"BTCONNECT - IO ERROR");
         try {
            Thread.sleep(MAX_WAIT_FOR_RECONNECT);
         } catch (InterruptedException ee) {
            // TODO Auto-generated catch block
            ee.printStackTrace();
         }
         return;
      }
      try {
         Thread.sleep(MAX_WAIT_FOR_RECONNECT);
      } catch (InterruptedException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }


   public RPiBluetoothConnectionManager(String deviceID)
   {
      isWatchThreadActivated = false;
      isServeThreadActivated = false;
      btAdapter = BluetoothAdapter.getDefaultAdapter();
      this.deviceID = deviceID;
      isInitialize = false;
   }

   @SuppressLint("NewApi")
   public int initializeBTConnection(String tuuid,String macAddr)
   {
      if(btAdapter == null)
      {
         return ERROR_BT_NOT_SUPPORTED;
      }

      if(!btAdapter.isEnabled())
      {
         return ERROR_BT_IS_NOT_ENABLED;
      }

      if(mCallback == null)
      {
         return ERROR_CALLBACK_IS_NOT_REGISTERED;
      }

      if(tuuid.equals("NULL"))
      {
         return ERROR_TARGET_UUID_NOT_REGISTERED;
      }
      this.uuid = UUID.fromString(tuuid);

      targetBTDevice = btAdapter.getRemoteDevice(macAddr);

      if(targetBTDevice == null)
      {
         return ERROR_TARGET_DEVICE_NOT_REGISTERED;
      }

      //connectInner();

      wtThread = new WatcherThread();

      isWatchThreadActivated = true;

      wtThread.start();

      svThread = new ServeThread();

      isServeThreadActivated = true;

      svThread.start();

      btAdapter.startLeScan(lecallback);

      isInitialize = true;
      return SUCCESS_INITIALIZE;
   }
   @SuppressLint("NewApi")
   public void stopBTConnection()
   {
      isWatchThreadActivated = false;
      isServeThreadActivated = false;
      if(btSocket.isConnected())
      {
         try {
            btSocket.close();
            mCallback.onDisconnected();
         } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
      btAdapter.stopLeScan(lecallback);
      try {
         wtThread.join();
         svThread.join();
      } catch (InterruptedException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
   private boolean sendTo(int type,String data)
   {
      JSONObject json = new JSONObject();
      if(!isInitialize())
      {
         return false;
      }
      try {
         json.put("id", this.deviceID);
         json.put("rssi", Integer.toString(currentRSSI));
         switch(type)
         {
            case SEND_TYPE_OUTTTS:
               json.put("type", "outtts");
               break;

            case SEND_TYPE_INTTS:
               json.put("type","intts");
               break;
            case SEND_TYPE_INIT:
               json.put("type","init");
               break;

            case SEND_TYPE_TEMP:
               json.put("type","temp");
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
            mCallback.onDisconnected();
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

   public boolean sendTTSWithRSSI(int type,String data)
   {
      Log.d("Bluetooth","SendData : "+data);
      return sendTo(type,data);
   }

   public boolean requestTempData()
   {
      return sendTo(SEND_TYPE_TEMP,"");
   }

   public void registerCallback(BluetoothRequestCallback callback)
   {
      if(mCallback == null)
      {
         mCallback = callback;
      }
   }
}