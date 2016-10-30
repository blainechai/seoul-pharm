package com.daejong.seoulpharm.activity;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.fragment.ComponentScannerFragment;
import com.daejong.seoulpharm.util.LanguageSelector;

public class ComponentActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Toolbar toolbar;
    Button toolbarBtn;
    TextView toolbarTitle;
    DrawerLayout drawerLayout;
    Button languageButton;

    public static LanguageSelector.OnLanguageChangeListener onLanguageChangeListener;

    String[] language = {"한국어", "中国语", "ENG"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component);

        toolbar = (Toolbar) findViewById(R.id.component_toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        toolbarBtn = (Button) findViewById(R.id.nav_hamburger_btn);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);

        toolbarBtn.setOnClickListener(this);
        // Nav Buttons Setting
        findViewById(R.id.nav_drawer_component_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_main_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_conversation_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_map_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_star_btn).setOnClickListener(this);


        Button languageButton = (Button) findViewById(R.id.btn_language);
        languageButton.setOnClickListener(this);
        languageButton.setText(LanguageSelector.getInstance().getCurrentLanguage());

//        Spinner spin = (Spinner) findViewById(R.id.spinner);
//        spin.setOnItemSelectedListener(this);
//        LanguageSpinnerAdapter languageSpinnerAdapter=new LanguageSpinnerAdapter(getApplicationContext(),language);
//        spin.setAdapter(languageSpinnerAdapter);
//        spin.getSelectedItem();



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
            case R.id.nav_hamburger_btn:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;

            case R.id.nav_drawer_main_btn:
                drawerLayout.closeDrawers();
                finish();
                break;
            case R.id.nav_drawer_map_btn:
                drawerLayout.closeDrawers();
                finish();
                break;
            case R.id.nav_drawer_component_btn:
                drawerLayout.closeDrawers();
                startActivity(new Intent(ComponentActivity.this, ComponentActivity.class));
                finish();
                break;
            case R.id.nav_drawer_star_btn:
                drawerLayout.closeDrawers();
                startActivity(new Intent(ComponentActivity.this, ScrapActivity.class));
                finish();
                break;
            case R.id.nav_drawer_conversation_btn:
                drawerLayout.closeDrawers();
                finish();
                startActivity(new Intent(ComponentActivity.this, ConversationActivity.class));
                break;

            case R.id.btn_language:
                LanguageSelector.getInstance().changeLanguage();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), language[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }
}
