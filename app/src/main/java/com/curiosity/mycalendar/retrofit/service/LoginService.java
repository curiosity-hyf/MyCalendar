package com.curiosity.mycalendar.retrofit.service;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Curiosity on 2016-12-31.
 */

public interface LoginService {
    @FormUrlEncoded
    @POST("/default2.aspx")
    Observable<String> loginAccess(@Field("txtUserName") String account,
                                   @Field("TextBox2") String pwd,
                                   @Field("txtSecretCode") String verifyCode);
}
