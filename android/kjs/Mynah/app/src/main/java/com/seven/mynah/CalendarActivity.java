package com.seven.mynah;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.CalendarContract;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.seven.mynah.artifacts.ScheduleInfo;
import com.seven.mynah.artifacts.SchedulesOnDateInfo;
import com.seven.mynah.calender.CalendarManager;
import com.seven.mynah.database.DBManager;
import com.seven.mynah.globalmanager.GlobalGoogleCalendarManager;
import com.seven.mynah.globalmanager.GlobalVariable;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.*;
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
    private LinearLayout layoutScheduleAdd;
    private LinearLayout layoutScheduleList;
    private ImageView ivDeleteEvent;
    private CalendarView cv;

    private ArrayList<ScheduleInfo> totalScheduleList;
    Multimap<String, ScheduleInfo> scheduleByDate;

    private CalendarManager calendarManager;
    private SchedulesOnDateInfo schedulesOnDateInfo;

    private String selectedDate;

    private String TAG = "CalendarActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate Start");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Log.d(TAG, "Google Calendar Start");
        //Google Calendar
        calendarManager = new CalendarManager(this, CalendarActivity.this);
        calendarManager.init();

        //calendarManager.asyncSchedule();
        //GlobalGoogleCalendarManager.calendarManager = calendarManager;

        //get calendar view and set month color white
        cv = (CalendarView) this.findViewById(R.id.calendarView);
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
                String date = String.format("%04d-%02d-%02d", year, month+1, dayOfMonth);
                setListView(date);
            }
        });

        layoutScheduleAdd = (LinearLayout)findViewById(R.id.lyScheduleAdd);
        layoutScheduleAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ScheduleManageActivity.class);
                startActivity(intent);
                //insertEvent();
            }
        });
        Log.d(TAG, "onCreate Finish");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult Start");
        super.onActivityResult(requestCode, resultCode, data);
        calendarManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult Finish");
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume Start");
        super.onResume();

        //progree dialog
        //SystemClock.sleep(2000);

        /*while(GlobalVariable.isScheduleDBUpdated == false)
        {
            long finish = System.currentTimeMillis();
            if(finish - start > 3000) {
                Log.d(TAG, "DB Update Complete ...");
                GlobalVariable.isScheduleDBUpdated = false;
                break;
            }
        }*/
        //pd.dismiss();
        Date selected = new Date(cv.getDate());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        selectedDate = sdf.format(selected);


        //ProgressDialog pd = ProgressDialog.show(CalendarActivity.this, "", "일정을 불러오는 중입니다 ...", true);
        //pd.dismiss();

        setListView(selectedDate);
        // get Current focused date from calendar

        // set List View

        Log.d(TAG, "onResume Finish");
    }

    public void setListView(String date)
    {
        Log.d(TAG, "setListView Start");

        calendarManager.asyncSchedule();
        GlobalGoogleCalendarManager.calendarManager = calendarManager;

        lvSchedule = (ListView) findViewById(R.id.lvSchedule);

        arrayList = new ArrayList<ScheduleInfo>();
        arrayList = DBManager.getManager(getApplicationContext()).getSchedulesByDateTimeDB(date).scheduleList;
        adapter = new ScheduleAdapter(getApplicationContext(), R.layout.list_row_schedule, arrayList);

        lvSchedule.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        lvSchedule.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO
                //change Activity to schedule setting
                /*
                ivDeleteEvent = (ImageView)view.findViewById(R.id.ivDeleteSchedule);
                ivDeleteEvent.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CalendarActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                    }
                });*/

                ViewHolder vh = (ViewHolder) view.getTag();
                String date = vh.scheduleDate;
                String time = vh.tvScheduleTime.getText().toString();
                String createdDate = vh.createdDate;

                Intent intent = new Intent(getApplicationContext(), ScheduleManageActivity.class);
                intent.putExtra("date", date);
                intent.putExtra("time", time);
                intent.putExtra("createdDate", createdDate);
                startActivity(intent);

                //Toast.makeText(CalendarActivity.this, date + " " + time, Toast.LENGTH_SHORT).show();

            }
        });
        Log.d(TAG, "setListView Finish");
    }
/*
    public void insertEvent()
    {
        calendarManager = GlobalGoogleCalendarManager.calendarManager;

        final Event event = new Event()
                .setSummary("Test")
                .setLocation("800 Howard St., San Francisco, CA 94103");

        DateTime startDateTime = new DateTime("2015-06-06T10:00:00+09:00");
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("Asia/Seoul");
        event.setStart(start);

        DateTime endDateTime = new DateTime("2015-06-06T12:00:00+09:00");
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("Asia/Seoul");
        event.setEnd(end);
        final String calendarId = "primary";

        calendarManager.insertEvent(event);

    }*/

    public void deleteEvent(String createdDate)
    {
        String eventId;
        calendarManager = GlobalGoogleCalendarManager.calendarManager;
        eventId = calendarManager.getEventIdFromCreatedDate(createdDate);
        calendarManager.deleteEvent(eventId);
    }




    /*
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.calendar, menu);
        return true;
    }*/

}
