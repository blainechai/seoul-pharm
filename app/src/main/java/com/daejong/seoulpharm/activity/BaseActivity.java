package com.daejong.seoulpharm.activity;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.daejong.seoulpharm.R;

/**
 * Created by blainechai on 2016. 10. 20..
 */

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    protected Toolbar toolbar;
    protected DrawerLayout drawerLayout;
    protected ActionBarDrawerToggle mDrawerToggle;

    @Override
    public void onClick(View view) {

    }


    public void setNavigationDrawer(){
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        setSupportActionBar(toolbar);
        setDrawerToggle();
    }
    private void setDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
    }
}
