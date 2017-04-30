package com.curiosity.mycalendar.main.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.curiosity.mycalendar.bean.StudentInfo;
import com.curiosity.mycalendar.utils.SQLiteHelper;
import com.curiosity.mycalendar.utils.SharedPreferenceUtil;

/**
 * Description :
 * Author : curiosity-hyf
 * Date : 17-4-28
 * E-mail : curiooosity.h@gmail.com
 */

public class MainModel implements IMainModel {

    /**
     * 退出登录操作
     * 该操作将会删除 STUDENT_INFO_TABLE 及 COURSE_INFO_TABLE 表中的数据
     * @param context 上下文
     */
    @Override
    public void logout(Context context) {
        SQLiteDatabase db = SQLiteHelper.getWritableDatabase(context);
        SQLiteHelper.executeDelete(db, SQLiteHelper.STUDENT_INFO_TABLE, null, null);
        SQLiteHelper.executeDelete(db, SQLiteHelper.COURSE_INFO_TABLE, SQLiteHelper.C_TYPE + " = ?", new String[]{SQLiteHelper.C_TYPE_SYSTEM});

        SharedPreferenceUtil.setLogin(context, false);
        Log.d("mytest", "MainModel logout: delete database");
    }

    @Override
    public boolean getLoginStatus(Context context) {
        return SharedPreferenceUtil.getLogin(context);
    }

    @Override
    public String getLoginNum(Context context) {

        return SharedPreferenceUtil.getSaveAccount(context);
    }

    @Override
    public StudentInfo getStudentInfo(Context context, String stuNum) {
        return SQLiteHelper.getStudentInfo(context, stuNum);
    }
}
