package com.daejong.seoulpharm.view;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.daejong.seoulpharm.R;

/**
 * Created by Hyunwoo on 2016. 10. 29..
 */
public class ConversationHeaderView extends FrameLayout {

    ImageView headerImage;

    public ConversationHeaderView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_conversation_header, this);
        headerImage = (ImageView) findViewById(R.id.image_header);
    }

    public void setHeaderImage(int imageResource) {
        headerImage.setImageResource(imageResource);
    }
}
