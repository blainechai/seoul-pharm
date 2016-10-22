package com.daejong.seoulpharm.activity;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daejong.seoulpharm.util.NetworkManager;
import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.db.DBHelper;
import com.daejong.seoulpharm.model.PharmItem;
import com.daejong.seoulpharm.model.ResponseResult;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView currentAddressView;
    DBHelper db;
    ImageView mapBtn;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(MainActivity.this);
        currentAddressView = (TextView) findViewById(R.id.current_address_view);
//        NetworkManager.getInstance().getAddress(MainActivity.this, );

        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
//        toolbar.setTitle("Seoul Pharm");
//        toolbar.setTitleMarginStart(16);
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        setSupportActionBar(toolbar);
        setDrawerToggle();

        // Nav Buttons Setting
        findViewById(R.id.nav_drawer_component_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_config_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_main_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_conversation_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_map_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_star_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_tutorial_btn).setOnClickListener(this);

        // setting Buttons in this activity
        mapBtn = (ImageView) findViewById(R.id.btn_map);
        mapBtn.setOnClickListener(this);
        findViewById(R.id.btn_conversation).setOnClickListener(this);


/*
        NetworkManager.getInstance().getPharms(MainActivity.this, new NetworkManager.OnResultListener<ResponseResult>() {
            @Override
            public void onSuccess(ResponseResult result) {
                textView.setText("" + result.getSebPharmacyInfoKor().getRow().get(0).getNameKor());
                // insert data
                for (PharmItem item : result.getSebPharmacyInfoKor().getRow()) {
                    db.addPharmItem(item);
                }
            }

            @Override
            public void onFail(int code) {
                textView.setText("FAILED");
            }
        });
*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_map:
                startActivity(new Intent(MainActivity.this, MapActivity.class));
                break;
            case R.id.btn_conversation:

                NetworkManager.getInstance().getEngAddress(this, new NetworkManager.OnResultListener<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Toast.makeText(MainActivity.this, ""+result, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(int code, String responseString) {
                        Log.d("REQTESTTTT","error code:"+code+"\n"+responseString);
                    }
                });

                break;

            case R.id.nav_drawer_tutorial_btn:
//                startActivity(new Intent(MainActivity.this, MapActivity.class));
                break;
            case R.id.nav_drawer_map_btn:
                startActivity(new Intent(MainActivity.this, MapActivity.class));
                break;
            case R.id.nav_drawer_component_btn:
                startActivity(new Intent(MainActivity.this, ComponentActivity.class));
                break;
            case R.id.nav_drawer_star_btn:
//                startActivity(new Intent(MainActivity.this, MapActivity.class));
                break;
            case R.id.nav_drawer_config_btn:
//                startActivity(new Intent(MainActivity.this, MapActivity.class));
                break;
            case R.id.nav_drawer_main_btn:
//                startActivity(new Intent(MainActivity.this, MapActivity.class));
                drawerLayout.closeDrawers();
                break;
            case R.id.nav_drawer_conversation_btn:
                startActivity(new Intent(MainActivity.this, ConversationActivity.class));
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
