package com.cellvscada.lfev2017.ViewGenerators;

/**
 * Created by ktdilsiz on 4/5/17.
 */

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.ActionBar;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class LineChartGenerator extends ViewGenerator {

    LineChart lineChart;
    ArrayList<Entry> yaxis;
    ArrayList<String> xaxis;
    LineDataSet lineDataSet;
    LineData lineData;

    List<ILineDataSet> allLines;

    int HEIGHT = 250;
    int WIDTH = 300;

    public LineChartGenerator(Context context){
        this.context = context;
        yaxis = new ArrayList<>();
        xaxis = new ArrayList<>();
        allLines = new ArrayList<>();

        initialSetup();
        findDPdimension(HEIGHT, WIDTH);
        viewSetup();
    }

    public LineChartGenerator(Context context, int HEIGHT, int WIDTH){
        this.context = context;
        yaxis = new ArrayList<>();
        xaxis = new ArrayList<>();

        initialSetup();
        findDPdimension(HEIGHT, WIDTH);
        viewSetup();
    }

    public void lineChartSetup(){
        lineChart = new LineChart(context);

        lineChart.setLayoutParams(new ActionBar.LayoutParams(widthDP, viewHeightDP));
    }

    public void finalSetup(){
        topLayout.addView(lineChart);
        topLayout.addView(holdHere);
    }

    public void insertYaxis(float position, float value){
        yaxis.add(new Entry(position, value));
    }

    public void insertXaxis(String xaxisLabel){
        xaxis.add(xaxisLabel);
    }

    public void setYaxis(String label){
        lineDataSet = new LineDataSet(yaxis, label);
    }

    public void setXaxis(String label){

    }

    public void singleLineSetData(){
        lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
    }

    public void addLine(ArrayList<Float> line, String label, int color){

        ArrayList<Entry> newLine = new ArrayList<>();

        for(int i = 0; i < line.size(); i++){
            newLine.add(new Entry(i, line.get(i)));
        }

        LineDataSet newDataSet = new LineDataSet(newLine, label);
        newDataSet.setColor(color);
        newDataSet.setCircleColor(color);
        newDataSet.setDrawValues(false);

        allLines.add(newDataSet);

    }

    public void multipleLineSetData(){
        lineData = new LineData(allLines);
        lineChart.setData(lineData);
    }

}
