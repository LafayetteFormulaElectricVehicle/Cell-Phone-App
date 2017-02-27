package com.lfev2017.ktdilsiz.cellapptest.ChartGenerators;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

/**
 * Created by ktdilsiz on 2/27/17.
 */

public class BarChartGenerator {

    public void chartPopulate(BarChart barChart){
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
        barChart.setData(data);
    }
}
