package com.daejong.seoulpharm.model;

/**
 * Created by Hyunwoo on 2016. 10. 29..
 */
public class ConversationListItem {

    boolean isSelected;
    int selectedImageResource;

    String contentKor;
    String contentEng;
    String contentChi;

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

    public int getSelectedImageResource() {
        return selectedImageResource;
    }

    public void setSelectedImageResource(int selectedImageResource) {
        this.selectedImageResource = selectedImageResource;
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
