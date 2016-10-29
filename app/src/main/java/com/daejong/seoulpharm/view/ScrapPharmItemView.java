package com.daejong.seoulpharm.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.db.DBHelper;
import com.daejong.seoulpharm.model.PharmItem;

/**
 * Created by Hyunwoo on 2016. 10. 28..
 */
public class ScrapPharmItemView extends FrameLayout {

    PharmItem item;

    TextView pharmTitleView;
    TextView pharmTelView;
    TextView pharmAddressView;
    ImageView callBtn;
    ImageView deleteScrapBtn;

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

        callBtn = (ImageView) findViewById(R.id.btn_call);
        deleteScrapBtn = (ImageView) findViewById(R.id.btn_bookmark_delete);

        callBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNum = pharmTelView.getText().toString();
                Uri uri = Uri.parse("tel:" + phoneNum);
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                getContext().startActivity(intent);
            }
        });

        deleteScrapBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // Delete item in DB
                DBHelper db = new DBHelper(getContext());
                db.deletePharmScrapped(item.getMainKey());

                // Delete item in Adapter
                mOnDeleteButtonClickListener.onDeleteButtonClicked(item.getMainKey());
            }
        });

    }

    public void setScrappedPharmItemView (PharmItem item) {
        this.item = item;

        // Language 에 따른 수정 필요
        pharmTitleView.setText(item.getNameKor());
        pharmTelView.setText(item.getTel());
        pharmAddressView.setText(item.getAddressKor());
    }

    public interface OnDeleteButtonClickListener {
        public void onDeleteButtonClicked(String deleteKey);
    }
    OnDeleteButtonClickListener mOnDeleteButtonClickListener;
    public void setOnDeleteButtonClickListener (OnDeleteButtonClickListener listener) {
        this.mOnDeleteButtonClickListener = listener;
    }

}
