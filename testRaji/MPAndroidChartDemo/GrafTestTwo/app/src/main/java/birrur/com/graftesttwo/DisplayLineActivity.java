package birrur.com.graftesttwo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class DisplayLineActivity extends AppCompatActivity {

    private LineChart line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_line);

        line = (LineChart) findViewById(R.id.linegraph);

        ArrayList<Entry> values = new ArrayList<>();
        values.add(new Entry(4f, 0));
        values.add(new Entry(8f, 1));
        values.add(new Entry(6f, 2));
        values.add(new Entry(2f, 3));
        values.add(new Entry(18f,4));
        values.add(new Entry(9f, 5));

        LineDataSet dataset = new LineDataSet(values, "# of instances");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("March");
        labels.add("May");
        labels.add("July");
        labels.add("September");
        labels.add("November");

        LineData data = new LineData(labels, dataset);
        line.setData(data);
        line.setDescription("Description");
    }
}
