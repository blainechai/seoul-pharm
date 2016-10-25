package com.daejong.seoulpharm.activity;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;

import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.fragment.ComponentScannerFragment;
import com.daejong.seoulpharm.fragment.MapHistoryFragment;

public class ComponentActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component);

        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        setSupportActionBar(toolbar);
        setDrawerToggle();

        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);

        // Nav Buttons Setting
        findViewById(R.id.nav_drawer_component_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_config_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_main_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_conversation_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_map_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_star_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_tutorial_btn).setOnClickListener(this);

        getFragmentManager().beginTransaction().replace(R.id.container, new ComponentScannerFragment()).commit();
    }


    // Handling Fragment
    // 검색 모드에서의 History List를 불러옴
    public void pushComponentScannerFragment() {
        getFragmentManager().beginTransaction().replace(R.id.container, new ComponentScannerFragment()).addToBackStack(null).commit();
    }
    public void popFragment() {
        getFragmentManager().popBackStack();
    }

    // Click Event 처리
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nav_drawer_main_btn:
                drawerLayout.closeDrawers();
                finish();
                break;
            case R.id.nav_drawer_tutorial_btn:
//                startActivity(new Intent(MainActivity.this, MapActivity.class));
                break;
            case R.id.nav_drawer_map_btn:
                drawerLayout.closeDrawers();
                finish();
                startActivity(new Intent(ComponentActivity.this, MapActivity.class));
                break;
            case R.id.nav_drawer_component_btn:
                drawerLayout.closeDrawers();
                finish();
                startActivity(new Intent(ComponentActivity.this, ComponentActivity.class));
                break;
            case R.id.nav_drawer_star_btn:
//                startActivity(new Intent(MainActivity.this, MapActivity.class));
                break;
            case R.id.nav_drawer_config_btn:
//                startActivity(new Intent(MainActivity.this, MapActivity.class));
                break;
            case R.id.nav_drawer_conversation_btn:
                drawerLayout.closeDrawers();
                finish();
                startActivity(new Intent(ComponentActivity.this, ConversationActivity.class));
                break;
        }
    }

    private void setDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
}
