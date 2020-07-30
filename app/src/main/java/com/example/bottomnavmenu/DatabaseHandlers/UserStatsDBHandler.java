package com.example.bottomnavmenu.DatabaseHandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.bottomnavmenu.SupportClasses.CaloriesField;
import com.example.bottomnavmenu.SupportClasses.SleepField;
import com.example.bottomnavmenu.SupportClasses.WeightField;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserStatsDBHandler extends SQLiteAssetHelper
{
    private static final String DB_Name = "UserStats.db";
    Context context;

    public UserStatsDBHandler(Context context) {
        super(context, DB_Name, null, 1);
        this.context  = context;
    }



    public void addSleep(float inputHrs) {

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        SQLiteDatabase db = this.getWritableDatabase();

        if(db == null)
        {
            Toast.makeText(context , "Database file has been deleted from asset folder", Toast.LENGTH_LONG).show();
            return;
        }

        Cursor cursor = db.rawQuery("select count(date) as count from Sleep_Duration where date = ?", new String[]{date});

        // if sleep duration was already given today delete the previous record and update it to new data

        //todo: the below if statement has not been tested
        if(cursor.moveToNext()) {
            int count = cursor.getInt(0);
            if(count > 0)
                deleteSleep(date, db);
        }


        int hrs = (int)inputHrs%100;
        int min = (int)((inputHrs - hrs)*60);
        String duration = String.format("%d-%02d", hrs, min);



        ContentValues values = new ContentValues();
        values.put("Date", date);
        values.put("Duration", duration);

        db.insert("Sleep_Duration", null, values);

        //todo: delete these test lines
        cursor = db.rawQuery("select * from Sleep_Duration", new String[]{});
        while(cursor.moveToNext())
            System.out.println("Date : "+cursor.getString(0)+" Duration :"+cursor.getString(1));

        cursor.close();
        db.close();
    }


    public void deleteSleep(String date , SQLiteDatabase db)
    {
        //Todo: write code for deleting using date

        db.delete("Sleep_Duration" ,"date = ?", new String[]{date});

        Toast.makeText(this.context , "The previously given data today has been updated" , Toast.LENGTH_SHORT).show();

    }


    public void addCalories(float calories) {

        String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        SQLiteDatabase db = this.getWritableDatabase();

        if(db == null)
        {
            Toast.makeText(context , "Database file has been deleted from asset folder", Toast.LENGTH_LONG).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put("DateTime", dateTime);
        values.put("Calories", calories);

        db.insert("Calories_Intake", null, values);

        //todo: delete these test lines
        Cursor cursor = db.rawQuery("select * from Calories_Intake", new String[]{});
        while(cursor.moveToNext())
            System.out.println("DateTime : "+cursor.getString(0)+" Calories :"+cursor.getString(1)+"kCal");

        cursor.close();
        db.close();
    }


    public void addWeight(float weight) {

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        SQLiteDatabase db = this.getWritableDatabase();

        if(db == null)
        {
            Toast.makeText(context , "Database file has been deleted from asset folder", Toast.LENGTH_LONG).show();
            return;
        }

        Cursor cursor = db.rawQuery("select count(date) as count from Weight where date = ?", new String[]{date});

        // if weight was already given today delete the previous record and update it to new data

        // todo: the below code has not been tested
        if(cursor.moveToNext()) {
            int count = cursor.getInt(0);
            if(count > 0)
                deleteWeight(date, db);
        }

        ContentValues values = new ContentValues();
        values.put("Date", date);
        values.put("Weight", weight);


        db.insert("Weight", null, values);

        //todo: delete these test lines
        cursor = db.rawQuery("select * from Weight", new String[]{});
        while(cursor.moveToNext())
            System.out.println("Date : "+cursor.getString(0)+" Weight :"+cursor.getString(1)+"kg");

        cursor.close();
        db.close();
    }



    public void deleteWeight(String date  , SQLiteDatabase db)
    {
        //Todo: write code for deleting using date

        db.delete("Weight" ,"date = ?", new String[]{date});

        Toast.makeText(this.context , "The previously given data today has been updated" , Toast.LENGTH_SHORT).show();
    }


    public List<SleepField> getSleepRecords()
    {
        List<SleepField> records = new ArrayList<>();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from Sleep_Duration", new String[]{});

        while(cursor.moveToNext())
        {
            String strDate = cursor.getString(0);
            String strDuration = cursor.getString(1);
            SleepField record = new SleepField();

            String []words = strDuration.split("-");
            int hrs = Integer.parseInt(words[0]);
            int mins = Integer.parseInt(words[1]);


            try {
                record.setDate(format.parse(strDate));
                record.setDuration(hrs + (float)mins/60);
                records.add(record);
            } catch (ParseException e) {
                System.err.println("cannot parse string to date in sleep duration view");
            }
        }
        Collections.sort(records);

        cursor.close();
        db.close();

        return records;

    }

    public List<WeightField> getWeightRecords()
    {
        List<WeightField> records = new ArrayList<>();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from Weight", new String[]{});

        while(cursor.moveToNext())
        {
            String strDate = cursor.getString(0);
            String strWeight = cursor.getString(1);
            WeightField record = new WeightField();



            try {
                record.setDate(format.parse(strDate));
                record.setWeight(Float.parseFloat(String.format("%.2f", Float.parseFloat(strWeight))));
                records.add(record);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(records);

        cursor.close();
        db.close();

        return records;

    }

    public List<CaloriesField> getCaloriesRecords()
    {
        List<CaloriesField> records = new ArrayList<>();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from Calories_Intake", new String[]{});

        while(cursor.moveToNext())
        {
            String strDate = cursor.getString(0);
            String strCalories = cursor.getString(1);
            CaloriesField record = new CaloriesField();



            try {
                record.setDateTime(format.parse(strDate));
                record.setCalories(Float.parseFloat(String.format("%.2f", Float.parseFloat (strCalories))));
                records.add(record);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(records);

        cursor.close();
        db.close();

        return records;

    }

}
