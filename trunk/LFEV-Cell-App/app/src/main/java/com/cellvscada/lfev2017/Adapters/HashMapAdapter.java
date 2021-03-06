package com.cellvscada.lfev2017.Adapters;

/**
 * Created by ktdilsiz on 3/7/17.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cellvscada.lfev2017.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class HashMapAdapter extends BaseAdapter {
    private ArrayList mData;
    private final Context mContext;
    private Map<String, String> map;

    public HashMapAdapter(Context mContext, Map<String, String> map) {
        mData = new ArrayList();
        mData.addAll(map.entrySet());
        //Collections.sort(mData);
        this.mContext = mContext;
        this.map = map;
    }

    public void addData(Map<String, String> map){
        new ArrayList();
        for(Map.Entry each : map.entrySet()) {
            if(!mData.contains(each))
                mData.add(each);
            //mData.addAll(map.entrySet());
        }
        //Collections.sort(mData);
    }

    public void setData(Map<String, String> map){
        mData = new ArrayList();
        mData.addAll(map.entrySet());
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Map.Entry<String, String> getItem(int position) {
        return (Map.Entry) mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO implement you own logic with ID
        return 0;
    }

    //@Override


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            result = inflater.inflate(R.layout.my_adapter_item, parent, false);
        } else {
            result = convertView;
        }

        Map.Entry<String, String> item = getItem(position);

        // TODO replace findViewById by ViewHolder
        ((TextView) result.findViewById(android.R.id.text1)).setText(item.getKey());
        ((TextView) result.findViewById(android.R.id.text2)).setText(item.getValue());

        return result;
    }
}
