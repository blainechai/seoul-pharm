package com.daejong.seoulpharm.view;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.model.MapHistoryItem;

/**
 * Created by Hyunwoo on 2016. 10. 19..
 */
public class MapHistoryItemView extends FrameLayout {

    ImageView historyTypeImageView;
    TextView nameView;
    TextView dateView;

    public MapHistoryItemView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_map_list_item, this);
        historyTypeImageView = (ImageView) findViewById(R.id.history_type_image);
        nameView = (TextView) findViewById(R.id.name_view);
        dateView = (TextView) findViewById(R.id.date_view);
    }

    public void setMapListItem(MapHistoryItem item) {
        historyTypeImageView.setImageResource(item.getHistoryTypeImage());
        nameView.setText(item.getName());
        dateView.setText(item.getDate());
    }
}
