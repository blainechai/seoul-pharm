package com.daejong.seoulpharm.fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.activity.MainActivity;
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

    ImageView imageView;
    LinearLayout resultContainer;
    Button languageButton;
    MedicineInfo medicineInfo;
    MedicineInfo enMedicineInfo = new MedicineInfo();
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
        LinearLayout contentView = (LinearLayout) inflater.inflate(R.layout.view_title_item, (ViewGroup) getView().getParent(), false);

        ((TextView) contentView.findViewById(R.id.text_title)).setText(titles[0]);
        ((TextView) contentView.findViewById(R.id.text_content)).setText(medicineInfo.getCompany());
        resultContainer.addView(contentView);

        contentView = (LinearLayout) inflater.inflate(R.layout.view_title_item, (ViewGroup) getView().getParent(), false);
        ((TextView) contentView.findViewById(R.id.text_title)).setText(titles[1]);
        String component = "";
        ArrayList<String> components = medicineInfo.getComponents();
        for (int i = 0; i < components.size(); i++) {
            if (i == components.size() - 1) component += components.get(i);
            else component += components.get(i) + "\n";

        }
        ((TextView) contentView.findViewById(R.id.text_content)).setText(component);
        resultContainer.addView(contentView);

        contentView = (LinearLayout) inflater.inflate(R.layout.view_title_item, (ViewGroup) getView().getParent(), false);
        ((TextView) contentView.findViewById(R.id.text_title)).setText(titles[2]);
        ((TextView) contentView.findViewById(R.id.text_content)).setText(medicineInfo.getEffect());
        resultContainer.addView(contentView);

        contentView = (LinearLayout) inflater.inflate(R.layout.view_title_item, (ViewGroup) getView().getParent(), false);
        ((TextView) contentView.findViewById(R.id.text_title)).setText(titles[3]);
        ((TextView) contentView.findViewById(R.id.text_content)).setText(medicineInfo.getUsage());
        resultContainer.addView(contentView);

        contentView = (LinearLayout) inflater.inflate(R.layout.view_title_item, (ViewGroup) getView().getParent(), false);
        ((TextView) contentView.findViewById(R.id.text_title)).setText(titles[4]);
        ((TextView) contentView.findViewById(R.id.text_content)).setText(medicineInfo.getCaution());
        resultContainer.addView(contentView);
    }

    //set about language
    void setOnLanguageChangeListener() {
        LanguageSelector.OnLanguageChangeListener mOnLanguageChangeListener = new LanguageSelector.OnLanguageChangeListener() {
            @Override
            public void setViewContentsLanguage(int id) {
                languageButton.setBackgroundResource(id);
                switch (id) {
                    case R.drawable.btn_kor:
                        getKoreanInfo();
                        break;

                    case R.drawable.btn_eng:
                        getEngInfo();
                        break;

                    case R.drawable.btn_china:

                        break;
                }
            }
        };
        LanguageSelector.getInstance().setOnLanguageChangeListener(mOnLanguageChangeListener);
    }
}
