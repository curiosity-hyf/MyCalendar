package com.curiosity.mycalendar.retrofit.Interceptor;

import android.util.Log;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Curiosity on 2016-12-31.
 */

public class LoginInterceptor implements Interceptor {
    private String cookie;
    private String viewState;
    private String account;
    private String pwd;
    private String verifyCode;
    public LoginInterceptor(String account, String pwd, String verifyCode, String cookie, String viewState) {
        this.cookie = cookie;
        this.viewState = viewState;

        this.account = account;
        this.pwd = pwd;
        this.verifyCode = verifyCode;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();

        FormBody formBody = (FormBody) oldRequest.body();
        FormBody.Builder formBuilder = new FormBody.Builder();
        for(int i = 0; i < formBody.size(); ++i) {
            formBuilder.add(formBody.name(i), formBody.value(i));
        }
        formBuilder.add("__VIEWSTATE", viewState);
        formBuilder.add("RadioButtonList1", "学生"); //%D1%A7%C9%FA
        formBuilder.add("Button1", "");
        formBuilder.add("lbLanguage", "");
        formBuilder.add("hidPdrs", "");
        formBuilder.add("hidsc", "");
        RequestBody requestBody = formBuilder.build();

        Request request = oldRequest.newBuilder()
                .addHeader("Cookie", cookie)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36")
                .post(requestBody).build();

        Response response = chain.proceed(request);
        Log.d("mytest", "login response code : " + response.code());

//        String responseBody = TextUtils.convertStreamToString(response.body().byteStream(), "gbk");
//        throw  new IOException(responseBody);
//
        return response;
    }
}
