package com.cellvscada.lfev2017.DataHandlers;

/**
 * Created by ktdilsiz on 3/7/17.
 */

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpHandler {

    private static final String TAG = HttpHandler.class.getSimpleName();
    StringBuilder result;

    public HttpHandler() {
        result = new StringBuilder();
    }

    public String makeServiceCall(String reqUrl) {
        String response = null;
        try {
            URL url = new URL(reqUrl);
            Log.e(TAG, "Step1" + reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            Log.e(TAG, "Step2");
            System.setProperty("http.keepAlive", "false");
            conn.setRequestMethod("GET");
            Log.e(TAG, "Step3");
            // read the response
            InputStream pass = conn.getInputStream();
            Log.e(TAG, "Step3.5");
            InputStream in = new BufferedInputStream(pass);
            //InputStream in = new BufferedInputStream(conn.getInputStream());
            Log.e(TAG, "Step4");
            response = convertStreamToString(in);
            Log.e(TAG, "Step5");

//            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            Log.e(TAG, "Step6");
//
//            String line;
//            while((line = rd.readLine()) != null){
//                result.append(line);
//            }
//            Log.e(TAG, "Step7");

        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.toString());
        }
        return response;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
}
