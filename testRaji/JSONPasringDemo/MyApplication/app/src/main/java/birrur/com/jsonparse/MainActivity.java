package birrur.com.jsonparse;

import android.app.NotificationManager;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    NotificationCompat.Builder mBuilder = (android.support.v7.app.NotificationCompat.Builder) new NotificationCompat.Builder(this).setSmallIcon(R.drawable.notification_icon).setContentTitle("My notification").setContentText("Hello World!");
    private Gson gson;
    private Data dataSet;
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user clicks the Notify button */
    public void notificate(View view) {

        // Do something in response to button
        int mNotificationId = 001;
        NotificationManager nmg = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nmg.notify(mNotificationId, mBuilder.build());

    }

    /** Called when the user clicks the Parse button */
    public void parsiate(View view){
        Intent intent = new Intent(this, Parsify.class);
        startActivity(intent);

        /**
        TextView textView = new TextView(this);
        textView.setTextSize(40);

        String data = readSon();

        Log.i(TAG, readSon());

        gson = new Gson();
        dataSet = new Data();
        dataSet = gson.fromJson(data, dataSet.getClass());


        textView.setText(dataSet.getTsv().getEnabled().toString());
        Log.i(TAG, dataSet.getTsv().getEnabled().toString());
         */
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
