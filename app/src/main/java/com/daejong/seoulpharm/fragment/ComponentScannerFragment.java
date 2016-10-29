package com.daejong.seoulpharm.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.model.MedicineInfo;
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

    public ComponentScannerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_component_scanner, container, false);
        mScannerView = (ZXingScannerView) view.findViewById(R.id.scanner_view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onClick(View view) {

    }


    @Override
    public void handleResult(final Result rawResult) {
        Toast.makeText(getActivity(), "Contents = " + rawResult.getText() +
                ", Format = " + rawResult.getBarcodeFormat().toString(), Toast.LENGTH_SHORT).show();
        Log.e("qrcode!!!!!!!!", "Contents = " + rawResult.getText() + "Format = " + rawResult.getBarcodeFormat().toString());

        Pattern barcodePattern = Pattern.compile("880([6][2456789]|[0][56789])[0-9]{7}[c|C|0-9]");

        Matcher matcher = barcodePattern.matcher(rawResult.getText());


        if (matcher.find()) {
            getComponentByBarcode(matcher.group(0));
            Log.d("!!!!!!!!!!!", matcher.group(0));
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

    private void getSpecificInfo(MedicineInfo medicineInfo) {
        // get medicine specific search
        NetworkManager.getInstance().getMedicineSpecific(getActivity(), medicineInfo, new NetworkManager.OnSpecificResultListener<String>() {
            @Override
            public void onSuccess(String result, MedicineInfo medicineInfo) {
                Document document = Jsoup.parse(result, "http://drug.mfds.go.kr/html/bxsSearchDrugProduct.jsp");
                medicineInfo.setEffect(document.select("#A_EE_DOC").get(0).parent().select(">div").text());
                medicineInfo.setUsage(document.select("#A_UD_DOC").get(0).parent().select(">div").text());
                medicineInfo.setCaution(document.select("#A_NB_DOC").get(0).parent().select(">div").text());
                String imgSrc;
                if (document.select(".txc-image").size() > 0) {
                    imgSrc = document.select(".txc-image").first().absUrl("src");
                } else {
                    imgSrc = "http://drug.mfds.go.kr/html/images/noimages.png";
                }
//                Log.d("!!!!!!!!!!!", "fdfghdfghdgfh" + imgSrc);
                medicineInfo.setImageSrc(imgSrc);

                ComponentInfoFragment fragment = new ComponentInfoFragment();
                Bundle bundle = new Bundle();
//                    bundle.putString("medData", result);
                bundle.putSerializable("medicineInfo", medicineInfo);
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
            }

            @Override
            public void onFail(int code, String response) {
                Log.d("REQTESTTTT", "error code:" + code + "\n" + response);
            }
        });

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
                Log.d("REQTESTTTT", "error code:" + code + "\n" + responseString);
            }
        });
    }
}