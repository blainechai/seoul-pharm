package com.daejong.seoulpharm.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.daejong.seoulpharm.model.PharmItem;
import com.daejong.seoulpharm.util.LanguageSelector;
import com.daejong.seoulpharm.view.ScrapPharmItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hyunwoo on 2016. 10. 28..
 */
public class ScrapPharmListAdapter extends BaseAdapter implements ScrapPharmItemView.OnDeleteButtonClickListener {

    List<PharmItem> pharmItems = new ArrayList<>();

    public void addScrappedPharm(PharmItem item) {
        pharmItems.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return pharmItems.size();
    }

    @Override
    public Object getItem(int position) {
        return pharmItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ScrapPharmItemView view;
        if (convertView != null) {
            view = (ScrapPharmItemView) convertView;
        } else {
            view = new ScrapPharmItemView(parent.getContext());
        }
        view.setScrappedPharmItemView(pharmItems.get(position));
        int currentLanguage = LanguageSelector.getInstance().getCurrentLanguage();
        view.initItemViewsLanguage(currentLanguage, pharmItems.get(position));
        view.setOnDeleteButtonClickListener(this);
        return view;
    }

    @Override
    public void onDeleteButtonClicked(String deleteKey) {
        for (int pos=0; pos<pharmItems.size(); pos++) {
            if (pharmItems.get(pos).getMainKey().equals(deleteKey)) {
                pharmItems.remove(pos);
                notifyDataSetChanged();
            }
        }
    }
}
