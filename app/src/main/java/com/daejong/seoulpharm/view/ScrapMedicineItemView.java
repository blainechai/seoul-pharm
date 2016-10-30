package com.daejong.seoulpharm.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.db.DBHelper;
import com.daejong.seoulpharm.model.MedicineInfo;
import com.daejong.seoulpharm.widget.NotoTextView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Hyunwoo on 2016. 10. 28..
 */
public class ScrapMedicineItemView extends FrameLayout {

    MedicineInfo item;

    ImageView medicineImage;
    NotoTextView medicineTitleView;
    NotoTextView medicineCompanyView;
    ImageView deleteScrapBtn;

    public ScrapMedicineItemView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_scrap_medicine_list_item, this);

        // VIEW INITIALIZE
        medicineImage = (ImageView) findViewById(R.id.medicine_image_view);
        medicineTitleView = (NotoTextView) findViewById(R.id.scarp_medicine_name);
        medicineCompanyView = (NotoTextView) findViewById(R.id.scrap_company);

        deleteScrapBtn = (ImageView) findViewById(R.id.btn_bookmark_delete);

        deleteScrapBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // Delete item in DB
                DBHelper db = new DBHelper(getContext());
                db.deleteMedicineScrapped(item.getItemSeq());

                // Delete item in Adapter
                mOnDeleteButtonClickListener.onDeleteButtonClicked(item.getBarcode());
            }
        });

    }

    public void setScrappedMedicineItemView (final MedicineInfo item) {
        this.item = item;

        // Language 에 따른 수정 필요
        medicineTitleView.setText(item.getName());
        medicineCompanyView.setText(item.getCompany());

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(item.getImageSrc(), null, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Bitmap image = BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length);
                medicineImage.setImageBitmap(image);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public interface OnDeleteButtonClickListener {
        public void onDeleteButtonClicked(String deleteKey);
    }
    OnDeleteButtonClickListener mOnDeleteButtonClickListener;
    public void setOnDeleteButtonClickListener (OnDeleteButtonClickListener listener) {
        this.mOnDeleteButtonClickListener = listener;
    }

}
