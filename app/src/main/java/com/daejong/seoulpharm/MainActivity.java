package com.daejong.seoulpharm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daejong.seoulpharm.db.DBHelper;
import com.daejong.seoulpharm.mapviewer.NMapViewer;
import com.daejong.seoulpharm.model.PharmItem;
import com.daejong.seoulpharm.model.ResponseResult;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textView;
    DBHelper db;
    Button mapBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(MainActivity.this);
        textView = (TextView) findViewById(R.id.resultView);
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
                startActivity(new Intent(MainActivity.this, NMapViewer.class));
                break;
        }
    }
}
