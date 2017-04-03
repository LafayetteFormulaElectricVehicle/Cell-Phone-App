package com.lafayette.lfevcell;

import java.io.*;
import java.net.*;

/**
 * Created by birru on 3/7/2017.
 */

public class DataGetter {

    public static String getHTML(String urlToRead) throws Exception {
        StringBuilder result;
        HttpURLConnection conn;
        BufferedReader rd;
        URL url;
        String line;

        try {
            url = new URL(urlToRead);
            result = new StringBuilder();
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
        } catch (MalformedURLException e) {
            throw e;
        } catch (IOException i){
            throw i;
        } catch (Exception ex){
            throw ex;
        }

        return result.toString();
    }

    public String gogo() throws Exception {
        //System.out.println(getHTML("http://139.147.197.78:3000/dbquery/recent"));
        return getHTML("http://139.147.205.144:3000/dbquery/recent");
    }

}
