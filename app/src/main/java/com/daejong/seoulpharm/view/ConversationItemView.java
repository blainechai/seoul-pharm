package com.daejong.seoulpharm.view;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.model.ConversationListItem;
import com.daejong.seoulpharm.widget.NotoTextView;

/**
 * Created by Hyunwoo on 2016. 10. 22..
 */
public class ConversationItemView extends FrameLayout {

    NotoTextView symptomForeignView;
    NotoTextView symptomKoreanView;

    public ConversationItemView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_conversation_list_item, this);
        symptomKoreanView = (NotoTextView) findViewById(R.id.symptom_text_kor);
        symptomForeignView = (NotoTextView) findViewById(R.id.symptom_text_foreign);
    }

    public void setSymptomItem (ConversationListItem item, String foreignContent) {
        symptomKoreanView.setText(item.getContentKor());
        symptomForeignView.setText(foreignContent);
    }



}
