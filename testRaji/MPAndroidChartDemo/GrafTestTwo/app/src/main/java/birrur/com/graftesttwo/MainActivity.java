package birrur.com.graftesttwo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user clicks the Send button */
    public void createBar(View view) {
        Intent intent = new Intent(this, DisplayGrafActivity.class);
        startActivity(intent);
    }

    /** Called when the user clicks the Send button */
    public void createLine(View view) {
        Intent intent = new Intent(this, DisplayLineActivity.class);
        startActivity(intent);
    }
}