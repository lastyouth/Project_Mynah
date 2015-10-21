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
        if(!GlobalVariable.isDebugMode) return;
        super.show();
    }


}
