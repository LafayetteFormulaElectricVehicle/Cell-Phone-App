package birrur.com.jsonparse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

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

        Log.i(TAG, readSon());

        gson = new Gson();
        dataSet = new Data();
        dataSet = gson.fromJson(data, dataSet.getClass());


        TextView tvRPME = (TextView) findViewById(R.id.tvRPME);
        TextView tvSpeedE = (TextView) findViewById(R.id.tvRPME);

        tvRPME.setText(dataSet.getDashboard().getRpm().toString());
        tvSpeedE.setText(dataSet.getDashboard().getSpeed().toString());
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
