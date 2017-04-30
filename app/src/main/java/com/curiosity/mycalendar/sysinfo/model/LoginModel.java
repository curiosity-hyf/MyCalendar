package com.curiosity.mycalendar.sysinfo.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.curiosity.mycalendar.bean.CoursesInfo;
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
 * Author : curiosity-hyf
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
                        listener.onLoginSuccess(info.getMsg());
                    } catch (Exception e) {
                        Log.d("myd", "login: " + e.getMessage());
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
                "select "+ SQLiteHelper.U_PWD + " from " +
                        SQLiteHelper.USER_LOGIN_TABLE +
                        " where " + SQLiteHelper.U_ACCOUNT + " = ?",
                new String[]{account});
        String pwd = "";
        if (cursor.moveToNext()) {
            pwd = cursor.getString(cursor.getColumnIndex(SQLiteHelper.U_PWD));
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
    @Override
    public void saveLoginInfo(Context context, String account, String pwd, boolean isCheck) {
        // 在 SharedPreference 中存入账号 和 是否记住密码
        SharedPreferenceUtil.setSaveAccount(context, account);
        SharedPreferenceUtil.setCheckPwd(context, isCheck);

        // 设置当前状态为已登录
        SharedPreferenceUtil.setLogin(context, true);
        // TODO 这里待加入 加密，防止泄露信息
        // 在数据库中存入登录的账号/密码
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.U_ACCOUNT, account);

        if(isCheck) {
            values.put(SQLiteHelper.U_PWD, pwd);
        } else {
            values.put(SQLiteHelper.U_PWD, "");
        }

        SQLiteHelper.executeInsertWithCheck(context, SQLiteHelper.USER_LOGIN_TABLE, SQLiteHelper.U_ACCOUNT, values);

        values.clear();
    }

    @Override
    public void fetchStudentInfo(final Context context, final OnLoginListener listener) {
        HttpUtils.ResultCallback<String> resultCallback = new HttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                StudentInfo info = DomUtils.getStudentInfo(response);
                Log.d("mytest", "LoginModel fetchStudentInfo: " + info.toString());
                try {
                    SQLiteHelper.saveStudentInfo(context, info);
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

    @Override
    public void fetchCurriculum(final Context context, String admission, final int grade, final int semester, final OnLoginListener listener) {
        String curriculumYear = getCurriculumYear(admission, grade, semester);

        HttpUtils.ResultCallback<String> resultCallback = new HttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                CoursesInfo info = new Gson().fromJson(response, CoursesInfo.class);
                Log.d("myd", "fetch: " + info.toString());
                try {
                    SQLiteHelper.saveCourse(context, info, grade, semester);
                    Log.d("myd", "total = " + info.getTotal());
                    listener.onLoadCurriculumSuccess();
                } catch (Exception e) {
                    Log.d("myd", "fetchCurriculum: " + e.getMessage());
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
