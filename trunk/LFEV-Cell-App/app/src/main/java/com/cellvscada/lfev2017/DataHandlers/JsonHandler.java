package com.cellvscada.lfev2017.DataHandlers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.cellvscada.lfev2017.Tools.DateAndTimeViewGenerator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ktdilsiz on 3/7/17.
 */

public class JsonHandler extends AsyncTask<Void, Void, Void>  {

    private static final String TAG = JsonHandler.class.getSimpleName();

    JSONObject jObject;
    TextView texttest;
    String jsonTest;
    String dataUrl;

    Context context;

    private ProgressDialog pdia;

    DataHandler dataHandler;

    boolean finished;
    boolean wantNotification;
    boolean exception;
    boolean wantAllData;
    boolean constantUpdate;

    //AsyncTask<Void, Void, Void> getData;

    public JsonHandler(){

    }

    public JsonHandler(DataHandler dataHandler, String dataUrl, Context context){
        this.dataHandler = dataHandler;
        this.dataUrl = dataUrl;
        this.context = context;

        wantNotification = true;
        wantAllData = true;
    }

    public JsonHandler(DataHandler dataHandler, String dataUrl, Context context, boolean wantNotification){
        this.dataHandler = dataHandler;
        this.dataUrl = dataUrl;
        this.context = context;
        this.wantNotification = wantNotification;
        wantAllData = true;
    }

    public JsonHandler(DataHandler dataHandler, String dataUrl, Context context, boolean wantNotification, boolean wantAllData){
        this.dataHandler = dataHandler;
        this.dataUrl = dataUrl;
        this.context = context;
        this.wantNotification = wantNotification;
        this.wantAllData = wantAllData;
    }

    public JsonHandler(DataHandler dataHandler, String dataUrl, Context context, boolean wantNotification, boolean wantAllData, boolean constantUpdate){
        this.dataHandler = dataHandler;
        this.dataUrl = dataUrl;
        this.context = context;
        this.wantNotification = wantNotification;
        this.wantAllData = wantAllData;
        this.constantUpdate = constantUpdate;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_json_test);
//
//        texttest = ((TextView) this.findViewById(R.id.jsonTestText));
//
//    }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                HttpHandler sh = new HttpHandler();
                String url;
                if (dataUrl != null) {
                    url = dataUrl;
                } else {
                    url = "http://139.147.205.136:3000/dbquery";
                }

                String jsonStr = sh.makeServiceCall(url);
                jsonTest = jsonStr;

//                if (jsonTest == null) {
//                    Toast.makeText(getBaseContext(), "data null", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(getBaseContext(), "data stored!", Toast.LENGTH_SHORT).show();
//                }

                //Toast.makeText(getParent().getApplicationContext(), jsonStr + "1test", Toast.LENGTH_SHORT).show();

                //Log.e("Test", jsonStr);

                if (jsonStr != null) {
                    try {
                        JSONArray jsonObj = new JSONArray(jsonStr);

                        for (int i = 0; i < jsonObj.length(); i++) {
                            JSONArray tempArr = jsonObj.getJSONArray(i);
                            //Log.e("Test", tempArr.toString());

//                            Log.e("Test", tempArr.get(0) +
//                                    tempArr.get(5).toString() +
//                                    tempArr.get(4).toString());

                            //key, timestamp, value
                            dataHandler.enterValue(tempArr.get(0).toString(),
                                    tempArr.get(5).toString(),
                                    tempArr.get(4).toString()
                            );


                            if(wantAllData) {
                                dataHandler.enterTagtoID(
                                        tempArr.get(1).toString(),
                                        tempArr.get(0).toString()
                                );

                                dataHandler.enterIDtoAll(
                                        tempArr.get(0).toString(),
                                        tempArr.get(1).toString(),
                                        tempArr.get(2).toString(),
                                        tempArr.get(3).toString()
                                );
                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("JSONException", e.getMessage());
                        exception = true;
                    }

                }
            }catch (Exception e){
                exception = true;
                //Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(wantNotification) {
                pdia = new ProgressDialog(context);
                pdia.setMessage("Loading...");
                pdia.show();
            }

            dataHandler.renewLast();

            finished = false;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dataHandler.generateArrayLists();
            Log.e("Test", "Completed Update");
            if(wantNotification && !exception) {
                if (context != null) {
                    Toast.makeText(context, "Completed Data Acquisition", Toast.LENGTH_SHORT).show();
                }
                pdia.dismiss();
            }

            if(exception){
                Toast.makeText(context, "Couldn't connect to webserver, please check either IP entered", Toast.LENGTH_SHORT).show();
            }

            finished = true;
        }

    public boolean isFinished(){
        return finished;
    }

}
