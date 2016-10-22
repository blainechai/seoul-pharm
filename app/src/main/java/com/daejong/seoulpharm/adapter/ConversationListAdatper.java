package com.daejong.seoulpharm.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.daejong.seoulpharm.view.ConversationItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hyunwoo on 2016. 10. 22..
 */
public class ConversationListAdatper extends BaseAdapter {

    List<String> symptomItems = new ArrayList<>();

    public void setSymptomItems(String symptom) {
        symptomItems.add(symptom);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return symptomItems.size();
    }

    @Override
    public Object getItem(int position) {
        return symptomItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ConversationItemView view;
        if (convertView != null) {
            view = (ConversationItemView) convertView;
        } else {
            view = new ConversationItemView(parent.getContext());
        }
        view.setSymptomText(symptomItems.get(position));
        return view;
    }




}
