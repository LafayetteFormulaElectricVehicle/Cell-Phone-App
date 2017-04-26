package com.cellvscada.lfev2017.ViewGenerators;

/**
 * Created by ktdilsiz on 4/5/17.
 */

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class BarChartGenerator extends ViewGenerator {

    BarChart barChart;
    ArrayList<BarEntry> yaxis;
    ArrayList<String> xaxis;

    TreeMap<String, BarEntry> yaxisTest;

    BarDataSet barDataSet;
    BarData barData;
    BarDataSet newDataSet;

    String label;

    String[] quarters;

    boolean addingUpdate;
    boolean replacingUpdate;

    int HEIGHT = 250;
    int WIDTH = 300;

    public BarChartGenerator(Context context, String id){
        this.context = context;
        yaxis = new ArrayList<>();
        xaxis = new ArrayList<>();
        yaxisTest = new TreeMap<>();

        this.id = id;
        this.addingUpdate = false;
        this.replacingUpdate = false;

        initialSetup();
        findDPdimension(HEIGHT, WIDTH);
        viewSetup();
    }

    public BarChartGenerator(Context context, String id, boolean addingUpdate, boolean replacingUpdate){
        this.context = context;
        yaxis = new ArrayList<>();
        xaxis = new ArrayList<>();
        yaxisTest = new TreeMap<>();

        this.id = id;
        this.addingUpdate = addingUpdate;
        this.replacingUpdate = replacingUpdate;

        initialSetup();
        findDPdimension(HEIGHT, WIDTH);
        viewSetup();
    }

    public BarChartGenerator(Context context, int HEIGHT, int WIDTH, String id){
        this.context = context;
        yaxis = new ArrayList<>();
        xaxis = new ArrayList<>();
        yaxisTest = new TreeMap<>();

        this.id = id;

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

    public void setupYaxis(){
        yaxis = new ArrayList<>();
    }

    public void insertYaxis(float position, float value){
        yaxis.add(new BarEntry(position, value));
    }

    public void insertYaxisTest(float position, float value, String date){
        yaxisTest.put(date, new BarEntry(position, value));
    }

    public void insertXaxis(String xaxisLabel){
        xaxis.add(xaxisLabel);
    }

    public void setYaxis(String label){
        barDataSet = new BarDataSet(yaxis, label);
    }

    public void setYaxisTest(String label){
        yaxis = new ArrayList<>();
        xaxis = new ArrayList<>();
        for(Map.Entry<String, BarEntry> each : yaxisTest.entrySet()){
            xaxis.add(each.getKey().substring(5));
            yaxis.add(each.getValue());
        }
        barDataSet = new BarDataSet(yaxis, label);
        this.label = label;

        quarters = new String[xaxis.size()];
        quarters = xaxis.toArray(quarters);
    }

    public void setXaxis(String label){

    }

    public void addDataSet(String label){
        newDataSet = new BarDataSet(yaxis, label);
        barData = new BarData(newDataSet);
        barChart.notifyDataSetChanged();
        barChart.invalidate();
    }

    public void addDataToExisting(float position, float value, String date){
        //yaxis.add(new BarEntry(position, value));
        yaxisTest.put(date, new BarEntry(position, value));
        //barData.addEntry(new BarEntry(position, value), barDataSet.getStackSize() - 1);
    }

    public int getSize(){
        return yaxisTest.size();
    }

    public boolean getAddingUpdate(){
        return addingUpdate;
    }

    public boolean getReplacingUpdate(){
        return replacingUpdate;
    }

    public void addDataToExistingFinish(){
        yaxis = new ArrayList<>();
        xaxis = new ArrayList<>();
        for(Map.Entry<String, BarEntry> each : yaxisTest.entrySet()){
            xaxis.add(each.getKey().substring(5));
            yaxis.add(each.getValue());
            //Log.e("Test", each.getKey());
            //Log.e("Test", each.getValue().toString());
        }
        barDataSet = new BarDataSet(yaxis, label);

        quarters = new String[xaxis.size()];
        quarters = xaxis.toArray(quarters);

        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                //Log.e("Formatter", quarters[((int)value) - 1]);
                return quarters[(int) value - 1];
            }

        };

        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);
        xAxis.setTextSize(3);

        barData = new BarData(barDataSet);

        barChart.setData(barData);
        barChart.notifyDataSetChanged();
        barChart.invalidate();

    }

    public void setBarData(){
        barData = new BarData(barDataSet);
        barChart.setData(barData);
        Description description = new Description();
        description.setText("");
        barChart.setDescription(description);

        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return quarters[(int) value];
            }

        };

        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);
        xAxis.setTextSize(3);
    }


}
