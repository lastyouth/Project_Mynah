package com.seven.mynah.artifacts;

/**
 * Created by KimJS on 2015-06-02.
 * 스케줄 1개를 담고있는 객체
 * 스케줄이름, 시간, 날짜
 */
public class ScheduleInfo {

    public String scheduleName;
    public String scheduleTime;
    public String scheduleDate;
    public String scheduleCreatedDate;

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


