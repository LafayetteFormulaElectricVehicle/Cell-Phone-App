package birrur.com.jsonparse;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

public class Parsify extends AppCompatActivity {

    private Gson gson;
    private Data dataSet;
    private static final String TAG = Parsify.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parsify);

        String data = readSon();

        gson = new Gson();

        dataSet = new Data();
        dataSet = gson.fromJson(data, dataSet.getClass());

        Map<String, Object> parsed = gson.fromJson(data , LinkedHashMap.class);

        LinkedTreeMap<String, Object> dash = (LinkedTreeMap<String, Object>) parsed.get("dashboard");
        LinkedTreeMap<String, Object> tsi  = (LinkedTreeMap<String, Object>) parsed.get("tsi");
        LinkedTreeMap<String, Object> tsv  = (LinkedTreeMap<String, Object>) parsed.get("tsv");
        LinkedTreeMap<String, Object> physics = (LinkedTreeMap<String, Object>) parsed.get("physics");
        LinkedTreeMap<String, Object> cooling = (LinkedTreeMap<String, Object>) parsed.get("cooling");

        Log.i(TAG, "PRINTED");
        Log.i(TAG, dash.get("speed").toString());

        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Sansita-Regular.ttf");

        TextView tvDD     = (TextView) findViewById(R.id.tvDash);
        TextView tvCO     = (TextView) findViewById(R.id.tvCO);
        TextView tvTS     = (TextView) findViewById(R.id.tvTS);

        tvDD.setTypeface(type);
        tvCO.setTypeface(type);
        tvTS.setTypeface(type);

        TextView tvRPME   = (TextView) findViewById(R.id.tvRPME);
        TextView tvSpeedE = (TextView) findViewById(R.id.tvSpeedE);
        TextView tvVE     = (TextView) findViewById(R.id.tvTE);
        TextView tvIE     = (TextView) findViewById(R.id.tvIE);
        TextView tvSocE   = (TextView) findViewById(R.id.tvSocE);
        TextView tvOnE    = (TextView) findViewById(R.id.tvOnE);
        TextView tvTE     = (TextView) findViewById(R.id.tvTE);

        tvRPME.setText(dash.get("rpm").toString());
        tvSpeedE.setText(dash.get("speed").toString());
        tvVE.setText(tsv.get("voltage").toString());
        tvIE.setText(tsv.get("current").toString());
        tvSocE.setText(tsv.get("state_of_charge").toString());
        tvOnE.setText(tsv.get("enabled").toString());
        tvTE.setText(cooling.get("temp").toString());
    }

    public String readSon(){
        String file = "color_json";
        StringBuffer fb = new StringBuffer();
        InputStream is = getResources().openRawResource(R.raw.color_json);

        int character;

        try
        {
            while((character = is.read())!=-1){
                fb.append((char)character);
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return fb.toString();
    }
}
