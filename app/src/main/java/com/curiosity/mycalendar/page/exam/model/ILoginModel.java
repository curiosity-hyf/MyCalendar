package com.curiosity.mycalendar.page.exam.model;

import android.content.Context;

/**
 * Description :
 * Author : curiosity-hyf
 * Date : 2017-3-15
 * E-mail : curiooosity.h@gmail.com
 */

public interface ILoginModel {
    /**
     * 登录业务
     *
     * @param context  上下文
     * @param account  账号
     * @param pwd      密码
     * @param listener 回调
     */
    void login(Context context, String account, String pwd, LoginModel.OnLoginListener listener);


    /**
     * 获取学生信息
     */
    void fetchStudentInfo(Context context, final LoginModel.OnLoginListener listener);

    void fetchAllExam(Context context, String admission, LoginModel.OnLoginListener listener);

}
