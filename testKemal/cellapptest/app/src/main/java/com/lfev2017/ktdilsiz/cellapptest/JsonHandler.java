package com.lfev2017.ktdilsiz.cellapptest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;

/**
 * Created by ktdilsiz on 3/7/17.
 */

public class JsonHandler extends AppCompatActivity {

    JSONObject jObject;
    TextView texttest;
    String jsonTest;
    String dataUrl;

    DataHandler dataHandler;

    public JsonHandler(){

    }

    public JsonHandler(DataHandler dataHandler){
        this.dataHandler = dataHandler;
        new GetJson().execute();
    }

    public JsonHandler(DataHandler dataHandler, String dataUrl){
        this.dataHandler = dataHandler;
        this.dataUrl = dataUrl;
        new GetJson().execute();
    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_json_test);
//
//        texttest = ((TextView) this.findViewById(R.id.jsonTestText));
//
//        new GetJson().execute();
//
//    }

    private class GetJson extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... params) {
            try {
                HttpHandler sh = new HttpHandler();
                String url;
                if (dataUrl != null) {
                    url = dataUrl;
                } else {
                    url = "http://139.147.194.194:3000/dbquery";
                }

                String jsonStr = sh.makeServiceCall(url);
                jsonTest = jsonStr;

//                if (jsonTest == null) {
//                    Toast.makeText(getBaseContext(), "data null", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(getBaseContext(), "data stored!", Toast.LENGTH_SHORT).show();
//                }

                //Toast.makeText(getParent().getApplicationContext(), jsonStr + "1test", Toast.LENGTH_SHORT).show();

                Log.e("Test", jsonStr);

                if (jsonStr != null) {
                    try {
                        JSONArray jsonObj = new JSONArray(jsonStr);

                        for (int i = 0; i < jsonObj.length(); i++) {
                            JSONArray tempArr = jsonObj.getJSONArray(i);
                            Log.e("Test", tempArr.toString());

                            Log.e("Test", tempArr.get(0) +
                                    tempArr.get(2).toString() +
                                    tempArr.get(1).toString());

                            //key, timestamp, value
                            dataHandler.enterValue(tempArr.get(0).toString(),
                                    tempArr.get(6).toString(),
                                    tempArr.get(5).toString()
                            );


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("JSONException", e.getMessage());
                    }

                }
            }catch (Exception e){
                //Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            Toast.makeText(getApplicationContext(), "Completed Data Acquisition", Toast.LENGTH_SHORT).show();
//        }
    }

}
