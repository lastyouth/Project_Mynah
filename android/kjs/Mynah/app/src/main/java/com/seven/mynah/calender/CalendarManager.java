package com.seven.mynah.calender;

import android.content.Context;

import com.google.android.gms.common.ConnectionResult;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.ExponentialBackOff;

import com.google.api.services.calendar.CalendarScopes;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import com.seven.mynah.CalendarActivity;
import com.seven.mynah.MainActivity;
import com.seven.mynah.artifacts.ScheduleInfo;
import com.seven.mynah.artifacts.SchedulesOnDateInfo;
import com.seven.mynah.database.DBManager;
import com.seven.mynah.globalmanager.GlobalGoogleCalendarManager;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalendarManager{
    /**
     * A Google Calendar API service object used to access the API.
     * Note: Do not confuse this class with API library's model classes, which
     * represent specific data structures.
     */
    com.google.api.services.calendar.Calendar mService;
    GoogleAccountCredential credential;
    final HttpTransport transport = AndroidHttp.newCompatibleTransport();
    final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = { CalendarScopes.CALENDAR_READONLY };
    // !Google Calendar

    //By Js
    private Context mContext;
    private Activity activity;

    private ArrayList<ScheduleInfo> totalScheduleList;
    private Multimap<String, ScheduleInfo> scheduleByDate;
    private CalendarManager calendarManager;
    private SchedulesOnDateInfo schedulesOnDateInfo;

    public CalendarManager(Context _mContext, Activity _activity) {
        mContext = _mContext;
        activity = _activity;

        //init();
    }
    public CalendarManager(Activity _activity)
    {
        activity = _activity;
    }

    /*
    public CalendarManager(Context _mContext, MainActivity _activity) {
        mContext = _mContext;
        activity = _activity;

        init();
    }
    public CalendarManager(Context _mContext, CalendarActivity _activity) {
        mContext = _mContext;
        activity = _activity;

        init();
    }*/

    public void getCredential()
    {
        // Initialize credentials and service object.
        SharedPreferences settings = activity.getPreferences(Context.MODE_PRIVATE);
        credential = GoogleAccountCredential.usingOAuth2(
                activity.getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff())
                .setSelectedAccountName(settings.getString(PREF_ACCOUNT_NAME, null));

        mService = new com.google.api.services.calendar.Calendar.Builder(
                transport, jsonFactory, credential)
                .setApplicationName("Google Calendar API Android Quickstart")
                .build();
    }


    public void startManager()
    {
        if (isGooglePlayServicesAvailable()) {
            refreshResults();
        } else {
            Toast.makeText(mContext, "Google Play Services required: " +
                    "after installing, close and relaunch this app.", Toast.LENGTH_SHORT).show();
//            mStatusText.setText("Google Play Services required: " +
//                    "after installing, close and relaunch this app.");
        }
    }

    /**
     * Attempt to get a set of data from the Google Calendar API to display. If the
     * email address isn't known yet, then call chooseAccount() method so the
     * user can pick an account.
     */
    private void refreshResults() {
        if (credential.getSelectedAccountName() == null) {
            chooseAccount();
        } else {
            if (isDeviceOnline()) {
                new ApiAsyncTask(this).execute();
            } else {
                Toast.makeText(mContext, "No network connection available.", Toast.LENGTH_SHORT).show();
//                mStatusText.setText("No network connection available.");
            }
        }

    }

    public void clearResultsText() {
        int a = 0;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, "Retrieving data¡¦", Toast.LENGTH_SHORT).show();
//                mStatusText.setText("Retrieving data¡¦");
//                mResultsText.setText("");
            }
        });
    }

    /**
     * Called when an activity launched here (specifically, AccountPicker
     * and authorization) exits, giving you the requestCode you started it with,
     * the resultCode it returned, and any additional data from it.
     * @param requestCode code indicating which activity result is incoming.
     * @param resultCode code indicating the result of the incoming
     *     activity result.
     * @param data Intent (containing result data) returned by incoming
     *     activity result.
     */

    //Must Override in such Clss
    public void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode == activity.RESULT_OK) {
                    refreshResults();
                } else {
                    isGooglePlayServicesAvailable();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == activity.RESULT_OK && data != null &&
                        data.getExtras() != null) {
                    String accountName =
                            data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        credential.setSelectedAccountName(accountName);
                        SharedPreferences settings =
                                activity.getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.commit();
                        refreshResults();
                    }
                } else if (resultCode == activity.RESULT_CANCELED) {
                    Toast.makeText(mContext, "Account unspecified.", Toast.LENGTH_SHORT).show();
//                    mStatusText.setText("Account unspecified.");
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode == activity.RESULT_OK) {
                    refreshResults();
                } else {
                    chooseAccount();
                }
                break;
        }

        //super.onActivityResult(requestCode, resultCode, data);
    }

    public void updateResultsText(final ArrayList<ScheduleInfo> dataList) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (dataList == null) {
                    Toast.makeText(mContext, "Error retrieving data!", Toast.LENGTH_SHORT).show();
//                    mStatusText.setText("Error retrieving data!");
                } else if (dataList.size() == 0) {
                    Toast.makeText(mContext, "No data found.", Toast.LENGTH_SHORT).show();
//                    mStatusText.setText("No data found.");
                } else {
                    totalScheduleList = new ArrayList<ScheduleInfo>();
                    totalScheduleList = dataList;
                    scheduleByDate = ArrayListMultimap.create();
                    scheduleByDate = setHashmapFromScheduleInfo(totalScheduleList);
//                    mStatusText.setText("Data retrieved using" + " the Google Calendar API:");
//                    mResultsText.setText(TextUtils.join("\n\n", dataStrings));
                }
            }
        });
    }

    /**
     * Show a status message in the list header TextView; called from background
     * threads and async tasks that need to update the UI (in the UI thread).
     * @param message a String to display in the UI header TextView.
     */
    public void updateStatus(final String message) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
//                mStatusText.setText(message);
            }
        });
    }

    /**
     * Starts an activity in Google Play Services so the user can pick an
     * account.
     */
    private void chooseAccount() {
        activity.startActivityForResult(
                credential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
    }

    /**
     * Checks whether the device currently has a network connection.
     * @return true if the device has a network connection, false otherwise.
     */
    private boolean isDeviceOnline() {
        ConnectivityManager connMgr =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * Check that Google Play services APK is installed and up to date. Will
     * launch an error dialog for the user to update Google Play Services if
     * possible.
     * @return true if Google Play Services is available and up to
     *     date on this device; false otherwise.
     */
    private boolean isGooglePlayServicesAvailable() {
        final int connectionStatusCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        if (GooglePlayServicesUtil.isUserRecoverableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
            return false;
        } else if (connectionStatusCode != ConnectionResult.SUCCESS ) {
            return false;
        }
        return true;
    }

    /**
     * Display an error dialog showing that Google Play Services is missing
     * or out of date.
     * @param connectionStatusCode code describing the presence (or lack of)
     *     Google Play Services on this device.
     */
    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Dialog dialog = GooglePlayServicesUtil.getErrorDialog(
                        connectionStatusCode,
                        activity,
                        REQUEST_GOOGLE_PLAY_SERVICES);
                dialog.show();
            }
        });
    }

    Multimap<String, ScheduleInfo> setHashmapFromScheduleInfo(ArrayList<ScheduleInfo> totalScheduleInfo)
    {
        Multimap<String, ScheduleInfo> _scheduleByDate = ArrayListMultimap.create();
        ArrayList<ScheduleInfo> tmpList = new ArrayList<ScheduleInfo>();

        for(ScheduleInfo info : totalScheduleInfo)
        {
            String date = info.scheduleDate;
            _scheduleByDate.put(date, info);
        }
        return _scheduleByDate;
    }

    public Multimap<String, ScheduleInfo> getAllScheduleByDate()
    {
        return scheduleByDate;
    }

    public ArrayList<ScheduleInfo> getScheduleOnDate(String date)
    {
        List list = (List<ScheduleInfo>) scheduleByDate.get(date);
        ArrayList<ScheduleInfo> arrayList = new ArrayList<ScheduleInfo>(list);

        return arrayList;
    }

    public Activity getActivity()
    {
        return activity;
    }
    public ArrayList<ScheduleInfo> getTotalScheduleList()
    {
        return totalScheduleList;
    }

    public void updateDB()
    {
        //Delete All Schedule List in DB
        DBManager.getManager(mContext).deleteSchedulesAll();

        //Insert All Schedule List into DB
        schedulesOnDateInfo = new SchedulesOnDateInfo();
        calendarManager = GlobalGoogleCalendarManager.calendarManager;
        schedulesOnDateInfo.scheduleList = calendarManager.getTotalScheduleList();
        DBManager.getManager(mContext).setSchedulesOnDateDB(schedulesOnDateInfo);
    }

}
