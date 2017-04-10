package com.cellvscada.lfev2017.Tools;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by ktdilsiz on 4/6/17.
 */

public class MenuFragment extends Fragment {

    MenuItem fav;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        fav = menu.add("add");


        super.onCreateOptionsMenu(menu, inflater);
    }
}
