package com.cellvscada.lfev2017.ViewGenerators;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cellvscada.lfev2017.Adapters.HashMapAdapter;
import com.cellvscada.lfev2017.Listeners.OnTouchListener;

import java.util.HashMap;

/**
 * Created by ktdilsiz on 4/5/17.
 */

public class RawDataGenerator extends ViewGenerator {

    LinearLayout topLayout;
    ListView listForValues;
    TextView holdHere;
    Context context;
    DisplayMetrics metrics;

    int HEIGHT = 300;
    int WIDTH = 250;
    int heightDP;
    int widthDP;

    public RawDataGenerator(Context context){
        this.context = context;

        metrics = context.getResources().getDisplayMetrics();
        heightDP = convertToDP(metrics, HEIGHT);
        widthDP = convertToDP(metrics, WIDTH);

        setup();
    }

    public RawDataGenerator(Context context, int h, int w){
        this.context = context;
        this.HEIGHT = h;
        this.WIDTH = w;

        metrics = context.getResources().getDisplayMetrics();
        heightDP = convertToDP(metrics, HEIGHT);
        widthDP = convertToDP(metrics, WIDTH);

        setup();
    }

    private void setup(){
        int height = 50;

        topLayout = new LinearLayout(context);
        topLayout.setOrientation(LinearLayout.VERTICAL);
        topLayout.setLayoutParams(new ActionBar.LayoutParams(widthDP, heightDP));

        listForValues = new ListView(context);
        int tempHeightDP = heightDP - convertToDP(metrics, height);
        listForValues.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, tempHeightDP));

        holdHere = new TextView(context);
        holdHere.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, convertToDP(metrics, height)));
        holdHere.setText("Hold me here!");
        holdHere.setGravity(Gravity.CENTER);

        topLayout.addView(listForValues);
        topLayout.addView(holdHere);

        topLayout.setOnTouchListener(new OnTouchListener(topLayout));

    }

    public void adapterSetup(HashMap<String, String> data){
        HashMapAdapter adapter = new HashMapAdapter(context, data);
        listForValues.setAdapter(adapter);
    }

    public View getView(){
        return topLayout;
    }

}
