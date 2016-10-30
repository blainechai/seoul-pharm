package com.daejong.seoulpharm.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.daejong.seoulpharm.R;


public class TutorialFragment extends Fragment {
    public static final String PARAM_PAGE_KEY = "PARAM_PAGE_KEY";

    public static final String PAGE_1 = "PAGE_1";
    public static final String PAGE_2 = "PAGE_2";
    public static final String PAGE_3 = "PAGE_3";
    public static final String PAGE_4 = "PAGE_4";
    public static final String PAGE_5 = "PAGE_5";


    private String param_page;

    ImageView tutorialImage;

    public TutorialFragment() {
        // Required empty public constructor
    }

    public static TutorialFragment newInstance(String param_page) {
        TutorialFragment fragment = new TutorialFragment();
        Bundle args = new Bundle();
        args.putString(PARAM_PAGE_KEY, param_page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            param_page = getArguments().getString(PARAM_PAGE_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tutorial, container, false);

        tutorialImage = (ImageView) view.findViewById(R.id.tutorial_imageview);
        switch (param_page) {
            case PAGE_1 :
                tutorialImage.setImageResource(R.drawable.tutorial_1);
                break;
            case PAGE_2 :
                tutorialImage.setImageResource(R.drawable.tutorial_2);
                break;
            case PAGE_3 :
                tutorialImage.setImageResource(R.drawable.tutorial_3);
                break;
            case PAGE_4 :
                tutorialImage.setImageResource(R.drawable.tutorial_4);
                break;
            case PAGE_5 :
                tutorialImage.setImageResource(R.drawable.tutorial_5);
                break;
        }
        return view;
    }

}
