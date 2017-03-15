package com.curiosity.mycalendar.utils;

import com.curiosity.mycalendar.config.FieldDefine;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;

import static com.curiosity.mycalendar.utils.HttpUtils.APP_LOGIN_URL;
import static com.curiosity.mycalendar.utils.HttpUtils.SOMETHING_ERROR;

/**
 * Created by Curiosity on 2016-12-30.
 */

public class RegisterUtils {

    public static final String HOST = "http://192.168.199.143:8080/";
    public static final String REGISTER_URL = FieldDefine.HOST + "/app_register";
    public static final int TIME_OUT = 10 * 1000;

    public static Observable<String> registerAccount(final String account, final String pwd) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS).build();
                FormBody.Builder builder = new FormBody.Builder();
                builder.add("account", account);
                builder.add("pwd", pwd);
                RequestBody requestBody = builder.build();
                Request request = new Request.Builder().url(REGISTER_URL).post(requestBody).build();
                okhttp3.Response response = null;
                try {
                    response = client.newCall(request).execute();
                    if(response.code() == 200) { //注册成功
                        subscriber.onNext(FieldDefine.REGISTER_PASS);
                    } else if(response.code() == 201) { //已被注册
                        subscriber.onNext(FieldDefine.REGISTER_DUPLI);
                    } else { //注册失败，可能是服务器的原因
                        subscriber.onNext(FieldDefine.REGISTER_FAIL);
                    }
                } catch (IOException e) {
                    subscriber.onNext(FieldDefine.REGISTER_FAIL);
                    e.printStackTrace();
                }
            }
        });
    }

    public static Observable<String> loginAccount(final String account, final String pwd) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS).build();
                FormBody.Builder builder = new FormBody.Builder();
                builder.add("aacount", account);
                builder.add("pwd", pwd);
                RequestBody body = builder.build();
                Request request = new Request.Builder().url(APP_LOGIN_URL).post(body).build();
                okhttp3.Response response1 = null;
                try {
                    response1 = client.newCall(request).execute();
                    if(response1.code() == 200) { //登录成功
                        subscriber.onNext(FieldDefine.LOGIN_PASS);
                    } else if(response1.code() == 201){ //密码错误
                        subscriber.onNext(FieldDefine.LOGIN_NO_EXISTS);
                    } else if(response1.code() == 202){ //账号不存在
                        subscriber.onNext(FieldDefine.LOGIN_NO_EXISTS);
                    }else { //账号异常
                        subscriber.onNext(FieldDefine.ACCOUNT_EXCEPTION);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(new IOException(SOMETHING_ERROR));
                }
            }
        });
    }
}
