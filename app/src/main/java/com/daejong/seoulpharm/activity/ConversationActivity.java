package com.daejong.seoulpharm.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.daejong.seoulpharm.adapter.ConversationListAdapter;
import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.adapter.TabsAdapter;
import com.daejong.seoulpharm.fragment.ConversationFragment;
import com.daejong.seoulpharm.view.ConversationItemView;

public class ConversationActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAB_ID_SKIN = "TAB_ID_SKIN";
    public static final String TAB_ID_RESPIRATORY = "TAB_ID_RESPIRATORY";
    public static final String TAB_ID_GASTROINTESTINAL = "TAB_ID_GASTROINTESTINAL";
    public static final String TAB_ID_CARDIOVASCULAR = "TAB_ID_CARDIOVASCULAR";
    public static final String TAB_ID_NEUROLOGICAL = "TAB_ID_NEUROLOGICAL";

    // TOOLBAR
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    TextView toolbarTitle;
    Button toolbarBtn;
    Button languageButton;

    // Tab
    TabHost tabHost;

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
            case R.id.nav_drawer_config_btn:
                drawerLayout.closeDrawers();
                break;
        }
    }

    public interface OnBackKeyPressedListener {
        public void onBackPressed();
    }
    private OnBackKeyPressedListener mOnBackKeyPressedListener;

    public void setOnBackKeyPressedListener(OnBackKeyPressedListener listener) {
        mOnBackKeyPressedListener = listener;
    }

    @Override
    public void onBackPressed() {
        if (mOnBackKeyPressedListener != null) {
            mOnBackKeyPressedListener.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }
}
