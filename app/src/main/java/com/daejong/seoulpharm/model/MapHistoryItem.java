package com.daejong.seoulpharm.model;

/**
 * Created by Hyunwoo on 2016. 10. 19..
 */
public class MapHistoryItem {
    // searched map items (mapListView in MapHistoryFragment)
    private int historyTypeImage;
    private String name;
    private String date;

    public int getHistoryTypeImage() {
        return historyTypeImage;
    }

    public void setHistoryTypeImage(int historyTypeImage) {
        this.historyTypeImage = historyTypeImage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
