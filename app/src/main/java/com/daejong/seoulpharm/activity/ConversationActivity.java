package com.daejong.seoulpharm.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.daejong.seoulpharm.adapter.ConversationListAdatper;
import com.daejong.seoulpharm.fragment.MyConversationFragment;
import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.fragment.SymptomFragment;

public class ConversationActivity extends AppCompatActivity {

    ListView conversationListView;
    ConversationListAdatper mAdatper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        conversationListView = (ListView) findViewById(R.id.conversation_list);
        mAdatper = new ConversationListAdatper();
        conversationListView.setAdapter(mAdatper);
        mAdatper.setSymptomItems("머리");
        mAdatper.setSymptomItems("가슴");
        mAdatper.setSymptomItems("배");

        /*
        // Fragment Build
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new MyConversationFragment()).commit();
        }
        */




    }

    /*
    public void pushSymptomFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new SymptomFragment()).addToBackStack(null).commit();
    }

    public void popFragment() {
        getSupportFragmentManager().popBackStack();
    }
    */
}
