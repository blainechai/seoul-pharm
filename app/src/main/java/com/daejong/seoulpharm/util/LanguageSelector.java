package com.daejong.seoulpharm.util;

import com.daejong.seoulpharm.R;

/**
 * Created by Hyunwoo on 2016. 10. 28..
 */
public class LanguageSelector {

    private static LanguageSelector instance;

    public static LanguageSelector getInstance() {
        if (instance == null) {
            instance = new LanguageSelector();
        }
        return instance;
    }

    private LanguageSelector() {
    }
    
    private int currentLanguage = R.drawable.btn_kor;

    public void setCurrentLanguage(int id) {
        this.currentLanguage = id;
    }

    public int getCurrentLanguage() {
        return currentLanguage;
    }

    public void syncLanguage() {
        mListener.setViewContentsLanguage(currentLanguage);
    }


    public interface OnLanguageChangeListener {
        public void setViewContentsLanguage(int backgroundId);
    }

    public void changeLanguage(int id){
        if(currentLanguage == id) return;
        setCurrentLanguage(id);
        syncLanguage();
    }

    private OnLanguageChangeListener mListener;

    public void setOnLanguageChangeListener(OnLanguageChangeListener mListener) {
        this.mListener = mListener;
    }


}
