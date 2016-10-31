package com.daejong.seoulpharm.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.util.LanguageSelector;
import com.daejong.seoulpharm.widget.NotoTextView;

public class DasanCallActivity extends AppCompatActivity implements View.OnClickListener {

    // TOOLBAR
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NotoTextView toolbarTitle;
    Button toolbarBtn;
    Button languageButton;

    // VIEWS
    NotoTextView textPlzTouchIconView;
    NotoTextView textNeedHelpView;
    NotoTextView textDescriptionView;
    NotoTextView textAvailableTime;
    ImageView callBtn;


    // text_plz_touch_icon text_need_help text_description text_available_time

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasan_call);

        // Toolbar initialize
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbarBtn = (Button) findViewById(R.id.nav_hamburger_btn);
        toolbarTitle = (NotoTextView) findViewById(R.id.toolbar_title);

        //init language spinner
        languageButton = (Button) findViewById(R.id.spinner);
        languageButton.setOnClickListener(this);

        // View Initialize
        textPlzTouchIconView = (NotoTextView) findViewById(R.id.text_plz_touch_icon);
        textNeedHelpView = (NotoTextView) findViewById(R.id.text_need_help);
        textDescriptionView = (NotoTextView) findViewById(R.id.text_description);
        textAvailableTime = (NotoTextView) findViewById(R.id.text_available_time);
        callBtn = (ImageView) findViewById(R.id.btn_dasan_call);


        // Navigation Drawer (Toolbar) Setting
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        toolbarBtn.setOnClickListener(this);

        // setting EventListener Nav Buttons
        findViewById(R.id.nav_drawer_component_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_main_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_conversation_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_map_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_star_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_tutorial).setOnClickListener(this);
        findViewById(R.id.nav_drawer_dasan_call_btn).setOnClickListener(this);

        // setting EventListener in this Activity
        callBtn.setOnClickListener(this);

        // setting language selector
        setOnLanguageChangeListener();
        LanguageSelector.getInstance().syncLanguage();

        int currentLanguage = LanguageSelector.getInstance().getCurrentLanguage();
        initViewsLanguageText(currentLanguage);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            // CLICK EVENT IN THIS ACTIVITY
            case R.id.btn_dasan_call :
                try {
                    String phoneNum = "120";
                    Uri uri = Uri.parse("tel:" + phoneNum);
                    Intent intent = new Intent(Intent.ACTION_CALL, uri);
                    startActivity(intent);
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
                break;

            // NAVIGATION DRAWER BUTTONS
            case R.id.nav_hamburger_btn:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.nav_drawer_map_btn:
                drawerLayout.closeDrawers();
                finish();
                break;
            case R.id.nav_drawer_dasan_call_btn:
                drawerLayout.closeDrawers();
                break;
            case R.id.nav_drawer_component_btn:
                drawerLayout.closeDrawers();
                finish();
                startActivity(new Intent(DasanCallActivity.this, ComponentActivity.class));
                break;
            case R.id.nav_drawer_star_btn:
                drawerLayout.closeDrawers();
                finish();
                startActivity(new Intent(DasanCallActivity.this, ScrapActivity.class));
                break;
            case R.id.nav_drawer_main_btn:
                drawerLayout.closeDrawers();
                finish();
                break;
            case R.id.nav_drawer_conversation_btn:
                drawerLayout.closeDrawers();
                finish();
                startActivity(new Intent(DasanCallActivity.this, ConversationActivity.class));
                break;
            case R.id.nav_drawer_tutorial:
                drawerLayout.closeDrawers();
                finish();
                startActivity(new Intent(DasanCallActivity.this, TutorialActivity.class));
                break;

            // language selector
            case R.id.spinner:
                PopupMenu pum = new PopupMenu(DasanCallActivity.this, findViewById(R.id.spinner));
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

    private void initViewsLanguageText(int currentLanguage) {
        switch (currentLanguage) {
            case R.drawable.btn_kor :
                toolbarTitle.setText("다산콜센터");
                textPlzTouchIconView.setText(R.string.dasan_kor_plz_touch_icon);
                textNeedHelpView.setText(R.string.dasan_kor_need_help);
                textDescriptionView.setText(R.string.dasan_kor_description);
                textAvailableTime.setText(R.string.dasan_kor_available_time);
                break;
            case R.drawable.btn_eng :
                toolbarTitle.setText("Dasan Call Center");
                textPlzTouchIconView.setText(R.string.dasan_eng_plz_touch_icon);
                textNeedHelpView.setText(R.string.dasan_eng_need_help);
                textDescriptionView.setText(R.string.dasan_eng_description);
                textAvailableTime.setText(R.string.dasan_eng_available_time);
                break;
            case R.drawable.btn_china :
                toolbarTitle.setText("首尔茶山热线");
                textPlzTouchIconView.setText(R.string.dasan_chi_plz_touch_icon);
                textNeedHelpView.setText(R.string.dasan_chi_need_help);
                textDescriptionView.setText(R.string.dasan_chi_description);
                textAvailableTime.setText(R.string.dasan_chi_available_time);
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setOnLanguageChangeListener();
        LanguageSelector.getInstance().syncLanguage();
    }

    //set about language
    void setOnLanguageChangeListener() {
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
                        break;

                    case R.drawable.btn_eng:
                        ((TextView) findViewById(R.id.nav_drawer_main_text)).setText("Main");
                        ((TextView) findViewById(R.id.nav_drawer_map_text)).setText("Search Pharmacies");
                        ((TextView) findViewById(R.id.nav_drawer_conversation_text)).setText("Translate Symptoms");
                        ((TextView) findViewById(R.id.nav_drawer_component_text)).setText("Drug Information");
                        ((TextView) findViewById(R.id.nav_drawer_dasan_call_text)).setText("Dasan Call Center");
                        ((TextView) findViewById(R.id.nav_drawer_star_text)).setText("Bookmarks");
                        ((TextView) findViewById(R.id.nav_drawer_tutorial_text)).setText("Tutorial");
                        break;

                    case R.drawable.btn_china:
                        ((TextView) findViewById(R.id.nav_drawer_main_text)).setText("主页");
                        ((TextView) findViewById(R.id.nav_drawer_map_text)).setText("寻找药店");
                        ((TextView) findViewById(R.id.nav_drawer_conversation_text)).setText("说明症状");
                        ((TextView) findViewById(R.id.nav_drawer_component_text)).setText("确认药品成分");
                        ((TextView) findViewById(R.id.nav_drawer_dasan_call_text)).setText("首尔茶山热线");
                        ((TextView) findViewById(R.id.nav_drawer_star_text)).setText("检索书签");
                        ((TextView) findViewById(R.id.nav_drawer_tutorial_text)).setText("教程");
                        break;
                }
            }
        };
        LanguageSelector.getInstance().setOnLanguageChangeListener(mOnLanguageChangeListener);
    }
}
