package com.curiosity.mycalendar.info.model;

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
     * @param isCheck  记住密码
     * @param listener 回调
     */
    void login(Context context, String account, String pwd, boolean isCheck, LoginModel.OnLoginListener listener);

    /**
     * 获取记住的密码
     *
     * @param context 上下文
     * @param account 账号
     * @return 密码
     */
    String getLoginInfo(Context context, String account);

    /**
     * 获取学生信息
     */
    void fetchStudentInfo(Context context, final LoginModel.OnLoginListener listener);

    /**
     * 获取指定课表信息
     *
     * @param context
     * @param admission 入学年份
     * @param grade
     * @param semester  学期
     */
    void fetchCurriculum(Context context, String admission, int grade, int semester, LoginModel.OnLoginListener listener);

    void saveLoginInfo(Context context, String account, String pwd, boolean isCheck);
}
