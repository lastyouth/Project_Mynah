package com.seven.mynah;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.common.collect.Multimap;
import com.seven.mynah.adapter.ScheduleAdapter;
import com.seven.mynah.adapter.ViewHolder;
import com.seven.mynah.artifacts.ScheduleInfo;
import com.seven.mynah.artifacts.SchedulesOnDateInfo;
import com.seven.mynah.calender.CalendarManager;
import com.seven.mynah.database.DBManager;
import com.seven.mynah.globalmanager.GlobalGoogleCalendarManager;
import com.seven.mynah.globalmanager.RECManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.*;

import static android.graphics.Color.WHITE;

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
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
//        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


        Log.d(TAG, "Google Calendar Start");
        //Google Calendar

        calendarManager = new CalendarManager(this, CalendarActivity.this);
        calendarManager.init();
        GlobalGoogleCalendarManager.calendarManager = calendarManager;

        calendarManager.asyncSchedule();
        GlobalGoogleCalendarManager.calendarManager = calendarManager;

        //get calendar view and set month color white
        cv = (CalendarView) this.findViewById(R.id.calendarView);
        ViewGroup vg = (ViewGroup) cv.getChildAt(0);
        View month_name = vg.getChildAt(0);
        View day_name = vg.getChildAt(1);
        cv.setShowWeekNumber(false);

        if (month_name instanceof TextView) {
            ((TextView) month_name).setTextColor(WHITE);
        }

        //TODO 색깔 확인
        if(day_name instanceof TextView) {
            ((TextView)day_name).setTextColor(WHITE);
        }

        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String date = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                selectedDate = date;
                setListView(date);
            }
        });

        layoutScheduleAdd = (LinearLayout) findViewById(R.id.lyScheduleAdd);

        layoutScheduleAdd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    layoutScheduleAdd.setAlpha((float) 0.8);
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    layoutScheduleAdd.setAlpha((float) 1.0);
                    Intent intent = new Intent(getApplicationContext(), ScheduleManageActivity.class);
                    intent.putExtra("type", "add");
                    intent.putExtra("date", selectedDate);
                    startActivity(intent);
                    return true;
                }
                return true;
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

        Date selected = new Date(cv.getDate());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        selectedDate = sdf.format(selected);

        setListView(selectedDate);
        Log.d(TAG, "onResume Finish");
    }

    public void setListView(String date) {
        Log.d(TAG, "setListView Start");

        calendarManager.asyncSchedule();
        GlobalGoogleCalendarManager.calendarManager = calendarManager;
        //if calendatManager.credential.getSelectedAccountName() == null
        //finish() ==> 캘린더 액티비티에 바로 업데이트가 안됨 메인을 다시 보여주면 동기화가 됨

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
                ViewHolder vh = (ViewHolder) view.getTag();
                String scheduleName = vh.tvScheduleName.getText().toString();
                String date = vh.scheduleDate;
                String time = vh.tvScheduleTime.getText().toString();
                String createdDate = vh.createdDate;

                Intent intent = new Intent(getApplicationContext(), ScheduleManageActivity.class);
                intent.putExtra("type", "modify");
                intent.putExtra("scheduleName", scheduleName);
                intent.putExtra("date", date);
                intent.putExtra("time", time);
                intent.putExtra("createdDate", createdDate);
                startActivity(intent);
            }
        });
        Log.d(TAG, "setListView Finish");
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
//        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
