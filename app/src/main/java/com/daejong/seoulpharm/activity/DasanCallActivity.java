package com.daejong.seoulpharm.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.util.LanguageSelector;
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
                try {
                    String phoneNum = "120";
                    Uri uri = Uri.parse("tel:" + phoneNum);
                    Intent intent = new Intent(Intent.ACTION_CALL, uri);
                    startActivity(intent);
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            }
        });

        int currentLanguage = LanguageSelector.getInstance().getCurrentLanguage();
        initViewsLanguageText(currentLanguage);

    }



    private void initViewsLanguageText(int currentLanguage) {
        switch (currentLanguage) {
            case R.drawable.btn_kor :
                textPlzTouchIconView.setText(R.string.dasan_kor_plz_touch_icon);
                textNeedHelpView.setText(R.string.dasan_kor_need_help);
                textDescriptionView.setText(R.string.dasan_kor_description);
                textAvailableTime.setText(R.string.dasan_kor_available_time);
                break;
            case R.drawable.btn_eng :
                textPlzTouchIconView.setText(R.string.dasan_eng_plz_touch_icon);
                textNeedHelpView.setText(R.string.dasan_eng_need_help);
                textDescriptionView.setText(R.string.dasan_eng_description);
                textAvailableTime.setText(R.string.dasan_eng_available_time);
                break;
            case R.drawable.btn_china :
                textPlzTouchIconView.setText(R.string.dasan_chi_plz_touch_icon);
                textNeedHelpView.setText(R.string.dasan_chi_need_help);
                textDescriptionView.setText(R.string.dasan_chi_description);
                textAvailableTime.setText(R.string.dasan_chi_available_time);
                break;

        }
    }
}
