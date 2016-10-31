package com.daejong.seoulpharm.activity;

import android.app.Fragment;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.fragment.ComponentInfoFragment;
import com.daejong.seoulpharm.fragment.ComponentScannerFragment;
import com.daejong.seoulpharm.model.MedicineInfo;
import com.daejong.seoulpharm.util.LanguageSelector;
import com.daejong.seoulpharm.util.NetworkManager;
import com.daejong.seoulpharm.widget.NotoTextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class ComponentActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Toolbar toolbar;
    Button toolbarBtn;
    NotoTextView toolbarTitle;
    DrawerLayout drawerLayout;
    Button languageButton;


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component);

        toolbar = (Toolbar) findViewById(R.id.component_toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        toolbarBtn = (Button) findViewById(R.id.nav_hamburger_btn);
        toolbarTitle = (NotoTextView) findViewById(R.id.toolbar_title);

        toolbarBtn.setOnClickListener(this);
        // Nav Buttons Setting
        findViewById(R.id.nav_drawer_component_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_main_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_conversation_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_map_btn).setOnClickListener(this);
        findViewById(R.id.nav_drawer_star_btn).setOnClickListener(this);


        languageButton = (Button) findViewById(R.id.spinner);
        languageButton.setOnClickListener(this);

//        Spinner spin = (Spinner) findViewById(R.id.spinner);
//        spin.setOnItemSelectedListener(this);
//        LanguageSpinnerAdapter languageSpinnerAdapter=new LanguageSpinnerAdapter(getApplicationContext(),language);
//        spin.setAdapter(languageSpinnerAdapter);
//        spin.getSelectedItem();

        if (getIntent().getStringExtra("barcode") != null) {
            NetworkManager.getInstance().getComponentByBarcode(this, getIntent().getStringExtra("barcode"), new NetworkManager.OnResultListener<String>() {
                @Override
                public void onSuccess(String result) {
//                Toast.makeText(getActivity(), "" + result, Toast.LENGTH_SHORT).show();
                    try {
                        Document document = Jsoup.parse(result);
//                    Log.d("hi!!!!!!!!!!!!!!!!", "onSuccess: " + document.getElementsByTag("c1"));

                        MedicineInfo medicineInfo = new MedicineInfo();
                        medicineInfo.setBarcode(getIntent().getStringExtra("barcode"));

                        //set name
                        Elements nameEls = document.getElementsByTag("b1");
                        medicineInfo.setName(nameEls.get(0).text());

                        //set company
                        Elements companyEls = document.getElementsByTag("b3");
                        medicineInfo.setCompany(companyEls.get(0).text());

                        //set item seq
                        Elements itemSeqEls = document.getElementsByTag("b7");
                        medicineInfo.setItemSeq(itemSeqEls.get(0).text());

                        //set component
                        Elements componentEls = document.getElementsByTag("c1");
                        ArrayList<String> components = new ArrayList<String>();
                        for (Element el : componentEls) {
                            components.add(el.text());
                        }
                        medicineInfo.setComponents(components);

                        getSpecificInfo(medicineInfo);

                    } catch (Exception e) {
                        onFail(500, "Contents = " + getIntent().getStringExtra("barcode") + "Format = ");
                    }
                }

                @Override
                public void onFail(int code, String responseString) {
//                Log.d("REQTESTTTT", "error code:" + code + "\n" + responseString);
                    int currentLanguage = LanguageSelector.getInstance().getCurrentLanguage();
                    switch (currentLanguage) {
                        case R.drawable.btn_kor:
                            Toast.makeText(ComponentActivity.this, "서버가 혼잡한니다. 바코드, QR코드를 다시 스캔하여주세요.", Toast.LENGTH_SHORT).show();
                            break;
                        case R.drawable.btn_eng:
                            Toast.makeText(ComponentActivity.this, "Server is busy. Please scan the barcode again.", Toast.LENGTH_SHORT).show();
                            break;
                        case R.drawable.btn_china:
                            Toast.makeText(ComponentActivity.this, "", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });
        } else {
            getFragmentManager().beginTransaction().replace(R.id.container, new ComponentScannerFragment()).commit();
        }

    }


    // Handling Fragment
    // 검색 모드에서의 History List를 불러옴
    public void pushComponentScannerFragment() {
        getFragmentManager().beginTransaction().replace(R.id.container, new ComponentScannerFragment()).addToBackStack(null).commit();
    }

    public void popFragment() {
        getFragmentManager().popBackStack();
    }

    // Click Event 처리
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nav_hamburger_btn:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;

            case R.id.nav_drawer_main_btn:
                drawerLayout.closeDrawers();
                finish();
                break;
            case R.id.nav_drawer_map_btn:
                drawerLayout.closeDrawers();
                finish();
                break;
            case R.id.nav_drawer_component_btn:
                drawerLayout.closeDrawers();
                startActivity(new Intent(ComponentActivity.this, ComponentActivity.class));
                finish();
                break;
            case R.id.nav_drawer_star_btn:
                drawerLayout.closeDrawers();
                startActivity(new Intent(ComponentActivity.this, ScrapActivity.class));
                finish();
                break;
            case R.id.nav_drawer_conversation_btn:
                drawerLayout.closeDrawers();
                finish();
                startActivity(new Intent(ComponentActivity.this, ConversationActivity.class));
                break;

            case R.id.spinner:
                PopupMenu pum = new PopupMenu(this, findViewById(R.id.spinner));
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    private void getSpecificInfo(MedicineInfo medicineInfo) {
        // get medicine specific search
        NetworkManager.getInstance().getMedicineSpecific(this, medicineInfo, new NetworkManager.OnSpecificResultListener<String>() {
            @Override
            public void onSuccess(String result, MedicineInfo medicineInfo) {
                Document document = Jsoup.parse(result, "http://drug.mfds.go.kr/html/bxsSearchDrugProduct.jsp");
                medicineInfo.setEffect(replaceMark(document.select("#A_EE_DOC").get(0).parent().select(">div").text()));
                medicineInfo.setUsage(replaceMark(document.select("#A_UD_DOC").get(0).parent().select(">div").text()));
                medicineInfo.setCaution(replaceMark(document.select("#A_NB_DOC").get(0).parent().select(">div").text()));
                String imgSrc;
                if (document.select(".txc-image").size() > 0) {
                    imgSrc = document.select(".txc-image").first().absUrl("src");
                } else {
                    imgSrc = "http://drug.mfds.go.kr/html/images/noimages.png";
                }
                medicineInfo.setImageSrc(imgSrc);

                ComponentInfoFragment fragment = new ComponentInfoFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("medicineInfo", medicineInfo);
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            }

            @Override
            public void onFail(int code, String response) {
                int currentLanguage = LanguageSelector.getInstance().getCurrentLanguage();
                switch (currentLanguage) {
                    case R.drawable.btn_kor:
                        Toast.makeText(getApplicationContext(), "서버가 혼잡한니다. 바코드를 다시 스캔하여주세요.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.drawable.btn_eng:
                        Toast.makeText(getApplicationContext(), "Server is busy. Please scan the barcode again.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.drawable.btn_china:
                        Toast.makeText(getApplicationContext(), "ㄴㅇㄹㅇㄴㄹ", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

    }

    String replaceMark(String str) {
        return str.replaceAll("&nbsp;", " ").replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&amp;", "&").replaceAll("&quot;", "\"");
    }
}
