package com.lfev2017.ktdilsiz.cellapptest;

import java.util.HashMap;

/**
 * Created by ktdilsiz on 3/2/17.
 */

public class DataHandler {
        HashMap<String, HashMap<String, String>> allSystems;
        HashMap<String, String> newSystem;

    public DataHandler(){
        allSystems = new HashMap<String, HashMap<String, String>>();
    }

    public void enterValue(String key, String timeStamp, String value){
        if(allSystems.get(key) == null){
            newSystem = new HashMap<String, String>();
            newSystem.put(timeStamp, value);
            allSystems.put(key, newSystem);
        }else{
            allSystems.get(key).put(timeStamp, value);
        }
    }

}
