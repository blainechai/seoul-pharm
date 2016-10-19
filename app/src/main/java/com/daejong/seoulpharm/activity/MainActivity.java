package com.daejong.seoulpharm.activity;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daejong.seoulpharm.util.NetworkManager;
import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.db.DBHelper;
import com.daejong.seoulpharm.model.PharmItem;
import com.daejong.seoulpharm.model.ResponseResult;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    TextView textView;
    DBHelper db;
    Button mapBtn;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.main_toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        db = new DBHelper(MainActivity.this);
        textView = (TextView) findViewById(R.id.resultView);

        setSupportActionBar(toolbar);
        setDrawerToggle();

        mapBtn = (Button) findViewById(R.id.map_btn);

        mapBtn.setOnClickListener(this);

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

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.map_btn:
                startActivity(new Intent(MainActivity.this, MapActivity.class));
                break;
        }
    }

    private void setDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
//        drawerLayout.setDrawerListener(mDrawerToggle);
        Log.d("hi", "" + mDrawerToggle.isDrawerIndicatorEnabled());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
}
