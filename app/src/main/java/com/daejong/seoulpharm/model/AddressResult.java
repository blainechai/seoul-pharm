package com.daejong.seoulpharm.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hyunwoo on 2016. 10. 22..
 */
public class AddressResult {
    private String total;
    private String userquery;
    private List<AddressItem> items = new ArrayList<>();

    public AddressResult(String total, String userquery, List<AddressItem> items) {
        this.total = total;
        this.userquery = userquery;
        this.items = items;
    }

    public String getTotal() {
        return total;
    }

    public String getUserquery() {
        return userquery;
    }

    public List<AddressItem> getItems() {
        return items;
    }
}
