package com.example.bottomnavmenu.SupportClasses;

import java.util.Date;

public class WeightField implements Comparable<WeightField> {

    private Date date;
    private float weight;
    private long timeStamp;

    public long getTimeStamp() {
        return timeStamp;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        timeStamp =  date.getTime();
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public WeightField() {
        this.date = new Date();
        this.weight = 0;
        timeStamp =  date.getTime();
    }

    public  WeightField(Date date, float duration) {
        this.date = date;
        this.weight = duration;
        timeStamp =  date.getTime();
    }

    @Override
    public int compareTo(WeightField o) {
        return this.date.compareTo(o.date);
    }
}
