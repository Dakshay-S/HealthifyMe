package com.example.bottomnavmenu.SupportClasses;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.AxisValueFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeAxisValueFormatter implements AxisValueFormatter {


    public long referenceTimestamp; // minimum timestamp in your data set
    private DateFormat mDataFormat;
    private Date mDate;

    public DateTimeAxisValueFormatter(long referenceTimestamp) {
        this.referenceTimestamp = referenceTimestamp;
        this.mDataFormat = new SimpleDateFormat("dd/MM HH:mm", Locale.ENGLISH);
        this.mDate = new Date();
    }



    @Override
    public String getFormattedValue(float value, AxisBase axis) {

        long convertedTimestamp = (long) value;

        long originalTimestamp = referenceTimestamp + convertedTimestamp;

        return getDate(originalTimestamp);
    }

    @Override
    public int getDecimalDigits() {
        return 0;
    }

    private String getDate(long timestamp){
        try{
            mDate.setTime(timestamp);
            return mDataFormat.format(mDate);
        }
        catch(Exception ex){
            return "xx";
        }
    }
}
