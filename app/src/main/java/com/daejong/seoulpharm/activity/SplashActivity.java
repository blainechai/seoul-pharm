package com.daejong.seoulpharm.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.daejong.seoulpharm.R;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    Handler mHandler = new Handler(Looper.getMainLooper());

    /** 두 가지 경우를 체크
     * 1) DB가 Initialize 되었는가?
     * 2) 필요한 Permission들이 Runtime으로 허가되었는가? (API >= 23)
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


//        checkAndRequestPermissions();
//        checkDBInitialized();
        if (checkAndRequestPermissions()) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }, 2000);
        }


    }

    /** 1. DB Initialize
     *
     *
     */
    private boolean checkDBInitialized() {
        return true;
    }



    /** 2. Request permissions at Runtime
     * ACCESS_FINE_LOCATION
     * ACCESS_COARSE_LOCATION
     * CAMERA
     */

    String[] permissions= new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA };


    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 100;
    private boolean checkAndRequestPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();

        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(SplashActivity.this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS : {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 권한 허가
                    // 해당 권한을 사용해서 작업을 진행할 수 있습니다
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));


                } else {
                    // 권한 거부
                    // 사용자가 해당권한을 거부했을때 해주어야 할 동작을 수행합니다.
                    // 종료
                    Toast.makeText(SplashActivity.this, "App 실행 시 필요한 권한을 허가해주세요", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }

    }
}
