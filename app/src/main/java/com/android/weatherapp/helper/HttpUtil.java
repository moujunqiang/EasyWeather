package com.android.weatherapp.helper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

    public static void postAsynHttp(String address, RequestBody formBody, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        /*RequestBody formBody = new FormBody.Builder()
                .add("size", "10")
                .build();*/
        Request request = new Request.Builder()
                .url(address)
                .post(formBody)
                .build();
        client.newCall(request).enqueue(callback);

    }
}
