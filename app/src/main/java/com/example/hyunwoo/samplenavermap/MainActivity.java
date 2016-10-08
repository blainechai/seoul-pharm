package com.example.hyunwoo.samplenavermap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.hyunwoo.samplenavermap.db.DBHelper;
import com.example.hyunwoo.samplenavermap.model.PharmItem;
import com.example.hyunwoo.samplenavermap.model.ResponseResult;
import com.example.hyunwoo.samplenavermap.model.SebcPharmacyInfoKor;

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
                // insert DB
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
