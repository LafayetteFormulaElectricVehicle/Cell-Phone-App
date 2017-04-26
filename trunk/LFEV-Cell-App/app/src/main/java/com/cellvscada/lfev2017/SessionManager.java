package com.cellvscada.lfev2017;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.os.HandlerThread;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cellvscada.lfev2017.Adapters.HashMapAdapter;
import com.cellvscada.lfev2017.Adapters.MainPagerAdapter;
import com.cellvscada.lfev2017.DataHandlers.DataHandler;
import com.cellvscada.lfev2017.DataHandlers.JsonHandler;
import com.cellvscada.lfev2017.Tools.DateAndTimeViewGenerator;
import com.cellvscada.lfev2017.Tools.EditTextNumeric;
import com.cellvscada.lfev2017.Tools.MenuFragment;
import com.cellvscada.lfev2017.ViewGenerators.BarChartGenerator;
import com.cellvscada.lfev2017.ViewGenerators.GaugeGenerator;
import com.cellvscada.lfev2017.ViewGenerators.LineChartGenerator;
import com.cellvscada.lfev2017.ViewGenerators.RawDataGenerator;
import com.cellvscada.lfev2017.ViewGenerators.ViewGenerator;
import com.github.mikephil.charting.charts.LineChart;

import java.lang.reflect.Array;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import android.os.Handler;

public class SessionManager extends AppCompatActivity {

    private ViewPager pager = null;
    private MainPagerAdapter pagerAdapter = null;
    private LayoutInflater inflater;

    DataHandler finalHandler;
    DataHandler constantHandler;
    JsonHandler finalJson;
    JsonHandler constantJson;
    String dataUrl;
    String dataUrlRecent;
    String idHex;

    Context context = this;

    ArrayList<HashMapAdapter> allAdapters;
    ArrayList<Map<String, String>> allMaps;
    ArrayList<BarChartGenerator> allBars;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            keepUpdating(dataUrlRecent);
            if(!allAdapters.isEmpty()) {
                for(int i = 0; i < allMaps.size(); i++) {
                    allAdapters.get(i).setData(allMaps.get(i));
                    allAdapters.get(i).notifyDataSetChanged();
                }
            }
            if(!allBars.isEmpty()){
                updateBarChartData();
            }
            handler.postDelayed(this, delay);
        }
    };
    int delay = 5000; //milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        dataUrl = "http://139.147.205.136:3000/dbquery";
        dataUrlRecent = dataUrl + "/recent";

        finalHandler = new DataHandler();
        constantHandler = new DataHandler();
        allAdapters = new ArrayList<>();
        allMaps = new ArrayList<>();
        allBars = new ArrayList<>();

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
        pagerAdapter.addView (newPage);
        TextView currentTitle = (TextView) newPage.findViewById(R.id.pageTitle);
        currentTitle.setText("Page " + pagerAdapter.getSize());
        pagerAdapter.notifyDataSetChanged();
    }

    public void addRawDataView(String id){

        FrameLayout currentLayout = (FrameLayout) getCurrentPage();
        RawDataGenerator newGenerator = new RawDataGenerator(this, finalHandler.idToAll.get(id)[0]);

        newGenerator.adapterSetup(finalHandler.allSystems.get(id));
        allAdapters.add(newGenerator.getAdapter());
        allMaps.add(finalHandler.allSystems.get(id));

        currentLayout.addView(newGenerator.getView());

    }

    public void addRawDataView(String id, String startDate, String endDate){

        FrameLayout currentLayout = (FrameLayout) getCurrentPage();
        RawDataGenerator newGenerator = new RawDataGenerator(this, finalHandler.idToAll.get(id)[0]);

        newGenerator.adapterSetup(finalHandler.allSystems.get(id).subMap(startDate,endDate));
        allAdapters.add(newGenerator.getAdapter());

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

    public void updateBarChartData() {

        int i = 0;

        for (BarChartGenerator each : allBars){
            //int i = 0;

            if(each.getAddingUpdate()) {
                for (Map.Entry<String, String> entry : finalHandler.allSystems.get(each.getId()).entrySet()) {
                    i++;
                    each.addDataToExisting(i, Float.parseFloat(entry.getValue()), entry.getKey());
                }
                each.addDataToExistingFinish();
            }else if(each.getReplacingUpdate()){
                each.setupYaxis();
                for (Map.Entry<String, String> entry : finalHandler.allSystems.get(each.getId()).entrySet()) {
                    i = each.getSize();
                    each.insertYaxis(i, Float.parseFloat(entry.getValue()));
                }
                each.addDataSet(finalHandler.idToAll.get(each.getId())[0]);
            }
        }

    }

    public void addBarChartView(String id, boolean addingUpdate, boolean replacingUpdate){
        FrameLayout currentLayout = (FrameLayout) getCurrentPage();

        BarChartGenerator newBar = new BarChartGenerator(this, id, addingUpdate, replacingUpdate);
        newBar.barChartSetup();
        int i = 0;
        for(Map.Entry<String, String> entry : finalHandler.allSystems.get(id).entrySet()){
            //newBar.insertYaxis(i, Float.parseFloat(entry.getValue()) );
            newBar.insertYaxisTest(i, Float.parseFloat(entry.getValue()), entry.getKey() );
            i++;
        }

        newBar.setYaxisTest(finalHandler.idToAll.get(id)[0]);
        newBar.setBarData();
        newBar.finalSetup();
        currentLayout.addView(newBar.getView());

        allBars.add(newBar);
    }

    public void addBarChartView(String id, String startDate, String endDate){
        FrameLayout currentLayout = (FrameLayout) getCurrentPage();

        SortedMap<String, String> dataMap = finalHandler.allSystems.get(id).subMap(startDate, endDate);

        BarChartGenerator newBar = new BarChartGenerator(this, id, false, false);
        newBar.barChartSetup();
        int i = 0;
        for(Map.Entry<String, String> entry : dataMap.entrySet()){

            //newBar.insertYaxis(i, Float.parseFloat(entry.getValue()) );
            newBar.insertYaxisTest(i, Float.parseFloat(entry.getValue()), entry.getKey() );
            i++;
        }

        newBar.setYaxisTest(finalHandler.idToAll.get(id)[0]);
        newBar.setBarData();
        newBar.finalSetup();
        currentLayout.addView(newBar.getView());

        allBars.add(newBar);
    }

    public void addLineChartView(String[] idList){
        FrameLayout currentLayout = (FrameLayout) getCurrentPage();

        LineChartGenerator newLineChart = new LineChartGenerator(this);
        newLineChart.lineChartSetup();

        ArrayList<Float> newLine = new ArrayList<>();

        Random rnd = new Random();
        int color;

        for(int i = 0; i < Array.getLength(idList); i++){

            for(Map.Entry<String, String> entry : finalHandler.allSystems.get(idList[i]).entrySet()){
                newLine.add(Float.parseFloat(entry.getValue()));
            }
            color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            newLineChart.addLine(newLine, idList[i] , (color));
            newLine = new ArrayList<>();
        }

        newLineChart.setYaxis("Values");
        newLineChart.multipleLineSetData();
        newLineChart.finalSetup();
        currentLayout.addView(newLineChart.getView());
    }

    public void addLineChartView(String[] idList, String startDate, String endDate){
        FrameLayout currentLayout = (FrameLayout) getCurrentPage();

        LineChartGenerator newLineChart = new LineChartGenerator(this);
        newLineChart.lineChartSetup();

        ArrayList<Float> newLine = new ArrayList<>();

        Random rnd = new Random();
        int color;

        for(int i = 0; i < Array.getLength(idList); i++){

            for(Map.Entry<String, String> entry : finalHandler.allSystems.get(idList[i]).subMap(startDate,endDate).entrySet()){
                newLine.add(Float.parseFloat(entry.getValue()));
            }
            color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            newLineChart.addLine(newLine, idList[i] , (color));
            newLine = new ArrayList<>();
        }

        newLineChart.setYaxis("Values");
        newLineChart.multipleLineSetData();
        newLineChart.finalSetup();
        currentLayout.addView(newLineChart.getView());
    }

    public void addBarParameters(){
        final AlertDialog alert = new AlertDialog.Builder(this).create();

        alert.setTitle("Bar Chart Parameters");
        alert.setMessage("Please set the parameters");

        ScrollView scrollView = new ScrollView(this);

        LinearLayout newLayout = new LinearLayout(this);
        newLayout.setOrientation(LinearLayout.VERTICAL);
        newLayout.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        final AutoCompleteTextView xaxis = new AutoCompleteTextView(this);
        xaxis.setHint("Which Value to show");
        xaxis.setAdapter(new ArrayAdapter<String>(this, R.layout.my_array_adapter_item, android.R.id.text1, finalHandler.IDs));
        newLayout.addView(xaxis);

        final Spinner spinner = new Spinner(this);
        //spinner.setAdapter(new HashMapAdapter(this, finalHandler.tagToId));
        spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.my_array_adapter_item, android.R.id.text1, finalHandler.tags));
        newLayout.addView(spinner);

        final DateAndTimeViewGenerator startGenerator = new DateAndTimeViewGenerator(this, "Start Date");
        newLayout.addView(startGenerator.getPicker());

        final DateAndTimeViewGenerator endGenerator = new DateAndTimeViewGenerator(this, "End Date");
        newLayout.addView(endGenerator.getPicker());

        startGenerator.setNextDownFocusID(endGenerator.getFocusID());

        final CheckBox addingCheck = new CheckBox(this);
        addingCheck.setHint("Update by adding on top of existing data");
        newLayout.addView(addingCheck);

        final CheckBox replacingCheck = new CheckBox(this);
        replacingCheck.setHint("Update by replacing the existing data");
        newLayout.addView(replacingCheck);

        replacingCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(replacingCheck.isChecked())
                    addingCheck.setChecked(false);
            }
        });
        addingCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(addingCheck.isChecked())
                    replacingCheck.setChecked(false);
            }
        });

        xaxis.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER){
                    try {
                        if (startGenerator.isNull() || endGenerator.isNull()) {
                            if (TextUtils.isEmpty(xaxis.getText())) {
                                addBarChartView(finalHandler.tagToId.get(spinner.getSelectedItem().toString()), addingCheck.isChecked(), replacingCheck.isChecked());
                            } else {
                                addBarChartView(xaxis.getText().toString().toUpperCase(), addingCheck.isChecked(), replacingCheck.isChecked());
                            }
                        } else {
                            if (TextUtils.isEmpty(xaxis.getText())) {

                                String tempUrl = createURL(startGenerator.getDateHttp(), endGenerator.getDateHttp());
                                updateDataWithWait(tempUrl);

                                addBarChartView(finalHandler.tagToId.get(spinner.getSelectedItem().toString()), startGenerator.getDate(), endGenerator.getDate());
                            } else if (finalHandler.idToAll.containsKey(xaxis.getText().toString().toUpperCase())) {

                                String tempUrl = createURL(startGenerator.getDateHttp(), endGenerator.getDateHttp());
                                updateDataWithWait(tempUrl);

                                addBarChartView(xaxis.getText().toString().toUpperCase(), startGenerator.getDate(), endGenerator.getDate());
                            }
                        }

                        TextView titleHelp = (TextView) getCurrentPage().findViewById(R.id.helpTitle);
                        titleHelp.setVisibility(View.GONE);
                        alert.dismiss();
                    }catch (Exception e){
                        Toast.makeText(context, "ID entered is not available", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });

        Button finished = new Button(this);
        finished.setHint("Create The Chart");
        finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if(startGenerator.isNull() || endGenerator.isNull()){
//                    addBarChartView(xaxis.getText().toString().toUpperCase(), addingCheck.isChecked(), replacingCheck.isChecked());
//                }else{
//                    addBarChartView(xaxis.getText().toString().toUpperCase(), startGenerator.getDate(), endGenerator.getDate(), false, false);
//                }

                try {
                    if (startGenerator.isNull() || endGenerator.isNull()) {
                        if (TextUtils.isEmpty(xaxis.getText())) {
                            addBarChartView(finalHandler.tagToId.get(spinner.getSelectedItem().toString()), addingCheck.isChecked(), replacingCheck.isChecked());
                        } else {
                            addBarChartView(xaxis.getText().toString().toUpperCase(), addingCheck.isChecked(), replacingCheck.isChecked());
                        }
                    } else {
                        if (TextUtils.isEmpty(xaxis.getText())) {

                            String tempUrl = createURL(startGenerator.getDateHttp(), endGenerator.getDateHttp());
                            updateDataWithWait(tempUrl);

                            addBarChartView(finalHandler.tagToId.get(spinner.getSelectedItem().toString()), startGenerator.getDate(), endGenerator.getDate());
                        } else if (finalHandler.idToAll.containsKey(xaxis.getText().toString().toUpperCase())) {

                            String tempUrl = createURL(startGenerator.getDateHttp(), endGenerator.getDateHttp());
                            updateDataWithWait(tempUrl);

                            addBarChartView(xaxis.getText().toString().toUpperCase(), startGenerator.getDate(), endGenerator.getDate());
                        }
                    }

                    TextView titleHelp = (TextView) getCurrentPage().findViewById(R.id.helpTitle);
                    titleHelp.setVisibility(View.GONE);
                    alert.dismiss();
                }catch (Exception e){
                    Toast.makeText(context, "ID entered is not available", Toast.LENGTH_SHORT).show();
                }

            }
        });
        newLayout.addView(finished);

        scrollView.addView(newLayout);
        alert.setView(scrollView);
        alert.show();
    }

    public void addLineParameters(){
        final AlertDialog alert = new AlertDialog.Builder(this).create();

        alert.setTitle("Line Chart Parameters");
        alert.setMessage("Please set the parameters");

        ScrollView scrollView = new ScrollView(this);

        LinearLayout newLayout = new LinearLayout(this);
        newLayout.setOrientation(LinearLayout.VERTICAL);
        newLayout.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        final EditTextNumeric numberOfLines = new EditTextNumeric(this);
        numberOfLines.setMaxLength(1);
        numberOfLines.setMinValue(1);
        numberOfLines.setHint("How many different sensors?");
        newLayout.addView(numberOfLines);

        final DateAndTimeViewGenerator startGenerator = new DateAndTimeViewGenerator(this, "Start Date");
        newLayout.addView(startGenerator.getPicker());

        final DateAndTimeViewGenerator endGenerator = new DateAndTimeViewGenerator(this, "End Date");
        newLayout.addView(endGenerator.getPicker());

        startGenerator.setNextDownFocusID(endGenerator.getFocusID());

        numberOfLines.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER){
                    if(!TextUtils.isEmpty(numberOfLines.getText())) {
                        if (startGenerator.isNull() || endGenerator.isNull()) {
                            addLineChartDataSets(numberOfLines.getValue(), null, null, null, null);
                        } else {
                            addLineChartDataSets(
                                    numberOfLines.getValue(),
                                    startGenerator.getDate(),
                                    endGenerator.getDate(),
                                    startGenerator.getDateHttp(),
                                    endGenerator.getDateHttp()
                            );
                        }
                        alert.dismiss();
                    }else{
                        Toast.makeText(context, "Please enter a number", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });

        Button finished = new Button(this);
        finished.setHint("Select the Data");
        finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(numberOfLines.getText())) {
                    if (startGenerator.isNull() || endGenerator.isNull()) {
                        addLineChartDataSets(numberOfLines.getValue(), null, null, null, null);
                    } else {
                        addLineChartDataSets(
                                numberOfLines.getValue(),
                                startGenerator.getDate(),
                                endGenerator.getDate(),
                                startGenerator.getDateHttp(),
                                endGenerator.getDateHttp()
                        );                    }
                    //addLineChartDataSets(numberOfLines.getValue());
                    alert.dismiss();
                }else{
                    Toast.makeText(context, "Please enter a number", Toast.LENGTH_SHORT).show();
                }
            }
        });
        newLayout.addView(finished);

        scrollView.addView(newLayout);
        alert.setView(scrollView);
        alert.show();
    }

    public void addLineChartDataSets(final int numberOfLines, final String startDate, final String endDate, final String startDateHttp, final String endDateHttp){
        final AlertDialog alert = new AlertDialog.Builder(this).create();

        alert.setTitle("Line Chart Parameters");
        alert.setMessage("Please set the parameters");

        ScrollView scrollView = new ScrollView(this);

        final LinearLayout newLayout = new LinearLayout(this);
        newLayout.setOrientation(LinearLayout.VERTICAL);
        newLayout.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        final TextView spinnerTitle = new TextView(this);
        spinnerTitle.setText("Available Systems Drop-Down List");
        newLayout.addView(spinnerTitle);

        final Spinner spinner = new Spinner(this);
        spinner.setAdapter(new HashMapAdapter(this, finalHandler.tagToId));
        newLayout.addView(spinner);

        final CheckBox addingCheck = new CheckBox(this);
        addingCheck.setHint("Update by adding on top of existing data");
        newLayout.addView(addingCheck);

        final CheckBox replacingCheck = new CheckBox(this);
        replacingCheck.setHint("Update by replacing the existing data");
        newLayout.addView(replacingCheck);

        replacingCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(replacingCheck.isChecked())
                    addingCheck.setChecked(false);
            }
        });
        addingCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(addingCheck.isChecked())
                    replacingCheck.setChecked(false);
            }
        });

        final EditText[] lines = new EditText[numberOfLines];

        for(int i = 0; i < numberOfLines; i++) {
//            final EditText newLine = new EditText(this);
//            newLine.setHint("Which Value to show:" + (i + 1));
//            lines[i] = newLine;
//            newLayout.addView(newLine);
            final AutoCompleteTextView newLine = new AutoCompleteTextView(this);
            newLine.setHint("Which Value to show" + (i+1));
            newLine.setAdapter(new ArrayAdapter<String>(this, R.layout.my_array_adapter_item, android.R.id.text1, finalHandler.IDs));
            lines[i] = newLine;
//            newLine.setId(i);
//            newLine.setNextFocusDownId();

            if(i < numberOfLines - 1) {
                final int finalI = i;
                newLine.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                            //newLine.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.));
                            lines[finalI +1].requestFocus();
                            return true;
                        }
                        return false;
                    }
                });
            }else{
                newLine.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_ENTER) {
                            String[] lineIDs = new String[numberOfLines];
                            try {
                                for (int i = 0; i < Array.getLength(lines); i++) {
                                    lineIDs[i] = lines[i].getText().toString().toUpperCase();
                                }

                                if (startDate == null || endDate == null) {
                                    addLineChartView(lineIDs);
                                } else {

                                    String tempUrl = createURL(startDateHttp, endDateHttp);
                                    updateDataWithWait(tempUrl);

                                    addLineChartView(lineIDs, startDate, endDate);
                                }

                                TextView titleHelp = (TextView) getCurrentPage().findViewById(R.id.helpTitle);
                                titleHelp.setVisibility(View.GONE);
                                alert.dismiss();
                            }catch (Exception e){
                                Toast.makeText(context, "One or more of ID(s) are not available", Toast.LENGTH_SHORT).show();
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }

            newLayout.addView(newLine);
        }


        Button finished = new Button(this);
        finished.setHint("Create The Chart");
        finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] lineIDs = new String[numberOfLines];
                try {
                    for (int i = 0; i < Array.getLength(lines); i++) {
                        lineIDs[i] = lines[i].getText().toString().toUpperCase();
                    }

                    if (startDate == null || endDate == null) {
                        addLineChartView(lineIDs);
                    } else {

                        String tempUrl = createURL(startDateHttp, endDateHttp);
                        updateDataWithWait(tempUrl);

                        addLineChartView(lineIDs, startDate, endDate);
                    }

                    TextView titleHelp = (TextView) getCurrentPage().findViewById(R.id.helpTitle);
                    titleHelp.setVisibility(View.GONE);
                    alert.dismiss();
                }catch (Exception e){
                    Toast.makeText(context, "One or more of ID(s) are not available", Toast.LENGTH_SHORT).show();
                }
            }
        });
        newLayout.addView(finished);

        scrollView.addView(newLayout);
        alert.setView(scrollView);
        alert.show();
    }

    public void addRawParameters(){
        final AlertDialog alert = new AlertDialog.Builder(this).create();

        alert.setTitle("Raw Data Parameters");
        alert.setMessage("Please either type the id of the sensor or choose the tag name from the drop-down menu");

        ScrollView scrollView = new ScrollView(this);

        LinearLayout newLayout = new LinearLayout(this);
        newLayout.setOrientation(LinearLayout.VERTICAL);
        newLayout.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        final AutoCompleteTextView xaxis = new AutoCompleteTextView(this);
        xaxis.setHint("Which Value to show");
        xaxis.setAdapter(new ArrayAdapter<String>(this, R.layout.my_array_adapter_item, android.R.id.text1, finalHandler.IDs));
        //xaxis.onKeyDown(KeyEvent.KEYCODE_ENTER, event)
        newLayout.addView(xaxis);

        final Spinner spinner = new Spinner(this);
        //spinner.setAdapter(new HashMapAdapter(this, finalHandler.tagToId));
        spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.my_array_adapter_item, android.R.id.text1, finalHandler.tags));
        newLayout.addView(spinner);

        final DateAndTimeViewGenerator startGenerator = new DateAndTimeViewGenerator(this, "Start Date");
        newLayout.addView(startGenerator.getPicker());

        final DateAndTimeViewGenerator endGenerator = new DateAndTimeViewGenerator(this, "End Date");
        newLayout.addView(endGenerator.getPicker());

        startGenerator.setNextDownFocusID(endGenerator.getFocusID());

        xaxis.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER){
                    try {
                        if (startGenerator.isNull() || endGenerator.isNull()) {
                            if (TextUtils.isEmpty(xaxis.getText())) {
                                addRawDataView(finalHandler.tagToId.get(spinner.getSelectedItem().toString()));
                            } else {
                                addRawDataView(xaxis.getText().toString().toUpperCase());
                            }
                        } else {

                            String tempUrl = createURL(startGenerator.getDateHttp(), endGenerator.getDateHttp());
                            updateDataWithWait(tempUrl);

                            if (TextUtils.isEmpty(xaxis.getText())) {
                                addRawDataView(finalHandler.tagToId.get(spinner.getSelectedItem().toString()), startGenerator.getDate(), endGenerator.getDate());
                            } else {
                                addRawDataView(xaxis.getText().toString().toUpperCase(), startGenerator.getDate(), endGenerator.getDate());
                            }
                        }

                        TextView titleHelp = (TextView) getCurrentPage().findViewById(R.id.helpTitle);
                        titleHelp.setVisibility(View.GONE);
                        alert.dismiss();
                    }catch (Exception e){
                        Toast.makeText(context, "ID entered is not available", Toast.LENGTH_SHORT).show();
                        //xaxis.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
                    }
                    return true;
                }
                return false;
            }
        });

        Button finished = new Button(this);
        finished.setHint("Create The Data View");
        finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (startGenerator.isNull() || endGenerator.isNull()) {
                        if (TextUtils.isEmpty(xaxis.getText())) {
                            addRawDataView(finalHandler.tagToId.get(spinner.getSelectedItem().toString()));
                        } else {
                            addRawDataView(xaxis.getText().toString().toUpperCase());
                        }
                    } else {

                        String tempUrl = createURL(startGenerator.getDateHttp(), endGenerator.getDateHttp());
                        updateDataWithWait(tempUrl);

                        if (TextUtils.isEmpty(xaxis.getText())) {
                            addRawDataView(finalHandler.tagToId.get(spinner.getSelectedItem().toString()), startGenerator.getDate(), endGenerator.getDate());
                        } else {
                            addRawDataView(xaxis.getText().toString().toUpperCase(), startGenerator.getDate(), endGenerator.getDate());
                        }
                    }

                    TextView titleHelp = (TextView) getCurrentPage().findViewById(R.id.helpTitle);
                    titleHelp.setVisibility(View.GONE);
                    alert.dismiss();
                }catch (Exception e){
                    Toast.makeText(context, "ID entered is not available", Toast.LENGTH_SHORT).show();
                }
            }
        });
        newLayout.addView(finished);

        scrollView.addView(newLayout);
        alert.setView(scrollView);
        alert.show();
    }

    public void addView(){
        final AlertDialog alert = new AlertDialog.Builder(this).create();

        LinearLayout newLayout = new LinearLayout(this);
        newLayout.setOrientation(LinearLayout.VERTICAL);
        newLayout.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        final Button addGaugeView = new Button(this);
        addGaugeView.setHint("Add Gauge");
        addGaugeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGaugeViewTest();
                alert.dismiss();
            }
        });
        newLayout.addView(addGaugeView);

        Button addLineView = new Button(this);
        addLineView.setHint("Add Line Chart");
        addLineView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLineParameters();
                alert.dismiss();
            }
        });
        newLayout.addView(addLineView);

        Button addBarView = new Button(this);
        addBarView.setHint("Add Bar Chart");
        addBarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBarParameters();
                alert.dismiss();
            }
        });
        newLayout.addView(addBarView);


        //PLEASE CHANGE THE NAME OF THE ADDVIEWTEST
        Button addRawView = new Button(this);
        addRawView.setHint("Add Raw Data");
        addRawView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRawParameters();
                alert.dismiss();
            }
        });
        newLayout.addView(addRawView);

        //AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Add View");
        alert.setMessage("Please choose a new display type to add");
        alert.setView(newLayout);

        alert.show();
    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.action_TestFragment:
                addFragmentOptions();
                return true;
            case R.id.action_UpdateURLandID:
                updateDataDialog();
                return true;
            case R.id.action_StopUpdate:
                stopUpdating();
                return true;
            case R.id.action_addView:
                    //addView();
                if(!finalHandler.allSystems.isEmpty()) {
                    addView();
                }else {
                    Toast.makeText(this, "Please update the data first", Toast.LENGTH_SHORT).show();
                    //addView();
                }
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

    //DATABASE STUFF
    public void updateDataConstantly() {

        Toast.makeText(context, "Please wait, pulling data from: " + dataUrl, Toast.LENGTH_SHORT).show();

        keepUpdating(dataUrlRecent);
//        finalJson = new JsonHandler(finalHandler, dataUrl, this);
//        finalJson.execute();

        handler.postDelayed(runnable, delay);
    }

    public void updateDataWithWait(){
        Toast.makeText(context, "Please wait, pulling data from: " + dataUrl, Toast.LENGTH_SHORT).show();

        finalJson = new JsonHandler(finalHandler, dataUrl, this);

        try {
            finalJson.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("Error Session Manager", e.getMessage());
        }
    }

    //CONSTANT HANDLER IDEA
    // TODO INVESTIGATE THE IDEA OF HAVING A SECOND HANDLER THAT CAN PASS DATA TO FIRST AND VIEWS, INSTEAD OF UPDATING
    public void keepUpdating(String dataUrlRecent){
        finalJson = new JsonHandler(finalHandler, dataUrlRecent, this, false, true);
        finalJson.execute();
    }

    public void stopUpdating(){
        handler.removeCallbacks(runnable);
    }

    public void updateData(String dataUrl) {

        finalJson = new JsonHandler(finalHandler, dataUrl, this, true);
        finalJson.execute();

        Toast.makeText(context, "Please wait, pulling data from: " + dataUrl, Toast.LENGTH_SHORT).show();

    }

    public void updateDataWithWait(String dataUrl){
        Toast.makeText(context, "Please wait, pulling data from: " + dataUrl, Toast.LENGTH_SHORT).show();

        finalJson = new JsonHandler(finalHandler, dataUrl, context, false, true);
        //finalJson.execute();

        try {
            finalJson.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("Error Session Manager", e.getMessage());
        }
    }

    public void updateDataDialog(){
        final AlertDialog alert = new AlertDialog.Builder(this).create();

        alert.setTitle("Update Data");
        alert.setMessage("Please set up data update settings");

        ScrollView scrollView = new ScrollView(this);

        LinearLayout newLayout = new LinearLayout(this);
        newLayout.setOrientation(LinearLayout.VERTICAL);
        newLayout.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        final EditText dataUrlText = new EditText(this);
        dataUrlText.setHint("Please enter URL");
        newLayout.addView(dataUrlText);

        final EditTextNumeric delayInput = new EditTextNumeric(this);
        delayInput.setMaxLength(4);
        delayInput.setMinValue(5);
        delayInput.setVisibility(View.GONE);
        delayInput.setHint("How often(seconds)?");

        final CheckBox addRecent = new CheckBox(this);
        addRecent.setHint("Constant Update");
        newLayout.addView(addRecent);
        newLayout.addView(delayInput);

        final DateAndTimeViewGenerator startGenerator = new DateAndTimeViewGenerator(this, "Start Date");
        newLayout.addView(startGenerator.getPicker());

        final DateAndTimeViewGenerator endGenerator = new DateAndTimeViewGenerator(this, "End Date");
        newLayout.addView(endGenerator.getPicker());

        startGenerator.setNextDownFocusID(endGenerator.getFocusID());

        dataUrlText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(keyCode == KeyEvent.KEYCODE_ENTER){
                    if(addRecent.isChecked() && event.getAction() == KeyEvent.ACTION_DOWN){
                        delayInput.requestFocus();
                    }else{
                        dataUrl = parseDataUrl(dataUrlText.getText().toString());
                        dataUrlRecent = dataUrl + "/recent";

                        if(startGenerator.isNull() || endGenerator.isNull()){
                            updateData(dataUrlRecent);
                        }else{
                            updateData(createURL(startGenerator.getDateHttp(), endGenerator.getDateHttp()));
                        }

                        //addLineChartDataSets(numberOfLines.getValue());
                        alert.dismiss();
                    }
                    return true;
                }

                return false;
            }
        });

        final Button constantly = new Button(this);
        constantly.setHint("Update Data Constantly");
        constantly.setVisibility(View.GONE);
        constantly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dataUrl = parseDataUrl(dataUrlText.getText().toString());
                dataUrlRecent = dataUrl + "/recent";
                delay = delayInput.getValue() * 1000;

                updateDataConstantly();

                alert.dismiss();
            }
        });
        newLayout.addView(constantly);

        final Button once = new Button(this);
        once.setHint("Update Data Once (default is /recent)");
        once.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dataUrl = parseDataUrl(dataUrlText.getText().toString());
                dataUrlRecent = dataUrl + "/recent";

                if(startGenerator.isNull() || endGenerator.isNull()){
                    updateData(dataUrlRecent);
                }else{
                    updateData(createURL(startGenerator.getDateHttp(), endGenerator.getDateHttp()));
                }

                //addLineChartDataSets(numberOfLines.getValue());
                alert.dismiss();
            }
        });
        newLayout.addView(once);

        addRecent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(addRecent.isChecked()){
                    delayInput.setVisibility(View.VISIBLE);
                    constantly.setVisibility(View.VISIBLE);

                    once.setVisibility(View.GONE);
                    startGenerator.setVisibility(View.GONE);
                    endGenerator.setVisibility(View.GONE);
                }else{
                    delayInput.setVisibility(View.GONE);
                    constantly.setVisibility(View.GONE);

                    once.setVisibility(View.VISIBLE);
                    startGenerator.setVisibility(View.VISIBLE);
                    endGenerator.setVisibility(View.VISIBLE);
                }
            }
        });

        scrollView.addView(newLayout);
        alert.setView(scrollView);
        alert.show();
    }

    //date time update to current type
    public String createURL(String startDateHttp, String endDateHttp){

        //dataUrl?startDate=2017-04-11%2022&3A40%3A00&endDate=2017-04-11%2023%3A00%3A00
        return dataUrl + "?startDate=" + startDateHttp + "&endDate=" + endDateHttp;
    }

    public void createURL(String id, String startDateHttp, String endDateHttp){

    }

    public void createURL(String id, String system, String startDateHttp, String endDateHttp){

    }

    public EditText[] createAlert(String[] fieldNames, LinearLayout newLayout){
        int size = Array.getLength(fieldNames);
        EditText[] textFields = new EditText[size];
        for(int i = 0; i < size; i++){
            EditText newText = new EditText(this);
            newText.setHint(fieldNames[i]);
            textFields[i] = newText;
            newLayout.addView(newText);
        }

        return textFields;
    }

    public void updateURL(){
        LinearLayout newLayout = new LinearLayout(this);
        newLayout.setOrientation(LinearLayout.VERTICAL);
        newLayout.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        String[] fields = new String[1];
        fields[0] = "URL";
        final EditText[] textFields = createAlert(fields, newLayout);

        final CheckBox addRecent = new CheckBox(this);
        addRecent.setHint("Add /recent");

        newLayout.addView(addRecent);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("URL");
        alert.setMessage("Please set new URL");
        alert.setView(newLayout);

        // Set an EditText view to get user input
        //final LinearLayout inputLayout = (LinearLayout) findViewById(R.id.UserInputXY);
        final EditText idInput = textFields[0];
        //final EditText x_input = new EditText(this);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if(addRecent.isChecked()){
                    dataUrl = "http://" + textFields[0].getText().toString() + ":3000/dbquery/recent";
                }else{
                    dataUrl = "http://" + textFields[0].getText().toString() + ":3000/dbquery";
                    dataUrlRecent = dataUrl + "/recent";
                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }

    public String parseDataUrl(String url){
        return "http://" + url + ":3000/dbquery";
    }


}
