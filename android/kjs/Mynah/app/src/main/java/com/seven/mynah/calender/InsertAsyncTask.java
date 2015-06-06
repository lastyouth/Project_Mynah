package com.seven.mynah.calender;

import android.os.AsyncTask;

import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.services.calendar.model.Event;

import java.io.IOException;

/**
 * Created by KimJS on 2015-06-06.
 */
public class InsertAsyncTask extends AsyncTask<Void, Void, Void>{

    private CalendarManager mManager;
    private Event mEvent;

    InsertAsyncTask(CalendarManager manager, Event event)
    {
        this.mManager = manager;
        this.mEvent = event;
    }

    @Override
    protected Void doInBackground(Void... params) {
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
        return null;
    }

    private void insertDataToApi() throws IOException
    {
        mManager.mService.events().insert("primary", mEvent).execute();
    }
}
