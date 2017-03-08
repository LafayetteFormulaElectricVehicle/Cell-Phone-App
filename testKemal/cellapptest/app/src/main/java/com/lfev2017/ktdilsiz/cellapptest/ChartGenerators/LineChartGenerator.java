package com.lfev2017.ktdilsiz.cellapptest.ChartGenerators;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ktdilsiz on 2/27/17.
 */

public class LineChartGenerator {

    public void chartPopulate(LineChart line){

        List<Entry> valuesFirst = new ArrayList<>();
        valuesFirst.add(new Entry(0f, 50));
        valuesFirst.add(new Entry(1f, 50));
        valuesFirst.add(new Entry(2f, 50));
        valuesFirst.add(new Entry(3f, 48));
        valuesFirst.add(new Entry(4f, 48));
        valuesFirst.add(new Entry(5f, 48));
        valuesFirst.add(new Entry(6f, 45));
        valuesFirst.add(new Entry(7f, 45));
        valuesFirst.add(new Entry(8f, 45));
        valuesFirst.add(new Entry(9f, 44));

        List<Entry> valuesSecond = new ArrayList<>();
        valuesSecond.add(new Entry(0f, 42));
        valuesSecond.add(new Entry(1f, 42));
        valuesSecond.add(new Entry(2f, 43));
        valuesSecond.add(new Entry(3f, 43));
        valuesSecond.add(new Entry(4f, 43));
        valuesSecond.add(new Entry(5f, 42));
        valuesSecond.add(new Entry(6f, 42));
        valuesSecond.add(new Entry(7f, 42));
        valuesSecond.add(new Entry(8f, 41));
        valuesSecond.add(new Entry(9f, 40));

        List<ILineDataSet> lines = new ArrayList<>();

        LineDataSet datasetFirst = new LineDataSet(valuesFirst, "Cell-4-1");
        datasetFirst.setColor(Color.RED);
        datasetFirst.setCircleColor(Color.RED);
        datasetFirst.setDrawValues(false);
        lines.add(datasetFirst);

        LineDataSet datasetSecond = new LineDataSet(valuesSecond, "Cell-4-2");
        datasetSecond.setDrawValues(false);
        lines.add(datasetSecond);

        valuesSecond = new ArrayList<>();
        valuesSecond.add(new Entry(0f, 45));
        valuesSecond.add(new Entry(1f, 45));
        valuesSecond.add(new Entry(2f, 44));
        valuesSecond.add(new Entry(3f, 43));
        valuesSecond.add(new Entry(4f, 43));
        valuesSecond.add(new Entry(5f, 42));
        valuesSecond.add(new Entry(6f, 42));
        valuesSecond.add(new Entry(7f, 42));
        valuesSecond.add(new Entry(8f, 41));
        valuesSecond.add(new Entry(9f, 40));

        datasetSecond = new LineDataSet(valuesSecond, "Cell-4-3");
        datasetSecond.setColor(Color.BLACK);
        datasetSecond.setCircleColor(Color.BLACK);
        datasetSecond.setDrawValues(false);
        lines.add(datasetSecond);

        valuesSecond = new ArrayList<>();
        valuesSecond.add(new Entry(0f, 48));
        valuesSecond.add(new Entry(1f, 48));
        valuesSecond.add(new Entry(2f, 48));
        valuesSecond.add(new Entry(3f, 48));
        valuesSecond.add(new Entry(4f, 48));
        valuesSecond.add(new Entry(5f, 47));
        valuesSecond.add(new Entry(6f, 46));
        valuesSecond.add(new Entry(7f, 46));
        valuesSecond.add(new Entry(8f, 46));
        valuesSecond.add(new Entry(9f, 46));

        datasetSecond = new LineDataSet(valuesSecond, "Cell-4-4");
        datasetSecond.setColor(Color.GREEN);
        datasetSecond.setCircleColor(Color.GREEN);
        datasetSecond.setDrawValues(false);
        lines.add(datasetSecond);

        //-----------------------------------

        valuesSecond = new ArrayList<>();
        valuesSecond.add(new Entry(0f, 30));
        valuesSecond.add(new Entry(1f, 30));
        valuesSecond.add(new Entry(2f, 30));
        valuesSecond.add(new Entry(3f, 32));
        valuesSecond.add(new Entry(4f, 30));
        valuesSecond.add(new Entry(5f, 28));
        valuesSecond.add(new Entry(6f, 28));
        valuesSecond.add(new Entry(7f, 28));
        valuesSecond.add(new Entry(8f, 28));
        valuesSecond.add(new Entry(9f, 26));

        datasetSecond = new LineDataSet(valuesSecond, "Cell-4-5");
        datasetSecond.setColor(Color.YELLOW);
        datasetSecond.setCircleColor(Color.YELLOW);
        datasetSecond.setDrawValues(false);
        lines.add(datasetSecond);

        //-----------------------------------

        valuesSecond = new ArrayList<>();
        valuesSecond.add(new Entry(0f, 58));
        valuesSecond.add(new Entry(1f, 58));
        valuesSecond.add(new Entry(2f, 58));
        valuesSecond.add(new Entry(3f, 58));
        valuesSecond.add(new Entry(4f, 58));
        valuesSecond.add(new Entry(5f, 57));
        valuesSecond.add(new Entry(6f, 56));
        valuesSecond.add(new Entry(7f, 56));
        valuesSecond.add(new Entry(8f, 56));
        valuesSecond.add(new Entry(9f, 56));

        datasetSecond = new LineDataSet(valuesSecond, "Cell-4-6");
        datasetSecond.setColor(Color.MAGENTA);
        datasetSecond.setCircleColor(Color.MAGENTA);
        datasetSecond.setDrawValues(false);
        lines.add(datasetSecond);

        //-----------------------------------

        valuesSecond = new ArrayList<>();
        valuesSecond.add(new Entry(0f, 48));
        valuesSecond.add(new Entry(1f, 38));
        valuesSecond.add(new Entry(2f, 38));
        valuesSecond.add(new Entry(3f, 38));
        valuesSecond.add(new Entry(4f, 38));
        valuesSecond.add(new Entry(5f, 37));
        valuesSecond.add(new Entry(6f, 36));
        valuesSecond.add(new Entry(7f, 36));
        valuesSecond.add(new Entry(8f, 36));
        valuesSecond.add(new Entry(9f, 36));

        datasetSecond = new LineDataSet(valuesSecond, "Cell-4-7");
        datasetSecond.setColor(Color.GRAY);
        datasetSecond.setCircleColor(Color.GRAY);
        datasetSecond.setDrawValues(false);
        lines.add(datasetSecond);

        //-----------------------------------

        YAxis leftaxis = line.getAxisLeft();
//
        leftaxis.setAxisMaximum(100);
        leftaxis.setAxisMinimum(0);

        YAxis rightaxis = line.getAxisRight();
        rightaxis.setAxisMinimum(0);
        rightaxis.setAxisMaximum(100);

        List<String> labels = new ArrayList<String>();
        labels.add("14:13:22");
        labels.add("14:13:23");
        labels.add("14:13:24");
        labels.add("14:13:25");
        labels.add("14:13:26");
        labels.add("14:13:27");
        labels.add("14:13:28");
        labels.add("14:13:29");
        labels.add("14:13:30");
        labels.add("14:13:31");

        String[] xAxis = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

        //LineChart data = new LineChart(xAxis, lines);
        LineData data2 = new LineData(lines);
        line.setData(data2);
    }

}
