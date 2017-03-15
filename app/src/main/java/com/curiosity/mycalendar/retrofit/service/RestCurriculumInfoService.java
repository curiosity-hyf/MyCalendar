package com.curiosity.mycalendar.retrofit.service;

import com.curiosity.mycalendar.bean.RestgetCurriculum;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Curiosity on 2017-1-1.
 */

public interface RestCurriculumInfoService {
    @FormUrlEncoded
    @POST("/xskbcx.aspx?xm=%BB%C6%D2%BB%B7%AB&gnmkdm=N121603")
    Observable<RestgetCurriculum> getAllCurriculumInfo(@Query("xh") String account,
                                               @Field("__VIEWSTATE") String viewState,
                                               @Field("xnd") String xnd,
                                               @Field("xqd") int xqd);
}
