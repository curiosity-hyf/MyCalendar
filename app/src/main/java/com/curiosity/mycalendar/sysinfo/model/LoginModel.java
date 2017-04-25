package com.curiosity.mycalendar.sysinfo.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.curiosity.mycalendar.bean.CourseInfo;
import com.curiosity.mycalendar.bean.LoginInfo;
import com.curiosity.mycalendar.bean.StudentInfo;
import com.curiosity.mycalendar.config.FieldDefine;
import com.curiosity.mycalendar.http.HttpUtils;
import com.curiosity.mycalendar.utils.DomUtils;
import com.curiosity.mycalendar.utils.SQLiteHelper;
import com.curiosity.mycalendar.utils.SharedPreferenceUtil;
import com.google.gson.Gson;

/**
 * Description :
 * Author : Curiosity
 * Date : 2017-3-12
 * E-mail : curiooosity.h@gmail.com
 */

public class LoginModel implements ILoginModel {

    @Override
    public void login(final Context context, final String account, final String pwd, final boolean isCheck, final LoginModel.OnLoginListener listener) {
        HttpUtils.ResultCallback<String> resultCallback = new HttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                LoginInfo info = new Gson().fromJson(response, LoginInfo.class);
                if ("n".equals(info.getStatus())) {
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
        HttpUtils.Param param = new HttpUtils.Param();
        param.addParam(FieldDefine.L_ACCOUNT, account);
        param.addParam(FieldDefine.L_PWD, pwd);
        HttpUtils.login(param, resultCallback);
    }

    @Override
    public String getLoginInfo(Context context, String account) {
        SQLiteDatabase db = SQLiteHelper.getReadableDatabase(context);
        Cursor cursor = SQLiteHelper.executeQuery(db,
                "select "+ FieldDefine.U_PWD + " from " +
                        FieldDefine.USER_LOGIN_TABLE +
                        " where " + FieldDefine.U_ACCOUNT + " = ?",
                new String[]{account});
        String pwd = "";
        if (cursor.moveToNext()) {
            pwd = cursor.getString(cursor.getColumnIndex(FieldDefine.U_PWD));
        }
        cursor.close();
        SQLiteHelper.closeDatabase(db);
        return pwd;
    }

    /**
     * 保存登录的表单
     * @param context 上下文
     * @param account 账号
     * @param pwd 密码
     * @param isCheck  记住密码
     * @throws Exception
     */
    private void saveLoginInfo(Context context, String account, String pwd, boolean isCheck) throws Exception {
        SharedPreferenceUtil.setSaveAccount(context, account);

        ContentValues values = new ContentValues();

        SharedPreferenceUtil.setCheckPwd(context, isCheck);
        values.put(FieldDefine.U_ACCOUNT, account);

        if(isCheck) {
            values.put(FieldDefine.U_PWD, pwd);
        } else {
            values.put(FieldDefine.U_PWD, "");
        }

        SQLiteHelper.executeInsertWithCheck(context, FieldDefine.USER_LOGIN_TABLE, FieldDefine.U_ACCOUNT, values);

        values.clear();
    }

    @Override
    public void fetchStudentInfo(final Context context, final OnLoginListener listener) {
        HttpUtils.ResultCallback<String> resultCallback = new HttpUtils.ResultCallback<String>() {
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

        HttpUtils.getStuInfo(resultCallback);
    }

    /**
     * 保存学生信息
     * @param context 上下文
     * @param info 学生信息
     * @throws Exception
     */
    private void saveStudentInfo(Context context, StudentInfo info) throws Exception {
        ContentValues values = new ContentValues();
        values.put(FieldDefine.S_NUM, info.getStuNum());
        values.put(FieldDefine.S_ADMISSION, info.getAdmission());
        values.put(FieldDefine.S_NAME, info.getName());
        values.put(FieldDefine.S_INSTITUTE, info.getInstitute());
        values.put(FieldDefine.S_MAJOR, info.getMajor());
        values.put(FieldDefine.S_CLASS, info.getClas());
        SQLiteHelper.executeInsertWithCheck(context, FieldDefine.STUDENT_INFO_TABLE, FieldDefine.S_NUM, values);
        values.clear();
    }

    @Override
    public String getStudentInfo(Context context, String whereArg, String whereVal, String columnName) {
        SQLiteDatabase db = SQLiteHelper.getReadableDatabase(context);
        Cursor cursor = SQLiteHelper.executeQuery(db,
                "select * from " + FieldDefine.STUDENT_INFO_TABLE +
                        " where " + whereArg + " = ?", new String[]{whereVal});
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

        HttpUtils.ResultCallback<String> resultCallback = new HttpUtils.ResultCallback<String>() {
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

        HttpUtils.Param param = new HttpUtils.Param();
        param.addParam("xnxqdm", curriculumYear);
        param.addParam("zc", "");
        param.addParam("page", "1");
        param.addParam("rows", "1000");
        param.addParam("sort", "zc");
        param.addParam("order", "asc");
        HttpUtils.getCurriculum(param, resultCallback);
    }

    private void saveCurriculum(Context context, CourseInfo info, int grade, int semester) throws Exception {
        SQLiteDatabase db = SQLiteHelper.getWritableDatabase(context);
        SQLiteHelper.executeDelete(db, FieldDefine.COURSE_INFO_TABLE,
                        FieldDefine.C_TYPE + " = ? and " +
                        FieldDefine.C_GRADE + " = ? and " +
                        FieldDefine.C_SEMESTER + " = ?",
                new String[]{"1", String.valueOf(grade), String.valueOf(semester)});

        for (int i = 0; i < info.getTotal(); ++i) {
            CourseInfo.RowsBean bean = info.getRows().get(i);
            ContentValues values = new ContentValues();
            values.put(FieldDefine.C_TYPE, 1);
            values.put(FieldDefine.C_GRADE, grade);
            values.put(FieldDefine.C_SEMESTER, semester);
            values.put(FieldDefine.C_WEEK_NUM, bean.getZc());
            values.put(FieldDefine.C_DAY_NUM, bean.getXq());
            values.put(FieldDefine.C_CLS_NUM, bean.getJcdm());
            values.put(FieldDefine.C_NAME, bean.getKcmc());
            values.put(FieldDefine.C_TEACHER, bean.getTeaxms());
            values.put(FieldDefine.C_ADDR, bean.getJxcdmc());
            values.put(FieldDefine.C_FULL_TIME, bean.getPkrq());
            SQLiteHelper.executeInsert(db, FieldDefine.COURSE_INFO_TABLE, values);
            values.clear();
        }
        SQLiteHelper.closeDatabase(db);
//        getCurriculum(context, grade, semester);
    }

    private void getCurriculum(Context context, int grade, int semester) {
        SQLiteDatabase db = SQLiteHelper.getReadableDatabase(context);
        Cursor cursor =
                SQLiteHelper.executeQuery(db, "select * from " + FieldDefine.COURSE_INFO_TABLE, null);
        while (cursor.moveToNext()) {
            Log.d("myd", FieldDefine.C_TYPE + " = " + cursor.getInt(cursor.getColumnIndex(FieldDefine.C_TYPE)) +
                    FieldDefine.C_GRADE + " = " + cursor.getInt(cursor.getColumnIndex(FieldDefine.C_GRADE)) +
                    FieldDefine.C_SEMESTER + " = " + cursor.getInt(cursor.getColumnIndex(FieldDefine.C_SEMESTER)) +
                    FieldDefine.C_CLS_NUM + " = " + cursor.getString(cursor.getColumnIndex(FieldDefine.C_CLS_NUM)) +
                    FieldDefine.C_NAME + " = " + cursor.getString(cursor.getColumnIndex(FieldDefine.C_NAME)) +
                    FieldDefine.C_TEACHER + " = " + cursor.getString(cursor.getColumnIndex(FieldDefine.C_TEACHER)) +
                    FieldDefine.C_ADDR + " = " + cursor.getString(cursor.getColumnIndex(FieldDefine.C_ADDR)) +
                    FieldDefine.C_FULL_TIME + " = " + cursor.getString(cursor.getColumnIndex(FieldDefine.C_FULL_TIME)) +
                    FieldDefine.C_OTHER + " = " + cursor.getString(cursor.getColumnIndex(FieldDefine.C_OTHER)));
        }
        cursor.close();
        SQLiteHelper.closeDatabase(db);
    }


    /**
     * 回调接口
     */
    public interface OnLoginListener {
        /**
         * 登录成功回调
         * @param msg
         */
        void onLoginSuccess(String msg);

        /**
         * 登录失败回调
         * @param msg
         */
        void onLoginFailure(String msg);

        /**
         * 加载学生信息成功回调
         * @param info
         */
        void onLoadStuInfoSuccess(StudentInfo info);

        /**
         * 加载学生信息失败回调
         * @param msg
         */
        void onLoadStuInfoFailure(String msg);

        /**
         * 加载课程信息成功回调
         */
        void onLoadCurriculumSuccess();

        /**
         * 加载课程信息失败回调
         * @param msg
         */
        void onLoadCurriculumFailure(String msg);
    }

    private String getCurriculumYear(String admission, int grade, int semester) {
        return String.valueOf(Integer.valueOf(admission) + grade - 1) + "0" + String.valueOf(semester);
    }
}
