package com.seven.mynah.artifacts;

/**
 * Created by KimJS on 2015-06-02.
 * ������ 1���� ����ִ� ��ü
 * �������̸�, �ð�, ��¥
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


