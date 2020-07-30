package com.example.bottomnavmenu.SupportClasses;

import java.util.Date;

public class CaloriesField implements Comparable<CaloriesField>{

    private Date dateTime;
    private float calories;
    private long timeStamp;

    public long getTimeStamp() {
        return timeStamp;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
        timeStamp = dateTime.getTime();
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public CaloriesField() {
        this.dateTime = new Date();
        this.calories = 0;
        timeStamp = dateTime.getTime();
    }

    public  CaloriesField(Date date, float duration) {
        this.dateTime = date;
        this.calories = duration;
        timeStamp = dateTime.getTime();
    }

    @Override
    public int compareTo(CaloriesField o) {
        return this.dateTime.compareTo(o.dateTime);
    }
}
