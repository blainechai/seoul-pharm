package com.daejong.seoulpharm.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hyunwoo on 2016. 10. 28..
 */
public class ScrapComponentListAdpater extends BaseAdapter {

    List<String> componentItems = new ArrayList<>();        // 모델로

    @Override
    public int getCount() {
        return componentItems.size();
    }

    @Override
    public Object getItem(int position) {
        return componentItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        return null;
    }
}
