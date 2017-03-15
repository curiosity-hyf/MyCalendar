package com.curiosity.mycalendar.retrofit.Interceptor;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Description :
 * Author : Curiosity
 * Date : 2017-3-12
 * E-mail : 1184581135qq@gmail.com
 */

public class AddCookiesInterceptor implements Interceptor {

    private List<String> mCookies;
    public AddCookiesInterceptor(List<String> cookies) {
        mCookies = cookies;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        Log.d("myd", "addcookies");
        for (String cookie : mCookies) {
            builder.addHeader("Cookie", cookie);
            Log.d("myd", "Adding Cookie: " + cookie);
        }

        return chain.proceed(builder.build());
    }
}
