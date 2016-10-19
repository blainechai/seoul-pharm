package com.daejong.seoulpharm.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.daejong.seoulpharm.fragment.MyConversationFragment;
import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.fragment.SymptomFragment;

public class ConversationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        // Fragment Build
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new MyConversationFragment()).commit();
        }


    }

    public void pushSymptomFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new SymptomFragment()).addToBackStack(null).commit();
    }

    public void popFragment() {
        getSupportFragmentManager().popBackStack();
    }
}
