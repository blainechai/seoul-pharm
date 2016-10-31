package com.daejong.seoulpharm.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.widget.NotoTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hyunwoo on 2016. 10. 31..
 */
public class ConversationCustomTabView extends FrameLayout implements View.OnClickListener {

    List<String> tabTexts = new ArrayList<String>();

    int currentTabPos = 0;

    LinearLayout tabsContainer;
    NotoTextView preTabTextView;
    NotoTextView currentTabTextView;
    NotoTextView postTabTextView;

    public ConversationCustomTabView(Context context) {
        super(context);
        init();
    }

    public ConversationCustomTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_custom_tab, this);

        tabsContainer = (LinearLayout) findViewById(R.id.tabs_container);
        preTabTextView = (NotoTextView) findViewById(R.id.pre_tab_text);
        currentTabTextView = (NotoTextView) findViewById(R.id.current_tab_text);
        postTabTextView = (NotoTextView) findViewById(R.id.post_tab_text);

        preTabTextView.setOnClickListener(this);
        postTabTextView.setOnClickListener(this);
    }

    public void initTabContents(String preTabText, String currentTabText, String postTabText) {
        preTabTextView.setText(preTabText);
        currentTabTextView.setText(currentTabText);
        postTabTextView.setText(postTabText);
    }

    public void setCurrentTab(int position) {
        String preTabText = "";
        String currentTabText = "";
        String postTabText = "";

        if (position == 0) {
            postTabText = tabTexts.get(position+1);
        } else if (position == (tabTexts.size()-1)) {
            preTabText = tabTexts.get(position-1);
        } else {
            preTabText = tabTexts.get(position-1);
            postTabText = tabTexts.get(position+1);
        }
        currentTabText = tabTexts.get(position);
        currentTabPos = position;
        initTabContents(preTabText, currentTabText, postTabText);
    }

    // First , add tab texts in this view.
    public void addTab(String tabText) {
        tabTexts.add(tabText);
    }

    public void clearTab() {
        tabTexts.clear();
    }



    // CLICK EVENT
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pre_tab_text :
                if (currentTabPos > 0) {
                    // set Click event listener callback
                    mOnTabClcickedListener.onPreTabBtnClicked(currentTabPos - 1);
                    // move left animation

                }
                break;

            case R.id.post_tab_text :
                if (currentTabPos < tabTexts.size()-1) {
                    // set Click event listener callback
                    mOnTabClcickedListener.onPostTabBtnClicked(currentTabPos + 1);
                    // move right animation

                }
                break;
        }
    }

    public interface OnTabClickedListener {
        public void onPreTabBtnClicked(int preTabPosition);
        public void onPostTabBtnClicked(int postTabPosition);
    }
    OnTabClickedListener mOnTabClcickedListener;
    public void setOnTabClcickedListener (OnTabClickedListener onTabClickedListener) {
        mOnTabClcickedListener = onTabClickedListener;
    }


    // Scroll Animation
    public void moveLeftAnimation() {

    }

    public void moveRightAnimation() {

    }

}
