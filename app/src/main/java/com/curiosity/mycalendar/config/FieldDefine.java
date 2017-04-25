package com.curiosity.mycalendar.config;

/**
 * Description :
 * Author : Curiosity
 * Date : 2016-12-25
 * E-mail : curiooosity.h@gmail.com
 */

public final class FieldDefine {
    public static final int LOGIN_REQUEST_CODE = 0;
    public static final String EMPTY_MSG = "EMPTY_MSG";
    public static final int DATABASE_VERSION = 1;

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

    /* ---------- database ---------- */

    /* ---------- table ---------- */
    public static final String USER_LOGIN_TABLE = "user_login";
    public static final String COURSE_INFO_TABLE = "course_info";
    public static final String STUDENT_INFO_TABLE = "student_info";

    /* ---------- table user_login field ---------- */
    public static final String U_ACCOUNT = "u_account";
    public static final String U_PWD = "u_pwd";
    /* ---------- table course_info field ---------- */
    public static final String C_TYPE = "c_type";
    public static final String C_GRADE = "c_grade";
    public static final String C_SEMESTER = "c_semester";
    public static final String C_WEEK_NUM = "c_week_num";
    public static final String C_DAY_NUM = "c_day_num";
    public static final String C_CLS_NUM = "c_cls_num";
    public static final String C_NAME = "c_name";
    public static final String C_TEACHER = "c_teacher";
    public static final String C_ADDR = "c_addr";
    public static final String C_FULL_TIME = "c_full_time";
    public static final String C_OTHER = "c_other";

    /* ---------- table student_info field ---------- */
    public static final String S_NUM = "s_num";
    public static final String S_ADMISSION = "s_admission";
    public static final String S_NAME = "s_name";
    public static final String S_INSTITUTE = "s_institute";
    public static final String S_MAJOR = "s_major";
    public static final String S_CLASS = "s_class";

    /* ---------- create table statement ---------- */
    public static final String CREATE_UEAR_TABLE =
            "create table " + USER_LOGIN_TABLE + " (" +
                    U_ACCOUNT + " varchar(30) primary key not null, " +
                    U_PWD + " varchar(30) not null)";

    public static final String CREATE_COURSE_TABLE =
            "create table " + COURSE_INFO_TABLE + " (" +
                    C_TYPE + " integer not null, " + /*类型 : 1 系统 2 自定义*/
                    C_GRADE + " integer not null, " + /*学年*/
                    C_SEMESTER + " integer not null, " + /*学期*/
                    C_WEEK_NUM + " text not null, " + /*周次*/
                    C_DAY_NUM + " text not null, " + /*星期*/
                    C_CLS_NUM + " text not null, " + /*节次*/
                    C_NAME + " text not null, " + /*课程名称*/
                    C_TEACHER + " text not null, " + /*教师名称*/
                    C_ADDR + " text not null, " + /*上课地点*/
                    C_FULL_TIME + " text not null, " + /*完整时间*/
                    C_OTHER + " text" + /*其他*/
                    ")";

    public static final String CREATE_STUDENT_TABLE =
            "create table " + STUDENT_INFO_TABLE + " (" +
                    S_NUM + " text primary key, " + /*学号*/
                    S_ADMISSION + " integer not null, " + /*入学年份*/
                    S_NAME + " text not null, " + /*名称*/
                    S_INSTITUTE + " text not null, " + /*学院*/
                    S_MAJOR + " text not null, " + /*专业*/
                    S_CLASS + " text not null" + /*班级*/
                    ")";


}
