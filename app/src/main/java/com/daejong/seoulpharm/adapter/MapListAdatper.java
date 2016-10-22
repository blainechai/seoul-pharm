package com.daejong.seoulpharm.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.daejong.seoulpharm.view.MapHistoryItemView;
import com.daejong.seoulpharm.model.MapHistoryItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hyunwoo on 2016. 10. 19..
 */
public class MapListAdatper extends BaseAdapter {

    List<MapHistoryItem> items = new ArrayList<>();

    public void add(MapHistoryItem item) {
        items.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MapHistoryItemView view;
        if (convertView != null) {
            view = (MapHistoryItemView) convertView;
        }
        else {
            view = new MapHistoryItemView(parent.getContext());
        }
        view.setMapListItem(items.get(position));
        return view;
    }
}
