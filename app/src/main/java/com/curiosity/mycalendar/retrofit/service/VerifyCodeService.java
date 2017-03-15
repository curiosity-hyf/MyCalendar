package com.curiosity.mycalendar.retrofit.service;

import android.graphics.Bitmap;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Curiosity on 2016-12-31.
 */

public interface VerifyCodeService {
    @GET("/CheckCode.aspx")
    Observable<Bitmap> getVerifyCode();
}
