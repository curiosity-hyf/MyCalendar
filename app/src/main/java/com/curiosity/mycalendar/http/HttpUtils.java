package com.curiosity.mycalendar.http;


import android.util.Log;

import com.curiosity.mycalendar.bean.StudentInfo;
import com.curiosity.mycalendar.http.Interceptor.AddCookiesInterceptor;
import com.curiosity.mycalendar.http.Interceptor.ReceivedCookiesInterceptor;
import com.curiosity.mycalendar.utils.DomUtils;

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

public class HttpUtils {
    private static final String TAG = "HttpUtils";

    private static final String BASE_URL = "http://222.200.98.147/";
    private static HttpUtils mInstance;

    private static RetrofitService retrofitService;
    private static List<String> cookies;

    private HttpUtils() {
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
            synchronized (HttpUtils.class) {
                if(mInstance == null) {
                    mInstance = new HttpUtils();
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

    public static void getStuInfo(final ResultCallback<String> callback) {
        getService().getStuInfo()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        StudentInfo mStudentInfo = DomUtils.getStudentInfo(s);
                        callback.onSuccess(s);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        callback.onFailure(new Exception(throwable));
                    }
                });
    }

    public static void getCurriculum(Param param, final ResultCallback<String> callback) {
        getService().getCurriculum(param.getParam())
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
