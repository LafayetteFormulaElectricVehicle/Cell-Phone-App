package com.lfev2017.ktdilsiz.cellapptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

import com.lfev2017.ktdilsiz.cellapptest.testing.DisplayGrafActivity;
import com.lfev2017.ktdilsiz.cellapptest.testing.DisplayLineActivity;

public class FrontScreen extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.lfev2017.ktdilsiz.cellapptest.MESSAGE";

    DataHandler finalHandler;
    JsonHandler finalJson;
    String dataUrl;
    String idHex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_screen);

        dataUrl = "http://139.147.194.194:3000/dbquery";
        finalHandler = new DataHandler();
        idHex = "1";
    }

    public void sendMessage(View view){
        //Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        dataUrl = "http://" + editText.getText().toString() + ":3000/dbquery";

        //intent.putExtra(EXTRA_MESSAGE, message);
        //startActivity(intent);
        Toast.makeText(this, "New url: " + dataUrl, Toast.LENGTH_SHORT).show();

    }

    public void createInflater(View view) {
        //LayoutInflater test = new LayoutInflater(finalHandler.allSystems);

        Intent intent = new Intent(this, LayoutInflater.class);
        intent.putExtra("hashmap", finalHandler.allSystems);
        intent.putExtra("url", dataUrl);
        intent.putExtra("id", idHex);

        startActivity(intent);
    }

    /** Called when the user clicks the Send button */
    public void createBar(View view) {
        finalJson = new JsonHandler(finalHandler, dataUrl);
    }

    /** Called when the user clicks the Send button */
    public void createLine(View view) {
        EditText editText = (EditText) findViewById(R.id.edit_id);
        idHex = editText.getText().toString();

        //intent.putExtra(EXTRA_MESSAGE, message);
        //startActivity(intent);
        Toast.makeText(this, "New id: " + idHex, Toast.LENGTH_SHORT).show();

    }

}
