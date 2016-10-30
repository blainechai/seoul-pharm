package com.daejong.seoulpharm.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.model.ConversationListItem;

import java.util.List;

public class ConversationResultActivity extends AppCompatActivity {

    public static final String PARAM_ITEMS_KEY = "PARAM_ITEMS_KEY";

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_NoTitleBar_Fullscreen);
        setContentView(R.layout.activity_conversation_result);

        Intent intent = getIntent();
        List<ConversationListItem> items = (List<ConversationListItem>) intent.getSerializableExtra(PARAM_ITEMS_KEY);

        tv = (TextView) findViewById(R.id.text);
        String testtt = "";
        for (ConversationListItem item : items) {
            testtt += (item.getContentKor()+" ");
        }
        tv.setText(testtt);
    }
}
