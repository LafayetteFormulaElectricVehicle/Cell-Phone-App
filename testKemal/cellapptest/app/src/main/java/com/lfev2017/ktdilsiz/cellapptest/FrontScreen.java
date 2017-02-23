package com.lfev2017.ktdilsiz.cellapptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;

public class FrontScreen extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.lfev2017.ktdilsiz.cellapptest.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_screen);
    }

    public void sendMessage(View view){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void createInflater(View view) {
        Intent intent = new Intent(this, LayoutInflater.class);
        startActivity(intent);
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
