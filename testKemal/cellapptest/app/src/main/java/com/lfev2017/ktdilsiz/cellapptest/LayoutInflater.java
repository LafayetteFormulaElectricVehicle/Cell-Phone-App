package com.lfev2017.ktdilsiz.cellapptest;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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
    String idHex;

    View linechart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_inflater);

        //testHash = (HashMap<String, HashMap<String,String>>) getIntent().getSerializableExtra("hashmap");
        test = (HashMap<String, String>) getIntent().getSerializableExtra("hashmap");

        dataUrl = getIntent().getStringExtra("url");
        idHex = getIntent().getStringExtra("id");

        Toast.makeText(this, "Url: " + dataUrl, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "ID: " + idHex, Toast.LENGTH_SHORT).show();

        //test = testHash.get(idHex);

//        if(test == null){
//            Toast.makeText(this, "No data avaliable", Toast.LENGTH_LONG).show();
//        }else{
//            Toast.makeText(this, "Data avaliable!", Toast.LENGTH_LONG).show();
//        }

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

    public void updateChartSize(MenuItem item){

        android.view.LayoutInflater inflater = getLayoutInflater();
        View inputLayout = inflater.inflate(R.layout.alert_x_y, null);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Title");
        alert.setMessage("Message");
        alert.setView(inputLayout);

// Set an EditText view to get user input
        //final LinearLayout inputLayout = (LinearLayout) findViewById(R.id.UserInputXY);
        final EditText x_input = (EditText) inputLayout.findViewById(R.id.x_input);
        final EditText y_input = (EditText) inputLayout.findViewById(R.id.y_input);
        //final EditText x_input = new EditText(this);


        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                linechart.setLayoutParams(new RelativeLayout.LayoutParams(Integer.parseInt(x_input.getText().toString()), Integer.parseInt(y_input.getText().toString())));
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }

    public void updateData(MenuItem item){
        DataHandler newHandler = new DataHandler();
        JsonHandler finalJson = new JsonHandler(newHandler, dataUrl);

        Toast.makeText(this, "Data updated from: " + dataUrl, Toast.LENGTH_SHORT).show();

        testHash = newHandler.allSystems;

        test = testHash.get(idHex);

        if(test == null){
            Toast.makeText(this, "No data avaliable", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Data avaliable!", Toast.LENGTH_LONG).show();
        }
    }

     public void createRawInflater(MenuItem item){

        try {
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

        }catch (Exception e){
            Toast.makeText(this, "Couldn't connect, url: " + dataUrl + " id: "+ idHex, Toast.LENGTH_LONG).show();
        }
    }

    public void createLineInflater(MenuItem item){
        // get a reference to the already created main layout
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.activity_main_layout);

        // inflate (create) another copy of our custom layout
        android.view.LayoutInflater inflater = getLayoutInflater();
        View myLayout = inflater.inflate(R.layout.line_layout, mainLayout, false);

        mainLayout.addView(myLayout);

        linechart = myLayout;

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
