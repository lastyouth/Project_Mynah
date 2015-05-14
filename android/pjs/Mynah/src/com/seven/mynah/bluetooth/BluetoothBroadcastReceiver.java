package com.seven.mynah.bluetooth;

import com.seven.mynah.R;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BluetoothBroadcastReceiver extends BroadcastReceiver {
	
	private String TAG = "BluetoothBroadcastReceiver";
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();

		short rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,
				Short.MIN_VALUE);
		
		// When discovery finds a device
		if (BluetoothDevice.ACTION_FOUND.equals(action)) {
			// Get the BluetoothDevice object from the Intent
			BluetoothDevice device = intent
					.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
			// If it's already paired, skip it, because it's been listed already
			if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
				Log.i(TAG, device.getName() + "\n"
						+ device.getAddress() + " (RSSI : " + rssi + "dBm)");
			}
			
			//여기서 타겟 디바이스 결정(이전에 설정한 목록이 있으면 
			//그것을 기준으로 타겟 디바이스로 등록한다.
            //targetDevice = device;
            
            
			// When discovery is finished, change the Activity title
		} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
//			setProgressBarIndeterminateVisibility(false);
//			setTitle(R.string.select_device);
//			if (mNewDevicesArrayAdapter.getCount() == 0) {
//				String noDevices = getResources().getText(R.string.none_found)
//						.toString();
//				mNewDevicesArrayAdapter.add(noDevices);
//			}
			
		}
	}
}
