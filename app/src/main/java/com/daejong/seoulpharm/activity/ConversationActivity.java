package com.daejong.seoulpharm.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.adapter.TabsAdapter;
import com.daejong.seoulpharm.fragment.ConversationFragment;
import com.daejong.seoulpharm.view.ConversationCustomTabView;

public class ConversationActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener, ConversationCustomTabView.OnTabClickedListener {

    public static final String TAB_ID_SKIN = "TAB_ID_SKIN";
    public static final String TAB_ID_RESPIRATORY = "TAB_ID_RESPIRATORY";
    public static final String TAB_ID_GASTROINTESTINAL = "TAB_ID_GASTROINTESTINAL";
    public static final String TAB_ID_CARDIOVASCULAR = "TAB_ID_CARDIOVASCULAR";
    public static final String TAB_ID_NEUROLOGICAL = "TAB_ID_NEUROLOGICAL";

    private String[] tabTextsKor = {"피부", "호흡",  "소화", "심혈", "신경"};
    private String[] tabTextsEng = {"Skin", "Respiratory", "Gastrointestinal", "Cardiovascular", "Neurological"};
    private String[] tabTextsChi = {"皮肤","呼吸","胃肠道","心血管", "神经学"};

    // TOOLBAR
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    TextView toolbarTitle;
    Button toolbarBtn;
    Button languageButton;

    // Tab
    TabHost tabHost;
    ConversationCustomTabView customTabView;

    // etc...
    ViewPager pager;
    TabsAdapter mAdapter;


    //ListView conversationListView;
    //ConversationListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);


        // Navigation Drawer (Toolbar) Setting
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbarBtn = (Button) findViewById(R.id.nav_hamburger_btn);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        languageButton = (Button) findViewById(R.id.btn_language);
        toolbarBtn.setOnClickListener(this);


        // setting EventListener Nav Buttons
        findViewById(R.id.nav_drawer_component_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_main_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_conversation_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_map_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_star_btn).setOnClickListener(this);
        languageButton.setOnClickListener(this);

        // TAB Settings
        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();
        customTabView = (ConversationCustomTabView) findViewById(R.id.customTab);
        // add Tab Text into custom TextView... (Language setting에서도 고칠 것)
        for (String tab : tabTextsEng) {
            customTabView.addTab(tab);
        }
        customTabView.setCurrentTab(0);
        customTabView.setOnTabClcickedListener(this);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.addOnPageChangeListener(this);
        mAdapter = new TabsAdapter(this, getSupportFragmentManager(), tabHost, pager);

        // Fragment Params Settings & add Fragment to TAB
        Bundle[] tabParams = {new Bundle(), new Bundle(), new Bundle(), new Bundle(), new Bundle()};
        tabParams[0].putString(ConversationFragment.KEY_FRAGMENT_TYPE, ConversationFragment.TYPE_SKIN);
        tabParams[1].putString(ConversationFragment.KEY_FRAGMENT_TYPE, ConversationFragment.TYPE_RESPIRATORY);
        tabParams[2].putString(ConversationFragment.KEY_FRAGMENT_TYPE, ConversationFragment.TYPE_GASTROINTESTINAL);
        tabParams[3].putString(ConversationFragment.KEY_FRAGMENT_TYPE, ConversationFragment.TYPE_CARDIOVASCULAR);
        tabParams[4].putString(ConversationFragment.KEY_FRAGMENT_TYPE, ConversationFragment.TYPE_NEUROLOGICAL);

        mAdapter.addTab(tabHost.newTabSpec(TAB_ID_SKIN).setIndicator("피부"), ConversationFragment.class, tabParams[0]);
        mAdapter.addTab(tabHost.newTabSpec(TAB_ID_RESPIRATORY).setIndicator("호흡"), ConversationFragment.class, tabParams[1]);
        mAdapter.addTab(tabHost.newTabSpec(TAB_ID_GASTROINTESTINAL).setIndicator("소화"), ConversationFragment.class, tabParams[2]);
        mAdapter.addTab(tabHost.newTabSpec(TAB_ID_CARDIOVASCULAR).setIndicator("심혈"), ConversationFragment.class, tabParams[3]);
        mAdapter.addTab(tabHost.newTabSpec(TAB_ID_NEUROLOGICAL).setIndicator("신경"), ConversationFragment.class, tabParams[4]);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            // NAVIGATION DRAWER BUTTONS
            case R.id.nav_hamburger_btn:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.nav_drawer_map_btn:
                drawerLayout.closeDrawers();
                startActivity(new Intent(ConversationActivity.this, MainActivity.class));
                finish();
                break;
            case R.id.nav_drawer_component_btn:
                drawerLayout.closeDrawers();
                startActivity(new Intent(ConversationActivity.this, ComponentActivity.class));
                finish();
                break;
            case R.id.nav_drawer_star_btn:
                drawerLayout.closeDrawers();
                break;
            case R.id.nav_drawer_conversation_btn:
                drawerLayout.closeDrawers();
                break;
            case R.id.nav_drawer_main_btn:
                drawerLayout.closeDrawers();
                finish();
                break;
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        customTabView.setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    // Tab Btn Clicked
    @Override
    public void onPreTabBtnClicked(int preTabPosition) {
        customTabView.setCurrentTab(preTabPosition);
        pager.setCurrentItem(preTabPosition , true);
    }

    @Override
    public void onPostTabBtnClicked(int postTabPosition) {
        customTabView.setCurrentTab(postTabPosition);
        pager.setCurrentItem(postTabPosition , true);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }
}
