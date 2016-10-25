package com.daejong.seoulpharm.fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.model.MedicineInfo;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by blainechai on 2016. 10. 23..
 */

public class ComponentInfoFragment extends Fragment implements View.OnClickListener {

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
//        Log.d("!!!!!!!!!!!!!!!",medicineInfo.getImageSrc());
        ((TextView)view.findViewById(R.id.test)).setText(medicineInfo.toString());

        TextView companyTextView = (TextView) view.findViewById(R.id.pharm_maker);
        TextView nameTextView= (TextView) view.findViewById(R.id.pharm_name_kor_view);
        companyTextView.setText(medicineInfo.getCompany());
        nameTextView.setText(medicineInfo.getName());

        // response 받은 정보 중에 제조사 / 성분목록 / 등의 정보가 있다면

        // container.addView(view...);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(medicineInfo.getImageSrc(), null, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Bitmap image = BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length);
                imageView.setImageBitmap(image);
//                resultContainer.addView(imageView);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

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
}
