package com.curiosity.mycalendar.main.model;

import android.content.Context;

import com.curiosity.mycalendar.bean.StudentInfo;

/**
 * Description :
 * Author : curiosity-hyf
 * Date : 17-4-28
 * E-mail : curiooosity.h@gmail.com
 */

public interface IMainModel {

    void logout(Context context);

    boolean getLoginStatus(Context context);

    String getLoginNum(Context context);

    StudentInfo getStudentInfo(Context context, String stuNum);
}
