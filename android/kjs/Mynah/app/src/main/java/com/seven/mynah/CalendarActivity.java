package com.seven.mynah;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.seven.mynah.artifacts.ScheduleInfo;
import java.util.ArrayList;

import com.google.android.gms.common.ConnectionResult;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.ExponentialBackOff;

import com.google.api.services.calendar.CalendarScopes;

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
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by KimJS on 2015-06-01.
 */
public class CalendarActivity extends Activity {

    private ScheduleInfo scheduleInfo;
    private ArrayList<ScheduleInfo> arrayList;
    private ListView lvSchedule;
    private ScheduleAdapter adapter;

    private ArrayList<ScheduleInfo> totalScheduleList;
    Multimap<String, ScheduleInfo> scheduleByDate;

    //for google calendar api
    com.google.api.services.calendar.Calendar mService;

    GoogleAccountCredential credential;
    //private TextView mStatusText;
    //private TextView mResultsText;
    final HttpTransport transport = AndroidHttp.newCompatibleTransport();
    final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = { CalendarScopes.CALENDAR };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        // Initialize credentials and service object.
        SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
        credential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff())
                .setSelectedAccountName(settings.getString(PREF_ACCOUNT_NAME, null));

        mService = new com.google.api.services.calendar.Calendar.Builder(
                transport, jsonFactory, credential)
                .setApplicationName("Google Calendar API Android Quickstart")
                .build();
        // !Google Calendar

        //get calendar view and set month color white
        CalendarView cv = (CalendarView) this.findViewById(R.id.calendarView);
        ViewGroup vg = (ViewGroup) cv.getChildAt(0);
        View month_name = vg.getChildAt(0);
        //View day_name = vg.getChildAt(1);

        if(month_name instanceof TextView) {
            ((TextView)month_name).setTextColor(Color.WHITE);
        }
        /*
        if(day_name instanceof TextView) {
            ((TextView)day_name).setTextColor(Color.WHITE);
        }*/



        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String date = "";
                String strMonth = "";
                String strDay = "";
                if (month < 9) {
                    strMonth = "0" + (month + 1);
                } else {
                    strMonth = (month + 1) + "";
                }
                if (dayOfMonth < 10) {
                    strDay = "0" + dayOfMonth;
                } else {
                    strDay = dayOfMonth + "";
                }
                date = year + "-" + strMonth + "-" + strDay;


                lvSchedule = (ListView) findViewById(R.id.lvSchedule);

                //Collection<ScheduleInfo> list = (ArrayList<ScheduleInfo>)scheduleByDate.get(date);
                //arrayList = (ArrayList<ScheduleInfo>)scheduleByDate.get(date);
                List list = (List<ScheduleInfo>) scheduleByDate.get(date);
                arrayList = new ArrayList<ScheduleInfo>(list);
                adapter = new ScheduleAdapter(getApplicationContext(), R.layout.list_row_schedule, arrayList);
                lvSchedule.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                lvSchedule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // TODO
                        //change Activity to schedule setting
                        Toast.makeText(CalendarActivity.this, "clicked", Toast.LENGTH_SHORT).show();

                    }
                });
                //Toast.makeText(CalendarActivity.this, date, Toast.LENGTH_SHORT).show();
            }
        });



    }

    // For Google Calendar
    @Override
    protected void onResume() {
        super.onResume();
        if (isGooglePlayServicesAvailable()) {
            refreshResults();
        } else {
            Toast.makeText(CalendarActivity.this, "Google Play Services required: " +
                    "after installing, close and relaunch this app.", Toast.LENGTH_SHORT).show();
//            mStatusText.setText("Google Play Services required: " +
//                    "after installing, close and relaunch this app.");
        }
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
    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode == RESULT_OK) {
                    refreshResults();
                } else {
                    isGooglePlayServicesAvailable();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null &&
                        data.getExtras() != null) {
                    String accountName =
                            data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        credential.setSelectedAccountName(accountName);
                        SharedPreferences settings =
                                getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.commit();
                        refreshResults();
                    }
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(CalendarActivity.this, "Account unspecified.", Toast.LENGTH_SHORT).show();
//                    mStatusText.setText("Account unspecified.");
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode == RESULT_OK) {
                    refreshResults();
                } else {
                    chooseAccount();
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
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
                Toast.makeText(CalendarActivity.this, "No network connection available.", Toast.LENGTH_SHORT).show();
//                mStatusText.setText("No network connection available.");
            }
        }
    }

    /**
     * Clear any existing Google Calendar API data from the TextView and update
     * the header message; called from background threads and async tasks
     * that need to update the UI (in the UI thread).
     */
    public void clearResultsText() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(CalendarActivity.this, "Retrieving data¡¦", Toast.LENGTH_SHORT).show();
//                mStatusText.setText("Retrieving data¡¦");
//                mResultsText.setText("");
            }
        });
    }

    /**
     * Fill the data TextView with the given List of Strings; called from
     * background threads and async tasks that need to update the UI (in the
     * UI thread).
     * @param dataList a List of Strings to populate the main TextView with.
     */
    public void updateResultsText(final ArrayList<ScheduleInfo> dataList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (dataList == null) {
                    Toast.makeText(CalendarActivity.this, "Error retrieving data!", Toast.LENGTH_SHORT).show();
//                    mStatusText.setText("Error retrieving data!");
                } else if (dataList.size() == 0) {
                    Toast.makeText(CalendarActivity.this, "No data found.", Toast.LENGTH_SHORT).show();
//                    mStatusText.setText("No data found.");
                } else {
                    totalScheduleList = new ArrayList<ScheduleInfo>();
                    totalScheduleList = dataList;
                    scheduleByDate = ArrayListMultimap.create();
                    scheduleByDate = setHashmapFromScheduleInfo(totalScheduleList);
                    int a = 0;
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(CalendarActivity.this, message, Toast.LENGTH_SHORT).show();
//                mStatusText.setText(message);
            }
        });
    }

    /**
     * Starts an activity in Google Play Services so the user can pick an
     * account.
     */
    private void chooseAccount() {
        startActivityForResult(
                credential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
    }

    /**
     * Checks whether the device currently has a network connection.
     * @return true if the device has a network connection, false otherwise.
     */
    private boolean isDeviceOnline() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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
        final int connectionStatusCode =
                GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Dialog dialog = GooglePlayServicesUtil.getErrorDialog(
                        connectionStatusCode,
                        CalendarActivity.this,
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

}
