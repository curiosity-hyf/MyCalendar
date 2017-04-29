package com.curiosity.mycalendar.config;

/**
 * Description :
 * Author : curiosity-hyf
 * Date : 2016-12-25
 * E-mail : curiooosity.h@gmail.com
 */

public final class FieldDefine {

    public static final String EMPTY_MSG = "EMPTY_MSG";
    /* ---------- login param ---------- */
    public static final String L_GRADE = "grade";
    public static final String L_SEMESTER = "semester";
    public static final String L_ACCOUNT = "account";
    public static final String L_PWD = "pwd";

    /* ---------- host ---------- */
    public static String HOST;
    static {
        String PRO = "http://";
        String HOST_PREX = "222.200.98.147";
        HOST = PRO + HOST_PREX + "/";
    }




}
