package com.curiosity.mycalendar.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.curiosity.mycalendar.bean.Course;

import java.util.Set;

/**
 * Description :
 * Author : curiosity-hyf
 * Date : 2016-12-29
 * E-mail : curiooosity.h@gmail.com
 */

public class SharedPreferenceUtil {
    public static final String hasClassFromSys = "hasClassFromSys";
    public static final String curYear = "curYear";
    public static final String curSemester = "curSemester";
    public static final String curWeekOrder = "curWeekOrder"; // 周次
    public static final String lastWeekTime = "lastWeekTime"; // 上一次设置周次时的时间
    public static final String SAVE_ACCOUNT = "SAVE_ACCOUNT";
    public static final String CHECK_PWD = "CHECK_PWD";
    public static final String SELECT_GRADE = "SELECT_GRADE";
    public static final String SELECT_SEMESTER = "SELECT_SEMESTER";

    private static final String LOGIN = "LOGIN";

    private SharedPreferenceUtil() {
    }

    public static void setSelectGrade(Context context, int grade) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SELECT_GRADE, grade);
        editor.apply();
    }

    public static int getSelectGrade(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(SELECT_GRADE, 0);
    }

    public static void setSelectSemester(Context context, int semester) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SELECT_SEMESTER, semester);
        editor.apply();
    }

    public static int getSelectSemester(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(SELECT_SEMESTER, 0);
    }

    public static void setWeekOrder(Context context, int weekOrder) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(curWeekOrder, weekOrder);
        editor.apply();
    }

    public static int getWeekOrder(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(curWeekOrder, 0);
    }

    public static void setWeekTime(Context context, String weekTime) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(lastWeekTime, weekTime);
        editor.apply();
    }

    public static String getWeekTime(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(lastWeekTime, "");
    }

    public static void setLogin(Context context, boolean login) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(LOGIN, login);
        editor.apply();
    }

    public static boolean getLogin(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public static void setHasClassFromSys(Context context, boolean has) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(hasClassFromSys, has);
        editor.apply();
    }

    public static String getCurYear(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(curYear, "1");
    }

    public static String getCurSemester(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(curSemester, "1");
    }

    public static String getSaveAccount(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(SAVE_ACCOUNT, "");
    }

    public static void setSaveAccount(Context context, String account) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_ACCOUNT, account);
        editor.apply();
    }

    public static boolean getCheckPwd(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(CHECK_PWD, false);
    }

    public static void setCheckPwd(Context context, boolean check) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(CHECK_PWD, check);
        editor.apply();
    }
}
