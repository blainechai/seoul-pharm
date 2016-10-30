package com.daejong.seoulpharm.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.adapter.TutorialPagerAdapter;
import com.daejong.seoulpharm.fragment.TutorialFragment;

public class TutorialActivity extends AppCompatActivity {

    ViewPager pager;
    TutorialPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_NoTitleBar_Fullscreen);
        setContentView(R.layout.activity_tutorial);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        pager = (ViewPager) findViewById(R.id.pager);

        mAdapter = new TutorialPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(mAdapter);

        mAdapter.add(TutorialFragment.newInstance(TutorialFragment.PAGE_1));
        mAdapter.add(TutorialFragment.newInstance(TutorialFragment.PAGE_2));
        mAdapter.add(TutorialFragment.newInstance(TutorialFragment.PAGE_3));
        mAdapter.add(TutorialFragment.newInstance(TutorialFragment.PAGE_4));
        mAdapter.add(TutorialFragment.newInstance(TutorialFragment.PAGE_5));
    }
}
