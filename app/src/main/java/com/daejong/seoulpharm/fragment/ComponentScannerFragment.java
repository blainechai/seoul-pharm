package com.daejong.seoulpharm.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.model.MedicineInfo;
import com.daejong.seoulpharm.util.LanguageSelector;
import com.daejong.seoulpharm.util.NetworkManager;
import com.google.zxing.Result;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by blainechai on 2016. 10. 23..
 */

public class ComponentScannerFragment extends Fragment implements View.OnClickListener, ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    Button languageButton;

    public ComponentScannerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_component_scanner, container, false);
        mScannerView = (ZXingScannerView) view.findViewById(R.id.scanner_view);
        languageButton = (Button) getActivity().findViewById(R.id.spinner);
        setOnLanguageChangeListener();
        LanguageSelector.getInstance().syncLanguage();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
        setOnLanguageChangeListener();
        LanguageSelector.getInstance().syncLanguage();
    }

    @Override
    public void onClick(View view) {

    }


    @Override
    public void handleResult(final Result rawResult) {
//        Toast.makeText(getActivity(), "Contents = " + rawResult.getText() +
//                ", Format = " + rawResult.getBarcodeFormat().toString(), Toast.LENGTH_SHORT).show();
//        Log.e("qrcode!!!!!!!!", "Contents = " + rawResult.getText() + "Format = " + rawResult.getBarcodeFormat().toString());

        Pattern barcodePattern = Pattern.compile("880([6][2456789]|[0][56789])[0-9]{7}[c|C|0-9]");

        Matcher matcher = barcodePattern.matcher(rawResult.getText());


        if (matcher.find()) {
            getComponentByBarcode(matcher.group(0));
//            Log.d("!!!!!!!!!!!", matcher.group(0));
        }


        // Note:
        // * Wait 2 seconds to resume the preview.
        // * On older devices continuously stopping and resuming camera preview can result in freezing the app.
        // * I don't know why this is the case but I don't have the time to figure out.


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(ComponentScannerFragment.this);
            }
        }, 2000);
    }


    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    public void getComponentByBarcode(final String barcodeText) {
        NetworkManager.getInstance().getComponentByBarcode(getActivity(), barcodeText, new NetworkManager.OnResultListener<String>() {
            @Override
            public void onSuccess(String result) {
//                Toast.makeText(getActivity(), "" + result, Toast.LENGTH_SHORT).show();
                try {
                    Document document = Jsoup.parse(result);
//                    Log.d("hi!!!!!!!!!!!!!!!!", "onSuccess: " + document.getElementsByTag("c1"));

                    MedicineInfo medicineInfo = new MedicineInfo();
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
                    onFail(500, "Contents = " + barcodeText + "Format = ");
                }
            }

            @Override
            public void onFail(int code, String responseString) {
//                Log.d("REQTESTTTT", "error code:" + code + "\n" + responseString);
                int currentLanguage = LanguageSelector.getInstance().getCurrentLanguage();
                switch (currentLanguage) {
                    case R.drawable.btn_kor:
                        Toast.makeText(getActivity(), "서버가 혼잡한니다. 바코드, QR코드를 다시 스캔하여주세요.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.drawable.btn_eng:
                        Toast.makeText(getActivity(), "Server is busy. Please scan the barcode again.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.drawable.btn_china:
                        Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    private void getSpecificInfo(MedicineInfo medicineInfo) {
        // get medicine specific search
        NetworkManager.getInstance().getMedicineSpecific(getActivity(), medicineInfo, new NetworkManager.OnSpecificResultListener<String>() {
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
                getFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
            }

            @Override
            public void onFail(int code, String response) {
                int currentLanguage = LanguageSelector.getInstance().getCurrentLanguage();
                switch (currentLanguage) {
                    case R.drawable.btn_kor:
                        Toast.makeText(getActivity(), "서버가 혼잡한니다. 바코드를 다시 스캔하여주세요.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.drawable.btn_eng:
                        Toast.makeText(getActivity(), "Server is busy. Please scan the barcode again.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.drawable.btn_china:
                        Toast.makeText(getActivity(), "ㄴㅇㄹㅇㄴㄹ", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

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
                        ((TextView) getActivity().findViewById(R.id.toolbar_title)).setText("약 성분 확인");
                        Toast.makeText(getActivity(),"정보를 확인하고자 하는 의약품의 바코드 혹은 QR코드를 스캔해주세요.",Toast.LENGTH_LONG).show();
                        break;

                    case R.drawable.btn_eng:
                        ((TextView) getActivity().findViewById(R.id.nav_drawer_main_text)).setText("Main");
                        ((TextView) getActivity().findViewById(R.id.nav_drawer_map_text)).setText("Search Pharmacies");
                        ((TextView) getActivity().findViewById(R.id.nav_drawer_conversation_text)).setText("Translate Symptoms");
                        ((TextView) getActivity().findViewById(R.id.nav_drawer_component_text)).setText("Drug Information");
                        ((TextView) getActivity().findViewById(R.id.nav_drawer_dasan_call_text)).setText("Dasan Call Center");
                        ((TextView) getActivity().findViewById(R.id.nav_drawer_star_text)).setText("Bookmarks");
                        ((TextView) getActivity().findViewById(R.id.nav_drawer_tutorial_text)).setText("Tutorial");
                        ((TextView) getActivity().findViewById(R.id.toolbar_title)).setText("Drug Information");
                        Toast.makeText(getActivity(),"Please scan the bar or QR code of the drug that you want to find information about.",Toast.LENGTH_LONG).show();
                        break;

                    case R.drawable.btn_china:
                        ((TextView) getActivity().findViewById(R.id.nav_drawer_main_text)).setText("主页");
                        ((TextView) getActivity().findViewById(R.id.nav_drawer_map_text)).setText("寻找药店");
                        ((TextView) getActivity().findViewById(R.id.nav_drawer_conversation_text)).setText("说明症状");
                        ((TextView) getActivity().findViewById(R.id.nav_drawer_component_text)).setText("确认药品成分");
                        ((TextView) getActivity().findViewById(R.id.nav_drawer_dasan_call_text)).setText("首尔茶山热线");
                        ((TextView) getActivity().findViewById(R.id.nav_drawer_star_text)).setText("检索书签");
                        ((TextView) getActivity().findViewById(R.id.nav_drawer_tutorial_text)).setText("教程");
                        ((TextView) getActivity().findViewById(R.id.toolbar_title)).setText("确认药物成分");
                        Toast.makeText(getActivity(),"请扫描您想要确认信息商品的条形码或二维码。",Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };
        LanguageSelector.getInstance().setOnLanguageChangeListener(mOnLanguageChangeListener);
    }

    String replaceMark(String str) {
        return str.replaceAll("&nbsp;", " ").replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&amp;", "&").replaceAll("&quot;", "\"");
    }
}