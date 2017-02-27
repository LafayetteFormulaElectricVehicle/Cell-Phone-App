package com.lfev2017.ktdilsiz.cellapptest;

import android.content.ClipData;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by ktdilsiz on 2/27/17.
 */

public class MyOnTouchListener implements View.OnTouchListener{

    View img;
    float dX;
    float dY;
    int lastAction;

    public MyOnTouchListener(View input){
        this.img = input;
    }

//    public boolean onTouch(View v, MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            ClipData data = ClipData.newPlainText("", "");
//            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(img);
//
//            img.startDragAndDrop(data, shadowBuilder, img, 0);
//            //img.setVisibility(View.INVISIBLE);
//            return true;
//        } else {
//            return false;
//        }
//    }

    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(img);

                //img.startDragAndDrop(data, shadowBuilder, img, 0);

                dX = view.getX() - event.getRawX();
                dY = view.getY() - event.getRawY();
                lastAction = MotionEvent.ACTION_DOWN;
                break;

            case MotionEvent.ACTION_MOVE:
                view.setY(event.getRawY() + dY);
                view.setX(event.getRawX() + dX);
                lastAction = MotionEvent.ACTION_MOVE;
                break;

            case MotionEvent.ACTION_UP:
                if (lastAction == MotionEvent.ACTION_DOWN)
                    Toast.makeText(img.getContext(), "Clicked!", Toast.LENGTH_SHORT).show();
                break;

            default:
                return false;
        }
        return true;
    }


}
