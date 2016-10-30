package com.daejong.seoulpharm.view;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.widget.NotoTextView;

/**
 * Created by Hyunwoo on 2016. 10. 31..
 */
public class ConversationResultView extends FrameLayout {

    NotoTextView foreignTextView;
    NotoTextView korTextView;

    public ConversationResultView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_conversation_result, this);
        foreignTextView = (NotoTextView) findViewById(R.id.text_foreign);
        korTextView = (NotoTextView) findViewById(R.id.text_kor);
    }

    public void setTextViews(String kor, String foreign) {
        foreignTextView.setText(foreign);
        korTextView.setText(kor);
    }
}
