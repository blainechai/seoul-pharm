package com.daejong.seoulpharm.model;

/**
 * Created by Hyunwoo on 2016. 10. 29..
 */
public class ConversationListItem {

    public static final String LAN_KOR = "LAN_KOR";
    public static final String LAN_ENG = "LAN_ENG";
    public static final String LAN_CHI = "LAN_CHI";

    boolean isSelected;
    String currentLanguage;

    String contentKor;
    String contentEng;
    String contentChi;

    public ConversationListItem(String content, String currentLanguage) {
        switch (currentLanguage) {
            case LAN_KOR :
                this.contentKor = content;
                this.contentEng = "";
                this.contentChi = "";
                break;
            case LAN_ENG :
                this.contentKor = "";
                this.contentEng = content;
                this.contentChi = "";
                break;
            case LAN_CHI :
                this.contentKor = "";
                this.contentEng = "";
                this.contentChi = content;
                break;
        }
    }



    public ConversationListItem(String contentKor, String contentEng, String contentChi) {
        this.contentKor = contentKor;
        this.contentEng = contentEng;
        this.contentChi = contentChi;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getContentKor() {
        return contentKor;
    }

    public void setContentKor(String contentKor) {
        this.contentKor = contentKor;
    }

    public String getContentEng() {
        return contentEng;
    }

    public void setContentEng(String contentEng) {
        this.contentEng = contentEng;
    }

    public String getContentChi() {
        return contentChi;
    }

    public void setContentChi(String contentChi) {
        this.contentChi = contentChi;
    }
}
