package com.example.hyunwoo.samplenavermap;

import android.content.Context;
import android.util.Log;

import com.example.hyunwoo.samplenavermap.model.ResponseResult;
import com.example.hyunwoo.samplenavermap.model.SebcPharmacyInfoKor;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Hyunwoo on 2016. 10. 1..
 */
public class NetworkManager {

    AsyncHttpClient client;
    Gson gson;

    private static NetworkManager instance;
    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }
    private NetworkManager() {
        client = new AsyncHttpClient();
        client.setMaxRetriesAndTimeout(1, 3000);
        gson = new Gson();
    }


    public interface OnResultListener<T> {
        public void onSuccess(T result);
        public void onFail(int code);
    }

    public static final String OPEN_API_URL = "http://openapi.seoul.go.kr:8088/477a6b4d74626c613836547a636172/json/SebcPharmacyInfoKor/1/10/";


    public void getPharms(Context context, final OnResultListener<SebcPharmacyInfoKor> listener) {
        client.get(context, OPEN_API_URL, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                SebcPharmacyInfoKor result = gson.fromJson(responseString, SebcPharmacyInfoKor.class);
                listener.onSuccess(result);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFail(statusCode);

            }

        });

    }
}
