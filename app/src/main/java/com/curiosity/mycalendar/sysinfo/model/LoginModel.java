package com.curiosity.mycalendar.sysinfo.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.curiosity.mycalendar.bean.CourseInfo;
import com.curiosity.mycalendar.bean.LoginInfo;
import com.curiosity.mycalendar.bean.StudentInfo;
import com.curiosity.mycalendar.utils.DomUtils;
import com.curiosity.mycalendar.utils.HttpUtils2;
import com.curiosity.mycalendar.utils.SQLiteHelper;
import com.curiosity.mycalendar.utils.SharedPreferenceUtil;
import com.curiosity.mycalendar.utils.TextUtils;
import com.google.gson.Gson;

/**
 * Created by red on 17-3-15.
 */

public class LoginModel implements ILoginModel {

    @Override
    public void login(final Context context, final String account, final String pwd, final boolean isCheck, final LoginModel.OnLoginListener listener) {
        HttpUtils2.ResultCallback<String> resultCallback = new HttpUtils2.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                LoginInfo info = new Gson().fromJson(response, LoginInfo.class);
                if("n".equals(info.getStatus())) {
                    listener.onLoginFailure(info.getMsg());
                } else {
                    try {
                        saveLoginInfo(context, account, pwd, isCheck);
                        listener.onLoginSuccess(info.getMsg());
                    } catch (Exception e) {
                        Log.d("myd", e.getMessage());
                        listener.onLoginFailure("A database error occurred");
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {
                Log.d("myd", "login fail: " + e.getMessage());
                listener.onLoginFailure("login failed");
            }
        };
        HttpUtils2.Param param = new HttpUtils2.Param();
        param.addParam("account", account);
        param.addParam("pwd", pwd);
        HttpUtils2.login(param, resultCallback);
    }

    @Override
    public String getLoginInfo(Context context, String account) {
        SQLiteDatabase db = SQLiteHelper.getReadableDatabase(context);
        Cursor cursor = SQLiteHelper.executeQuery(db,
                SQLiteHelper.GET_PWD,
                new String[]{account});
        String pwd = "";
        if (cursor.moveToNext()) {
            pwd = cursor.getString(cursor.getColumnIndex("pwd"));
        }
        cursor.close();
        SQLiteHelper.closeDatabase(db);
        return pwd;
    }

    private void saveLoginInfo(Context context, String account, String pwd, boolean isCheck) throws Exception{
        SharedPreferenceUtil.setSaveAccount(context, account);
        if (isCheck) {
            SharedPreferenceUtil.setCheckPwd(context, true);
            ContentValues values = new ContentValues();
            values.put("account", account);
            values.put("pwd", pwd);
            SQLiteHelper.executeInsertWithCheck(context, SQLiteHelper.USER_LOGIN_TABLE, "account", values);
        } else {
            SharedPreferenceUtil.setCheckPwd(context, false);
            ContentValues values = new ContentValues();
            values.put("account", account);
            values.put("pwd", "");
            SQLiteHelper.executeInsertWithCheck(context, SQLiteHelper.USER_LOGIN_TABLE, "account", values);
        }
    }

    /**
     * get the student information
     * @param listener
     */
    @Override
    public void fetchStudentInfo(final Context context, final OnLoginListener listener) {
        HttpUtils2.ResultCallback<String> resultCallback = new HttpUtils2.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                StudentInfo info = DomUtils.getStudentInfo(response);
                try {
                    saveStudentInfo(context, info);
                    listener.onLoadStuInfoSuccess(info);
                } catch (Exception e) {
                    Log.d("myd", e.getMessage());
                    listener.onLoadStuInfoFailure("A database error occurred");
                }
            }

            @Override
            public void onFailure(Exception e) {
                Log.d("myd", "fetchStudentInfo fail: " + e.getMessage());
                listener.onLoadStuInfoFailure("A server error occurred");
            }
        };

        HttpUtils2.getStuInfo(resultCallback);
    }

    private void saveStudentInfo(Context context, StudentInfo info) throws Exception {
        ContentValues values = new ContentValues();
        values.put("stuNum", info.getStuNum());
        values.put("admission", info.getAdmission());
        values.put("name", info.getName());
        values.put("institute", info.getInstitute());
        values.put("major", info.getMajor());
        values.put("clas", info.getClas());
        SQLiteHelper.executeInsertWithCheck(context, SQLiteHelper.STUDENT_INFO_TABLE, "stuNum", values);
    }

    @Override
    public String getStudentInfo(Context context, String whereArg, String whereVal, String columnName) {
        SQLiteDatabase db = SQLiteHelper.getReadableDatabase(context);
        Cursor cursor = SQLiteHelper.executeQuery(db,
                "select * from " + SQLiteHelper.STUDENT_INFO_TABLE +
                        " where " + whereArg +" = ?", new String[]{whereVal});
        String res = "";
        if (cursor.moveToNext()) {
            res = cursor.getString(cursor.getColumnIndex(columnName));
            Log.d("mytest", "getStudentInfo: = " + res);
        }
        cursor.close();
        SQLiteHelper.closeDatabase(db);
        return res;
    }

    @Override
    public void fetchCurriculum(final Context context, String admission, final int grade, final int semester, final OnLoginListener listener) {
        String curriculumYear = getCurriculumYear(admission, grade, semester);

        HttpUtils2.ResultCallback<String> resultCallback = new HttpUtils2.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                CourseInfo info = new Gson().fromJson(response, CourseInfo.class);
                try {
                    saveCurriculum(context, info, grade, semester);
                    Log.d("myd", "total = " + info.getTotal());
                    listener.onLoadCurriculumSuccess();
                } catch (Exception e) {
                    Log.d("myd", e.getMessage());
                    listener.onLoadCurriculumFailure("A database error occurred");
                }
            }

            @Override
            public void onFailure(Exception e) {
                Log.d("myd", "fetchCurriculum failed:" + e.getMessage());
                listener.onLoadCurriculumFailure("A server error occurred");
            }
        };

        HttpUtils2.Param param = new HttpUtils2.Param();
        param.addParam("xnxqdm", curriculumYear);
        param.addParam("zc", "");
        param.addParam("page", "1");
        param.addParam("rows", "1000");
        param.addParam("sort", "zc");
        param.addParam("orde", "asc");
        HttpUtils2.getCurriculum(param, resultCallback);
    }

    private void saveCurriculum(Context context, CourseInfo info, int grade, int semester) throws Exception {
        SQLiteDatabase db = SQLiteHelper.getWritableDatabase(context);
        SQLiteHelper.executeDelete(db, SQLiteHelper.COURSE_INFO_TABLE,
                "type = ? and grade = ? and semester = ?",
                new String[]{"1", String.valueOf(grade), String.valueOf(semester)});

        for(int i = 0; i < info.getTotal(); ++i) {
            CourseInfo.RowsBean bean = info.getRows().get(i);
            ContentValues values = new ContentValues();
            values.put("type", 1);
            values.put("grade", grade);
            values.put("semester", semester);
            values.put("weekNum", bean.getZc());
            values.put("dayNum", bean.getXq());
            values.put("clsNum", bean.getJcdm());
            values.put("name", bean.getKcmc());
            values.put("teacher", bean.getTeaxms());
            values.put("addr", bean.getJxcdmc());
            values.put("dayOfYear", bean.getPkrq());
            SQLiteHelper.executeInsert(db, SQLiteHelper.COURSE_INFO_TABLE, values);
            values.clear();
        }
        SQLiteHelper.closeDatabase(db);
//        getCurriculum(context, grade, semester);
    }

    private void getCurriculum(Context context, int grade, int semester) {
        SQLiteDatabase db = SQLiteHelper.getReadableDatabase(context);
        Cursor cursor =
                SQLiteHelper.executeQuery(db, "select * from " + SQLiteHelper.COURSE_INFO_TABLE, null);
        while(cursor.moveToNext()) {
            Log.d("myd", "type = " + cursor.getInt(cursor.getColumnIndex("type")) +
                    "grade = " + cursor.getInt(cursor.getColumnIndex("grade")) +
                    "semester = " + cursor.getInt(cursor.getColumnIndex("semester")) +
                    "semester = " + cursor.getString(cursor.getColumnIndex("semester")) +
                    "semester = " + cursor.getString(cursor.getColumnIndex("semester")) +
                    "clsNum = " + cursor.getString(cursor.getColumnIndex("clsNum")) +
                    "name = " + cursor.getString(cursor.getColumnIndex("name")) +
                    "teacher = " + cursor.getString(cursor.getColumnIndex("teacher")) +
                    "addr = " + cursor.getString(cursor.getColumnIndex("addr")) +
                    "dayOfYear = " + cursor.getString(cursor.getColumnIndex("dayOfYear")) +
                    "other = " + cursor.getString(cursor.getColumnIndex("other")));
        }
        cursor.close();
        SQLiteHelper.closeDatabase(db);
    }


    /**
     * 回调接口
     */
    public interface OnLoginListener {
        void onLoginSuccess(String msg);
        void onLoginFailure(String msg);

        void onLoadStuInfoSuccess(StudentInfo info);
        void onLoadStuInfoFailure(String msg);

        void onLoadCurriculumSuccess();
        void onLoadCurriculumFailure(String msg);
    }

    private String getCurriculumYear(String admission, int grade, int semester) {
        return String.valueOf(Integer.valueOf(admission) + grade - 1) + "0" + String.valueOf(semester);
    }
}
