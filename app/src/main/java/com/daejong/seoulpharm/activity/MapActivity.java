package com.daejong.seoulpharm.activity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.fragment.MapHistoryFragment;
import com.daejong.seoulpharm.navermap.NMapPOIflagType;
import com.daejong.seoulpharm.navermap.NMapViewerResourceProvider;
import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.mapviewer.overlay.NMapMyLocationOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends NMapActivity implements View.OnClickListener, NMapView.OnMapStateChangeListener, NMapView.OnMapViewTouchEventListener {

    // VIEWS
    NMapView nMapView;
    EditText searchInputView;
    Button cancelBtn;
    DrawerLayout drawerLayout;

    // NAVER MAP API KEY
    public static final String CLIENT_ID = "s3q7uwJzMyOjOZfTnYDK";

    // NAVER MAP OBJECT
    private NMapController nMapController;
    private NMapOverlayManager nMapOverlayManager;
    private NMapLocationManager nMapLocationManager;
    private NMapViewerResourceProvider nMapViewerResourceProvider;
    private NMapMyLocationOverlay nMapMyLocationOverlay;

    // Location Manager
    LocationManager mLM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Navigation Drawer Setting
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        findViewById(R.id.nav_hamburger_btn).setOnClickListener(this);

        // View initialize
        nMapView = (NMapView) findViewById(R.id.mapView);
        searchInputView = (EditText) findViewById(R.id.search_input_view);
        cancelBtn = (Button) findViewById(R.id.cancel_btn);
        searchInputView.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);

        // 현재 위치를 받아올 수 있는 Location Manager Setting
        mLM = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // NMap Initialize
        nMapInit();

        // 초기 화면은 Map이 보여지는 모드로! (MapViewMode: 지도모드 / InputMode: 검색모드)
        goToMapViewMode();

    }

    @Override
    protected void onStart() {
        super.onStart();
        registerLocationListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unRegisterLocationListener();
    }

    // Click Event 처리
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nav_hamburger_btn :
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.search_input_view :
                goToInputMode();
                break;
            case R.id.cancel_btn :
                goToMapViewMode();
                break;
        }
    }

    // NaverMap에 필요한 객체들 초기화
    private void nMapInit() {
        // MapView initialize
        nMapView.setClientId(CLIENT_ID);
        nMapView.setClickable(true);
        nMapView.setBuiltInZoomControls(true, null);
        nMapView.setOnMapStateChangeListener(this);
        nMapView.setScalingFactor(4.0f);        // Map 확대 배율

        // 지도 조작 컨트롤러 생성
        nMapController = nMapView.getMapController();

        // LocationManager
        nMapLocationManager = new NMapLocationManager(this);

        // 오버레이 리소스 관리객체 할당
        nMapViewerResourceProvider = new NMapViewerResourceProvider(this);  // ResourceProvider

        // 오버레이 관리자 추가
        nMapOverlayManager = new NMapOverlayManager(this, nMapView, nMapViewerResourceProvider);    // OverlayManager

        // MyLocationOverlay
        nMapMyLocationOverlay = new NMapMyLocationOverlay(this, nMapView, nMapLocationManager, null, nMapViewerResourceProvider);
    }

    // Mode 변경 함수들 (지도 모드 / 검색 모드)
    private void goToInputMode() {          // 지도모드 > 검색모드
        pushMapHistoryFragment();
        cancelBtn.setVisibility(View.VISIBLE);
        nMapView.setVisibility(View.GONE);
    }
    private void goToMapViewMode() {        // 검색모드 > 지도모드
        popFragment();
        cancelBtn.setVisibility(View.GONE);
        nMapView.setVisibility(View.VISIBLE);
        // soft keyboard close
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
    // Handling Fragment
    // 검색 모드에서의 History List를 불러옴
    public void pushMapHistoryFragment() {
        getFragmentManager().beginTransaction().replace(R.id.container, new MapHistoryFragment()).addToBackStack(null).commit();
    }
    public void popFragment() {
        getFragmentManager().popBackStack();
    }


/*
    private void startMyLocation() {

        if (nMapMyLocationOverlay != null) {
            if (!nMapOverlayManager.hasOverlay(nMapMyLocationOverlay)) {
                nMapOverlayManager.addOverlay(nMapMyLocationOverlay);
            }

            if (nMapLocationManager.isMyLocationEnabled()) {

                if (!nMapView.isAutoRotateEnabled()) {
                    nMapMyLocationOverlay.setCompassHeadingVisible(true);

                    // mMapCompassManager.enableCompass();

                    nMapView.setAutoRotateEnabled(true, false);

                    // mMapContainerView.requestLayout();
                } else {
                    stopMyLocation();
                }

                nMapView.postInvalidate();
            } else {
                boolean isMyLocationEnabled = nMapLocationManager.enableMyLocation(true);
                if (!isMyLocationEnabled) {
                    Toast.makeText(MapActivity.this, "Please enable a My Location source in system settings",
                            Toast.LENGTH_LONG).show();

                    Intent goToSettings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(goToSettings);

                    return;
                }
            }
        }
    }

    private void stopMyLocation() {
        if (nMapMyLocationOverlay != null) {
            nMapLocationManager.disableMyLocation();
            if (nMapView.isAutoRotateEnabled()) {
                nMapMyLocationOverlay.setCompassHeadingVisible(false);
                // mMapCompassManager.disableCompass();
                nMapView.setAutoRotateEnabled(false, false);
                //mMapContainerView.requestLayout();
            }
        }
    }
*/


    // ========== 지도 구현 ========== //
    List<NGeoPoint> pois = new ArrayList<>();
    private void setMap(NGeoPoint currentPos) {

        // 현재위치 검색
        registerLocationListener();

        // 현재 위치로 지도의 중심과 ZOOM 설정
        nMapController.setMapCenter(currentPos, 12);


        // ===== 근처 약국위치 가져오기 ===== //
        // 약국 위치들을 DB에서 읽어옴
        pois.add(new NGeoPoint(/* getLongtitude(), getLatitide() */));


        // ===== 가져온 위치들을 화면에 뿌리기 ===== //
        // 오버래이에 표시하기 위한 마커 이미지의 id값 생성
        int markerId = NMapPOIflagType.PIN;

        // 표시할 위치 데이터를 지정한다. -- 마지막 인자가 오버래이를 인식하기 위한 id값
        NMapPOIdata poiDatas = new NMapPOIdata(3, nMapViewerResourceProvider);
        poiDatas.beginPOIdata(3);
        // 현재 위치 등록
        poiDatas.addPOIitem(currentPos.getLongitude(), currentPos.getLatitude(), "현재위치", markerId, 0);  // PIN 바꾸기
        // for (NGeoPoint poi : searchedPharms) {
            poiDatas.addPOIitem(127.0630205, 37.5091300, "약국이름!!", markerId, 0);
        // }

        poiDatas.endPOIdata();

        // 위치 데이터를 사용하여 오버레이 생성
        NMapPOIdataOverlay poiDataOverlay = nMapOverlayManager.createPOIdataOverlay(poiDatas, null);

    }


    // 현재 위치 정보 검색
    boolean isFirst = true;
    private void registerLocationListener() {
        // 위치 정보 설정이 Enabled 상태인지 확인
        // Enabled 상태가 아니라면
        if (!mLM.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            if (isFirst) {
                isFirst = false;
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));        // 위치정보 설정으로 이동
            } else {
                Toast.makeText(MapActivity.this, "위치 정보 설정이 꺼져 있습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
            return;
        }

        // Enable 상태라면
        isFirst = true;
        Location location = mLM.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null) {
            mListener.onLocationChanged(location);
        }

        // 위치 정보 등록
        mLM.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, mListener, null);

        // Timeout 측정 시작
        Message msg = mHandler.obtainMessage(MESSAGE_TIMEOUT_LOCATION_UPDATE);
        mHandler.sendMessageDelayed(msg, TIMEOUT_LOCATION_UPDATE);
    }

    // 현재 위치 검색 해제
    private void unRegisterLocationListener() {
        mLM.removeUpdates(mListener);
        mHandler.removeMessages(MESSAGE_TIMEOUT_LOCATION_UPDATE);
    }

    // 위치정보 검색에 대한 Timeout 처리 (1분동안 위치 정보를 검색할 수 없을 때 TIMEOUT)
    private static final int MESSAGE_TIMEOUT_LOCATION_UPDATE = 1;
    private static final int TIMEOUT_LOCATION_UPDATE = 60 * 1000;
    Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_TIMEOUT_LOCATION_UPDATE :
                    Toast.makeText(MapActivity.this, "Timeout location update", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    NGeoPoint currentPos;
    // LocationListener : Application이 Location Service로부터 위치와 관련된 정보를 수신하기 위해 사용하는 interface
    LocationListener mListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            // 새로 fix된 위치정보가 있을 시 타임아웃 핸들러 끄기
            mHandler.removeMessages(MESSAGE_TIMEOUT_LOCATION_UPDATE);

            // Toast.makeText(MapActivity.this, ""+location.getLatitude(), Toast.LENGTH_SHORT).show();
            // 시작 경로를 설정
            currentPos = new NGeoPoint(location.getLongitude(), location.getLatitude());

            // 현재 위치를 중심으로 Map을 세팅
            setMap(currentPos);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // Provider의 상태 변경시 호출. status는 LocationProvider에 정의되어있음
            switch (status) {
                case LocationProvider.AVAILABLE :
                    Toast.makeText(MapActivity.this, "위치정보 기능을 사용할 수 있습니다", Toast.LENGTH_SHORT).show();
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Toast.makeText(MapActivity.this, "현재 위치정보 기능을 받아올 수 없습니다", Toast.LENGTH_SHORT).show();
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Toast.makeText(MapActivity.this, "위치정보 기능을 사용할 수 없습니다", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void onProviderEnabled(String provider) {
            // 설정에서 등록된 Provider가 enabled로 설정되면 호출
        }

        @Override
        public void onProviderDisabled(String provider) {
            // 설정에서 등록된 Provider가 disabled로 설정되면 호출
        }
    };



    // Naver Map implement methods
    @Override
    public void onMapInitHandler(NMapView nMapView, NMapError nMapError) {
    }
    @Override
    public void onMapCenterChange(NMapView nMapView, NGeoPoint nGeoPoint) {
        // 맵 중심이 변경됬을 때
        // 주소 재검색 후
        // 주소가 변경되었다면 약국 다시 뿌리기
    }
    @Override
    public void onMapCenterChangeFine(NMapView nMapView) {
    }
    @Override
    public void onZoomLevelChange(NMapView nMapView, int i) {
    }
    @Override
    public void onAnimationStateChange(NMapView nMapView, int i, int i1) {
    }
    @Override
    public void onLongPress(NMapView nMapView, MotionEvent motionEvent) {
    }
    @Override
    public void onLongPressCanceled(NMapView nMapView) {
    }
    @Override
    public void onTouchDown(NMapView nMapView, MotionEvent motionEvent) {
    }
    @Override
    public void onTouchUp(NMapView nMapView, MotionEvent motionEvent) {
    }
    @Override
    public void onScroll(NMapView nMapView, MotionEvent motionEvent, MotionEvent motionEvent1) {
    }
    @Override
    public void onSingleTapUp(NMapView nMapView, MotionEvent motionEvent) {
    }

}
