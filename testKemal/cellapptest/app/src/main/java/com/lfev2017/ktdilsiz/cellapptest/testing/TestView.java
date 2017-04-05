package com.lfev2017.ktdilsiz.cellapptest.testing;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lfev2017.ktdilsiz.cellapptest.R;

/**
 * Created by ktdilsiz on 3/29/17.
 */

public class TestView extends AppCompatActivity {

    pl.pawelkleczkowski.customgauge.CustomGauge gauge;
    pl.pawelkleczkowski.customgauge.CustomGauge gauge2;
    pl.pawelkleczkowski.customgauge.CustomGauge gauge3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //addLayoutDynamic();

        gauge = ((pl.pawelkleczkowski.customgauge.CustomGauge) findViewById(R.id.gauge1));
        gauge2 = ((pl.pawelkleczkowski.customgauge.CustomGauge) findViewById(R.id.gauge2));
        //gauge3 = ((pl.pawelkleczkowski.customgauge.CustomGauge) findViewById(R.id.gauge3));
    }


    public void increaseValue(View view){

        //gauge = ((pl.pawelkleczkowski.customgauge.CustomGauge) findViewById(R.id.gauge1));

        gauge.setValue(gauge.getValue() + 100);
        gauge2.setValue(gauge2.getValue() + 50);
        //gauge3.setValue(gauge3.getValue() + 25);

        TextView valueGauge1 = (TextView) findViewById(R.id.textView1);
        TextView valueGauge2 = (TextView) findViewById(R.id.textView2);
        int temp = gauge.getValue();
        int temp2 = gauge2.getValue();


        valueGauge1.setText(Integer.toString(temp));
        valueGauge2.setText(Integer.toString(temp2));

        addLayoutDynamic();
    }

    public void addLayoutDynamic(){
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.activity_test);

        RelativeLayout newLayout = new RelativeLayout(this);
        //newLayout.setOrientation(LinearLayout.VERTICAL);
        newLayout.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        pl.pawelkleczkowski.customgauge.CustomGauge newGauge = new pl.pawelkleczkowski.customgauge.CustomGauge(this);
        newGauge.setLayoutParams(new ActionBar.LayoutParams(800,800));
        newGauge.setStartAngle(180);
        newGauge.setSweepAngle(180);
        newGauge.setStartValue(0);
        newGauge.setEndValue(200);
        newGauge.setValue(150);

        TextView testView = new TextView(this);
        testView.setText("TEST AMK");

        com.github.mikephil.charting.charts.BarChart newChart = new com.github.mikephil.charting.charts.BarChart(this);
        newChart.setLayoutParams(new ActionBar.LayoutParams(1000,1000));

        com.cardiomood.android.controls.gauge.SpeedometerGauge newTestGauge = new com.cardiomood.android.controls.gauge.SpeedometerGauge(this);

        newTestGauge.setLabelConverter(new com.cardiomood.android.controls.gauge.SpeedometerGauge.LabelConverter() {
            @Override
            public String getLabelFor(double progress, double maxProgress) {
                return String.valueOf((int) Math.round(progress));
            }
        });

        newTestGauge.setMaxSpeed(500);
        newTestGauge.setMajorTickStep(100);
        newTestGauge.setMinorTicks(2);
        newTestGauge.setLayoutParams(new ActionBar.LayoutParams(1800,1800));

        newLayout.addView(testView);
        newLayout.addView(newGauge);
        newLayout.addView(newChart);
        newLayout.addView(newTestGauge);
        //android.view.LayoutInflater inflater = getLayoutInflater();
        //mainLayout.addView(newLayout);
        mainLayout.addView(newLayout);

        Toast.makeText(this, "OMG DOESNT FUCKING WORK", Toast.LENGTH_LONG).show();
        //this.addContentView(newGauge, newGauge.getLayoutParams());
    }
}

