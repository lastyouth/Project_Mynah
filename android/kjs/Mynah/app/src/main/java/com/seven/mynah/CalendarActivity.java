package com.seven.mynah;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.seven.mynah.artifacts.ScheduleInfo;
import com.seven.mynah.artifacts.SchedulesOnDateInfo;
import com.seven.mynah.calender.CalendarManager;
import com.seven.mynah.database.DBManager;
import com.seven.mynah.globalmanager.GlobalGoogleCalendarManager;

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

    private ArrayList<ScheduleInfo> totalScheduleList;
    Multimap<String, ScheduleInfo> scheduleByDate;

    private CalendarManager calendarManager;
    private SchedulesOnDateInfo schedulesOnDateInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        //Google Calendar
        calendarManager = new CalendarManager(this, CalendarActivity.this);
        calendarManager.getCredential();
        //calendarManager = GlobalGoogleCalendarManager.calendarManager;

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

                setListView(date);
            }
        });

        layoutScheduleAdd = (LinearLayout)findViewById(R.id.lyScheduleAdd);
        layoutScheduleAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ScheduleManageActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        calendarManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        calendarManager.startManager();
        GlobalGoogleCalendarManager.calendarManager = calendarManager;

        // get Current focused date from calendar

        // set List View
    }

    public void setListView(String date)
    {
        lvSchedule = (ListView) findViewById(R.id.lvSchedule);

        arrayList = DBManager.getManager(getApplicationContext()).getSchedulesByDateTimeDB(date).scheduleList;
        //ScheduleInfo lastInfo = new ScheduleInfo();
        //lastInfo.scheduleDate = "last";
        //lastInfo.scheduleTime = "last";
        //arrayList.add(lastInfo);
        adapter = new ScheduleAdapter(getApplicationContext(), R.layout.list_row_schedule, arrayList);

        //View header = getLayoutInflater().inflate(R.layout.list_row_add, null, false);
        //lvSchedule.addHeaderView(header);

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
    }

    public void insertSchedule()
    {

    }

    /*
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.calendar, menu);
        return true;
    }*/

}
