package com.curiosity.mycalendar.retrofit.service;

import com.curiosity.mycalendar.bean.FirstgetCurriculum;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Curiosity on 2017-1-1.
 */

public interface FirstCurriculumInfoService {
    @GET("/xskbcx.aspx?xm=%BB%C6%D2%BB%B7%AB&gnmkdm=N121603")
    Observable<FirstgetCurriculum> getFirstInfo(@Query("xh") String account);
}
