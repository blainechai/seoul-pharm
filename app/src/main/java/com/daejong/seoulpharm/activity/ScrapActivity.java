package com.daejong.seoulpharm.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.daejong.seoulpharm.util.OnLanguageChangeHandler;
import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.adapter.TabsAdapter;
import com.daejong.seoulpharm.fragment.ScrapFragment;
import com.daejong.seoulpharm.model.PharmItem;
import com.daejong.seoulpharm.util.LanguageSelector;
import com.daejong.seoulpharm.widget.NotoTextView;

import java.io.Serializable;

public class ScrapActivity extends AppCompatActivity implements View.OnClickListener, ScrapFragment.OnScrappedPharmListItemClickListener {

    public static final int RESULT_CODE = 1;
    public static final String KEY_RESULT_DATA = "KEY_RESULT_DATA";

    // TOOLBAR
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NotoTextView toolbarTitle;
    Button toolbarBtn;
    Button languageButton;

    // Tab
    TabHost tabHost;
    TextView tabPharms;
    TextView tabComponent;

    // etc...
    ViewPager pager;
    TabsAdapter mAdapter;

    private static final String TAB_ID_PHARMS = "TAB_ID_PHARMS";
    private static final String TAB_ID_COMPONENT = "TAB_ID_COMPONENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrap);

        // Navigation Drawer (Toolbar) Setting
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbarBtn = (Button) findViewById(R.id.nav_hamburger_btn);
        toolbarTitle = (NotoTextView) findViewById(R.id.toolbar_title);
        toolbarBtn.setOnClickListener(this);

        //init language spinner
        languageButton = (Button) findViewById(R.id.spinner);
        languageButton.setOnClickListener(this);

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

        pager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new TabsAdapter(this, getSupportFragmentManager(), tabHost, pager);
        setOnLanguageChangeHandler(mAdapter);

        // Fragment Params Settings
        Bundle pharmBundle = new Bundle();
        pharmBundle.putString(ScrapFragment.KEY_FRAGMENT_TYPE, ScrapFragment.TYPE_PHARMS);
        Bundle componentBundle = new Bundle();
        componentBundle.putString(ScrapFragment.KEY_FRAGMENT_TYPE, ScrapFragment.TYPE_COMPONENT);

        // Add Fragment to TAB
        mAdapter.addTab(tabHost.newTabSpec(TAB_ID_PHARMS).setIndicator("약국"), ScrapFragment.class, pharmBundle);
        mAdapter.addTab(tabHost.newTabSpec(TAB_ID_COMPONENT).setIndicator("의약품"), ScrapFragment.class, componentBundle);

        //set tab font
        TabWidget tw = (TabWidget) tabHost.findViewById(android.R.id.tabs);
        for (int i = 0; i < tw.getChildCount(); ++i) {
            final View tabView = tw.getChildTabViewAt(i);
            final TextView tv = (TextView) tabView.findViewById(android.R.id.title);
            tv.setTextSize(16);
            tv.setTextColor(Color.parseColor("#5f5f5f"));
            tv.setTypeface(Typeface.createFromAsset(this.getAssets(), "NotoSansKR-Regular-Hestia.otf"));
        }

        // get tab's textview
        tabPharms = (TextView) tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
        tabComponent = (TextView) tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);


        // language sync
        setOnLanguageChangeListener();
        LanguageSelector.getInstance().syncLanguage();
    }


    /**
     * TODO : NAVIGATION DRAWER BUTTON CLICK EVENT 처리
     */

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            // NAVIGATION DRAWER BUTTONS
            case R.id.nav_hamburger_btn:
                drawerLayout.openDrawer(Gravity.LEFT);
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
                startActivity(new Intent(ScrapActivity.this, ScrapActivity.class));
                finish();
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

            case R.id.spinner:
                PopupMenu pum = new PopupMenu(ScrapActivity.this, findViewById(R.id.spinner));
                getMenuInflater().inflate(R.menu.language_chooser_popup, pum.getMenu());
                pum.show();
                pum.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // TODO Auto-generated method stub
                        switch (item.getItemId()) {//눌러진 MenuItem의 Item Id를 얻어와 식별
                            case R.id.menu_item_kor:
                                LanguageSelector.getInstance().changeLanguage(R.drawable.btn_kor);
                                break;

                            case R.id.menu_item_eng:
                                LanguageSelector.getInstance().changeLanguage(R.drawable.btn_eng);
                                break;

                            case R.id.menu_item_china:
                                LanguageSelector.getInstance().changeLanguage(R.drawable.btn_china);
                                break;
                        }
                        return false;
                    }
                });
                break;
        }
    }

    //set about language
    void setOnLanguageChangeListener(){
        LanguageSelector.OnLanguageChangeListener mOnLanguageChangeListener = new LanguageSelector.OnLanguageChangeListener() {
            @Override
            public void setViewContentsByLanguage(int id) {
                languageButton.setBackgroundResource(id);
                initViewsLanguageText(id);
                switch (id) {
                    case R.drawable.btn_kor:
                        ((TextView) findViewById(R.id.nav_drawer_main_text)).setText("메인페이지");
                        ((TextView) findViewById(R.id.nav_drawer_map_text)).setText("약국찾기");
                        ((TextView) findViewById(R.id.nav_drawer_conversation_text)).setText("증상설명");
                        ((TextView) findViewById(R.id.nav_drawer_component_text)).setText("약 성분 확인");
                        ((TextView) findViewById(R.id.nav_drawer_dasan_call_text)).setText("다산콜센터");
                        ((TextView) findViewById(R.id.nav_drawer_star_text)).setText("스크랩");
                        ((TextView) findViewById(R.id.nav_drawer_tutorial_text)).setText("튜토리얼");
                        mLanguageHandler.onLanguageChanged(id);
                        break;

                    case R.drawable.btn_eng:
                        ((TextView) findViewById(R.id.nav_drawer_main_text)).setText("Main");
                        ((TextView) findViewById(R.id.nav_drawer_map_text)).setText("Search Pharmacies");
                        ((TextView) findViewById(R.id.nav_drawer_conversation_text)).setText("Translate Symptoms");
                        ((TextView) findViewById(R.id.nav_drawer_component_text)).setText("Drug Information");
                        ((TextView) findViewById(R.id.nav_drawer_dasan_call_text)).setText("Dasan Call Center");
                        ((TextView) findViewById(R.id.nav_drawer_star_text)).setText("Bookmarks");
                        ((TextView) findViewById(R.id.nav_drawer_tutorial_text)).setText("Tutorial");
                        mLanguageHandler.onLanguageChanged(id);
                        break;

                    case R.drawable.btn_china:
                        ((TextView) findViewById(R.id.nav_drawer_main_text)).setText("主页");
                        ((TextView) findViewById(R.id.nav_drawer_map_text)).setText("寻找药店");
                        ((TextView) findViewById(R.id.nav_drawer_conversation_text)).setText("说明症状");
                        ((TextView) findViewById(R.id.nav_drawer_component_text)).setText("确认药品成分");
                        ((TextView) findViewById(R.id.nav_drawer_dasan_call_text)).setText("首尔茶山热线");
                        ((TextView) findViewById(R.id.nav_drawer_star_text)).setText("检索书签");
                        ((TextView) findViewById(R.id.nav_drawer_tutorial_text)).setText("教程");
                        mLanguageHandler.onLanguageChanged(id);
                        break;
                }
            }
        };
        LanguageSelector.getInstance().setOnLanguageChangeListener(mOnLanguageChangeListener);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            // NavigationDrawer가 열려있는 경우 >> Drawer close
            drawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

    public void initViewsLanguageText(int currentLanguage) {
        switch (currentLanguage) {
            case R.drawable.btn_kor :
                toolbarTitle.setText("스크랩");
                tabPharms.setText("약국");
                tabComponent.setText("의약품");
                break;
            case R.drawable.btn_eng :
                toolbarTitle.setText("Bookmarks");
                tabPharms.setText("Pharmacies");
                tabComponent.setText("Drugs");
                break;
            case R.drawable.btn_china :
                toolbarTitle.setText("检索书签");
                tabPharms.setText("药房");
                tabComponent.setText("药");
                break;
        }
    }


    @Override
    public void onScrappedPharmItemClicked(PharmItem item) {
        Intent intent = new Intent();
        intent.putExtra(KEY_RESULT_DATA, (Serializable) item);
        setResult(RESULT_CODE, intent);
        finish();
    }

    OnLanguageChangeHandler mLanguageHandler;
    public void setOnLanguageChangeHandler(OnLanguageChangeHandler handler) {
        mLanguageHandler = handler;
    }

}
