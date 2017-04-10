package com.cellvscada.lfev2017;

import android.content.DialogInterface;
import android.graphics.Color;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.cellvscada.lfev2017.Adapters.MainPagerAdapter;
import com.cellvscada.lfev2017.Tools.MenuFragment;
import com.cellvscada.lfev2017.ViewGenerators.BarChartGenerator;
import com.cellvscada.lfev2017.ViewGenerators.GaugeGenerator;
import com.cellvscada.lfev2017.ViewGenerators.LineChartGenerator;
import com.cellvscada.lfev2017.ViewGenerators.RawDataGenerator;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;
import java.util.HashMap;

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

    public void addViewTest(){

        FrameLayout currentLayout = (FrameLayout) getCurrentPage();

        HashMap<String, String> list = new HashMap<>();
        list.put("04/03/2017 12:59", "The webserver has started running.");
        list.put("04/03/2017 17:58", "A device has connected to the webserver.");
        list.put("04/03/2017 17:57", "A device requested data from the server.");
        list.put("04/03/2017 17:56", "A sudden shutdown has been experienced.");
        //list.put("04/03/2017 17:55", "I like unicorns.");
        //list.put("04/03/2017 17:54", "1801");

        RawDataGenerator newGenerator = new RawDataGenerator(this);
        newGenerator.adapterSetup(list);
        currentLayout.addView(newGenerator.getView());

    }

    public void addGaugeViewTest(){
        FrameLayout currentLayout = (FrameLayout) getCurrentPage();
        GaugeGenerator newGauge = new GaugeGenerator(this);
        currentLayout.addView(newGauge.getView());
    }

    public void addFragmentOptions(){
        FrameLayout currentLayout = (FrameLayout) getCurrentPage();
        currentLayout.setId(R.id.fragmentTest1);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        MenuFragment fragment = new MenuFragment();
        fragmentTransaction.add(R.id.fragmentTest1, fragment);
        fragmentTransaction.commit();
    }

    public void addBarChartView(){
        FrameLayout currentLayout = (FrameLayout) getCurrentPage();

        BarChartGenerator newBar = new BarChartGenerator(this);
        newBar.barChartSetup();
        for(int i = 0; i < 6; i++){
            newBar.insertYaxis(i, 80-i);
        }
        newBar.setYaxis("Values");
        newBar.setBarData();
        newBar.finalSetup();
        currentLayout.addView(newBar.getView());
    }

    public void addLineChartView(){
        FrameLayout currentLayout = (FrameLayout) getCurrentPage();

        LineChartGenerator newLineChart = new LineChartGenerator(this);
        newLineChart.lineChartSetup();

        ArrayList<Float> newLine = new ArrayList<>();

        for(float i = 0; i < 6; i++){
            for(float j = 0; j < 6; j++) {
                newLine.add(80 - j - i);
            }
            newLineChart.addLine(newLine, "Line" + i, (int) (Color.BLACK + (i)*12345));
            newLine = new ArrayList<>();
        }

        newLineChart.setYaxis("Values");
        newLineChart.multipleLineSetData();
        newLineChart.finalSetup();
        currentLayout.addView(newLineChart.getView());
    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.action_GaugeView:
                addGaugeViewTest();
                return true;
            case R.id.action_RawView:
                addViewTest();
                return true;
            case R.id.action_BarChartView:
                addBarChartView();
                return true;
            case R.id.action_LineChartView:
                addLineChartView();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateChartSize(MenuItem item){

        android.view.LayoutInflater inflater = getLayoutInflater();
        View inputLayout = inflater.inflate(R.layout.alert_view_size, null);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Resize");
        alert.setMessage("Choose new sizes in dp");
        alert.setView(inputLayout);

        // Set an EditText view to get user input
        //final LinearLayout inputLayout = (LinearLayout) findViewById(R.id.UserInputXY);
        final EditText x_input = (EditText) inputLayout.findViewById(R.id.x_input);
        final EditText y_input = (EditText) inputLayout.findViewById(R.id.y_input);
        //final EditText x_input = new EditText(this);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //linechart.setLayoutParams(new RelativeLayout.LayoutParams(Integer.parseInt(x_input.getText().toString()), Integer.parseInt(y_input.getText().toString())));
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }

}
