package com.seven.mynah;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.seven.mynah.artifacts.ScheduleInfo;
import com.seven.mynah.calender.CalendarManager;
import com.seven.mynah.database.DBManager;
import com.seven.mynah.globalmanager.GlobalGoogleCalendarManager;
import com.seven.mynah.globalmanager.ServiceAccessManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ScheduleManageActivity extends Activity {

    private EditText etScheduleName;
    private EditText etScheduleDate;
    private EditText etScheduleTime;
    private Button btCancel;
    private Button btDelete;
    private Button btAdd;

    private int year, month, day, hour, minute;
    private String date;
    private String time;
    private String summary;
    private String createdDate;

    private CalendarManager calendarManager;

    private String TAG = "ScheduleManageActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate Start");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_manage);

        btCancel = (Button)findViewById(R.id.btScheduleCancel);
        btDelete = (Button)findViewById(R.id.btScheduleDelete);
        btAdd = (Button)findViewById(R.id.btScheduleAdd);

        Intent intent = getIntent();
        // Modify Schedule Event
        if(intent.getExtras() != null)
        {
            date = intent.getExtras().getString("date").toString();
            time = intent.getExtras().getString("time").toString();
            createdDate = intent.getExtras().getString("createdDate").toString();

            year = Integer.parseInt(date.substring(0, 4));
            month = Integer.parseInt(date.substring(5, 7)) - 1;
            day = Integer.parseInt(date.substring(8, 10));
            hour = Integer.parseInt(time.substring(0, 2));
            minute = Integer.parseInt(time.substring(3, 5));

            btDelete.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(!createdDate.equals(""))
                    {
                        deleteEvent(createdDate);
                        finish();
                    }
                }
            });
        }
        //Add Schedule Event
        else
        {
            GregorianCalendar calendar = new GregorianCalendar();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);

            ((ViewManager)btDelete.getParent()).removeView(btDelete);
            btAdd.setText("추가");
        }


        etScheduleName = (EditText) findViewById(R.id.etScheduleName);
        etScheduleDate = (EditText) findViewById(R.id.etScheduleDate);
        etScheduleTime = (EditText) findViewById(R.id.etScheduleTime);
        etScheduleDate.setText(date);
        etScheduleDate.setGravity(Gravity.CENTER);
        etScheduleTime.setText(time);
        etScheduleTime.setGravity(Gravity.CENTER);

        etScheduleDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ScheduleManageActivity.this, dateSetListener, year, month, day).show();
            }
        });

        etScheduleTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(ScheduleManageActivity.this, timeSetListener, hour, minute, false).show();
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btAdd.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                summary = etScheduleName.getText().toString().trim();
                date = etScheduleDate.getText().toString();
                time = etScheduleTime.getText().toString();

                String startTime = date + "T" + time;
                String endTime;

                Date tmp = null;

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                SimpleDateFormat simpleCreatedDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

                try {
                    tmp = simpleDateFormat.parse(startTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Calendar cal = Calendar.getInstance();
                cal.setTime(tmp);
                cal.add(Calendar.HOUR, 1);
                tmp = cal.getTime();
                endTime = simpleDateFormat.format(tmp);
                startTime += ":00+09:00";
                endTime += ":00+09:00";

                final Event event = new Event()
                        .setSummary(summary);

                //DateTime startDateTime = new DateTime("2015-06-06T10:00:00+09:00");
                DateTime startDateTime = new DateTime(startTime);
                EventDateTime start = new EventDateTime()
                        .setDateTime(startDateTime)
                        .setTimeZone("Asia/Seoul");
                event.setStart(start);

                //DateTime endDateTime = new DateTime("2015-06-06T12:00:00+09:00");
                DateTime endDateTime = new DateTime(endTime);
                EventDateTime end = new EventDateTime()
                        .setDateTime(endDateTime)
                        .setTimeZone("Asia/Seoul");
                event.setEnd(end);

                Date n = new Date();
                String nn = simpleCreatedDateFormat.format(n);
                nn += "+09:00";
                DateTime now = new DateTime(nn);
                event.setCreated(now);
                final String calendarId = "primary";

                // insert event to MynahDB
                // event to ScheduleInfo
                ScheduleInfo scheduleInfo;
                DateTime startt = event.getStart().getDateTime();
                if (startt == null) {
                    // All-day events don't have start times, so just use
                    // the start date.
                    startt = event.getStart().getDate();
                }
                String[] str = startt.toString().split("T");
                // yyyy-mm-dd
                String date = str[0];
                // hh-mm-ss
                String time = str[1].split("\\.")[0];
                time = time.substring(0, 5);
                scheduleInfo = new ScheduleInfo();
                scheduleInfo.scheduleName = event.getSummary();
                scheduleInfo.scheduleDate = date;
                scheduleInfo.scheduleTime = time;
                scheduleInfo.scheduleCreatedDate = event.getCreated().toString();
                DBManager.getManager(getApplicationContext()).setScheduleDB(scheduleInfo);



                //Insert to api
                calendarManager = GlobalGoogleCalendarManager.calendarManager;
                calendarManager.eventIdMap.put(event.getCreated().toString(), event.getId());

                Log.d(TAG, "calendarManager.insertEvent Start");
                calendarManager.insertEvent(event);
                Log.d(TAG, "calendarManager.insertEvent Finish");

                finish();
            }
        }));

        Log.d(TAG, "onCreate Finish");
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            date = String.format("%04d-%02d-%02d", year, monthOfYear+1, dayOfMonth);
            etScheduleDate.setText(date);
        }
    };

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // TODO Auto-generated method stub
            time = String.format("%02d:%02d", hourOfDay, minute);
            etScheduleTime.setText(time);
        }
    };

    public void deleteEvent(String createdDate)
    {
        String eventId;

        //delete event from DB
        DBManager.getManager(getApplicationContext()).deleteSchedulesByCreatedDate(createdDate);
        calendarManager = GlobalGoogleCalendarManager.calendarManager;

        eventId = calendarManager.getEventIdFromCreatedDate(createdDate);
        if(eventId != null)
        {
            calendarManager.deleteEvent(eventId);
            calendarManager.eventIdMap.remove(eventId);
        }
    }
}
