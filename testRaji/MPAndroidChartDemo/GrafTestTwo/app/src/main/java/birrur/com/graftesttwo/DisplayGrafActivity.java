package birrur.com.graftesttwo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class DisplayGrafActivity extends AppCompatActivity {

    private BarChart barGraf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_graf);

        barGraf = (BarChart) findViewById(R.id.bargraph);

        ArrayList<BarEntry> values = new ArrayList<>();
        values.add(new BarEntry(44f, 0));
        values.add(new BarEntry(88f, 1));
        values.add(new BarEntry(66f, 2));
        values.add(new BarEntry(19f, 3));
        values.add(new BarEntry(19f, 4));
        values.add(new BarEntry(91f, 5));
        BarDataSet dataSet = new BarDataSet(values, "Values");

        ArrayList<String> datesList = new ArrayList<String>();
        datesList.add("January");
        datesList.add("March");
        datesList.add("May");
        datesList.add("July");
        datesList.add("September");
        datesList.add("November");

        BarData data = new BarData(datesList, dataSet);
        barGraf.setData(data);

        barGraf.setScaleEnabled(true);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        TextView textView = new TextView(this);
        textView.setText(message);

        barGraf.setDescription("Test Graph");

        //ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_graf);
        //layout.addView(textView);
    }
}
