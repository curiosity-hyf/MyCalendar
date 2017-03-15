package com.curiosity.mycalendar.sysinfo.model;

import android.content.Context;

import com.curiosity.mycalendar.bean.StudentInfo;

/**
 * Created by red on 17-3-15.
 */

public interface ILoginModel {
    /**
     * 登录业务
     * @param account 账号
     * @param pwd 密码
     * @param listener 回调
     */
    void login(String account, String pwd, LoginModel.OnLoginListener listener);

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
    void fetchStudentInfo(final LoginModel.OnLoginListener listener);

    /**
     * save the student's information
     * @param info
     */
    void saveStudentInfo(StudentInfo info);
    /**
     * 获取指定课表信息
     * @param year 年份
     * @param semester 学期
     * @param weeks 周次
     */
    void fetchCurriculum(String year, String semester, String weeks);
}
