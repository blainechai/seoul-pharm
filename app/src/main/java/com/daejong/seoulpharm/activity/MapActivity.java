package com.daejong.seoulpharm.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.fragment.MapHistoryFragment;
import com.daejong.seoulpharm.fragment.MyConversationFragment;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Fragment Build
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new MapHistoryFragment()).commit();
        }
    }



}
