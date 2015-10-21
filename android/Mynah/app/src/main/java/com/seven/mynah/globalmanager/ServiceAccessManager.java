package com.seven.mynah.globalmanager;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.seven.mynah.MainActivity;
import com.seven.mynah.backgroundservice.GetInformationService;

/**
 * Created by KimJS on 2015-06-09.
 */
public class ServiceAccessManager {
    GetInformationService infoService;
    private Context mCtx;
    private String TAG = "ServiceAccessManager";
    public static final String PREF = "PREF_RPI";
    public static final String TSTAT = "TTS_STATUS";
    public static final String WIFISTAT = "WIFI_STATUS";
    private boolean mDelete ;
    private int pid;

    boolean isServiceConnected = false;
    ServiceConnection mBkServiceConnection = new ServiceConnection(){
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "Call Back Start");
            GetInformationService.LocalBinder tb = (GetInformationService.LocalBinder)service;

            infoService = tb.getService();
            isServiceConnected = true;
            infoService.setBindStatus(true);
            ((MainActivity)mCtx).getHandler().sendEmptyMessage(MainActivity.SIGNAL_UI_UPDATE);
            Log.d(TAG, "Call Back Finish");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isServiceConnected = false;
        }
    };
    private static ServiceAccessManager mTarget = new ServiceAccessManager();

    private ServiceAccessManager()
    {
        mDelete = false;
    }

    public static ServiceAccessManager getInstance() {
        return mTarget;
    }

    public void setContext(Context ctx)
    {
        if(mCtx == null) {
            mCtx = ctx;
        }
    }

    public boolean prepareService()
    {
        if(mCtx == null)
        {
            return false;
        }
        Intent t = new Intent(mCtx, GetInformationService.class);
        mCtx.bindService(t,mBkServiceConnection,Context.BIND_AUTO_CREATE);

        return true;
    }

    public boolean releaseService()
    {
        mCtx.unbindService(mBkServiceConnection);
        infoService.setBindStatus(false);
        mCtx = null;
        return true;
    }
    public boolean checkServiceConnected()
    {
        return isServiceConnected;
    }

    public void setMainPid(int pid)
    {
        this.pid = pid;
    }
    public int getMainPid()
    {
        return this.pid;
    }

    public void setDeleteFlag(boolean flag)
    {
        mDelete = flag;
    }

    public boolean getDeleteFlag()
    {
        return mDelete;
    }

    public Activity getMainActivity() {return (MainActivity)mCtx;}


    public GetInformationService getService()
    {
        return infoService;
    }

}
