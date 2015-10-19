package com.seven.mynah.backgroundservice;

/**
 * Created by sbh on 2015-06-09.
 */
public interface BluetoothRequestCallback {
    public void onRequestOutTTSWithRSSI();
    public void onRequestInTTSWithRSSI();
    public void onTempDataArrived(int temp);
    public void onConnected();
    public void onDisconnected();
}
