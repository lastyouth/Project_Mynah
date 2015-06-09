package com.seven.mynah.backgroundservice;

/**
 * Created by sbh on 2015-06-09.
 */
public interface BluetoothRequestCallback {
    public void onRequestTTSWithRSSI();
    public void onTempDataArrived(int temp);
}
