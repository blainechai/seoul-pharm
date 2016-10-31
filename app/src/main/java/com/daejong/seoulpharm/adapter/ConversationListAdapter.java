package com.daejong.seoulpharm.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.model.ConversationListItem;
import com.daejong.seoulpharm.util.LanguageSelector;
import com.daejong.seoulpharm.view.ConversationItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hyunwoo on 2016. 10. 22..
 */
public class ConversationListAdapter extends BaseAdapter {

    public List<ConversationListItem> items = new ArrayList<>();

    public void add(ConversationListItem item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void setItemSelect(int position, boolean selected) {
        items.get(position).setSelected(selected);
        notifyDataSetChanged();
    }

    public void setAllItemsNonSelected() {
        for (ConversationListItem item : items) {
            item.setSelected(false);
        }
        notifyDataSetChanged();
    }

    public void setSelectedItems(List<ConversationListItem> selectedItems) {
        this.items = selectedItems;
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
        ConversationItemView view = null;

        if (convertView != null) {
            view = (ConversationItemView) convertView;
        } else {
            view = new ConversationItemView(parent.getContext());
        }

        setViewSelected(view, items.get(position).isSelected());

        if (LanguageSelector.getInstance().getCurrentLanguage() == R.drawable.btn_china) {
            ((TextView) view.findViewById(R.id.symptom_text_foreign)).setText(items.get(position).getContentChi());
            ((TextView) view.findViewById(R.id.symptom_text_kor)).setText(items.get(position).getContentKor());
        } else {
            ((TextView) view.findViewById(R.id.symptom_text_foreign)).setText(items.get(position).getContentEng());
            ((TextView) view.findViewById(R.id.symptom_text_kor)).setText(items.get(position).getContentKor());
        }
        return view;
    }

    private void setViewSelected(ConversationItemView view, boolean isSelected) {
        if (isSelected) {
            view.findViewById(R.id.list_item).setBackgroundResource(R.drawable.list_shadow_selected);
        } else {
            view.findViewById(R.id.list_item).setBackgroundResource(R.drawable.list_shadow);
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
