package com.daejong.seoulpharm.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Hyunwoo on 2016. 10. 8..
 */
public class SebcPharmacyInfoKor {  // response type model

    @SerializedName("list_total_count")
<<<<<<< HEAD:app/src/main/java/com/daejong/seoulpharm/model/SebcPharmacyInfoKor.java
    public int list_total_count;

    @SerializedName("RESULT")
    public Result RESULT;
=======
    private int listTotalCount;
    @SerializedName("RESULT")
    private Result result;
    private List<PharmItem> row;
>>>>>>> 180b046b3df8c82c20784fc8529323acdd7c618c:app/src/main/java/com/example/hyunwoo/samplenavermap/model/SebcPharmacyInfoKor.java

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
