package com.curiosity.mycalendar.retrofit.Interceptor;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Curiosity on 2017-1-1.
 */

public class RestCurriculumInfoInterceptor implements Interceptor {

    private String cookie;
    private String account;

    public RestCurriculumInfoInterceptor(String cookie, String account) {
        this.cookie = cookie;
        this.account = account;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();

        FormBody formBody = (FormBody) oldRequest.body();

        FormBody.Builder formBuilder = new FormBody.Builder();
        for(int i = 0; i < formBody.size(); ++i) {
            formBuilder.add(formBody.name(i), formBody.value(i));
        }
        formBuilder.add("__EVENTTARGET", "xnd");
        formBuilder.add("__EVENTARGUMENT", "");
        RequestBody requestBody = formBuilder.build();

        Request request = oldRequest.newBuilder()
                .addHeader("Cookie", cookie)
                .addHeader("Referer", "http://jwgl.gdut.edu.cn/xskbcx.aspx?xh=" + account + "&xm=%BB%C6%D2%BB%B7%AB&gnmkdm=N121603")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36")
                .post(requestBody).build();

        Response response = chain.proceed(request);
        return response;
    }
}
