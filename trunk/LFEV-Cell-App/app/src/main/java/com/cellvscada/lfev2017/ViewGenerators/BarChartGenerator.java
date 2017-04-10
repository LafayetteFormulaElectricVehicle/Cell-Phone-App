package com.cellvscada.lfev2017.ViewGenerators;

/**
 * Created by ktdilsiz on 4/5/17.
 */

import android.content.Context;
import android.support.v7.app.ActionBar;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class BarChartGenerator extends ViewGenerator {

    BarChart barChart;
    ArrayList<BarEntry> yaxis;
    ArrayList<String> xaxis;
    BarDataSet barDataSet;
    BarData barData;

    int HEIGHT = 250;
    int WIDTH = 300;

    public BarChartGenerator(Context context){
        this.context = context;
        yaxis = new ArrayList<>();
        xaxis = new ArrayList<>();

        initialSetup();
        findDPdimension(HEIGHT, WIDTH);
        viewSetup();
    }

    public BarChartGenerator(Context context, int HEIGHT, int WIDTH){
        this.context = context;
        yaxis = new ArrayList<>();
        xaxis = new ArrayList<>();

        initialSetup();
        findDPdimension(HEIGHT, WIDTH);
        viewSetup();
    }

    public void barChartSetup(){
        barChart = new BarChart(context);

        barChart.setLayoutParams(new ActionBar.LayoutParams(widthDP, viewHeightDP));

    }

    public void finalSetup(){
        topLayout.addView(barChart);
        topLayout.addView(holdHere);
    }

    public void insertYaxis(float position, float value){
        yaxis.add(new BarEntry(position, value));
    }

    public void insertXaxis(String xaxisLabel){
        xaxis.add(xaxisLabel);
    }

    public void setYaxis(String label){
        barDataSet = new BarDataSet(yaxis, label);
    }

    public void setXaxis(String label){

    }

    public void setBarData(){
        barData = new BarData(barDataSet);
        barChart.setData(barData);
    }



}
