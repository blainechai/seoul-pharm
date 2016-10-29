package com.daejong.seoulpharm.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.adapter.ScrapComponentListAdpater;
import com.daejong.seoulpharm.adapter.ScrapPharmListAdpater;
import com.daejong.seoulpharm.db.DBHelper;
import com.daejong.seoulpharm.model.PharmItem;


public class ScrapFragment extends Fragment {

    /** show Scrapped Datas
     */

    // FRAGMENT TYPE
    public static final String KEY_FRAGMENT_TYPE = "KEY_FRAGMENT_TYPE";
    public static final String TYPE_PHARMS = "TYPE_PHARMS";
    public static final String TYPE_COMPONENT = "TYPE_COMPONENT";

    private String type;

    // VIEW
    ListView listView;
    ScrapPharmListAdpater mPharmListAdapter;
    ScrapComponentListAdpater mComponentListAdapter;

    // DB
    DBHelper db;

    public ScrapFragment() {
        // Required empty public constructor
    }

    public static ScrapFragment newInstance(String type) {
        ScrapFragment fragment = new ScrapFragment();
        Bundle args = new Bundle();
        args.putString(KEY_FRAGMENT_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(KEY_FRAGMENT_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scrap, container, false);

        db = new DBHelper(getActivity());

        listView = (ListView) view.findViewById(R.id.listView);
        mPharmListAdapter = new ScrapPharmListAdpater();
        mComponentListAdapter = new ScrapComponentListAdpater();

        switch (type) {
            case TYPE_PHARMS :
                // 약국 스크랩 목록
                listView.setAdapter(mPharmListAdapter);
                for (PharmItem item : db.getScrappedPharms()) {
                    mPharmListAdapter.addScrappedPharm(item);
                }

                break;
            case TYPE_COMPONENT :
                // 의약품,성분 스크랩 목록
                listView.setAdapter(mComponentListAdapter);


                break;
        }

        return view;
    }

}
