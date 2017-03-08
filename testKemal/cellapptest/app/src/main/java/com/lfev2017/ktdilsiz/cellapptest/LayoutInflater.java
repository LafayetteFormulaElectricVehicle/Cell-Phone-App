package com.lfev2017.ktdilsiz.cellapptest;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.lfev2017.ktdilsiz.cellapptest.ChartGenerators.BarChartGenerator;
import com.lfev2017.ktdilsiz.cellapptest.ChartGenerators.LineChartGenerator;
import com.lfev2017.ktdilsiz.cellapptest.Listeners.MyOnTouchListener;
import com.lfev2017.ktdilsiz.cellapptest.testing.ArrayAdapterItem;
import com.lfev2017.ktdilsiz.cellapptest.testing.ObjectItem;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Created by ktdilsiz on 2/20/17.
 */

public class LayoutInflater extends AppCompatActivity {

    HashMap<String, String> test;
    HashMap<String, HashMap<String,String>> testHash;
    ObjectItem[] ObjectItemData;
    String dataUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_inflater);

        testHash = (HashMap<String, HashMap<String,String>>) getIntent().getSerializableExtra("hashmap");

        dataUrl = getIntent().getStringExtra("url");

        test = testHash.get("1");

//        for(int i = 0; i < 3; i++) {
//            test.put("kemal" + i, "test");
//            test.put("second" + i, "third");
//            test.put("maybe" + i, "last");
//        }


//        Button inflaterButton = (Button) findViewById(R.id.inflater_button);
//        Button inflaterLineButton = (Button) findViewById(R.id.inflater_line_button);
//        Button inflaterBarButton = (Button) findViewById(R.id.inflater_bar_button);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public void updateData(MenuItem item){
        DataHandler newHandler = new DataHandler();
        JsonHandler finalJson = new JsonHandler(newHandler, dataUrl);

        Toast.makeText(this, "Data updated from: " + dataUrl, Toast.LENGTH_SHORT).show();

        testHash = newHandler.allSystems;
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

    public void createRawInflater(MenuItem item){

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

        //myLayout.setOnLongClickListener(new MyOnLongClickListener(myLayout));
        myLayout.setOnTouchListener(new MyOnTouchListener(myLayout));
        //myLayout.setOnDragListener(new MyOnDragListener());
    }

    public void createLineInflater(MenuItem item){
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

    public void createBarInflater(MenuItem item){
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
