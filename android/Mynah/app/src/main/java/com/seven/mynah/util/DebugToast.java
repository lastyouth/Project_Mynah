package com.seven.mynah.util;

import android.content.Context;
import android.widget.Toast;

import com.seven.mynah.globalmanager.GlobalVariable;

/**
 * Created by PSJ on 2015-10-17.
 */

public class DebugToast extends Toast {

    public DebugToast(Context context)
    {
        super(context);
    }

    @Override
    public void show()
    {
        //디버그 모드가 false라면 바로 return 한다.
        if(!GlobalVariable.isDebugMode) return;
        super.show();
    }


}
