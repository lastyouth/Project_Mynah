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
import com.google.api.services.calendar.model.Event;
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
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CalendarManager {
    /**
     * A Google Calendar API service object used to access the API.
     * Note: Do not confuse this class with API library's model classes, which
     * represent specific data structures.
     */
    public com.google.api.services.calendar.Calendar mService;
    private GoogleAccountCredential credential;
    final HttpTransport transport = AndroidHttp.newCompatibleTransport();
    final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = {CalendarScopes.CALENDAR};
    // !Google Calendar

    //By Js
    private Context mContext;
    private Activity activity;

    private ArrayList<ScheduleInfo> totalScheduleList = new ArrayList<ScheduleInfo>();
    private Multimap<String, ScheduleInfo> scheduleByDate;
    public HashMap<String, String> eventIdMap;
    private CalendarManager calendarManager;
    private SchedulesOnDateInfo schedulesOnDateInfo;

    private String TAG = "CalendarManager";

    public CalendarManager(Context _mContext, Activity _activity) {
        mContext = _mContext;
        activity = _activity;
        eventIdMap = new HashMap<String, String>();
        //init();
    }

    public CalendarManager(Activity _activity) {
        activity = _activity;
    }

    public void init() {
        // Initialize credentials and service object.
        SharedPreferences settings = activity.getPreferences(Context.MODE_PRIVATE);
        credential = GoogleAccountCredential.usingOAuth2(
                activity.getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff())
                .setSelectedAccountName(settings.getString(PREF_ACCOUNT_NAME, null));

        mService = new com.google.api.services.calendar.Calendar.Builder(
                transport, jsonFactory, credential)
                .setApplicationName("Mynah")
                .build();
    }


    public void asyncSchedule() {
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
        Log.d(TAG, "refreshResults Start");
        if (credential.getSelectedAccountName() == null) {
            chooseAccount();
        } else {
            if (isDeviceOnline()) {
                Log.d(TAG, "ApiAsyncTask execute start");
                //new ApiAsyncTask(this).execute();
                ApiAsyncTaskExecute();
                Log.d(TAG, "ApiAsyncTask execute finish");
            } else {
                Toast.makeText(mContext, "네트워크 연결을 확인하세요.", Toast.LENGTH_SHORT).show();
//                mStatusText.setText("No network connection available.");
            }
        }
        Log.d(TAG, "refreshResults Finish");
    }

    public void clearResultsText() {
        /*
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, "Retrieving data...", Toast.LENGTH_SHORT).show();
            }
        });
        */
    }

    /**
     * Called when an activity launched here (specifically, AccountPicker
     * and authorization) exits, giving you the requestCode you started it with,
     * the resultCode it returned, and any additional data from it.
     *
     * @param requestCode code indicating which activity result is incoming.
     * @param resultCode  code indicating the result of the incoming
     *                    activity result.
     * @param data        Intent (containing result data) returned by incoming
     *                    activity result.
     */

    //Must Override in such Clss
    public void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
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
                    Toast.makeText(mContext, "계정이 선택되지 않았습니다.", Toast.LENGTH_SHORT).show();
                    activity.finish();
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
                    DBManager.getManager(mContext).deleteSchedulesAll();
//                    mStatusText.setText("No data found.");
                } else {
                    totalScheduleList = new ArrayList<ScheduleInfo>();
                    totalScheduleList = dataList;
                    scheduleByDate = ArrayListMultimap.create();
                    scheduleByDate = setHashmapFromScheduleInfo(totalScheduleList);
                    updateDB();
//                    mStatusText.setText("Data retrieved using" + " the Google Calendar API:");
//                    mResultsText.setText(TextUtils.join("\n\n", dataStrings));
                }
            }
        });
    }

    /**
     * Show a status message in the list header TextView; called from background
     * threads and async tasks that need to update the UI (in the UI thread).
     *
     * @param message a String to display in the UI header TextView.
     */
    public void updateStatus(final String message) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                   //Temp Setting

                //Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
//                mStatusText.setText(message);
            }
        });
    }

    /**
     * Starts an activity in Google Play Services so the user can pick an
     * account.
     */
    private void chooseAccount() {
        if(isDeviceOnline())
        {
            activity.startActivityForResult(
                    credential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
        }
        else
        {
            Toast.makeText(mContext, "네트워크 연결을 확인하세요.", Toast.LENGTH_SHORT).show();
            activity.finish();
        }
    }

    /**
     * Checks whether the device currently has a network connection.
     *
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
     *
     * @return true if Google Play Services is available and up to
     * date on this device; false otherwise.
     */
    private boolean isGooglePlayServicesAvailable() {
        final int connectionStatusCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        if (GooglePlayServicesUtil.isUserRecoverableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
            return false;
        } else if (connectionStatusCode != ConnectionResult.SUCCESS) {
            return false;
        }
        return true;
    }

    /**
     * Display an error dialog showing that Google Play Services is missing
     * or out of date.
     *
     * @param connectionStatusCode code describing the presence (or lack of)
     *                             Google Play Services on this device.
     */
    public void showGooglePlayServicesAvailabilityErrorDialog(
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

    Multimap<String, ScheduleInfo> setHashmapFromScheduleInfo(ArrayList<ScheduleInfo> totalScheduleInfo) {
        Multimap<String, ScheduleInfo> _scheduleByDate = ArrayListMultimap.create();
        ArrayList<ScheduleInfo> tmpList = new ArrayList<ScheduleInfo>();

        for (ScheduleInfo info : totalScheduleInfo) {
            String date = info.scheduleDate;
            _scheduleByDate.put(date, info);
        }
        return _scheduleByDate;
    }

    public Multimap<String, ScheduleInfo> getAllScheduleByDate() {
        return scheduleByDate;
    }

    public ArrayList<ScheduleInfo> getScheduleOnDate(String date) {
        List list = (List<ScheduleInfo>) scheduleByDate.get(date);
        ArrayList<ScheduleInfo> arrayList = new ArrayList<ScheduleInfo>(list);

        return arrayList;
    }

    public Activity getActivity() {
        return activity;
    }

    public ArrayList<ScheduleInfo> getTotalScheduleList() {
        return totalScheduleList;
    }

    public void insertTotalScheduleList(ScheduleInfo sinfo)
    {
        totalScheduleList.add(sinfo);
    }

    public void updateDB() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Delete All Schedule List in DB
                Log.d(TAG, "updateDB start");
                DBManager.getManager(mContext).deleteSchedulesAll();

                //Insert All Schedule List into DB
                schedulesOnDateInfo = new SchedulesOnDateInfo();
                calendarManager = GlobalGoogleCalendarManager.calendarManager;
                schedulesOnDateInfo.scheduleList = calendarManager.getTotalScheduleList();

                if(schedulesOnDateInfo.scheduleList != null)
                {
                    DBManager.getManager(mContext).setSchedulesOnDateDB(schedulesOnDateInfo);
                }

                Log.d(TAG, "updateDB finish");
            }
        });

    }

    public com.google.api.services.calendar.Calendar getCalendarService() {
        return mService;
    }

    public GoogleAccountCredential getCalendarCredential() {
        return credential;
    }

    public void insertEvent(Event event) {
        Log.d(TAG, "insertEvent Start");
        if (credential.getSelectedAccountName() == null) {
            chooseAccount();
        } else {
            if (isDeviceOnline()) {
                Log.d(TAG, "InsertAsyncTask Start");
                //new InsertAsyncTask(this, event).execute();
                InsertAsyncTaskExecute(event);
                Log.d(TAG, "InsertAsyncTask Finish");
            } else {
                Toast.makeText(mContext, "No network connection available.", Toast.LENGTH_SHORT).show();
//                mStatusText.setText("No network connection available.");
            }
        }
        Log.d(TAG, "insertEvent Finish");
    }

    public void deleteEvent(String eventId) {
        Log.d(TAG, "deleteEvent Start");
        if (credential.getSelectedAccountName() == null) {
            chooseAccount();
        } else {
            if (isDeviceOnline()) {
                Log.d(TAG, "DeleteAsyncTask Start");
                //new DeleteAsyncTask(this, eventId).execute();
               DeleteAsyncTaskExecute(eventId);
                Log.d(TAG, "DeleteAsyncTask Start");
            } else {
                Toast.makeText(mContext, "No network connection available.", Toast.LENGTH_SHORT).show();
//                mStatusText.setText("No network connection available.");
            }
        }
        Log.d(TAG, "deleteEvent Finish");
    }

    public void setEventId(List<Event> items) {
        final List<Event> mItems = items;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (Event e : mItems) {
                    eventIdMap.put(e.getCreated().toString(), e.getId());
                }
            }
        });
    }

    public String getEventIdFromCreatedDate(String createdDate) {
        String id = eventIdMap.get(createdDate);
        return id;
    }


    //http://stackoverflow.com/questions/9119627/android-sdk-asynctask-doinbackground-not-running-subclass
    // AsyncTask.execute() is NOT WORKED in OLD API version
    // So use AsyncTask.executeOnExecutor instead depend on API version
    private void ApiAsyncTaskExecute()
    {
        ApiAsyncTask refreshTask = new ApiAsyncTask(this);
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.HONEYCOMB)
        {
            refreshTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        else
        {
            refreshTask.execute();
        }
    }

    private void InsertAsyncTaskExecute(Event event)
    {
        InsertAsyncTask insertTask = new InsertAsyncTask(this, event);
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.HONEYCOMB)
        {
            insertTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        else
        {
            insertTask.execute();
        }
    }

    private void DeleteAsyncTaskExecute(String eventId)
    {
        DeleteAsyncTask deleteTask = new DeleteAsyncTask(this, eventId);
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.HONEYCOMB) {
            deleteTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        else
        {
            deleteTask.execute();
        }
    }



}
