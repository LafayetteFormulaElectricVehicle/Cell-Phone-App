package com.cellvscada.lfev2017;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
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
import com.github.mikephil.charting.charts.LineChart;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SessionManager extends AppCompatActivity {

    private ViewPager pager = null;
    private MainPagerAdapter pagerAdapter = null;
    private LayoutInflater inflater;

    DataHandler finalHandler;
    JsonHandler finalJson;
    String dataUrl;
    String idHex;

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        dataUrl = "http://139.147.194.194:3000/dbquery/recent";
        finalHandler = new DataHandler();
        //finalJson = new JsonHandler(finalHandler, dataUrl, this);
        idHex = "MRPM";

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

    public void addRawDataView(String id){

        FrameLayout currentLayout = (FrameLayout) getCurrentPage();
        RawDataGenerator newGenerator = new RawDataGenerator(this, finalHandler.idToAll.get(id)[0]);

        newGenerator.adapterSetup(finalHandler.allSystems.get(id));

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

    public void addBarChartView(String id){
        FrameLayout currentLayout = (FrameLayout) getCurrentPage();

        BarChartGenerator newBar = new BarChartGenerator(this);
        newBar.barChartSetup();
        int i = 0;
        for(Map.Entry<String, String> entry : finalHandler.allSystems.get(id).entrySet()){

            newBar.insertYaxis(i, Float.parseFloat(entry.getValue()) );
            i++;
        }

        newBar.setYaxis("Values");
        newBar.setBarData();
        newBar.finalSetup();
        currentLayout.addView(newBar.getView());
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

    public void addBarParameters(){
        final AlertDialog alert = new AlertDialog.Builder(this).create();

        alert.setTitle("Bar Chart Parameters");
        alert.setMessage("Please set the parameters");

        ScrollView scrollView = new ScrollView(this);

        LinearLayout newLayout = new LinearLayout(this);
        newLayout.setOrientation(LinearLayout.VERTICAL);
        newLayout.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        final EditText xaxis = new EditText(this);
        xaxis.setHint("X-Axis for Chart");
        newLayout.addView(xaxis);

        final Spinner spinner = new Spinner(this);
        spinner.setAdapter(new HashMapAdapter(this, finalHandler.tagToId));
        newLayout.addView(spinner);


        DateAndTimeViewGenerator startGenerator = new DateAndTimeViewGenerator(this, "Start Date");
        newLayout.addView(startGenerator.getPicker());

        DateAndTimeViewGenerator endGenerator = new DateAndTimeViewGenerator(this, "End Date");
        newLayout.addView(endGenerator.getPicker());

        Button finished = new Button(this);
        finished.setHint("Create The Chart");
        finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBarChartView(xaxis.getText().toString().toUpperCase());
                String spinout = spinner.getSelectedItem().toString();
                Toast.makeText(context, spinout, Toast.LENGTH_SHORT).show();
                alert.dismiss();
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
        numberOfLines.setMinValue(0);
        numberOfLines.setHint("How many different sensors?");
        newLayout.addView(numberOfLines);

        DateAndTimeViewGenerator startGenerator = new DateAndTimeViewGenerator(this, "Start Date");
        newLayout.addView(startGenerator.getPicker());

        DateAndTimeViewGenerator endGenerator = new DateAndTimeViewGenerator(this, "End Date");
        newLayout.addView(endGenerator.getPicker());

        Button finished = new Button(this);
        finished.setHint("Select the Data");
        finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLineChartDataSets(numberOfLines.getValue());
                alert.dismiss();
            }
        });
        newLayout.addView(finished);

        scrollView.addView(newLayout);
        alert.setView(scrollView);
        alert.show();
    }

    public void addLineChartDataSets(final int numberOfLines){
        final AlertDialog alert = new AlertDialog.Builder(this).create();

        alert.setTitle("Line Chart Parameters");
        alert.setMessage("Please set the parameters");

        ScrollView scrollView = new ScrollView(this);

        LinearLayout newLayout = new LinearLayout(this);
        newLayout.setOrientation(LinearLayout.VERTICAL);
        newLayout.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        final Spinner spinner = new Spinner(this);
        spinner.setAdapter(new HashMapAdapter(this, finalHandler.tagToId));
        newLayout.addView(spinner);

        final EditText[] lines = new EditText[numberOfLines];

        for(int i = 0; i < numberOfLines; i++) {
            final EditText newLine = new EditText(this);
            newLine.setHint("Which Value to show:" + (i + 1));
            lines[i] = newLine;
            newLayout.addView(newLine);
        }

        Button finished = new Button(this);
        finished.setHint("Create The Chart");
        finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] lineIDs = new String[numberOfLines];

                for(int i = 0; i < Array.getLength(lines); i++){
                    lineIDs[i] = lines[i].getText().toString().toUpperCase();
                }

                addLineChartView(lineIDs);

                alert.dismiss();
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
        alert.setMessage("Please set the parameters");

        ScrollView scrollView = new ScrollView(this);

        LinearLayout newLayout = new LinearLayout(this);
        newLayout.setOrientation(LinearLayout.VERTICAL);
        newLayout.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        final EditText xaxis = new EditText(this);
        xaxis.setHint("Which Value to show");
        newLayout.addView(xaxis);

        final Spinner spinner = new Spinner(this);
        spinner.setAdapter(new HashMapAdapter(this, finalHandler.tagToId));
        newLayout.addView(spinner);

        DateAndTimeViewGenerator startGenerator = new DateAndTimeViewGenerator(this, "Start Date");
        newLayout.addView(startGenerator.getPicker());

        DateAndTimeViewGenerator endGenerator = new DateAndTimeViewGenerator(this, "End Date");
        newLayout.addView(endGenerator.getPicker());

        Button finished = new Button(this);
        finished.setHint("Create The Data View");
        finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRawDataView(xaxis.getText().toString().toUpperCase());
                alert.dismiss();
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

        alert.setTitle("ID");
        alert.setMessage("Please set new ID");
        alert.setView(newLayout);

        alert.show();
    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.action_TestFragment:
                addFragmentOptions();
                return true;
            case R.id.action_UpdateData:
                updateData();
                return true;
            case R.id.action_UpdateURLandID:
                updateURL();
                return true;
            case R.id.action_addView:
                    addView();
//                if(!finalHandler.allSystems.isEmpty()) {
//                    addView();
//                }else{
//                    Toast.makeText(this, "Please update the data first", Toast.LENGTH_SHORT).show();
//                }
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
    public void updateData() {

        //finalJson.getDataFromURL();

        finalJson = new JsonHandler(finalHandler, dataUrl, this);

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

}
