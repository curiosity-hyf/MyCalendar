package com.curiosity.mycalendar.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.curiosity.mycalendar.config.FieldDefine.DATABASE_VERSION;

/**
 * Created by Curiosity on 2016-12-29.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String USER_LOGIN_TABLE = "user_login";

    public static final String COURSE_INFO_TABLE = "course_info";

    public static final String STUDENT_INFO_TABLE = "student_info";

    public static final String GET_PWD =
            "select pwd from " +
            SQLiteHelper.USER_LOGIN_TABLE +
            " where account = ?";

    private static final String CREATE_UEAR_TABLE =
            "create table " + USER_LOGIN_TABLE + " (" +
            "account varchar(30) primary key not null, " +
            "pwd varchar(30) not null)";

    private static final String CREATE_COURSE_TABLE =
            "create table " + COURSE_INFO_TABLE + " (" +
            "type integer not null, " + /*类型 : 1 系统 2 自定义*/
            "grade integer not null, " + /*学年*/
            "semester integer not null, " + /*学期*/
            "weekNum text not null, " + /*周次*/
            "dayNum text not null, " + /*星期*/
            "clsNum text not null, " + /*节次*/
            "name text not null, " + /*课程名称*/
            "teacher text not null, " + /*教师名称*/
            "addr text not null, " + /*上课地点*/
            "dayOfYear text not null, " + /*完整时间*/
            "other text" + /*其他*/
            ")";

    private static final String CREATE_STUDENT_TABLE =
            "create table " + STUDENT_INFO_TABLE + " (" +
            "stuNum text primary key, " + /*学号*/
            "admission integer not null, " + /*入学年份*/
            "name text not null, " + /*名称*/
            "institute text not null, " + /*学院*/
            "major text not null, " + /*专业*/
            "clas text not null" + /*班级*/
            ")";

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("mytest", "create database");
        db.execSQL(CREATE_UEAR_TABLE);
//        db.execSQL(CREATE_CURRI_BASE);

        db.execSQL(CREATE_COURSE_TABLE);

        db.execSQL(CREATE_STUDENT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    /**
     * 插入
     * @param tableName 表名
     * @param values 键值对 列名-列值
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
     * @param context
     * @param tableName
     * @param checkColumn
     * @param values
     */
    public static void executeInsertWithCheck(Context context, String tableName, String checkColumn, ContentValues values) {
        SQLiteDatabase db1 = getReadableDatabase(context);
        String value = values.getAsString(checkColumn);
        String []strings = new String[]{value};
        Cursor cursor = executeQuery(db1,
                "select " + checkColumn +
                " from " + tableName +
                " where " + checkColumn + " = ?", strings);
        if(cursor.getCount() != 0) {
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
     * @param table 表名
     * @param values 键值对 列名-列值
     * @param whereClause where条件 e.g. id = ?
     * @param whereArgs 占位符值
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
     * @param table 表名
     * @param whereClause where条件 e.g. id = ?
     * @param whereArgs 占位符值
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
     * @param sql sql语句
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
        if(db.isOpen()) {
            db.close();
        }
    }
}
