package com.example.bottomnavmenu.SupportClasses;

import java.sql.Time;
import java.util.Date;

public class SleepField implements Comparable<SleepField> {
    private Date date;
    private float duration;
    private long timeStamp;

    public long getTimeStamp() {
        return timeStamp;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        timeStamp = date.getTime();
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public SleepField() {
        this.date = new Date();
        this.duration = 0;
        timeStamp = date.getTime();
    }

    public SleepField(Date date, float duration) {
        this.date = date;
        this.duration = duration;
        timeStamp = date.getTime();
    }

    @Override
    public int compareTo(SleepField o) {
        return this.date.compareTo(o.date);
    }
}
