package com.seven.mynah;

import android.app.Activity;
import android.content.Intent;
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
import com.seven.mynah.calender.CalendarManager;
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

    private ArrayList<ScheduleInfo> totalScheduleList;
    Multimap<String, ScheduleInfo> scheduleByDate;

    private CalendarManager calendarManager;

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


                lvSchedule = (ListView) findViewById(R.id.lvSchedule);

                //Collection<ScheduleInfo> list = (ArrayList<ScheduleInfo>)scheduleByDate.get(date);
                //arrayList = (ArrayList<ScheduleInfo>)scheduleByDate.get(date);

                //List list = (List<ScheduleInfo>) scheduleByDate.get(date);
                //arrayList = new ArrayList<ScheduleInfo>(list);
                arrayList = calendarManager.getScheduleOnDate(date);
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
    }
}
