package com.daejong.seoulpharm.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.adapter.ScrapTabsAdapter;
import com.daejong.seoulpharm.db.DBHelper;
import com.daejong.seoulpharm.fragment.ScrapFragment;
import com.daejong.seoulpharm.model.PharmItem;

import java.util.ArrayList;
import java.util.List;

public class ScrapActivity extends AppCompatActivity implements View.OnClickListener {

    // TOOLBAR
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    TextView toolbarTitle;
    Button toolbarBtn;
    Button languageButton;

    TabHost tabHost;
    ViewPager pager;
    ScrapTabsAdapter mAdapter;

    private static final String TAB_ID_PHARMS = "TAB_ID_PHARMS";
    private static final String TAB_ID_COMPONENT = "TAB_ID_COMPONENT";

    TextView tv;
    String fuck = "";

    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrap);

        // Navigation Drawer (Toolbar) Setting
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbarBtn = (Button) findViewById(R.id.nav_hamburger_btn);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        languageButton = (Button) findViewById(R.id.btn_language);
        toolbarBtn.setOnClickListener(this);

        // setting EventListener Nav Buttons
        findViewById(R.id.nav_drawer_component_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_config_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_main_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_conversation_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_map_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_star_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_tutorial_btn).setOnClickListener(this);
        languageButton.setOnClickListener(this);

        // TAB Settings
        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();

        pager = (ViewPager)findViewById(R.id.pager);
        mAdapter = new ScrapTabsAdapter(this, getSupportFragmentManager(), tabHost, pager);

        // Fragment Settings
        Bundle pharmBundle = new Bundle();
        pharmBundle.putString(ScrapFragment.KEY_FRAGMENT_TYPE, ScrapFragment.TYPE_PHARMS);
        Bundle componentBundle = new Bundle();
        componentBundle.putString(ScrapFragment.KEY_FRAGMENT_TYPE, ScrapFragment.TYPE_COMPONENT);

        // Add Fragment to TAB
        mAdapter.addTab(tabHost.newTabSpec(TAB_ID_PHARMS).setIndicator("약국"), ScrapFragment.class, pharmBundle);
        mAdapter.addTab(tabHost.newTabSpec(TAB_ID_COMPONENT).setIndicator("의약품"), ScrapFragment.class, componentBundle);

/*
        tv = (TextView) findViewById(R.id.text);

        List<PharmItem> items = db.getBookmarkedPharms();
        for (PharmItem item : items) {
            fuck += item.getNameKor()+"\n";
        }
        tv.setText(fuck);
*/
    }


    /** TODO : NAVIGATION DRAWER BUTTON CLICK EVENT 처리
     */

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            // NAVIGATION DRAWER BUTTONS
            case R.id.nav_hamburger_btn :
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.nav_drawer_tutorial_btn:
//                startActivity(new Intent(MainActivity.this, MapActivity.class));
                break;
            case R.id.nav_drawer_map_btn:
                drawerLayout.closeDrawers();
                finish();
                break;
            case R.id.nav_drawer_component_btn:
                drawerLayout.closeDrawers();
                startActivity(new Intent(ScrapActivity.this, ComponentActivity.class));
                finish();
                break;
            case R.id.nav_drawer_star_btn:
                drawerLayout.closeDrawers();
                break;
            case R.id.nav_drawer_conversation_btn:
                drawerLayout.closeDrawers();
                startActivity(new Intent(ScrapActivity.this, ConversationActivity.class));
                finish();
                break;
            case R.id.nav_drawer_main_btn:
                drawerLayout.closeDrawers();
                finish();
                break;
            case R.id.nav_drawer_config_btn:
                drawerLayout.closeDrawers();
                break;
        }
    }

    /*
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            // NavigationDrawer가 열려있는 경우 >> Drawer close
            drawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }
    */
}
