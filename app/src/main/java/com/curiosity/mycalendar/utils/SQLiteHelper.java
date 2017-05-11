package com.curiosity.mycalendar.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.curiosity.mycalendar.bean.Course;
import com.curiosity.mycalendar.bean.CoursesJSON;
import com.curiosity.mycalendar.bean.Curriculum;
import com.curiosity.mycalendar.bean.StudentInfo;
import com.curiosity.mycalendar.bean.WeekCourses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Description :
 * Author : curiosity-hyf
 * Date : 2016-12-29
 * E-mail : curiooosity.h@gmail.com
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    /* ---------- database ---------- */

    /* ---------- table ---------- */
    public static final String USER_LOGIN_TABLE = "user_login";
    public static final String COURSE_INFO_TABLE = "course_info";
    public static final String STUDENT_INFO_TABLE = "student_info";

    /* ---------- table user_login field ---------- */
    public static final String U_ACCOUNT = "u_account";
    public static final String U_PWD = "u_pwd";
    /* ---------- table course_info field ---------- */
    public static final String C_TYPE = "c_type"; /*类型 : 1 系统 2 自定义*/
    public static final String C_TYPE_SYSTEM = "1";
    public static final String C_TYPE_CUSTOM = "2";

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
    private static final String CREATE_USER_TABLE =
            "create table " + USER_LOGIN_TABLE + " (" +
                    U_ACCOUNT + " varchar(30) primary key not null, " +
                    U_PWD + " varchar(30) not null" +
                    ")";

    private static final String CREATE_COURSE_TABLE =
            "create table " + COURSE_INFO_TABLE + " (" +
                    C_TYPE + " integer not null, " + /*类型 : 1 系统 2 自定义*/
                    C_GRADE + " integer not null, " + /*学年*/
                    C_SEMESTER + " integer not null, " + /*学期*/
                    C_WEEK_NUM + " integer not null, " + /*周次*/
                    C_DAY_NUM + " integer not null, " + /*星期*/
                    C_CLS_NUM + " text not null, " + /*节次*/
                    C_NAME + " text not null, " + /*课程名称*/
                    C_TEACHER + " text not null, " + /*教师名称*/
                    C_ADDR + " text not null, " + /*上课地点*/
                    C_FULL_TIME + " text not null, " + /*完整时间 yyyy-MM-dd*/
                    C_OTHER + " text" + /*其他*/
                    ")";

    private static final String CREATE_STUDENT_TABLE =
            "create table " + STUDENT_INFO_TABLE + " (" +
                    S_NUM + " text primary key, " + /*学号*/
                    S_ADMISSION + " integer not null, " + /*入学年份*/
                    S_NAME + " text not null, " + /*名称*/
                    S_INSTITUTE + " text not null, " + /*学院*/
                    S_MAJOR + " text not null, " + /*专业*/
                    S_CLASS + " text not null" + /*班级*/
                    ")";

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    /**
     * 插入
     *
     * @param tableName 表名
     * @param values    键值对 列名-列值
     */
    public static void executeInsert(SQLiteDatabase db, String tableName, ContentValues values) {
        db.beginTransaction();
        try {
            db.insert(tableName, null, values);
            values.clear();
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    /**
     * 插入前先检查，若存在则更新。
     *
     * @param context
     * @param tableName
     * @param checkColumn
     * @param values
     */
    public static void executeInsertWithCheck(Context context, String tableName, String checkColumn, ContentValues values) {
        SQLiteDatabase db1 = getReadableDatabase(context);
        String value = values.getAsString(checkColumn);
        String[] strings = new String[]{value};
        Cursor cursor = executeQuery(db1,
                "select " + checkColumn +
                        " from " + tableName +
                        " where " + checkColumn + " = ?", strings);
        if (cursor.getCount() != 0) {
            Log.d("mytest", "insertCheck with update");
            SQLiteDatabase db2 = getWritableDatabase(context);
            executeUpdate(db2, tableName, values, checkColumn + " = ?", strings);
            closeDatabase(db2);
        } else {
            Log.d("mytest", "insertCheck with insert");
            SQLiteDatabase db3 = getWritableDatabase(context);
            executeInsert(db3, tableName, values);
            closeDatabase(db3);
        }
        cursor.close();
        closeDatabase(db1);
    }

    /**
     * 更新
     *
     * @param table       表名
     * @param values      键值对 列名-列值
     * @param whereClause where条件 e.g. id = ?
     * @param whereArgs   占位符值
     */
    public static void executeUpdate(SQLiteDatabase db, String table, ContentValues values, String whereClause, String[] whereArgs) {
        db.beginTransaction();
        try {
            db.update(table, values, whereClause, whereArgs);
            values.clear();
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    /**
     * 删除
     *
     * @param table       表名
     * @param whereClause where条件 e.g. id = ?
     * @param whereArgs   占位符值
     */
    public static void executeDelete(SQLiteDatabase db, String table, String whereClause, String[] whereArgs) {
        db.beginTransaction();
        try {
            db.delete(table, whereClause, whereArgs);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    /**
     * 查询
     *
     * @param sql        sql语句
     * @param parameters 占位符值
     * @return 查询结果 {@link Cursor}
     */
    public static Cursor executeQuery(SQLiteDatabase db, String sql, String[] parameters) {
        Cursor cursor = null;
        db.beginTransaction();
        try {
            cursor = db.rawQuery(sql, parameters);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return cursor;
    }

    public static SQLiteDatabase getWritableDatabase(Context context) {
        SQLiteHelper helper = new SQLiteHelper(context, "DB", null, DATABASE_VERSION);
        return helper.getWritableDatabase();
    }

    public static SQLiteDatabase getReadableDatabase(Context context) {
        SQLiteHelper helper = new SQLiteHelper(context, "DB", null, DATABASE_VERSION);
        return helper.getReadableDatabase();
    }

    public static void closeDatabase(SQLiteDatabase db) {
        if (db.isOpen()) {
            db.close();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("mytest", "create database");
        db.execSQL(CREATE_USER_TABLE);

        db.execSQL(CREATE_COURSE_TABLE);

        db.execSQL(CREATE_STUDENT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /**
     * 保存学生信息
     *
     * @param context 上下文
     * @param info    学生信息
     * @throws Exception
     */
    public static void saveStudentInfo(Context context, StudentInfo info) throws Exception {
        SQLiteDatabase db = getWritableDatabase(context);
        executeDelete(db, STUDENT_INFO_TABLE, null, null);

        ContentValues values = new ContentValues();
        values.put(S_NUM, info.getStuNum());
        values.put(S_ADMISSION, info.getAdmission());
        values.put(S_NAME, info.getName());
        values.put(S_INSTITUTE, info.getInstitute());
        values.put(S_MAJOR, info.getMajor());
        values.put(S_CLASS, info.getClas());
        SQLiteHelper.executeInsertWithCheck(context, STUDENT_INFO_TABLE, S_NUM, values);
        values.clear();
        closeDatabase(db);
    }

    public static StudentInfo getStudentInfo(Context context, String stuNum) {
        SQLiteDatabase db = getReadableDatabase(context);
        Cursor cursor = executeQuery(db,
                "select * from " + STUDENT_INFO_TABLE +
                        " where " + S_NUM + " = ?", new String[]{stuNum});
        StudentInfo info = null;
        if (cursor.moveToNext()) {
            info = new StudentInfo();
            info.setStuNum(cursor.getString(cursor.getColumnIndex(S_NUM)));
            info.setName(cursor.getString(cursor.getColumnIndex(S_NAME)));
            info.setInstitute(cursor.getString(cursor.getColumnIndex(S_INSTITUTE)));
            info.setMajor(cursor.getString(cursor.getColumnIndex(S_MAJOR)));
            info.setClas(cursor.getString(cursor.getColumnIndex(S_CLASS)));
            info.setAdmission(cursor.getString(cursor.getColumnIndex(S_ADMISSION)));

            Log.d("mytest", "SQLiteHelper getStudentInfo: = " + info.toString());
        }
        cursor.close();
        closeDatabase(db);
        return info;
    }

    public static String getStudentInfo(Context context, String stuNum, String columnName) {
        SQLiteDatabase db = getReadableDatabase(context);
        Cursor cursor = executeQuery(db,
                "select * from " + STUDENT_INFO_TABLE +
                        " where " + S_NUM + " = ?", new String[]{stuNum});
        String res = "";
        if (cursor.moveToNext()) {
            res = cursor.getString(cursor.getColumnIndex(columnName));
            Log.d("mytest", "getStudentInfo: = " + res);
        }
        cursor.close();
        closeDatabase(db);
        return res;
    }

    public static void saveCourse(Context context, CoursesJSON info, int grade, int semester) throws Exception {
        SQLiteDatabase db = getWritableDatabase(context);
        executeDelete(db, COURSE_INFO_TABLE,
                C_TYPE + " = ? and " +
                        C_GRADE + " = ? and " +
                        C_SEMESTER + " = ?",
                new String[]{String.valueOf(Course.TYPE.SYSTEM.ordinal()), String.valueOf(grade), String.valueOf(semester)});

        ContentValues values = new ContentValues();
        for (int i = 0; i < info.getTotal(); ++i) {
            CoursesJSON.RowsBean bean = info.getRows().get(i);

            values.put(C_TYPE, Course.TYPE.SYSTEM.ordinal());
            values.put(C_GRADE, grade);
            values.put(C_SEMESTER, semester);
            values.put(C_WEEK_NUM, bean.getZc());
            values.put(C_DAY_NUM, bean.getXq());
            values.put(C_CLS_NUM, bean.getJcdm());
            values.put(C_NAME, bean.getKcmc());
            values.put(C_TEACHER, bean.getTeaxms());
            values.put(C_ADDR, bean.getJxcdmc());
            values.put(C_FULL_TIME, bean.getPkrq());
            SQLiteHelper.executeInsert(db, COURSE_INFO_TABLE, values);
            values.clear();
        }
        closeDatabase(db);
    }

    private static List<Integer> getWeekNumList(Context context, int grade, int semester) {
        SQLiteDatabase db = getReadableDatabase(context);
        Cursor cursor = executeQuery(db,
                "select distinct " + C_WEEK_NUM +
                        " from " + COURSE_INFO_TABLE +
                        " where " + C_GRADE + " = ?" +
                        " and " + C_SEMESTER + " = ?" +
                        " order by " + C_WEEK_NUM + " asc",
                new String[]{String.valueOf(grade), String.valueOf(semester)});
        List<Integer> weekNum = new ArrayList<>();

        while (cursor.moveToNext()) {
            int week = cursor.getInt(cursor.getColumnIndex(C_WEEK_NUM));
            weekNum.add(week);
        }
        cursor.close();
        db.close();
        return weekNum;
    }

    public static int getMaxWeekNum(Context context, int grade, int semester) {
        SQLiteDatabase db = getReadableDatabase(context);
        Cursor cursor = executeQuery(db,
                "select max(" + C_WEEK_NUM + ")" +
                        " from " + COURSE_INFO_TABLE +
                        " where " + C_GRADE + " = ?" +
                        " and " + C_SEMESTER + " = ?",
                new String[]{String.valueOf(grade), String.valueOf(semester)});

        int res = 1;
        if (cursor.moveToNext()) {
            res = cursor.getInt(cursor.getColumnIndex("max("+C_WEEK_NUM+")"));
        }
        cursor.close();
        db.close();
        return res;
    }

    public static Curriculum getCourse(Context context, int grade, int semester) {
        List<Integer> list = getWeekNumList(context, grade, semester);

        Curriculum curriculum = new Curriculum(grade, semester);
        SQLiteDatabase db = SQLiteHelper.getReadableDatabase(context);
        Cursor cursor = null;
        for (int i = 0; i < list.size(); ++i) {
            cursor = executeQuery(db,
                    "select *" +
                            " from " + COURSE_INFO_TABLE +
                            " where " + C_WEEK_NUM + " = ?" +
                            " and " + C_GRADE + " = ?" +
                            " and " + C_SEMESTER + " = ?" +
                            " order by " + C_DAY_NUM + ", " + C_CLS_NUM +
                            " asc", new String[]{String.valueOf(list.get(i)), String.valueOf(grade), String.valueOf(semester)});
            WeekCourses courses = new WeekCourses(list.get(i));
            while (cursor.moveToNext()) {
                Course.TYPE type = cursor.getInt(cursor.getColumnIndex(C_TYPE)) == 0 ? Course.TYPE.SYSTEM : Course.TYPE.CUSTOM;
                int weekNum = cursor.getInt(cursor.getColumnIndex(C_WEEK_NUM));
                int dayNum = cursor.getInt(cursor.getColumnIndex(C_DAY_NUM));
                String clsNum = cursor.getString(cursor.getColumnIndex(C_CLS_NUM));
                String name = cursor.getString(cursor.getColumnIndex(C_NAME));
                String teacher = cursor.getString(cursor.getColumnIndex(C_TEACHER));
                String addr = cursor.getString(cursor.getColumnIndex(C_ADDR));
                String fullTime = cursor.getString(cursor.getColumnIndex(C_FULL_TIME));
                String other = cursor.getString(cursor.getColumnIndex(C_OTHER));

                Course course = new Course();
                course.setType(type);
                course.setGrade(grade);
                course.setSemester(semester);
                course.setWeekNum(weekNum);
                course.setDayNum(dayNum);
                course.setClsNum(clsNum);
                course.setName(name);
                course.setTeacher(teacher);
                course.setAddr(addr);
                course.setFullTime(fullTime);
                course.setFullTime(fullTime);
                course.setOther(other);

                courses.add(course);
            }
            curriculum.add(courses);
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return curriculum;
    }
}
