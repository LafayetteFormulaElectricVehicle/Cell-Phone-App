package com.cellvscada.lfev2017.DataHandlers;

import android.support.annotation.NonNull;
import android.util.Log;

import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by ktdilsiz on 3/2/17.
 */

public class DataHandler {
        public HashMap<String, TreeMap<String, String>> allSystems;
        public HashMap<String, TreeMap<String, String>> lastUpdate;

        public TreeMap<String, String> newSystem;
        public TreeMap<String, String> tagToId;
        //[0] tag, [1] system, [2] unit
        public HashMap<String, String[]> idToAll;

        public ArrayList<String> tags;
        public ArrayList<String> IDs;

    public DataHandler(){
        tagToId = new TreeMap<>();
        idToAll = new HashMap<>();
        allSystems = new HashMap<>();
        lastUpdate = new HashMap<>();
        tags = new ArrayList<>();
        IDs = new ArrayList<>();
    }

    public void renewLast(){
        lastUpdate = new HashMap<>();
    }

    public void enterValue(String key, String timeStamp, String value){
        if(allSystems.get(key) == null){
            newSystem = new TreeMap<String, String>(){
                @NonNull
                @Override
                public SortedMap<String, String> subMap(String first, String second){

                    Set<String> keySet = newSystem.keySet();
                    Iterator<String> iterator = keySet.iterator();

                    String start = first;
                    if(iterator.hasNext()) {
                        start = iterator.next();
                    }else{
                        return super.subMap(first, second);
                    }
                    String end = start;

                    while ((start.compareTo(first) <= 0) && iterator.hasNext()){
                        start = iterator.next();
                    }

                    while ((end.compareTo(second) <= 0) && iterator.hasNext()){
                        end = iterator.next();
                    }

                    return super.subMap(start, end);
                }
            };
            newSystem.put(timeStamp, value);
            allSystems.put(key, newSystem);
            lastUpdate.put(key, newSystem);
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

    public void generateArrayLists(){
        for (String key: tagToId.keySet()) {
            if(!tags.contains(key))
                tags.add(key);
            if(!IDs.contains(tagToId.get(key)))
                IDs.add(tagToId.get(key));
        }
    }

}
