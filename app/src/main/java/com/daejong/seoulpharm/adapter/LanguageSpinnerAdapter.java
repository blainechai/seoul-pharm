package com.daejong.seoulpharm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daejong.seoulpharm.R;

/**
 * Created by blainechai on 2016. 10. 27..
 */

public class LanguageSpinnerAdapter extends BaseAdapter {
    Context context;
    String[] countryNames;
    LayoutInflater inflater;

    public LanguageSpinnerAdapter(Context applicationContext, String[] countryNames) {
        this.context = applicationContext;
        this.countryNames = countryNames;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return countryNames.length;
    }

    @Override
    public Object getItem(int i) {
        return countryNames[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.view_language_spinner_item, null);
//        ImageView icon = (ImageView) view.findViewById(R.id.language_image_view);
        TextView names = (TextView) view.findViewById(R.id.language_text_view);
//        icon.setImageResource(flags[i]);
        names.setText(countryNames[i]);
        return view;
    }
}
