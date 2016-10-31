package com.daejong.seoulpharm.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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


    boolean isFirstLaunched;
    SharedPreferences mPrefs;
    SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_NoTitleBar_Fullscreen);
        setContentView(R.layout.activity_tutorial);

        mPrefs = PreferenceManager.getDefaultSharedPreferences(TutorialActivity.this);
        mEditor = mPrefs.edit();
        isFirstLaunched = mPrefs.getBoolean(SplashActivity.IS_FIRST_LAUNCHED, true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFirstLaunched) {
                    if (pager.getCurrentItem() < mAdapter.getCount()-1) {
                        pager.setCurrentItem(pager.getCurrentItem()+1, true);
                    } else {
                        mEditor.putBoolean(SplashActivity.IS_FIRST_LAUNCHED, false);
                        mEditor.commit();
                        startActivity(new Intent(TutorialActivity.this, MainActivity.class));
                        finish();
                    }
                } else {
                    finish();
                }
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
