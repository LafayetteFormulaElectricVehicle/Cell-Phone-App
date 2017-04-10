package com.cellvscada.lfev2017.Listeners;

import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by ktdilsiz on 2/26/17.
 */

public class OnDragListener implements View.OnDragListener{

    String msg;
    public android.widget.RelativeLayout.LayoutParams layoutParams;

    public boolean onDrag(View v, DragEvent event) {
        switch(event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                layoutParams = (RelativeLayout.LayoutParams)v.getLayoutParams();
                Log.d(msg, "Action is DragEvent.ACTION_DRAG_STARTED");

                // Do nothing
                break;

            case DragEvent.ACTION_DRAG_ENTERED:
                Log.d(msg, "Action is DragEvent.ACTION_DRAG_ENTERED");
                int x_cord = (int) event.getX();
                int y_cord = (int) event.getY();
                break;

            case DragEvent.ACTION_DRAG_EXITED :
                Log.d(msg, "Action is DragEvent.ACTION_DRAG_EXITED");
                x_cord = (int) event.getX();
                y_cord = (int) event.getY();
                layoutParams.leftMargin = x_cord;
                layoutParams.topMargin = y_cord;
                v.setY(y_cord);
                v.setX(x_cord);
                Toast.makeText(v.getContext(), x_cord + " " + y_cord, Toast.LENGTH_LONG).show();
                //v.setLayoutParams(layoutParams);
                break;

            case DragEvent.ACTION_DRAG_LOCATION  :
                Log.d(msg, "Action is DragEvent.ACTION_DRAG_LOCATION");
                x_cord = (int) event.getX();
                y_cord = (int) event.getY();

                break;

            case DragEvent.ACTION_DRAG_ENDED   :
                Log.d(msg, "Action is DragEvent.ACTION_DRAG_ENDED");

                // Do nothing
                break;

            case DragEvent.ACTION_DROP:
                Log.d(msg, "ACTION_DROP event");

                // Do nothing
                break;
            default: break;
        }
        return true;
    }
}
