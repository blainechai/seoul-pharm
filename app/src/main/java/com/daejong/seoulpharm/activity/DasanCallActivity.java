package com.daejong.seoulpharm.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.widget.NotoTextView;

public class DasanCallActivity extends AppCompatActivity {

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

        // View Initialize
        textPlzTouchIconView = (NotoTextView) findViewById(R.id.text_plz_touch_icon);
        textNeedHelpView = (NotoTextView) findViewById(R.id.text_need_help);
        textDescriptionView = (NotoTextView) findViewById(R.id.text_description);
        textAvailableTime = (NotoTextView) findViewById(R.id.text_available_time);

        callBtn = (ImageView) findViewById(R.id.btn_dasan_call);
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNum = "120";
                Uri uri = Uri.parse("tel:" + phoneNum);
                Intent intent = new Intent(Intent.ACTION_CALL, uri);
                startActivity(intent);
            }
        });


    }
}
