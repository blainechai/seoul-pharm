package com.daejong.seoulpharm.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.model.ConversationListItem;
import com.daejong.seoulpharm.view.ConversationItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hyunwoo on 2016. 10. 22..
 */
public class ConversationListAdapter extends BaseAdapter {

    List<ConversationListItem> items = new ArrayList<>();

    public void add(ConversationListItem item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void setItemSelect(int position, boolean selected) {
        items.get(position).setSelected(selected);
        notifyDataSetChanged();
    }

    public boolean getItemIsSelected(int position) {
        return items.get(position).isSelected();
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
        ConversationItemView view;
        if (convertView != null) {
            view = (ConversationItemView) convertView;
        } else {
            view = new ConversationItemView(parent.getContext());
        }

        setViewSelected(view,items.get(position).isSelected());
        view.setSymptomItem(items.get(position));
        return view;
    }

    private void setViewSelected(ConversationItemView view, boolean isSelected) {
        if (isSelected) {
            view.findViewById(R.id.list_item).setBackgroundResource(R.drawable.list_shadow_selected);
            ((TextView)view.findViewById(R.id.symptom_text_foreign)).setTextColor(view.getResources().getColor(R.color.color_white));
            TextView textKorView = (TextView)view.findViewById(R.id.symptom_text_kor);
            textKorView.setTextColor(view.getResources().getColor(R.color.color_white));
        } else {
            view.findViewById(R.id.list_item).setBackgroundResource(R.drawable.list_shadow);
            ((TextView)view.findViewById(R.id.symptom_text_foreign)).setTextColor(view.getResources().getColor(R.color.colorPrimary));
            TextView textKorView = (TextView)view.findViewById(R.id.symptom_text_kor);
            textKorView.setTextColor(view.getResources().getColor(R.color.color_nav_drawer_font));
        }
    }

/*
    // wobble animation
    ObjectAnimator wobbleAnimator;
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void animateWobble(View v) {
        wobbleAnimator = createBaseWobble(v);
        wobbleAnimator.setFloatValues(-2, 2);
        wobbleAnimator.start();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void stopWobble() {
        wobbleAnimator.cancel();
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private ObjectAnimator createBaseWobble(final View v) {
        ObjectAnimator animator = new ObjectAnimator();
        animator.setDuration(180);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setPropertyName("rotation");
        animator.setTarget(v);
        return animator;
    }
*/
}
