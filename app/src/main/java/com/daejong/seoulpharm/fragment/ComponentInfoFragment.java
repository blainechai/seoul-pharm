package com.daejong.seoulpharm.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daejong.seoulpharm.R;

/**
 * Created by blainechai on 2016. 10. 23..
 */

public class ComponentInfoFragment extends Fragment implements View.OnClickListener {

    LinearLayout container;

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
        container = (LinearLayout) view.findViewById(R.id.container);

        ((TextView)view.findViewById(R.id.test)).setText("" + getArguments().getString("medData"));
        Toast.makeText(getActivity(), "" + getArguments().getString("medData"), Toast.LENGTH_SHORT).show();

        // response 받은 정보 중에 제조사 / 성분목록 / 등의 정보가 있다면

        // container.addView(view...);


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
