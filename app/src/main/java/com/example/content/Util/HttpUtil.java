package com.example.content.Util;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 佳南 on 2017/8/27.
 */

public class HttpUtil {
    public static void sendOkHttpRequest(final String address,final okhttp3.Callback callback){
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(address).build();
                client.newCall(request).enqueue(callback);
            }
    }

