package com.daejong.seoulpharm.view;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daejong.seoulpharm.R;

/**
 * Created by Hyunwoo on 2016. 10. 22..
 */
public class ConversationItemView extends FrameLayout {

    TextView text;
    LinearLayout item;

    public ConversationItemView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_conversation_list_item, this);
        text = (TextView) findViewById(R.id.symptom_text);


    }

    public void setItemText (String symptom) {
        text.setText(symptom);
    }
    public String getItemText() {
        return text.getText().toString();
    }
}
