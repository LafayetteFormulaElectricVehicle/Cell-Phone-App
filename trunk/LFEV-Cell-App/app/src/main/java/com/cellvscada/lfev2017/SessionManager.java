package com.cellvscada.lfev2017;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.cellvscada.lfev2017.Adapters.MainPagerAdapter;

public class SessionManager extends AppCompatActivity {

    private ViewPager pager = null;
    private MainPagerAdapter pagerAdapter = null;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        pagerAdapter = new MainPagerAdapter();
        pager = (ViewPager) findViewById(R.id.view_pager);
        if(pagerAdapter != null) {
            pager.setAdapter(pagerAdapter);
        }

        //Create an initial view to display; must be a subclass of FrameLayout.
        inflater = this.getLayoutInflater();
        FrameLayout v0 = (FrameLayout) inflater.inflate (R.layout.empty_fragment, null);
        pagerAdapter.addView (v0, 0);
        pagerAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.session_manager_menu, menu);
        return true;
    }

    //-----------------------------------------------------------------------------
    // Here's what the app should do to add a view to the ViewPager.
    public void addView (View newPage)
    {
        int pageIndex = pagerAdapter.addView (newPage);
        // You might want to make "newPage" the currently displayed page:
        pager.setCurrentItem (pageIndex, true);
    }

    //-----------------------------------------------------------------------------
    // Here's what the app should do to remove a view from the ViewPager.
    public void removeView (View defunctPage)
    {
        int pageIndex = pagerAdapter.removeView (pager, defunctPage);
        // You might want to choose what page to display, if the current page was "defunctPage".
        if (pageIndex == pagerAdapter.getCount())
            pageIndex--;
        pager.setCurrentItem (pageIndex);
    }

    //-----------------------------------------------------------------------------
    // Here's what the app should do to get the currently displayed page.
    public View getCurrentPage ()
    {
        return pagerAdapter.getView (pager.getCurrentItem());
    }

    //-----------------------------------------------------------------------------
    // Here's what the app should do to set the currently displayed page.  "pageToShow" must
    // currently be in the adapter, or this will crash.
    public void setCurrentPage (View pageToShow)
    {
        pager.setCurrentItem (pagerAdapter.getItemPosition (pageToShow), true);
    }

    public void addNewPage(MenuItem item){
        FrameLayout newPage = (FrameLayout) inflater.inflate (R.layout.empty_fragment, null);
        pagerAdapter.addView (newPage, 1);
        pagerAdapter.notifyDataSetChanged();
    }
}
