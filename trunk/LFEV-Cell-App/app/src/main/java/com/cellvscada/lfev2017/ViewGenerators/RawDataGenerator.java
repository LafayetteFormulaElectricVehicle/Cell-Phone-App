package com.cellvscada.lfev2017.ViewGenerators;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cellvscada.lfev2017.Adapters.HashMapAdapter;
import com.cellvscada.lfev2017.Listeners.OnTouchListener;
import com.cellvscada.lfev2017.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ktdilsiz on 4/5/17.
 */

public class RawDataGenerator extends ViewGenerator {

    LinearLayout topLayout;
    ListView listForValues;
    TextView holdHere;
    Context context;
    DisplayMetrics metrics;
    HashMapAdapter adapter;

    int HEIGHT = 300;
    int WIDTH = 250;
    int heightDP;
    int widthDP;
    int holdHereHeight = 50;

    String id;

    public RawDataGenerator(Context context){
        this.context = context;

        metrics = context.getResources().getDisplayMetrics();
        heightDP = convertToDP(metrics, HEIGHT);
        widthDP = convertToDP(metrics, WIDTH);

        setup();
    }

    public RawDataGenerator(Context context, String id){
        this.context = context;
        this.id = id;

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

    public RawDataGenerator(Context context, int h, int w, String id){
        this.context = context;
        this.HEIGHT = h;
        this.WIDTH = w;
        this.id = id;

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

        if(id != null) {
            TextView title = new TextView(context);
            title.setText(id);
            topLayout.addView(title);
        }

        listForValues = new ListView(context);
        int tempHeightDP = heightDP - convertToDP(metrics, height);
        listForValues.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, tempHeightDP));

        GradientDrawable border = new GradientDrawable();
        border.setColor(0xFFFFFFFF); //white background
        border.setStroke(1, 0xFF000000); //black border with full opacity

        holdHere = new TextView(context);
        holdHere.setLayoutParams(
                new ActionBar.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        convertToDP(metrics, height-20)
                )
        );
        holdHere.setText("Hold me here!");
        holdHere.setPadding(convertToDP(metrics, 10), 0, convertToDP(metrics, 10), 0);
        holdHere.setGravity(Gravity.CENTER);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            holdHere.setBackgroundDrawable(border);
        } else {
            holdHere.setBackground(border);
        }

        topLayout.addView(listForValues);
        topLayout.addView(holdHere);

        topLayout.setOnTouchListener(new OnTouchListener(topLayout));

    }

    public void purgeItems(){
        listForValues = new ListView(context);
        int tempHeightDP = heightDP - convertToDP(metrics, holdHereHeight);
        listForValues.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, tempHeightDP));
    }

    public void adapterSetup(Map<String, String> data){
        adapter = new HashMapAdapter(context, data);
        listForValues.setAdapter(adapter);
        //listForValues.setAdapter(new ArrayAdapter<>(context, R.layout.my_adapter_item, android.R.id.text1 ,list));
    }

    public HashMapAdapter getAdapter(){
        return adapter;
    }

    public View getView(){
        return topLayout;
    }

}
