package com.curiosity.mycalendar.config;

/**
 * Description :
 * Author : Curiosity
 * Date : 2016-12-25
 * E-mail : 1184581135qq@gmail.com
 */

public class FieldDefine {
    public static final int LOGIN_REQUEST_CODE = 0;
    public static final String BUNDLE_STU_INFO = "BUNDLE_STU_INFO";
    public static final String EMPTY_MSG = "EMPTY_MSG";
    public static final int DATABASE_VERSION = 1;
    public static final String ISFIRSTIN = "ISFIRSTIN";
    public static final String ACCOUNT_EXCEPTION = "EXCEPTION";
    public static final String LOGIN_PASS = "LOGIN_PASS";
    public static final String LOGIN_NO_EXISTS = "LOGIN_NO_EXISTS";
    public static final String LOGIN_PSW_ERROR = "LOGIN_PSW_ERROR";

    public static final String REGISTER_PASS = "REGISTER_PASS";
    public static final String REGISTER_DUPLI = "REGISTER_DUPLI";
    public static final String REGISTER_FAIL = "REGISTER_FAIL";

    public static final String COOKIE_KEY = "COOKIE_KEY";
    public static final String ACCOUNT_KEY = "ACCOUNT_KEY";

    public static final String GET_STU_ERROR = "GET_STU_ERROR";

    public static String PRO = "http://";
    public static String HOST_PREX = "192.168.43.183";
    public static String PORT = "8080";
    public static String HOST;
    static {
        HOST = PRO + HOST_PREX + ":" + PORT;
    }

}
