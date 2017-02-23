package com.lfev2017.ktdilsiz.cellapptest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

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
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.activity_main_layout);

        // inflate (create) another copy of our custom layout
        android.view.LayoutInflater inflater = getLayoutInflater();
        View myLayout = inflater.inflate(R.layout.my_layout, mainLayout, false);

        // make changes to our custom layout and its subviews
        myLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        TextView textView = (TextView) myLayout.findViewById(R.id.textView);
        textView.setText("New Layout");

        // add our custom layout to the main layout
        mainLayout.addView(myLayout);
    }

    public void createLineInflater(View view){
        // get a reference to the already created main layout
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.activity_main_layout);

        // inflate (create) another copy of our custom layout
        android.view.LayoutInflater inflater = getLayoutInflater();
        View myLayout = inflater.inflate(R.layout.line_layout, mainLayout, false);

        mainLayout.addView(myLayout);
    }

    public void createBarInflater(View view){
        // get a reference to the already created main layout
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.activity_main_layout);

        // inflate (create) another copy of our custom layout
        android.view.LayoutInflater inflater = getLayoutInflater();


        View myLayout = inflater.inflate(R.layout.bar_layout, mainLayout, false);

    //        DisplayGrafActivity newgraf = new DisplayGrafActivity();
    //
    //        newgraf.createElsewhere();

        mainLayout.addView(myLayout);

    }



}
