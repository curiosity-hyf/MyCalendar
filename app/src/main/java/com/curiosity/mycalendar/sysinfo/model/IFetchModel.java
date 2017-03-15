package com.curiosity.mycalendar.sysinfo.model;

import android.content.Context;

/**
 * Description : 学生登录业务模型
 * Author : Curiosity
 * Date : 2017-3-12
 * E-mail : 1184581135qq@gmail.com
 */

public interface IFetchModel {
    /**
     * 登录业务
     * @param account 账号
     * @param pwd 密码
     * @param listener 回调
     */
    void login(String account, String pwd, FetchModel.OnLoginListener listener);

    /**
     * 获取记住的密码
     * @param context 上下文
     * @param account 账号
     *
     * @return 密码
     */
    String getSave(Context context, String account);

    /**
     * 保存登录信息
     * @param context 上下文
     * @param account 账号
     * @param pwd 密码
     * @param isCheck 记住密码
     */
    void saveLoginInfo(Context context, String account, String pwd, boolean isCheck);

    /**
     * 获取学生信息
     */
//    void fetchStudentInfo();

    /**
     * 获取指定课表信息
     * @param year 年份
     * @param semester 学期
     * @param weeks 周次
     */
//    void fetchCurriculum(String year, String semester, String weeks);
}
