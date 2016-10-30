package com.daejong.seoulpharm.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.model.ConversationListItem;
import com.daejong.seoulpharm.view.ConversationResultView;

import java.util.List;

public class ConversationResultActivity extends AppCompatActivity {

    public static final String PARAM_ITEMS_KEY = "PARAM_ITEMS_KEY";

    LinearLayout selectedContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_NoTitleBar_Fullscreen);
        setContentView(R.layout.activity_conversation_result);

        Intent intent = getIntent();
        List<ConversationListItem> items = (List<ConversationListItem>) intent.getSerializableExtra(PARAM_ITEMS_KEY);

        selectedContainer = (LinearLayout) findViewById(R.id.selectedContainer);
        ConversationResultView view = null;
        for (ConversationListItem item : items) {
            String kor = item.getContentKor();
            String foreign = item.getContentEng();

            view = new ConversationResultView(this);
            view.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            view.setTextViews(kor , foreign);

            selectedContainer.addView(view);
        }

    }
}
