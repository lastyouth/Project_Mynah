package com.seven.mynah.globalmanager;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.seven.mynah.calender.CalendarManager;

/**
 * Created by KimJS on 2015-06-04.
 */
public class GlobalGoogleCalendarManager {

    public static CalendarManager calendarManager;

    public void setGoogleAccountCredential(CalendarManager _calendarManager)
    {
        calendarManager = _calendarManager;
    }

    public CalendarManager getGoogleAccountCredential()
    {
        return calendarManager;
    }
}
