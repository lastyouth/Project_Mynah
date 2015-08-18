package com.seven.mynah.calender;

/**
 * Created by KimJS on 2015-05-31.
 */

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.seven.mynah.artifacts.ScheduleInfo;
import com.seven.mynah.globalmanager.GlobalGoogleCalendarManager;
import com.seven.mynah.globalmanager.GlobalVariable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * An asynchronous task that handles the Google Calendar API call.
 * Placing the API calls in their own task ensures the UI stays responsive.
 */
public class ApiAsyncTask extends AsyncTask<Void, Void, Void> {
    private CalendarManager mManager;
    private ScheduleInfo scheduleInfo;
    private String TAG = "ApiAsyncTask";

    private List<Event> items;
    /**
     * Constructor.
     *
     * @param activity MainActivity that spawned this task.
     */
    ApiAsyncTask(CalendarManager activity) {
        this.mManager = activity;
    }

    /**
     * Background task to call Google Calendar API.
     *
     * @param params no parameters needed for this task.
     */
    @Override
    protected Void doInBackground(Void... params) {
        Log.d(TAG, "doInBackground Start");
        try {
            //mManager.clearResultsText();
            mManager.updateResultsText(getDataFromApi());
            mManager.setEventId(items);
            //mManager.updateDB();
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

    /**
     * Fetch a list of the next 10 events from the primary calendar.
     *
     * @return List of Strings describing returned events.
     * @throws IOException
     */
    private ArrayList<ScheduleInfo> getDataFromApi() throws IOException {
        // List the next 10 events from the primary calendar.
        DateTime now = new DateTime(System.currentTimeMillis());
        ArrayList<ScheduleInfo> eventList = new ArrayList<ScheduleInfo>();
        Events events = mManager.mService.events().list("primary")
                .setMaxResults(20)
                //.setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        //List<Event> items = events.getItems();
        items = events.getItems();

        for (Event event : items) {
            DateTime start = event.getStart().getDateTime();
            if (start == null) {
                // All-day events don't have start times, so just use
                // the start date.
                start = event.getStart().getDate();
            }
            String[] str = start.toString().split("T");
            // yyyy-mm-dd
            String date = str[0];

            // hh-mm-ss
            String time;
            if(str.length == 1)
            {
                time = "00:00:00";
            }
            else
            {
                time = str[1].split("\\.")[0];
                time = time.substring(0, 5);
            }

            scheduleInfo = new ScheduleInfo();
            scheduleInfo.scheduleName = event.getSummary();
            scheduleInfo.scheduleDate = date;
            scheduleInfo.scheduleTime = time;
            scheduleInfo.scheduleCreatedDate = event.getCreated().toString();
            eventList.add(scheduleInfo);
            //eventStrings.add(
            //        String.format("%s (%s)", event.getSummary(), start));

            //mManager.setEventId(items);

        }
        GlobalGoogleCalendarManager.calendarManager = mManager;
        return eventList;
    }

}