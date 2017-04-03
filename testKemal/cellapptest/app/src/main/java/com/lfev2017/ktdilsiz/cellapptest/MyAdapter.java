package com.lfev2017.ktdilsiz.cellapptest;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class MyAdapter extends BaseAdapter {
    private final ArrayList mData;
    private final Context mContext;

    public MyAdapter(Context mContext, Map<String, String> map) {
        mData = new ArrayList();
        mData.addAll(map.entrySet());
        //Collections.sort(mData);
        this.mContext = mContext;
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
