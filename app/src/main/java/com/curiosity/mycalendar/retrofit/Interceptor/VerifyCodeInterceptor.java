package com.curiosity.mycalendar.retrofit.Interceptor;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.curiosity.mycalendar.utils.HttpMethods.cookie;

/**
 * Created by Curiosity on 2016-12-31.
 */

public class VerifyCodeInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        Request newRequest = builder
                .addHeader("Cookie", cookie)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
                .build();
        Response response = chain.proceed(newRequest);

        //cookie = response.header("Set-Cookie");
        Log.d("mytest", "get verify code response code = " + response.code());
        if(response.code() == 302) {
            throw new IOException("error");
        }
        return chain.proceed(newRequest);
    }
}
