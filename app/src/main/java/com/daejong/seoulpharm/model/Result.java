package com.daejong.seoulpharm.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hyunwoo on 2016. 10. 8..
 */
public class Result {

    @SerializedName("CODE")
    private String code;
    @SerializedName("MESSAGE")
    private String message;

    // constructor
    public Result(String code, String message) {
        this.code = code;
        this.message = message;
    }

    // getter
    public String getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }


}
