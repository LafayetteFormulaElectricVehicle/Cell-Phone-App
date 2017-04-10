package com.cellvscada.lfev2017.ViewGenerators;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cellvscada.lfev2017.Listeners.OnTouchListener;

/**
 * Created by ktdilsiz on 4/5/17.
 */

public class ViewGenerator {

    LinearLayout topLayout;
    TextView holdHere;
    Context context;
    DisplayMetrics metrics;

    int heightDP;
    int widthDP;
    int viewHeightDP;
    int heightHold = 50;

    public int convertToDP(DisplayMetrics metrics, int input){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, input, metrics);
    }

    public void initialSetup(){
        metrics = context.getResources().getDisplayMetrics();
    }

    public void viewSetup(){
        topLayout = new LinearLayout(context);
        topLayout.setOrientation(LinearLayout.VERTICAL);
        topLayout.setLayoutParams(new ActionBar.LayoutParams(widthDP, heightDP));

        holdHere = new TextView(context);
        holdHere.setLayoutParams(
                new ActionBar.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        convertToDP(metrics, heightHold)
                )
        );
        holdHere.setText("Hold me here!");
        holdHere.setGravity(Gravity.CENTER);

        topLayout.setOnTouchListener(new OnTouchListener(topLayout));
    }

    public void findDPdimension(int HEIGHT, int WIDTH){
        heightDP = convertToDP(metrics, HEIGHT);
        widthDP = convertToDP(metrics, WIDTH);
        viewHeightDP = heightDP - convertToDP(metrics, heightHold);
    }

    public View getView(){
        return topLayout;
    }

}
