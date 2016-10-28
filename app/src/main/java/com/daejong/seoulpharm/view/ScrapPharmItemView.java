package com.daejong.seoulpharm.view;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.model.PharmItem;

/**
 * Created by Hyunwoo on 2016. 10. 28..
 */
public class ScrapPharmItemView extends FrameLayout {

    TextView pharmTitleView;
    TextView pharmTelView;
    TextView pharmAddressView;

    public ScrapPharmItemView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_scrap_pharm_list_item, this);

        // VIEW INITIALIZE
        pharmTitleView = (TextView) findViewById(R.id.scrap_pharm_title);
        pharmTelView = (TextView) findViewById(R.id.scrap_pharm_tel);
        pharmAddressView = (TextView) findViewById(R.id.scrap_pharm_address);

    }

    public void setScrappedPharmItemView (PharmItem item) {

        // Language 에 따른 수정 필요
        pharmTitleView.setText(item.getNameKor());
        pharmTelView.setText(item.getTel());
        pharmAddressView.setText(item.getAddKorRoad());
    }

}
