package com.example.hyunwoo.samplenavermap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.hyunwoo.samplenavermap.model.ResponseResult;
import com.example.hyunwoo.samplenavermap.model.SebcPharmacyInfoKor;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.resultView);


        NetworkManager.getInstance().getPharms(MainActivity.this, new NetworkManager.OnResultListener<SebcPharmacyInfoKor>() {
            @Override
            public void onSuccess(SebcPharmacyInfoKor result) {
                textView.setText(result.listTotalCount);
            }

            @Override
            public void onFail(int code) {
                textView.setText("FAILED");
            }
        });

    }

//hi

}
