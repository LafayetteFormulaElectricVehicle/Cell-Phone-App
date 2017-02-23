package birrur.com.jsonparse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;

public class Parsify extends AppCompatActivity {

    private Gson gson;
    private Data dataSet;
    private static final String TAG = Parsify.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parsify);

        String data = readSon();

        //Log.i(TAG, readSon());

        gson = new Gson();

        dataSet = new Data();
        dataSet = gson.fromJson(data, dataSet.getClass());

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(data);
        JsonObject object = element.getAsJsonObject();

        try {
            JSONObject ob = new JSONObject(data);
            ob.keys();
        } catch (JSONException e){

        }


        //JsonObject object = parser.parse(data).getAsJsonObject();
        //JsonArray array = (JsonArray) parser.parse(data).getAsJsonObject().getAsJsonArray();
        Log.i(TAG, "PRINTED");
        Log.i(TAG, element.toString());


        TextView tvRPME = (TextView) findViewById(R.id.tvRPME);
        TextView tvSpeedE = (TextView) findViewById(R.id.tvVE);
        TextView tvVE = (TextView) findViewById(R.id.tvVE);
        TextView tvIE = (TextView) findViewById(R.id.tvIE);
        TextView tvSocE = (TextView) findViewById(R.id.tvSocE);
        TextView tvOnE = (TextView) findViewById(R.id.tvOnE);

        tvRPME.setText(dataSet.getDashboard().getRpm().toString());
        tvSpeedE.setText(dataSet.getDashboard().getSpeed().toString());
        tvVE.setText(dataSet.getTsv().getVoltage().toString());
        tvIE.setText(dataSet.getTsv().getCurrent().toString());
        tvSocE.setText(dataSet.getTsv().getStateOfCharge().toString());
        tvOnE.setText(dataSet.getTsv().getEnabled().toString());
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
