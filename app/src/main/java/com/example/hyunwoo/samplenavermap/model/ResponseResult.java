package com.example.hyunwoo.samplenavermap.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hyunwoo on 2016. 10. 8..
 */
public class ResponseResult {

    @SerializedName("SebcPharmacyInfoKor")
    private SebcPharmacyInfoKor sebcPharmacyInfoKor;

    // constructor
    public ResponseResult(SebcPharmacyInfoKor sebcPharmacyInfoKor) {
        this.sebcPharmacyInfoKor = sebcPharmacyInfoKor;
    }

    // getter method
    public SebcPharmacyInfoKor getSebPharmacyInfoKor() {
        return sebcPharmacyInfoKor;
    }

}
