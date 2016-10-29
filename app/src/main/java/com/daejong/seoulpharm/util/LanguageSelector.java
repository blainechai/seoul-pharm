package com.daejong.seoulpharm.util;

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

    public static final String LANGUAGE_KOREAN = "MODE_KOREAN";
    public static final String LANGUAGE_CHINESE = "MODE_CHINESE";
    public static final String LANGUAGE_ENGLISH = "MODE_ENGLISH";

    private String languages[] = {LANGUAGE_KOREAN, LANGUAGE_ENGLISH, LANGUAGE_CHINESE};
    private int index = 0;

    private String currentLanguage = LANGUAGE_KOREAN;

    public void setCurrentLanguage(String currentLanguage) {
        this.currentLanguage = currentLanguage;
    }

    public String getCurrentLanguage() {
        return currentLanguage;
    }

    public void changeLanguage() {
        index = (index + 1) % 3;
        currentLanguage = languages[index];
        mListener.setViewContentsLanguage(currentLanguage);
    }

    public void syncLanguage() {
        mListener.setViewContentsLanguage(currentLanguage);
    }


    public interface OnLanguageChangeListener {
        public void setViewContentsLanguage(String currentLanguage);
    }

    private OnLanguageChangeListener mListener;

    public void setOnLanguageChangeListener(OnLanguageChangeListener mListener) {
        this.mListener = mListener;
    }


}
