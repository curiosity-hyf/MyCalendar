package com.curiosity.mycalendar.retrofit.service;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Description :
 * Author : Curiosity
 * Date : 2017-3-12
 * E-mail : 1184581135qq@gmail.com
 */

public interface RetrofitService {
    @FormUrlEncoded
    @POST("/login!doLogin.action")
    Observable<String> login(@FieldMap Map<String, String> map);
}
