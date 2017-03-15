package com.curiosity.mycalendar.retrofit.service;

import com.curiosity.mycalendar.bean.StudentInfo;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Curiosity on 2017-1-1.
 */

public interface StudentInfoService {
    @GET("/xsgrxx.aspx?xm=%25BB%25C6%25D2%25BB%25B7%25AB&gnmkdm=N121501")
    Observable<StudentInfo> getStudentInfo(@Query("xh") String account);
}
