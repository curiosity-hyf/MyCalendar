package com.curiosity.mycalendar.http.Interceptor;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Description :
 * Author : Curiosity
 * Date : 2017-3-16
 * E-mail : curiooosity.h@gmail.com
 */

public class ReceivedCookiesInterceptor implements Interceptor {
    private List<String> mCookies;

    public ReceivedCookiesInterceptor(List<String> cookies) {
        mCookies = cookies;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        Log.d("myd", "received cookies");
        List<String> cookies = originalResponse.headers("Set-Cookie");
        if (!cookies.isEmpty()) {
            for (String cookie : cookies) {
                mCookies.add(cookie);
                Log.d("myd", "Receiving Cookie: " + cookie);
            }
        }

        return originalResponse;
    }
}
