package com.cellvscada.lfev2017.DataHandlers;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by ktdilsiz on 3/2/17.
 */

public class DataHandler {
        public HashMap<String, Map<String, String>> allSystems;
        public TreeMap<String, String> newSystem;
        public TreeMap<String, String> tagToId;
        //[0] tag, [1] system, [2] unit
        public HashMap<String, String[]> idToAll;

    public DataHandler(){
        tagToId = new TreeMap<>();
        idToAll = new HashMap<>();
        allSystems = new HashMap<>();
    }

    public void enterValue(String key, String timeStamp, String value){
        if(allSystems.get(key) == null){
            newSystem = new TreeMap<>();
            newSystem.put(timeStamp, value);
            allSystems.put(key, newSystem);
        }else{
            allSystems.get(key).put(timeStamp, value);
        }
    }

    public boolean enterTagtoID(String tag, String id){
        if(tagToId.get(tag) == null){
            tagToId.put(tag, id);
            return true;
        }else{
            return false;
        }
    }

    public boolean enterIDtoAll(String id, String tag, String system, String unit){
        if(idToAll.get(id) == null){
            String[] tempArray = new String[3];
            tempArray[0] = tag;
            tempArray[1] = system;
            tempArray[2] = unit;
            idToAll.put(id, tempArray);
            return true;
        }else{
            return false;
        }
    }

}
