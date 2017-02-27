package com.lfev2017.ktdilsiz.cellapptest;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.lfev2017.ktdilsiz.cellapptest.ChartGenerators.BarChartGenerator;
import com.lfev2017.ktdilsiz.cellapptest.ChartGenerators.LineChartGenerator;

/**
 * Created by ktdilsiz on 2/20/17.
 */

public class LayoutInflater extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_inflater);

//        // get a reference to the already created main layout
//        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.activity_main_layout);
//
//        // inflate (create) another copy of our custom layout
//        android.view.LayoutInflater inflater = getLayoutInflater();
//        View myLayout = inflater.inflate(R.layout.my_layout, mainLayout, false);
//
//        // make changes to our custom layout and its subviews
//        myLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
//        TextView textView = (TextView) myLayout.findViewById(R.id.textView);
//        textView.setText("New Layout");
//
//        // add our custom layout to the main layout
//        mainLayout.addView(myLayout);

        Button inflaterButton = (Button) findViewById(R.id.inflater_button);
        Button inflaterLineButton = (Button) findViewById(R.id.inflater_line_button);
        Button inflaterBarButton = (Button) findViewById(R.id.inflater_bar_button);
    }




    public void createNewInflater(View view){
        // get a reference to the already created main layout
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.activity_main_layout);

        // inflate (create) another copy of our custom layout
        android.view.LayoutInflater inflater = getLayoutInflater();
        View myLayout = inflater.inflate(R.layout.my_layout, mainLayout, false);

        // make changes to our custom layout and its subviews
        myLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        TextView textView = (TextView) myLayout.findViewById(R.id.textView);
        textView.setText("New Layout");

        // add our custom layout to the main layout
        mainLayout.addView(myLayout);

        //myLayout.setOnLongClickListener(new MyOnLongClickListener(myLayout));
        myLayout.setOnTouchListener(new MyOnTouchListener(myLayout));
        //myLayout.setOnDragListener(new MyOnDragListener());

    }

    public void createLineInflater(View view){
        // get a reference to the already created main layout
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.activity_main_layout);

        // inflate (create) another copy of our custom layout
        android.view.LayoutInflater inflater = getLayoutInflater();
        View myLayout = inflater.inflate(R.layout.line_layout, mainLayout, false);

        mainLayout.addView(myLayout);

        LineChart lineChart = (LineChart) findViewById(R.id.linegraph);
        LineChartGenerator newGenerator = new LineChartGenerator();
        newGenerator.chartPopulate(lineChart);

        //myLayout.setOnLongClickListener(new MyOnLongClickListener(myLayout));
        myLayout.setOnTouchListener(new MyOnTouchListener(myLayout));
        //myLayout.setOnDragListener(new MyOnDragListener());
    }

    public void createBarInflater(View view){
        // get a reference to the already created main layout
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.activity_main_layout);

        // inflate (create) another copy of our custom layout
        android.view.LayoutInflater inflater = getLayoutInflater();

        View myLayout = inflater.inflate(R.layout.bar_layout, mainLayout, false);
        mainLayout.addView(myLayout);

    //        DisplayGrafActivity newgraf = new DisplayGrafActivity();
    //
    //        newgraf.createElsewhere();

        BarChart barChart = (BarChart) findViewById(R.id.bargraph);
        BarChartGenerator newGenerator = new BarChartGenerator();
        newGenerator.chartPopulate(barChart);


        //myLayout.setOnLongClickListener(new MyOnLongClickListener(myLayout));
        myLayout.setOnTouchListener(new MyOnTouchListener(myLayout));
        //myLayout.setOnDragListener(new MyOnDragListener());

    }



}
