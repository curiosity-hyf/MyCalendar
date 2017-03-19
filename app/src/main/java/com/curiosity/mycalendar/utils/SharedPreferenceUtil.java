package com.curiosity.mycalendar.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Set;

/**
 * Description :
 * Author : Curiosity
 * Date : 2016-12-29
 * E-mail : 1184581135qq@gmail.com
 */

public class SharedPreferenceUtil {
    public static final String ISFIRSTIN = "isFirstIn";
    public static final String GETUSER = "getUser";
    public static final String CURRINUM = "CURRINUM";
    public static final String GETSTUINFO = "GETSTUINFO";
    public static final String hasClassFromSys = "hasClassFromSys";
    public static final String hasClassCustom = "hasClassCustom";
    public static final String curYear = "curYear";
    public static final String curSemester = "curSemester";
    public static final String curWeek = "curWeek";
    public static final String SAVE_ACCOUNT = "SAVE_ACCOUNT";
    public static final String CHECK_PWD = "CHECK_PWD";
    public static final String COOKIES = "COOKIES";
    private SharedPreferenceUtil() {
    }

    public static void setCookies(Context context, Set<String> cookies) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(COOKIES, cookies);
        editor.apply();
    }

    public static Set<String> getCookies(Context context, Set<String> set) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getStringSet(COOKIES, set);
    }

    public static boolean getHasClassFromSys(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(hasClassFromSys, true);
    }

    public static void setHasClassFromSys(Context context, boolean has) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(hasClassFromSys, has);
        editor.apply();
    }

    public static boolean getHasClassCustom(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(hasClassCustom, true);
    }

    public static void setHasClassCustom(Context context, boolean has) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(hasClassCustom, has);
        editor.apply();
    }

    public static String getCurYear(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(curYear, "1");
    }

    public static void setCurYear(Context context, String year) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(curYear, year);
        editor.apply();
    }

    public static String getCurSemester(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(curSemester, "1");
    }

    public static void setCurSemester(Context context, String semester) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(curSemester, semester);
        editor.apply();
    }

    public static String getCurWeek(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(curWeek, "1");
    }

    public static void setCurWeek(Context context, String week) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(curWeek, week);
        editor.apply();
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

    public static boolean isFirstIn(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(ISFIRSTIN, true);
    }

//    public static String getUser(Context context) {
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//        return sharedPreferences.getString(GETUSER, null);
//    }

    public static void setIsFirstIn(Context context, boolean isFirsIn) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(ISFIRSTIN, isFirsIn);
        editor.apply();
    }

//    public static void setUser(Context context, String account) {
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(GETUSER, account);
//        editor.apply();
//    }

    public static String getCurriNum(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(CURRINUM, null);
    }

    public static void setCurriNum(Context context, String StuNum) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CURRINUM, StuNum);
        editor.apply();
    }

    public static boolean hasStuInfo(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(GETSTUINFO, false);
    }

    public static void setStuInfo(Context context, Boolean StuNum) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(GETSTUINFO, StuNum);
        editor.apply();
    }
}
