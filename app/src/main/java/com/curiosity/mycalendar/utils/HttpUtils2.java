package com.curiosity.mycalendar.utils;


import android.util.Log;

import com.curiosity.mycalendar.retrofit.Interceptor.AddCookiesInterceptor;
import com.curiosity.mycalendar.retrofit.Interceptor.ReceivedCookiesInterceptor;
import com.curiosity.mycalendar.retrofit.service.RetrofitService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Description :
 * Author : Curiosity
 * Date : 2017-3-12
 * E-mail : 1184581135qq@gmail.com
 */

public class HttpUtils2 {
    private static final String TAG = "HttpUtils2";

    private static final String BASE_URL = "http://222.200.98.147/";
    private static HttpUtils2 mInstance;

    private static RetrofitService retrofitService;
    private static List<String> cookies;

    private HttpUtils2() {
        cookies = new ArrayList<>();
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .followRedirects(false)
                .addInterceptor(new ReceivedCookiesInterceptor(cookies))
                .addInterceptor(new AddCookiesInterceptor(cookies))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(mOkHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        retrofitService = retrofit.create(RetrofitService.class);
    }

    private static RetrofitService getService() {
        if(mInstance == null) {
            synchronized (HttpUtils2.class) {
                if(mInstance == null) {
                    mInstance = new HttpUtils2();
                }
            }
        }
        return retrofitService;
    }

    public static void login(Param param, final ResultCallback<String> callback) {
        if(cookies != null)
            cookies.clear();
        getService().login(param.getParam())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        callback.onSuccess(s);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d("myd", throwable.getMessage());
                        callback.onFailure(new Exception(throwable));
                    }
                });
    }

    public static void post(String url) {

    }

    public void getRequest(String url, ResultCallback callback) {

    }

    public void postRequest() {

    }

    //回调接口
    public interface ResultCallback<T> {
        void onSuccess(T response);
        void onFailure(Exception e);
    }

    //Post表单参数
    public static class Param {
        private Map<String, String> param;

        public Param() {
            param = new HashMap<>();
        }

        public void addParam(String key, String val) {
            param.put(key, val);
        }

        public Map<String, String> getParam() {
            return param;
        }
    }
}
