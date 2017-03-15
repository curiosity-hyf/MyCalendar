package com.curiosity.mycalendar.retrofit.Interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Curiosity on 2017-1-1.
 */

public class StudentInfoInterceptor implements Interceptor {

    private String cookie;
    private String account;

    public StudentInfoInterceptor(String cookie, String account) {
        this.cookie = cookie;
        this.account = account;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();

        Request.Builder newBuilder = oldRequest.newBuilder();
        Request newRequest = newBuilder
                .addHeader("Cookie", cookie)
                .addHeader("Referer", "http://jwgl.gdut.edu.cn/xs_main.aspx?xh=" + account)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
                .build();
//        Log.d("mytest", newRequest.toString());
//        Log.d("mytest", newRequest.headers().toString());
        Response response = chain.proceed(newRequest);
//        Log.d("mytest", response.body().string());
//        throw new IOException("info");
        return response;
    }
}
