package com.lfev2017.ktdilsiz.cellapptest;

import android.content.ClipData;
import android.content.ClipDescription;
import android.view.View;
import android.widget.TextView;

/**
 * Created by ktdilsiz on 2/27/17.
 */

public class MyOnLongClickListener implements View.OnLongClickListener {

    View img;

    public MyOnLongClickListener(View input){
        this.img = input;
    }

    public boolean onLongClick(View v) {
        ClipData.Item item = new ClipData.Item((CharSequence)v.getTag());
        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};

        ClipData dragData = new ClipData(v.getTag().toString(),mimeTypes, item);
        View.DragShadowBuilder myShadow = new View.DragShadowBuilder(img);

        v.startDragAndDrop(dragData,myShadow,null,0);
        return true;
    }

}
