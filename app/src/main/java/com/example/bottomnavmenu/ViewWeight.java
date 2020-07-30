package com.example.bottomnavmenu;

import android.graphics.Color;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
/*
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;*/

import com.example.bottomnavmenu.DatabaseHandlers.UserStatsDBHandler;
import com.example.bottomnavmenu.SupportClasses.DateAxisValueFormatter;
import com.example.bottomnavmenu.SupportClasses.WeightField;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ViewWeight extends AppCompatActivity {

    LineChart lineChart;
    private UserStatsDBHandler DbHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DbHandler = new UserStatsDBHandler(this);

        setContentView(R.layout.activity_view_weight);
        lineChart = (LineChart) findViewById(R.id.lineChart);

        List<WeightField> records = DbHandler.getWeightRecords();
        DateAxisValueFormatter xAxisFormatter = new DateAxisValueFormatter(Collections.min(records).getTimeStamp());
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(xAxisFormatter);
        xAxis.setAxisMinValue(0);
        xAxis.setLabelCount(4);


        YAxis LAxis = lineChart.getAxisLeft();
        YAxis RAxis = lineChart.getAxisRight();

        LAxis.setAxisMinValue(0);
        LAxis.setAxisMaxValue(100);
        RAxis.setAxisMinValue(0);
        RAxis.setAxisMaxValue(100);


        //lineChart.moveViewToX(Collections.max(records).getTimeStamp() - xAxisFormatter.referenceTimestamp);





        ArrayList<Entry> coordinates = new ArrayList<>();

        for(WeightField record : records)
        {
            coordinates.add( new Entry(record.getTimeStamp() - xAxisFormatter.referenceTimestamp , record.getWeight()));
        }

        LineDataSet lineDataSet = new LineDataSet(coordinates ,"Weight (Kg)");
        lineDataSet.setLineWidth(3);
        lineDataSet.setColor(Color.parseColor("#26C8F7"));
        lineDataSet.setValueTextSize(10);

        lineDataSet.setFillAlpha(110);
        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(lineDataSet);

        LineData lineData = new LineData(iLineDataSets);
        lineChart.setData(lineData);
        lineChart.setVisibleXRangeMaximum(777600000);
        lineChart.setDescription("");
        //lineChart.moveViewToX(Collections.max(records).getTimeStamp() - xAxisFormatter.referenceTimestamp);

    }


}
