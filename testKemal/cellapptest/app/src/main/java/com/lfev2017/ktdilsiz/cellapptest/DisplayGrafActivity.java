package com.lfev2017.ktdilsiz.cellapptest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayGrafActivity extends DraggableView {

    public BarChart barGraf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_graf);

        final View dragView = findViewById(R.id.activity_display_graf);
        dragView.setOnTouchListener(this);

        barGraf = (BarChart) findViewById(R.id.bargraph);

        ArrayList<BarEntry> values = new ArrayList<>();
        values.add(new BarEntry(0f, 80));
        values.add(new BarEntry(1f, 78));
        values.add(new BarEntry(2f, 78));
        values.add(new BarEntry(3f, 76));
        values.add(new BarEntry(4f, 70));
        values.add(new BarEntry(5f, 70));
        values.add(new BarEntry(6f, 65));
        BarDataSet dataSet = new BarDataSet(values, "Values");

        ArrayList<String> datesList = new ArrayList<String>();
        datesList.add("Pack-4" + " / " + "15:30");
        datesList.add("Pack-4" + " / " + "16:30");
        datesList.add("Pack-4" + " / " + "17:30");
        datesList.add("Pack-4" + " / " + "18:30");
        datesList.add("Pack-4" + " / " + "19:30");
        datesList.add("Pack-4" + " / " + "20:30");
        datesList.add("Pack-4" + " / " + "21:30");

        BarData data = new BarData(dataSet);
        barGraf.setData(data);

        barGraf.setScaleEnabled(true);

        YAxis leftaxis = barGraf.getAxisLeft();

        leftaxis.setAxisMaximum(100);
        leftaxis.setAxisMinimum(0);

        //barGraf.setDescription("Des");

        //ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_graf);
        //layout.addView(textView);
    }

    public void createElsewhere(){

        barGraf = (BarChart) findViewById(R.id.bargraph);

        ArrayList<BarEntry> values = new ArrayList<>();
        values.add(new BarEntry(0f, 80));
        values.add(new BarEntry(1f, 78));
        values.add(new BarEntry(2f, 78));
        values.add(new BarEntry(3f, 76));
        values.add(new BarEntry(4f, 70));
        values.add(new BarEntry(5f, 70));
        values.add(new BarEntry(6f, 65));
        BarDataSet dataSet = new BarDataSet(values, "Values");

        ArrayList<String> datesList = new ArrayList<String>();
        datesList.add("Pack-4" + " / " + "15:30");
        datesList.add("Pack-4" + " / " + "16:30");
        datesList.add("Pack-4" + " / " + "17:30");
        datesList.add("Pack-4" + " / " + "18:30");
        datesList.add("Pack-4" + " / " + "19:30");
        datesList.add("Pack-4" + " / " + "20:30");
        datesList.add("Pack-4" + " / " + "21:30");

        BarData data = new BarData(dataSet);
        barGraf.setData(data);

        barGraf.setScaleEnabled(true);

        YAxis leftaxis = barGraf.getAxisLeft();

        leftaxis.setAxisMaximum(100);
        leftaxis.setAxisMinimum(0);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        return super.onTouch(view, event);
    }

}
