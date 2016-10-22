package com.daejong.seoulpharm.view;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.daejong.seoulpharm.R;

/**
 * Created by Hyunwoo on 2016. 10. 22..
 */
public class ConversationItemView extends FrameLayout {

    TextView symptomText;

    public ConversationItemView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_conversation_list_item, this);
        symptomText = (TextView) findViewById(R.id.symptom_text);


    }

    public void setSymptomText (String symptom) {
        symptomText.setText(symptom);
    }
}
