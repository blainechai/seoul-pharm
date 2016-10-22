package com.daejong.seoulpharm.util;

import android.content.Context;
import android.util.Log;

import com.daejong.seoulpharm.model.ResponseResult;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HeaderElement;
import cz.msebera.android.httpclient.ParseException;

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
        public void onFail(int code, String response);
    }

    public static final String OPEN_API_URL = "http://openapi.seoul.go.kr:8088/477a6b4d74626c613836547a636172/json/SebcPharmacyInfoKor/1/10/";


    public void getPharms(Context context, final OnResultListener<ResponseResult> listener) {
        client.get(context, OPEN_API_URL, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                ResponseResult result = gson.fromJson(responseString, ResponseResult.class);
                listener.onSuccess(result);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFail(statusCode, responseString);

            }

        });

    }

    public void getEngAddress(Context context, final OnResultListener<String> listener) {
        RequestParams params = new RequestParams();
        params.put("query", "seoul pharm");

        client.addHeader("Content-Type", "application/xml");
        client.addHeader("X-Naver-Client-Id", "VTse802YjiIWqbRNxuIl");
        client.addHeader("X-Naver-Client-Secret", "THzJyx3ZHT");

        client.get(context, "https://openapi.naver.com/v1/search/local.xml/",params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFail(statusCode, responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                listener.onSuccess(responseString);
            }
        });
    }
}
