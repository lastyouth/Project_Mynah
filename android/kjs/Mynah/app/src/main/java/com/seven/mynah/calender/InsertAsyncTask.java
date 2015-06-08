package com.seven.mynah.calender;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.seven.mynah.artifacts.ScheduleInfo;
import com.seven.mynah.globalmanager.GlobalGoogleCalendarManager;
import com.seven.mynah.globalmanager.GlobalVariable;

import java.io.IOException;

/**
 * Created by KimJS on 2015-06-06.
 */
public class InsertAsyncTask extends AsyncTask<Void, Void, Void>{

    private CalendarManager mManager;
    private Event mEvent;
    private String TAG = "InsertAsyncTask";

    InsertAsyncTask(CalendarManager manager, Event event)
    {
        this.mManager = manager;
        this.mEvent = event;
    }

    @Override
    protected Void doInBackground(Void... params) {
        Log.d(TAG, "doInBackground Start");
        try {
            mManager.clearResultsText();
            insertDataToApi();
            mManager.updateDB();
        } catch (final GooglePlayServicesAvailabilityIOException availabilityException) {
            mManager.showGooglePlayServicesAvailabilityErrorDialog(
                    availabilityException.getConnectionStatusCode());

        } catch (UserRecoverableAuthIOException userRecoverableException) {
            mManager.getActivity().startActivityForResult(
                    userRecoverableException.getIntent(),
                    mManager.REQUEST_AUTHORIZATION);

        } catch (IOException e) {
            mManager.updateStatus("The following error occurred: " +
                    e.getMessage());
        }
        Log.d(TAG, "doInBackground Finish");
        return null;
    }

    private void insertDataToApi() throws IOException
    {
        mManager.mService.events().insert("primary", mEvent).execute();

        ScheduleInfo scheduleInfo;
        DateTime start = mEvent.getStart().getDateTime();
        if (start == null) {
            // All-day events don't have start times, so just use
            // the start date.
            start = mEvent.getStart().getDate();
        }
        String[] str = start.toString().split("T");
        // yyyy-mm-dd
        String date = str[0];
        // hh-mm-ss
        String time = str[1].split("\\.")[0];
        time = time.substring(0, 5);
        scheduleInfo = new ScheduleInfo();
        scheduleInfo.scheduleName = mEvent.getSummary();
        scheduleInfo.scheduleDate = date;
        scheduleInfo.scheduleTime = time;
        scheduleInfo.scheduleCreatedDate = mEvent.getCreated().toString();

        mManager.insertTotalScheduleList(scheduleInfo);
        GlobalGoogleCalendarManager.calendarManager = mManager;

    }
}
