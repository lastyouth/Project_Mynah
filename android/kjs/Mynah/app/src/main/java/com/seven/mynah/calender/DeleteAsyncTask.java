package com.seven.mynah.calender;

import android.os.AsyncTask;

import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.services.calendar.model.Event;

import java.io.IOException;

/**
 * Created by KimJS on 2015-06-06.
 */
public class DeleteAsyncTask extends AsyncTask<Void, Void, Void>{

    private CalendarManager mManager;
    private String mEventId;

    DeleteAsyncTask(CalendarManager manager, String eventId)
    {
        this.mManager = manager;
        this.mEventId = eventId;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            mManager.clearResultsText();
            deleteDataFromApi();
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
        return null;
    }

    private void deleteDataFromApi() throws IOException
    {
        if(mEventId != null)
        {
            mManager.mService.events().delete("primary", mEventId).execute();
        }
    }
}
