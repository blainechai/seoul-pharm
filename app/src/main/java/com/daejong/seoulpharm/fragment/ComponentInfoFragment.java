package com.daejong.seoulpharm.fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.adapter.LanguageSpinnerAdapter;
import com.daejong.seoulpharm.model.MedicineInfo;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by blainechai on 2016. 10. 23..
 */

public class ComponentInfoFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    String[] titles = {"제조사", "성분목록", "효능", "용법용량", "사용상 주의사항"};
    String[] language = {"한국어", "中国语", "ENG"};

    ImageView imageView;
    LinearLayout resultContainer;

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
        MedicineInfo medicineInfo = (MedicineInfo) getArguments().getSerializable("medicineInfo");

        // set text around image
        TextView companyTextView = (TextView) view.findViewById(R.id.pharm_maker);
        TextView nameTextView = (TextView) view.findViewById(R.id.pharm_name_kor_view);
        companyTextView.setText(medicineInfo.getCompany());
        nameTextView.setText(medicineInfo.getName());

        //get image
        setImage(medicineInfo, imageView);

        //set list item

        LinearLayout contentView = (LinearLayout) inflater.inflate(R.layout.view_title_item, container, false);
        ((TextView) contentView.findViewById(R.id.text_title)).setText(titles[0]);
        ((TextView) contentView.findViewById(R.id.text_content)).setText(medicineInfo.getCompany());
        resultContainer.addView(contentView);


        contentView = (LinearLayout) inflater.inflate(R.layout.view_title_item, container, false);
        ((TextView) contentView.findViewById(R.id.text_title)).setText(titles[1]);
        ((TextView) contentView.findViewById(R.id.text_content)).setText(medicineInfo.getComponents().get(0));
        resultContainer.addView(contentView);

        contentView = (LinearLayout) inflater.inflate(R.layout.view_title_item, container, false);
        ((TextView) contentView.findViewById(R.id.text_title)).setText(titles[2]);
        ((TextView) contentView.findViewById(R.id.text_content)).setText(medicineInfo.getEffect());
        resultContainer.addView(contentView);

        contentView = (LinearLayout) inflater.inflate(R.layout.view_title_item, container, false);
        ((TextView) contentView.findViewById(R.id.text_title)).setText(titles[3]);
        ((TextView) contentView.findViewById(R.id.text_content)).setText(medicineInfo.getUsage());
        resultContainer.addView(contentView);

        contentView = (LinearLayout) inflater.inflate(R.layout.view_title_item, container, false);
        ((TextView) contentView.findViewById(R.id.text_title)).setText(titles[4]);
        ((TextView) contentView.findViewById(R.id.text_content)).setText(medicineInfo.getCaution());
        resultContainer.addView(contentView);

        //set custom spinner
        Spinner spin = (Spinner) getActivity().findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);
        LanguageSpinnerAdapter languageSpinnerAdapter=new LanguageSpinnerAdapter(getActivity(),language);
        spin.setAdapter(languageSpinnerAdapter);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {

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
        Toast.makeText(getActivity(), language[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }
}
