package com.lfev2017.ktdilsiz.cellapptest;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.lfev2017.ktdilsiz.cellapptest.ChartGenerators.BarChartGenerator;
import com.lfev2017.ktdilsiz.cellapptest.ChartGenerators.LineChartGenerator;
import com.lfev2017.ktdilsiz.cellapptest.Listeners.MyOnTouchListener;
import com.lfev2017.ktdilsiz.cellapptest.testing.ArrayAdapterItem;
import com.lfev2017.ktdilsiz.cellapptest.testing.ObjectItem;

import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Created by ktdilsiz on 2/20/17.
 */

public class LayoutInflater extends AppCompatActivity {

    HashMap<String, String> test;
    ObjectItem[] ObjectItemData;

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

        test = new HashMap<String, String>();

        for(int i = 0; i < 3; i++) {
            test.put("kemal" + i, "test");
            test.put("second" + i, "third");
            test.put("maybe" + i, "last");
        }

        ObjectItemData = new ObjectItem[20];

        ObjectItemData[0] = new ObjectItem(91, "Mercury");
        ObjectItemData[1] = new ObjectItem(92, "Watson");
        ObjectItemData[2] = new ObjectItem(93, "Nissan");
        ObjectItemData[3] = new ObjectItem(94, "Puregold");
        ObjectItemData[4] = new ObjectItem(95, "SM");
        ObjectItemData[5] = new ObjectItem(96, "7 Eleven");
        ObjectItemData[6] = new ObjectItem(97, "Ministop");
        ObjectItemData[7] = new ObjectItem(98, "Fat Chicken");
        ObjectItemData[8] = new ObjectItem(99, "Master Siomai");
        ObjectItemData[9] = new ObjectItem(100, "Mang Inasal");
        ObjectItemData[10] = new ObjectItem(101, "Mercury 2");
        ObjectItemData[11] = new ObjectItem(102, "Watson 2");
        ObjectItemData[12] = new ObjectItem(103, "Nissan 2");
        ObjectItemData[13] = new ObjectItem(104, "Puregold 2");
        ObjectItemData[14] = new ObjectItem(105, "SM 2");
        ObjectItemData[15] = new ObjectItem(106, "7 Eleven 2");
        ObjectItemData[16] = new ObjectItem(107, "Ministop 2");
        ObjectItemData[17] = new ObjectItem(108, "Fat Chicken 2");
        ObjectItemData[18] = new ObjectItem(109, "Master Siomai 2");
        ObjectItemData[19] = new ObjectItem(110, "Mang Inasal 2");


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

    public void createRawInflater(View view){

        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.activity_main_layout);
        //RelativeLayout subLayout = (RelativeLayout) findViewById(R.id.rawdataview);

        android.view.LayoutInflater inflater = getLayoutInflater();
        View myLayout = inflater.inflate(R.layout.rawdata_layout, mainLayout, false);

        //subLayout.addView(list);
            mainLayout.addView(myLayout);

        //Context input = myLayout.getContext();

        MyAdapter adapter = new MyAdapter(myLayout.getContext(), test);

        ListView list = (ListView) findViewById(R.id.rawdatalist);
        //ListView list = new ListView(this);
        list.setAdapter(adapter);
        //subLayout.addView(list);
        //list.setAdapter(adapter);

            //myLayout.setOnTouchListener(new MyOnTouchListener(myLayout));
            //RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.activity_main_layout);

            // inflate (create) another copy of our custom layout
            //android.view.LayoutInflater inflater = getLayoutInflater();
            //View myLayout = inflater.inflate(R.layout.line_layout, mainLayout, false);

            //mainLayout.addView(myLayout);

//            LineChart lineChart = (LineChart) findViewById(R.id.linegraph);
//            LineChartGenerator newGenerator = new LineChartGenerator();
//            newGenerator.chartPopulate(lineChart);

            //myLayout.setOnLongClickListener(new MyOnLongClickListener(myLayout));
        myLayout.setOnTouchListener(new MyOnTouchListener(myLayout));
            //myLayout.setOnDragListener(new MyOnDragListener());
    }

    public void createTestInflater(View view){
        ArrayAdapterItem adapter = new ArrayAdapterItem(this, R.layout.list_view_row_item, ObjectItemData);

        // create a new ListView, set the adapter and item click listener
        //ListView listViewItems = new ListView(this);
        ListView listViewItems = (ListView) findViewById(R.id.rawdatalist);
        //listViewItems.setAdapter(adapter);


        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.activity_main_layout);

        android.view.LayoutInflater inflater = getLayoutInflater();
        //View myLayout = inflater.inflate(R.layout.list_view_row_item, mainLayout, false);

        mainLayout.addView(listViewItems);

        listViewItems.setAdapter(adapter);
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
