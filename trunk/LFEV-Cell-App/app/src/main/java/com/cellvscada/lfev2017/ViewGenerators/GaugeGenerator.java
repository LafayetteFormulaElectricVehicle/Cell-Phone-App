package com.cellvscada.lfev2017.ViewGenerators;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.cardiomood.android.controls.gauge.SpeedometerGauge;

/**
 * Created by ktdilsiz on 4/5/17.
 */

public class GaugeGenerator extends ViewGenerator{

    SpeedometerGauge gaugeView;

    int HEIGHT = 300;
    int WIDTH = 250;

    int maxSpeed = 200;
    int majorTickStep = 40;
    int minorTickStep = 2;

    public GaugeGenerator(Context context){
        this.context = context;

        initialSetup();
        findDPdimension(HEIGHT, WIDTH);
        viewSetup();

        gaugeSetup();
    }

    public GaugeGenerator(Context context, int HEIGHT, int WIDTH){
        this.context = context;

        initialSetup();
        findDPdimension(HEIGHT, WIDTH);
        viewSetup();
    }

    public void gaugeSetup(){
        gaugeView = new SpeedometerGauge(context);

        gaugeView.setLabelConverter(new SpeedometerGauge.LabelConverter() {
            @Override
            public String getLabelFor(double progress, double maxProgress) {
                return String.valueOf((int) Math.round(progress));
            }
        });

        gaugeView.setMaxSpeed(maxSpeed);
        gaugeView.setMajorTickStep(majorTickStep);
        gaugeView.setMinorTicks(minorTickStep);
        gaugeView.setLayoutParams(new ActionBar.LayoutParams(widthDP, viewHeightDP));

        topLayout.addView(gaugeView);
        topLayout.addView(holdHere);
    }
}
