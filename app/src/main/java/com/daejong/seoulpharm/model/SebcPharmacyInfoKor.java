package com.daejong.seoulpharm.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Hyunwoo on 2016. 10. 8..
 */
public class SebcPharmacyInfoKor {
    // response type model

    @SerializedName("list_total_count")
    public int listTotalCount;

    @SerializedName("RESULT")
    public Result result;

    public List<PharmItem> row;
/*
    public SebcPharmacyInfoKor (int listTotalCount, Result result) {
        this.listTotalCount = listTotalCount;
        this.result = result;
    }


    public int getListTotalCount() {
        return listTotalCount;
    }
    public Result getResult() {
        return result;
    }

*/
}
