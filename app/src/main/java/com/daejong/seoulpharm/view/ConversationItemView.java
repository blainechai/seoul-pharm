package com.daejong.seoulpharm.view;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.model.ConversationListItem;

/**
 * Created by Hyunwoo on 2016. 10. 22..
 */
public class ConversationItemView extends FrameLayout {

    TextView symptomForeignView;
    TextView symptomKoreanView;

    public ConversationItemView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_conversation_list_item, this);
        symptomKoreanView = (TextView) findViewById(R.id.symptom_text_kor);
        symptomForeignView = (TextView) findViewById(R.id.symptom_text_foreign);
    }

    public void setSymptomItem (ConversationListItem item) {
        symptomKoreanView.setText(item.getContentKor());
        symptomForeignView.setText(item.getContentEng());
    }

}
