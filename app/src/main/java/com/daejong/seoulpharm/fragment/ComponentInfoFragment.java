package com.daejong.seoulpharm.fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.db.DBHelper;
import com.daejong.seoulpharm.model.MedicineInfo;
import com.daejong.seoulpharm.util.LanguageSelector;
import com.daejong.seoulpharm.util.NetworkManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by blainechai on 2016. 10. 23..
 */

public class ComponentInfoFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    String[] titles = {"제조사", "성분목록", "효능", "용법용량", "사용상 주의사항"};
    String[] engTitles = {"Manufacturer", "Ingredients", "Effects", "Dosage", "Warnings"};
    String[] chTitles = {"生产者", "原料", "药效", "用法与用量", "使用注意事项"};

    ImageView imageView;
    LinearLayout resultContainer;
    Button languageButton;
    MedicineInfo medicineInfo;
    MedicineInfo enMedicineInfo = new MedicineInfo();
    MedicineInfo chMedicineInfo = new MedicineInfo();
    ImageView bookmarkImageView;

    DBHelper dbHelper;

    boolean isBookmarkChecked;


    public ComponentInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_component_info, container, false);
        // View Initialize
        resultContainer = (LinearLayout) view.findViewById(R.id.container);
        imageView = (ImageView) view.findViewById(R.id.pharm_image_view);
        bookmarkImageView = (ImageView) view.findViewById(R.id.btn_bookmark);
        languageButton = (Button) getActivity().findViewById(R.id.spinner);

        medicineInfo = (MedicineInfo) getArguments().getSerializable("medicineInfo");

        dbHelper = new DBHelper(getActivity());
        if (dbHelper.searchMedicine(medicineInfo.getItemSeq())) {
            bookmarkImageView.setImageResource(R.drawable.ic_map_btn_bookmark_on);
            isBookmarkChecked = true;
        } else {
            isBookmarkChecked = false;
        }
        bookmarkImageView.setOnClickListener(this);
        setImage(medicineInfo, imageView);

        // set text around image
        TextView companyTextView = (TextView) view.findViewById(R.id.pharm_maker);
        TextView nameTextView = (TextView) view.findViewById(R.id.pharm_name_kor_view);

        companyTextView.setText(medicineInfo.getCompany());
        nameTextView.setText(medicineInfo.getName());

        setOnLanguageChangeListener();

        //setList item
        LanguageSelector.getInstance().syncLanguage();

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        setOnLanguageChangeListener();
        LanguageSelector.getInstance().syncLanguage();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_bookmark:
                if (!isBookmarkChecked) {
                    bookmarkImageView.setImageResource(R.drawable.ic_map_btn_bookmark_on);
                    dbHelper.addMedicineBookmark(medicineInfo);
                    isBookmarkChecked = !isBookmarkChecked;
                } else {
                    bookmarkImageView.setImageResource(R.drawable.ic_map_btn_bookmark_off);
                    dbHelper.deleteMedicineScrapped(medicineInfo.getItemSeq());
                    isBookmarkChecked = !isBookmarkChecked;
                }
                break;

        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void setImage(MedicineInfo medicineInfo, final View targetView) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(medicineInfo.getImageSrc(), null, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Bitmap image = BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length);
                ((ImageView) targetView).setImageBitmap(image);
//                resultContainer.addView(imageView);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    public void getEngInfo() {
        resultContainer.removeAllViews();
        ArrayList<String> targets = new ArrayList<>();
        String component = "";
        ArrayList<String> components = medicineInfo.getComponents();
        for (int i = 0; i < components.size(); i++) {
            if (i == 0) {
                component += medicineInfo.getComponents().get(i);
            } else {
                component += "\n" + medicineInfo.getComponents().get(i);
            }
        }
        targets.add(medicineInfo.getCompany());
        targets.add(component);
        targets.add(medicineInfo.getEffect());
        targets.add(medicineInfo.getUsage());
        targets.add(medicineInfo.getCaution());
        NetworkManager.getInstance().getTranslation(getActivity(), "ko", "en", targets, new NetworkManager.OnResultListener<String>() {
            @Override
            public void onSuccess(String result) {
//                Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
Log.d("!!!!!!!!!!", ""+result);
                JsonElement jsonElement = new JsonParser().parse(result);
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                jsonObject = jsonObject.getAsJsonObject("data");
                JsonArray jsonArray = jsonObject.getAsJsonArray("translations");



                LayoutInflater inflater = getActivity().getLayoutInflater();
                LinearLayout contentView = (LinearLayout) inflater.inflate(R.layout.view_title_item, null, false);

                ((TextView) contentView.findViewById(R.id.text_title)).setText(titles[0]);
                ((TextView) contentView.findViewById(R.id.text_title)).setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "NotoSansKR-Medium-Hestia.otf"));
                ((TextView) contentView.findViewById(R.id.text_content)).setText(jsonArray.get(0).getAsJsonObject().get("translatedText").getAsString());
                resultContainer.addView(contentView);

                contentView = (LinearLayout) inflater.inflate(R.layout.view_title_item, null, false);
                ((TextView) contentView.findViewById(R.id.text_title)).setText(titles[1]);
                ((TextView) contentView.findViewById(R.id.text_content)).setText(jsonArray.get(1).getAsJsonObject().get("translatedText").getAsString());
                resultContainer.addView(contentView);

                contentView = (LinearLayout) inflater.inflate(R.layout.view_title_item, null, false);
                ((TextView) contentView.findViewById(R.id.text_title)).setText(titles[2]);
                ((TextView) contentView.findViewById(R.id.text_content)).setText(jsonArray.get(2).getAsJsonObject().get("translatedText").getAsString());
                resultContainer.addView(contentView);

                contentView = (LinearLayout) inflater.inflate(R.layout.view_title_item, null, false);
                ((TextView) contentView.findViewById(R.id.text_title)).setText(titles[3]);
                ((TextView) contentView.findViewById(R.id.text_content)).setText(jsonArray.get(3).getAsJsonObject().get("translatedText").getAsString());
                resultContainer.addView(contentView);

                contentView = (LinearLayout) inflater.inflate(R.layout.view_title_item, null, false);
                ((TextView) contentView.findViewById(R.id.text_title)).setText(titles[4]);
                ((TextView) contentView.findViewById(R.id.text_content)).setText(jsonArray.get(4).getAsJsonObject().get("translatedText").getAsString());
                resultContainer.addView(contentView);
            }

            @Override
            public void onFail(int code, String response) {
                Log.d("!!!!!!!!!!", ""+response);
            }
        });
    }

    public void getChInfo() {

    }

    public void getEngInfoFromWeb() {
        ArrayList<String> components = medicineInfo.getComponents();
        enMedicineInfo.setComponents(new ArrayList<String>());
        NetworkManager.getInstance().getTranslation(getActivity(), "ko", "en", components, new NetworkManager.OnResultListener<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();

                JsonElement jsonElement = new JsonParser().parse(result);
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                jsonObject = jsonObject.getAsJsonObject("data");
                JsonArray jsonArray = jsonObject.getAsJsonArray("translations");

                for (JsonElement jEle : jsonArray) {
                    enMedicineInfo.getComponents().add(jEle.getAsJsonObject().get("translatedText").getAsString());
                    Log.d("component", jEle.getAsJsonObject().get("translatedText").getAsString());
                }
                String query = "";
                for (int i = 0; i < enMedicineInfo.getComponents().size(); i++) {
                    if (i == 0) {
                        query += enMedicineInfo.getComponents().get(i);
                    } else {
                        query += " " + enMedicineInfo.getComponents().get(i);
                    }
                }
                AsyncHttpClient client = new AsyncHttpClient();
                query = "https://www.drugs.com/search.php?searchterm=" + query.replaceAll(" \\([^\\)]+\\)", "").replaceAll(" ", "+");
                Log.d("DRUGS.com", query);
                client.get(query, null, new TextHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseBody) {
                        Document document = Jsoup.parse(responseBody);
                        Log.d("target!!", "" + document.select(".snippet.search-result").get(0).select("a").attr("href"));
                        document.select(".snippet.search-result").get(0).select("a").attr("href");

                        Toast.makeText(getActivity(), document.select(".snippet.search-result").get(0).select("a").attr("href"), Toast.LENGTH_SHORT).show();

                        String url = document.select(".snippet.search-result").get(0).select("a").attr("href");
                        AsyncHttpClient client = new AsyncHttpClient();
                        Log.d("DRUGS.com", url);
                        client.get(url, null, new TextHttpResponseHandler() {

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, String responseBody) {
                                Document document = Jsoup.parse(responseBody);

//                                                document.select(".contentBox p").text();

                                Toast.makeText(getActivity(), document.select(".contentBox p").text(), Toast.LENGTH_SHORT).show();

                                resultContainer.removeAllViews();

                                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                LinearLayout contentView = (LinearLayout) inflater.inflate(R.layout.view_title_item, null, false);
                                ((TextView) contentView.findViewById(R.id.text_title)).setText("Overview");
                                ((TextView) contentView.findViewById(R.id.text_title)).setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "NotoSansKR-Medium-Hestia.otf"));
                                ((TextView) contentView.findViewById(R.id.text_content)).setText(document.select(".contentBox p").text());
                                resultContainer.addView(contentView);

                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error) {

                            }
                        });

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error) {

                    }
                });
            }

            @Override
            public void onFail(int code, String response) {

            }
        });

    }

    void getKoreanInfo() {
        //set list item
        resultContainer.removeAllViews();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        LinearLayout contentView = (LinearLayout) inflater.inflate(R.layout.view_title_item, null, false);

        ((TextView) contentView.findViewById(R.id.text_title)).setText(titles[0]);
        ((TextView) contentView.findViewById(R.id.text_title)).setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "NotoSansKR-Medium-Hestia.otf"));
        ((TextView) contentView.findViewById(R.id.text_content)).setText(medicineInfo.getCompany());
        resultContainer.addView(contentView);

        contentView = (LinearLayout) inflater.inflate(R.layout.view_title_item, null, false);
        ((TextView) contentView.findViewById(R.id.text_title)).setText(titles[1]);
        String component = "";
        ArrayList<String> components = medicineInfo.getComponents();
        for (int i = 0; i < components.size(); i++) {
            if (i == components.size() - 1) component += components.get(i);
            else component += components.get(i) + "\n";

        }
        ((TextView) contentView.findViewById(R.id.text_content)).setText(component);
        resultContainer.addView(contentView);

        contentView = (LinearLayout) inflater.inflate(R.layout.view_title_item, null, false);
        ((TextView) contentView.findViewById(R.id.text_title)).setText(titles[2]);
        ((TextView) contentView.findViewById(R.id.text_content)).setText(medicineInfo.getEffect());
        resultContainer.addView(contentView);

        contentView = (LinearLayout) inflater.inflate(R.layout.view_title_item, null, false);
        ((TextView) contentView.findViewById(R.id.text_title)).setText(titles[3]);
        ((TextView) contentView.findViewById(R.id.text_content)).setText(medicineInfo.getUsage());
        resultContainer.addView(contentView);

        contentView = (LinearLayout) inflater.inflate(R.layout.view_title_item, null, false);
        ((TextView) contentView.findViewById(R.id.text_title)).setText(titles[4]);
        ((TextView) contentView.findViewById(R.id.text_content)).setText(medicineInfo.getCaution());
        resultContainer.addView(contentView);
    }

    //set about language
    void setOnLanguageChangeListener() {
        LanguageSelector.OnLanguageChangeListener mOnLanguageChangeListener = new LanguageSelector.OnLanguageChangeListener() {
            @Override
            public void setViewContentsByLanguage(int id) {
                languageButton.setBackgroundResource(id);
                switch (id) {
                    case R.drawable.btn_kor:
                        ((TextView) getActivity().findViewById(R.id.nav_drawer_main_text)).setText("메인페이지");
                        ((TextView) getActivity().findViewById(R.id.nav_drawer_map_text)).setText("약국찾기");
                        ((TextView) getActivity().findViewById(R.id.nav_drawer_conversation_text)).setText("증상설명");
                        ((TextView) getActivity().findViewById(R.id.nav_drawer_component_text)).setText("약 성분 확인");
                        ((TextView) getActivity().findViewById(R.id.nav_drawer_dasan_call_text)).setText("다산콜센터");
                        ((TextView) getActivity().findViewById(R.id.nav_drawer_star_text)).setText("스크랩");
                        ((TextView) getActivity().findViewById(R.id.nav_drawer_tutorial_text)).setText("튜토리얼");
                        getKoreanInfo();
                        break;

                    case R.drawable.btn_eng:
                        ((TextView) getActivity().findViewById(R.id.nav_drawer_main_text)).setText("Main");
                        ((TextView) getActivity().findViewById(R.id.nav_drawer_map_text)).setText("Search Pharmacies");
                        ((TextView) getActivity().findViewById(R.id.nav_drawer_conversation_text)).setText("Translate Symptoms");
                        ((TextView) getActivity().findViewById(R.id.nav_drawer_component_text)).setText("Drug Information");
                        ((TextView) getActivity().findViewById(R.id.nav_drawer_dasan_call_text)).setText("Dasan Call Center");
                        ((TextView) getActivity().findViewById(R.id.nav_drawer_star_text)).setText("Bookmarks");
                        ((TextView) getActivity().findViewById(R.id.nav_drawer_tutorial_text)).setText("Tutorial");
                        getEngInfo();
                        break;

                    case R.drawable.btn_china:
                        ((TextView) getActivity().findViewById(R.id.nav_drawer_main_text)).setText("主页");
                        ((TextView) getActivity().findViewById(R.id.nav_drawer_map_text)).setText("寻找药店");
                        ((TextView) getActivity().findViewById(R.id.nav_drawer_conversation_text)).setText("说明症状");
                        ((TextView) getActivity().findViewById(R.id.nav_drawer_component_text)).setText("确认药品成分");
                        ((TextView) getActivity().findViewById(R.id.nav_drawer_dasan_call_text)).setText("首尔茶山热线");
                        ((TextView) getActivity().findViewById(R.id.nav_drawer_star_text)).setText("检索书签");
                        ((TextView) getActivity().findViewById(R.id.nav_drawer_tutorial_text)).setText("教程");

                        break;
                }
            }
        };
        LanguageSelector.getInstance().setOnLanguageChangeListener(mOnLanguageChangeListener);
    }


}
