package com.seven.mynah.artifacts;

/**
 * Created by KimJS on 2015-06-02.
 */
public class ScheduleInfo {

    public String scheduleName;
    public String scheduleTime;
    public String scheduleDate;

    public boolean isCorrectDate(String date)
    {
        if(date.equals(scheduleDate))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}


