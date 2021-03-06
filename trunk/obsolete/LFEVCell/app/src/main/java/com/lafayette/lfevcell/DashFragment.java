package com.lafayette.lfevcell;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by birrur on 3/7/2017.
 */

public class DashFragment extends Fragment {

    TextView mainText;
    DataGetter dg = new DataGetter();
    String json = "PAR";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.dash_layout,null);
        mainText = (TextView) view.findViewById(R.id.textView);
        Log.d("GG", json+"HHHHHHHHHH");
        Log.d("GOT", "GOT HERE");
        new Rush().execute();
        return view;
    }

    private class Rush extends AsyncTask <Void, Void, String> {
        @Override
        protected String doInBackground(Void... arg0) {
            return makePostRequest();
        }

        private String makePostRequest() {
            StringBuilder result = null;
            HttpURLConnection conn;
            BufferedReader rd;
            URL url;
            String line;

            try {
                url = new URL("http://139.147.205.144:3000/dbquery/recent");
                Log.d("GOT", "PAST URL");
                result = new StringBuilder();
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                rd.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException i){
                i.printStackTrace();
            } catch (Exception ex){
                ex.printStackTrace();
            }

            json = result.toString();
            Log.d("RESPONSE: ", json);
            if(json != null){
                mainText.setText(json);
            }
            return json;
        }

        protected void onPostExecute(String json){
            super.onPostExecute(json);
        }
    }
}