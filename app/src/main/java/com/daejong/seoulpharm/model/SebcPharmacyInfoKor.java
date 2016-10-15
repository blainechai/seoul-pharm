package com.daejong.seoulpharm.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Hyunwoo on 2016. 10. 8..
 */
public class SebcPharmacyInfoKor {  // response type model

    @SerializedName("list_total_count")
    private int listTotalCount;
    @SerializedName("RESULT")
    private Result result;
    private List<PharmItem> row;

    // constructor
    public SebcPharmacyInfoKor (int listTotalCount, Result result, List<PharmItem> row) {
        this.listTotalCount = listTotalCount;
        this.result = result;
        this.row = row;
    }

    // getter & setter
    public int getListTotalCount() {
        return listTotalCount;
    }
    public Result getResult() {
        return result;
    }
    public List<PharmItem> getRow() {
        return row;
    }


}
