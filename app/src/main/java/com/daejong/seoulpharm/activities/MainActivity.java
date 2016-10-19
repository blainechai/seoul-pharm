package com.daejong.seoulpharm.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.daejong.seoulpharm.NetworkManager;
import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.db.DBHelper;
import com.daejong.seoulpharm.model.PharmItem;
import com.daejong.seoulpharm.model.ResponseResult;


public class MainActivity extends AppCompatActivity {

    TextView textView;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(MainActivity.this);
        textView = (TextView) findViewById(R.id.resultView);


        NetworkManager.getInstance().getPharms(MainActivity.this, new NetworkManager.OnResultListener<ResponseResult>() {
            @Override
            public void onSuccess(ResponseResult result) {
                textView.setText(""+result.getSebPharmacyInfoKor().getRow().get(0).getNameKor());
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

}
