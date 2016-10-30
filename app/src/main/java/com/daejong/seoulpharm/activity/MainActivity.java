package com.daejong.seoulpharm.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daejong.seoulpharm.db.DBHelper;
import com.daejong.seoulpharm.model.PharmItem;
import com.daejong.seoulpharm.navermap.NMapPOIflagType;
import com.daejong.seoulpharm.navermap.NMapViewerResourceProvider;
import com.daejong.seoulpharm.util.LanguageSelector;
import com.daejong.seoulpharm.util.NetworkManager;
import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.widget.NotoTextView;
import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.NMapOverlay;
import com.nhn.android.maps.NMapOverlayItem;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.mapviewer.overlay.NMapCalloutOverlay;
import com.nhn.android.mapviewer.overlay.NMapMyLocationOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;

import java.util.List;


public class MainActivity extends NMapActivity implements View.OnClickListener, NMapView.OnMapStateChangeListener, NMapView.OnMapViewTouchEventListener, NMapOverlayManager.OnCalloutOverlayListener {

    // TOOLBAR
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NotoTextView toolbarTitle;
    Button toolbarBtn;
    Button languageButton;

    // VIEWS
    NotoTextView currentAddressView;
    NotoTextView currentRefreshView;

    NMapView nMapView;    // NAVER MAP VIEW
    NotoTextView detailNameView;
    NotoTextView detailAvailableLanguageView;
    NotoTextView detailAddressView;
    NotoTextView detailTelephoneView;
    ImageView detailBookmarkBtn;
    ImageView detailCallBtn;
    Button goMainBtn;

    // CONTAINERS
    LinearLayout btnContainer;
    LinearLayout mapDetailContainer;

    // NAVER MAP API KEY
    public static final String NAVER_MAP_CLIENT_ID = "s3q7uwJzMyOjOZfTnYDK";

    // NAVER MAP OBJECT
    private NMapController nMapController;
    private NMapOverlayManager nMapOverlayManager;
    private NMapLocationManager nMapLocationManager;
    private NMapViewerResourceProvider nMapViewerResourceProvider;
    private NMapMyLocationOverlay nMapMyLocationOverlay;

    // Location Manager
    LocationManager mLM;

    // Map DB
    DBHelper db;

    // Map List
    List<PharmItem> pharmList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar initialize
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbarBtn = (Button) findViewById(R.id.nav_hamburger_btn);
        toolbarTitle = (NotoTextView) findViewById(R.id.toolbar_title);

        //init language spinner
        languageButton = (Button) findViewById(R.id.spinner);
        languageButton.setOnClickListener(this);

        // containers initialize
        btnContainer = (LinearLayout) findViewById(R.id.panel_buttons);
        mapDetailContainer = (LinearLayout) findViewById(R.id.panel_map_detail);

        // Views initialize
        nMapView = (NMapView) findViewById(R.id.mapView);
        goMainBtn = (Button) findViewById(R.id.goMainBtn);
        goMainBtn.setOnClickListener(this);

        currentAddressView = (NotoTextView) findViewById(R.id.current_address_view);
        currentRefreshView = (NotoTextView) findViewById(R.id.current_refresh_view);
        // detail panel
        detailNameView = (NotoTextView) findViewById(R.id.text_title);
        detailAvailableLanguageView = (NotoTextView) findViewById(R.id.text_available_language);
        detailTelephoneView = (NotoTextView) findViewById(R.id.text_telephone);
        detailAddressView = (NotoTextView) findViewById(R.id.text_address);
        detailBookmarkBtn = (ImageView) findViewById(R.id.btn_bookmark);
        detailCallBtn = (ImageView) findViewById(R.id.btn_call);

        // NMap Initialize
        nMapInit();

        // get Pharms location in Database
        db = new DBHelper(MainActivity.this);
        pharmList = db.getPharmList();

        // Navigation Drawer (Toolbar) Setting
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        toolbarBtn.setOnClickListener(this);

        // setting EventListener Nav Buttons
        findViewById(R.id.nav_drawer_component_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_main_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_conversation_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_map_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_star_btn).setOnClickListener(this);

        // setting EventListener in this activity
        findViewById(R.id.current_refresh_view).setOnClickListener(this);
        findViewById(R.id.btn_dasan).setOnClickListener(this);
        findViewById(R.id.btn_conversation).setOnClickListener(this);
        findViewById(R.id.btn_component).setOnClickListener(this);
        findViewById(R.id.btn_scrap).setOnClickListener(this);

        // setting EventListener in Detail Panel
        detailBookmarkBtn.setOnClickListener(this);
        detailCallBtn.setOnClickListener(this);

        changeMode(MODE_MAIN);

        setOnLanguageChangeListener();
        LanguageSelector.getInstance().syncLanguage();
    }


    // MAIN ACTIVITY MODES
    private static final String MODE_MAP_DETAIL = "MODE_MAP_DETAIL";
    private static final String MODE_MAIN = "MODE_MAIN";
    private static final String TITLE_MAP_DETAIL = "약국 찾기";
    private static final String TITLE_MAIN = "Seoul Pharm";

    private String currentMode = MODE_MAIN;

    private void changeMode(String mode) {
        switch (mode) {
            case MODE_MAP_DETAIL:
                // set toolbar title
                toolbarTitle.setText(TITLE_MAP_DETAIL);

                // container gone
                btnContainer.setVisibility(View.GONE);
                goMainBtn.setVisibility(View.VISIBLE);
                // mapDetailContainer.setVisibility(View.VISIBLE);

                // animation
                Animation animDisappearToBottom = AnimationUtils.loadAnimation(MainActivity.this, R.anim.disappear_to_bottom);
                btnContainer.setAnimation(animDisappearToBottom);

                // change mode status
                currentMode = MODE_MAP_DETAIL;

                break;

            case MODE_MAIN:
                // set toolbar title
                toolbarTitle.setText(TITLE_MAIN);

                // container visible
                btnContainer.setVisibility(View.VISIBLE);
                mapDetailContainer.setVisibility(View.GONE);
                goMainBtn.setVisibility(View.GONE);

                // animation
                Animation animAppearFromBottom = AnimationUtils.loadAnimation(MainActivity.this, R.anim.appear_from_bottom);
                btnContainer.setAnimation(animAppearFromBottom);

                // change mode status
                currentMode = MODE_MAIN;

                break;

        }
    }


    // MAP DETAIL PANEL
    boolean isBookmarked;

    private void setDetailPanels(final PharmItem item) {

        // SET VISIBILITY AND ANIMATION
        if (mapDetailContainer.getVisibility() == View.GONE) {
            mapDetailContainer.setVisibility(View.VISIBLE);
            Animation animAppearFromBottom = AnimationUtils.loadAnimation(MainActivity.this, R.anim.appear_from_bottom);
            mapDetailContainer.setAnimation(animAppearFromBottom);
        }

        // SET BOOKMARK
        isBookmarked = db.searchPharmScrapped(item.getMainKey());
        if (isBookmarked) {
            // 즐겨찾기에 등록된 약국이라면
            detailBookmarkBtn.setImageResource(R.drawable.ic_map_btn_bookmark_on);
        } else {
            detailBookmarkBtn.setImageResource(R.drawable.ic_map_btn_bookmark_off);
        }

        // SET TEXTS
        detailNameView.setText(item.getNameKor());
        detailAvailableLanguageView.setText("| 외국어 가능 약국 |   " + item.getAvailLanKor());
        detailAddressView.setText(item.getAddressKor());
        detailTelephoneView.setText("Tel )  " + item.getTel());

        // SET CLICK EVENT LISTENER
        detailCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 전화연결
                String phoneNum = item.getTel();
                Uri uri = Uri.parse("tel:" + phoneNum);
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
            }
        });
        detailBookmarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBookmarked) {
                    db.deletePharmScrapped(item.getMainKey());
                    detailBookmarkBtn.setImageResource(R.drawable.ic_map_btn_bookmark_off);
                } else {
                    db.addPharmBookmark(item.getMainKey());
                    detailBookmarkBtn.setImageResource(R.drawable.ic_map_btn_bookmark_on);
                }
                isBookmarked = !isBookmarked;
            }
        });

    }

    // ========== 지도 초기화 ========== //
    private void nMapInit() {
        // MapView initialize
        nMapView.setClientId(NAVER_MAP_CLIENT_ID);
        nMapView.setClickable(true);
        nMapView.setBuiltInZoomControls(false, null);
        nMapView.setOnMapStateChangeListener(this);
        nMapView.setScalingFactor(4.0f);        // Map 확대 배율
        nMapView.setOnMapViewTouchEventListener(this);


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


    // ========== 지도 구현 ========== //
    private void setNaverMap(NGeoPoint currentPos) {

        // 현재 위치로 지도의 중심과 ZOOM 설정
        nMapController.setMapCenter(currentPos, 12);

        // ===== 가져온 위치들을 화면에 뿌리기 ===== //
        // 기존의 위치들을 지도에서 삭제
        nMapOverlayManager.removeMyLocationOverlay();
        nMapOverlayManager.removePersistentOverlay();
        nMapOverlayManager.clearOverlays();
        nMapOverlayManager.clearCalloutOverlay();

        // 오버래이에 표시하기 위한 마커 이미지의 id값 생성
        int pharmMarkerId = NMapPOIflagType.PIN_PHARM;
        int currentMarkerId = NMapPOIflagType.PIN_CURRENT_POS;

        // 표시할 위치 데이터를 지정한다. -- 마지막 인자가 오버래이를 인식하기 위한 id값
        NMapPOIdata poiDatas = new NMapPOIdata(pharmList.size() + 1, nMapViewerResourceProvider);

        poiDatas.beginPOIdata(pharmList.size() + 1);
        // 현재 위치 등록
        poiDatas.addPOIitem(currentPos.getLongitude(), currentPos.getLatitude(), "현재위치", currentMarkerId, 0);  // PIN 바꾸기
        for (PharmItem item : pharmList) {
            double latitude = Double.parseDouble(item.getLatitude());
            double longtitude = Double.parseDouble(item.getLongtitude());
//            Log.d(" LIST ADDED !! ","LAT : "+latitude + "  /  LNG : "+longtitude);
            poiDatas.addPOIitem(longtitude, latitude, item.getMainKey(), pharmMarkerId, 0);
        }
        poiDatas.endPOIdata();

        // 위치 데이터를 사용하여 오버레이 생성
        NMapPOIdataOverlay poiDataOverlay = nMapOverlayManager.createPOIdataOverlay(poiDatas, null);
        nMapOverlayManager.setOnCalloutOverlayListener(this);

    }


    // ========== 현재 위치 정보 검색 ========== //
    boolean isFirst = true;

    @TargetApi(23)
    private void registerLocationListener() {
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        try {
            mLM = (LocationManager) getSystemService(LOCATION_SERVICE);


            // 위치 정보 설정이 Enabled 상태인지 확인
            // Enabled 상태가 아니라면

            if (!mLM.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                if (isFirst) {
                    isFirst = false;
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));        // 위치정보 설정으로 이동
                } else {
                    Toast.makeText(MainActivity.this, "위치 정보 설정이 꺼져 있습니다.", Toast.LENGTH_SHORT).show();
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

        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    // ========== 현재 위치 정보 검색 해제 ========== //
    private void unRegisterLocationListener() {
        try {
            mLM.removeUpdates(mListener);
            mHandler.removeMessages(MESSAGE_TIMEOUT_LOCATION_UPDATE);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    // 위치정보 검색에 대한 Timeout 처리 (1분동안 위치 정보를 검색할 수 없을 때 TIMEOUT)
    private static final int MESSAGE_TIMEOUT_LOCATION_UPDATE = 1;
    private static final int TIMEOUT_LOCATION_UPDATE = 60 * 1000;
    Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_TIMEOUT_LOCATION_UPDATE:
                    Toast.makeText(MainActivity.this, "Timeout location update", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    // 위치 기반으로 주소 검색하여 주소 TextView 갱신
    private void findLocationAddress(NGeoPoint location) {
        NetworkManager.getInstance().getAddress(MainActivity.this, location.getLatitude(), location.getLongitude(), new NetworkManager.OnResultListener<String>() {
            @Override
            public void onSuccess(String result) {
                currentAddressView.setText(result);
            }

            @Override
            public void onFail(int code, String response) {
                Toast.makeText(MainActivity.this, "ERROR get current address code:" + code + "\n" + response, Toast.LENGTH_SHORT).show();
            }
        });
    }


    NGeoPoint currentPos;
    // LocationListener : Application이 Location Service로부터 위치와 관련된 정보를 수신하기 위해 사용하는 interface
    LocationListener mListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            // 새로 fix된 위치정보가 있을 시 타임아웃 핸들러 끄기
            mHandler.removeMessages(MESSAGE_TIMEOUT_LOCATION_UPDATE);

            // 현재 위치 저장
            currentPos = new NGeoPoint(location.getLongitude(), location.getLatitude());

            // 현재 위치를 중심으로 Map을 세팅
            setNaverMap(currentPos);

            // 주소 TextView를 현재 주소로
            findLocationAddress(currentPos);

            // Location이 변경되었으면 LocationListener 종료 (최초 한번만 위치를 찾도록)
            unRegisterLocationListener();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // Provider의 상태 변경시 호출. status는 LocationProvider에 정의되어있음
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Toast.makeText(MainActivity.this, "위치정보 기능을 사용할 수 있습니다", Toast.LENGTH_SHORT).show();
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Toast.makeText(MainActivity.this, "현재 위치정보 기능을 받아올 수 없습니다", Toast.LENGTH_SHORT).show();
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Toast.makeText(MainActivity.this, "위치정보 기능을 사용할 수 없습니다", Toast.LENGTH_SHORT).show();
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
        // 맵 중심이 변경됬을 때 중심 좌표의 주소 재검색
        Log.d("POS CHANGED", "latitude : " + nGeoPoint.getLatitude() + ", longtitude : " + nGeoPoint.getLongitude());
        findLocationAddress(nGeoPoint);

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
        Log.d("MAP TOUCH DOWN", "CLICKED");
        if (currentMode.equals(MODE_MAIN)) {
            Log.d("MAP TOUCH DOWN", "MODE CHANGED");
            changeMode(MODE_MAP_DETAIL);
        }
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

    // Overlay Click event callback
    @Override
    public NMapCalloutOverlay onCreateCalloutOverlay(NMapOverlay nMapOverlay, NMapOverlayItem nMapOverlayItem, Rect rect) {
        if (!currentMode.equals(MODE_MAP_DETAIL)) {
            changeMode(MODE_MAP_DETAIL);
        }

        // 클릭된 약국의 상세정보를 띄우기
        PharmItem clickedItem;
        clickedItem = db.getPharmItemByKey(nMapOverlayItem.getTitle());

        if (!nMapOverlayItem.getTitle().equals("현재위치")) {
            setDetailPanels(clickedItem);
            // 지도의 중심을 선택한 위치로 이동
            double lat = Double.parseDouble(clickedItem.getLatitude());
            double lng = Double.parseDouble(clickedItem.getLongtitude());
//            Log.d(" !!! CLICKED POI !!! ", "LATITUDE : " + lat +  " / LONGTITUDE : " +lng);
            nMapController.animateTo(new NGeoPoint(lng, lat));
        }

        return null;
    }


    // Click Event
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            // MAP REFRESH
            case R.id.current_refresh_view:
                registerLocationListener();
                break;
            case R.id.goMainBtn :
                changeMode(MODE_MAIN);
                break;

            // MAIN BUTTONS
            case R.id.btn_dasan:
                startActivity(new Intent(MainActivity.this, DasanCallActivity.class));
                break;
            case R.id.btn_conversation:
                startActivity(new Intent(MainActivity.this, ConversationActivity.class));
                break;
            case R.id.btn_component:
                startActivity(new Intent(MainActivity.this, ComponentActivity.class));
                break;
            case R.id.btn_scrap:
                startActivity(new Intent(MainActivity.this, ScrapActivity.class));
                break;

            // NAVIGATION DRAWER BUTTONS
            case R.id.nav_hamburger_btn:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.nav_drawer_map_btn:
                drawerLayout.closeDrawers();
                changeMode(MODE_MAP_DETAIL);
                break;
            case R.id.nav_drawer_component_btn:
                drawerLayout.closeDrawers();
                startActivity(new Intent(MainActivity.this, ComponentActivity.class));
                break;
            case R.id.nav_drawer_star_btn:
                drawerLayout.closeDrawers();
                startActivity(new Intent(MainActivity.this, ScrapActivity.class));
                break;
            case R.id.nav_drawer_main_btn:
                drawerLayout.closeDrawers();
                changeMode(MODE_MAIN);
                break;
            case R.id.nav_drawer_conversation_btn:
                drawerLayout.closeDrawers();
                startActivity(new Intent(MainActivity.this, ConversationActivity.class));
                break;

            case R.id.spinner:
                PopupMenu pum = new PopupMenu(MainActivity.this, findViewById(R.id.spinner));
                getMenuInflater().inflate(R.menu.language_chooser_popup, pum.getMenu());
                pum.show();
                pum.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // TODO Auto-generated method stub
                        switch (item.getItemId()) {//눌러진 MenuItem의 Item Id를 얻어와 식별
                            case R.id.menu_item_kor:
                                LanguageSelector.getInstance().changeLanguage(R.drawable.btn_kor);
                                break;

                            case R.id.menu_item_eng:
                                LanguageSelector.getInstance().changeLanguage(R.drawable.btn_eng);
                                break;

                            case R.id.menu_item_china:
                                LanguageSelector.getInstance().changeLanguage(R.drawable.btn_china);
                                break;
                        }
                        return false;
                    }
                });
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setOnLanguageChangeListener();
        LanguageSelector.getInstance().syncLanguage();
        registerLocationListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unRegisterLocationListener();
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            // NavigationDrawer가 열려있는 경우 >> Drawer close
            drawerLayout.closeDrawers();
        } else if (currentMode.equals(MODE_MAP_DETAIL)) {
            // MAP_DETAIL 모드인 경우
            if (mapDetailContainer.getVisibility() == View.VISIBLE) {
                // 약국 상세정보가 켜져있다면
                Animation animDisappearToBottom = AnimationUtils.loadAnimation(MainActivity.this, R.anim.disappear_to_bottom);
                mapDetailContainer.setAnimation(animDisappearToBottom);
                mapDetailContainer.setVisibility(View.GONE);    // 안보이도록
            } else {
                // 약국 상세정보가 꺼져있다면 (맵만 보인다면)
                changeMode(MODE_MAIN);
            }
        } else {
            // Main 화면이라면
            super.onBackPressed();      // 앱 종료
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    //set about language
    void setOnLanguageChangeListener(){
        LanguageSelector.OnLanguageChangeListener mOnLanguageChangeListener = new LanguageSelector.OnLanguageChangeListener() {
            @Override
            public void setViewContentsByLanguage(int id) {
                languageButton.setBackgroundResource(id);
                    switch (id) {
                        case R.drawable.btn_kor:
                            ((TextView) findViewById(R.id.nav_drawer_main_text)).setText("메인페이지");
                            ((TextView) findViewById(R.id.nav_drawer_map_text)).setText("약국찾기");
                            ((TextView) findViewById(R.id.nav_drawer_conversation_text)).setText("증상설명");
                            ((TextView) findViewById(R.id.nav_drawer_component_text)).setText("약 성분 확인");
                            ((TextView) findViewById(R.id.nav_drawer_dasan_call_text)).setText("다산콜센터");
                            ((TextView) findViewById(R.id.nav_drawer_star_text)).setText("스크랩");
                            ((TextView) findViewById(R.id.nav_drawer_tutorial_text)).setText("튜토리얼");

                            ((TextView) findViewById(R.id.btn_component)).setText("약 성분 확인");
                            ((TextView) findViewById(R.id.btn_conversation)).setText("증상설");
                            ((TextView) findViewById(R.id.btn_scrap)).setText("스크랩");
                            ((TextView) findViewById(R.id.btn_dasan)).setText("다산콜센터");


                            break;

                        case R.drawable.btn_eng:
                            ((TextView) findViewById(R.id.nav_drawer_main_text)).setText("Main");
                            ((TextView) findViewById(R.id.nav_drawer_map_text)).setText("Search Pharmacies");
                            ((TextView) findViewById(R.id.nav_drawer_conversation_text)).setText("Translate Symptoms");
                            ((TextView) findViewById(R.id.nav_drawer_component_text)).setText("Drug Information");
                            ((TextView) findViewById(R.id.nav_drawer_dasan_call_text)).setText("Dasan Call Center");
                            ((TextView) findViewById(R.id.nav_drawer_star_text)).setText("Bookmarks");
                            ((TextView) findViewById(R.id.nav_drawer_tutorial_text)).setText("Tutorial");

                            ((TextView) findViewById(R.id.btn_component)).setText("Drug Information");
                            ((TextView) findViewById(R.id.btn_conversation)).setText("Translate Symptoms");
                            ((TextView) findViewById(R.id.btn_scrap)).setText("Bookmarks");
                            ((TextView) findViewById(R.id.btn_dasan)).setText("Dasan Call Center");

                            break;

                        case R.drawable.btn_china:
                            ((TextView) findViewById(R.id.nav_drawer_main_text)).setText("主页");
                            ((TextView) findViewById(R.id.nav_drawer_map_text)).setText("寻找药店");
                            ((TextView) findViewById(R.id.nav_drawer_conversation_text)).setText("说明症状");
                            ((TextView) findViewById(R.id.nav_drawer_component_text)).setText("确认药品成分");
                            ((TextView) findViewById(R.id.nav_drawer_dasan_call_text)).setText("首尔茶山热线");
                            ((TextView) findViewById(R.id.nav_drawer_star_text)).setText("检索书签");

                            ((TextView) findViewById(R.id.nav_drawer_tutorial_text)).setText("教程");
                            ((TextView) findViewById(R.id.btn_component)).setText("说明症状");
                            ((TextView) findViewById(R.id.btn_conversation)).setText("说明症状");
                            ((TextView) findViewById(R.id.btn_scrap)).setText("检索书签");
                            ((TextView) findViewById(R.id.btn_dasan)).setText("首尔茶山热线");


                            break;
                    }
            }
        };
        LanguageSelector.getInstance().setOnLanguageChangeListener(mOnLanguageChangeListener);
    }
}
