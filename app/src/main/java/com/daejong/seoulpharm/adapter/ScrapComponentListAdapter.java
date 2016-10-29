package com.daejong.seoulpharm.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.daejong.seoulpharm.model.MedicineInfo;
import com.daejong.seoulpharm.view.ScrapMedicineItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hyunwoo on 2016. 10. 28..
 */
public class ScrapComponentListAdapter extends BaseAdapter implements ScrapMedicineItemView.OnDeleteButtonClickListener {

    List<MedicineInfo> componentItems = new ArrayList<>();        // 모델로

    public void addScrappedMedicine(MedicineInfo item) {
        componentItems.add(item);
        notifyDataSetChanged();
    }

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
        ScrapMedicineItemView view;
        if (convertView != null) {
            view = (ScrapMedicineItemView) convertView;
        } else {
            view = new ScrapMedicineItemView(parent.getContext());
        }
        view.setScrappedMedicineItemView(componentItems.get(position));
        view.setOnDeleteButtonClickListener(this);
        return view;
    }

    @Override
    public void onDeleteButtonClicked(String deleteKey) {
        for (int pos = 0; pos < componentItems.size(); pos++) {
            if (componentItems.get(pos).getItemSeq().equals(deleteKey)) {
                componentItems.remove(pos);
                notifyDataSetChanged();
            }

        }
    }
}
