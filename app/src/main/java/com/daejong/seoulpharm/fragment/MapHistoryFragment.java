package com.daejong.seoulpharm.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.adapter.MapListAdatper;
import com.daejong.seoulpharm.model.MapHistoryItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapHistoryFragment extends Fragment {

    ListView mapHistoryListView;       // show searched history
    MapListAdatper mAdapter;

    public MapHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map_history, container, false);

        mapHistoryListView = (ListView) view.findViewById(R.id.map_history_list_view);
        mAdapter = new MapListAdatper();
        mapHistoryListView.setAdapter(mAdapter);

        // DB 접근 후 History 불러와서 listview 에 추가
        for (int i=0; i<30; i++) {
            MapHistoryItem item = new MapHistoryItem();
            item.setName("약국 "+i);
            item.setDate("오늘");
            mAdapter.add(item);
        }


        return view;
    }

}
