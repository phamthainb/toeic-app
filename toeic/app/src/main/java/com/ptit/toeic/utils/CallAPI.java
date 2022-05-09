package com.ptit.toeic.utils;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.params.ClientPNames;

public class CallAPI {
    private static Context mContext;
    private static final String BASE_URL = "http://xxx.xxx.xxx:4567";
    private static AsyncHttpClient client;

    public CallAPI(Context mContext) {
        this.mContext = mContext;
        this.client = new AsyncHttpClient();
        this.client.getHttpClient().getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
    }

    // riêng login viết riêng 1 method để lưu lại token khi đăng nhập, còn lại dùng chung POST, GET ở dưới
    public static void login(RequestParams params) {
        client.post(mContext, getAbsoluteUrl("/account/login/"), params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    MySharedPreferences.savePreferences(mContext, "access", response.getString("access"));
                    MySharedPreferences.savePreferences(mContext, "refresh", response.getString("refresh"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getWithToken(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        String token = MySharedPreferences.getPreferences(mContext, "access", "");
        client.addHeader("Authorization", "Bearer " + token);
        client.get(mContext, getAbsoluteUrl(url), params, responseHandler);
    }

    public static void postWithToken(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        String token = MySharedPreferences.getPreferences(mContext, "access", "");
        client.addHeader("Authorization", "Bearer " + token);
        client.post(mContext, getAbsoluteUrl(url), params, responseHandler);
    }

    public static void putWithToken(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        String token = MySharedPreferences.getPreferences(mContext, "access", "");
        client.addHeader("Authorization", "Bearer " + token);
        client.put(mContext, getAbsoluteUrl(url), params, responseHandler);
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(mContext, getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(mContext, getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

}
