package com.cellvscada.lfev2017.Listeners;

import android.content.Context;
import android.view.View;

/**
 * Created by ktdilsiz on 4/11/17.
 */

public class OnLongClickListener implements View.OnLongClickListener {

    Context context;

    public OnLongClickListener(Context context){
        this.context = context;
    }

    public boolean onLongClick(View view){


        return true;
    }


}
