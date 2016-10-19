package com.daejong.seoulpharm.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daejong.seoulpharm.R;
import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {

    NMapView nMapView;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        nMapView = (NMapView) view.findViewById(R.id.mapView);
        nMapView.setClientId("s3q7uwJzMyOjOZfTnYDK");
        return view;
    }

}
