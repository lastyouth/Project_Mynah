package com.seven.mynah;

/**
 * Created by KimJS on 2015-05-31.
 */
import android.os.AsyncTask;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.util.DateTime;

import com.google.api.services.calendar.model.*;
import com.seven.mynah.artifacts.ScheduleInfo;
import com.seven.mynah.calender.CalendarManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * An asynchronous task that handles the Google Calendar API call.
 * Placing the API calls in their own task ensures the UI stays responsive.
 */
public class ApiAsyncTask extends AsyncTask<Void, Void, Void> {
    private ScheduleListActivity mActivity_tmp;
    private CalendarManager mActivity;
    private ScheduleInfo scheduleInfo;

    /**
     * Constructor.
     *
     * @param activity MainActivity that spawned this task.
     */
    ApiAsyncTask(ScheduleListActivity activity) {
        this.mActivity_tmp = activity;
    }

    ApiAsyncTask(CalendarManager activity) {
        this.mActivity = activity;
    }

    /**
     * Background task to call Google Calendar API.
     *
     * @param params no parameters needed for this task.
     */
    @Override
    protected Void doInBackground(Void... params) {
        try {
            mActivity.clearResultsText();
            mActivity.updateResultsText(getDataFromApi());

        } catch (final GooglePlayServicesAvailabilityIOException availabilityException) {
            mActivity.showGooglePlayServicesAvailabilityErrorDialog(
                    availabilityException.getConnectionStatusCode());

        } catch (UserRecoverableAuthIOException userRecoverableException) {
            mActivity.getActivity().startActivityForResult(
                    userRecoverableException.getIntent(),
                    ScheduleListActivity.REQUEST_AUTHORIZATION);

        } catch (IOException e) {
            mActivity.updateStatus("The following error occurred: " +
                    e.getMessage());
        }
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
        Events events = mActivity.mService.events().list("primary")
                .setMaxResults(20)
                //.setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();

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
            String time = str[1].split("\\.")[0];
            scheduleInfo = new ScheduleInfo();
            scheduleInfo.scheduleName = event.getSummary();
            scheduleInfo.scheduleDate = date;
            scheduleInfo.scheduleTime = time;
            eventList.add(scheduleInfo);
            //eventStrings.add(
            //        String.format("%s (%s)", event.getSummary(), start));
        }
        return eventList;
    }

}