package com.curiosity.mycalendar.sysinfo.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.curiosity.mycalendar.bean.CourseInfo;
import com.curiosity.mycalendar.bean.LoginInfo;
import com.curiosity.mycalendar.bean.StudentInfo;
import com.curiosity.mycalendar.utils.DomUtils;
import com.curiosity.mycalendar.utils.HttpUtils2;
import com.curiosity.mycalendar.utils.SQLiteHelper;
import com.curiosity.mycalendar.utils.SharedPreferenceUtil;
import com.google.gson.Gson;

/**
 * Created by red on 17-3-15.
 */

public class LoginModel implements ILoginModel {
    @Override
    public void login(String account, String pwd, final LoginModel.OnLoginListener listener) {
        HttpUtils2.ResultCallback<String> resultCallback = new HttpUtils2.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                LoginInfo info = new Gson().fromJson(response, LoginInfo.class);
                if("n".equals(info.getStatus())) {
                    listener.onLoginFailure(info.getMsg());
                } else {
                    listener.onLoginSuccess(info.getMsg());
                }
            }

            @Override
            public void onFailure(Exception e) {
                listener.onLoginFailure("login failed");
            }
        };
        HttpUtils2.Param param = new HttpUtils2.Param();
        param.addParam("account", account);
        param.addParam("pwd", pwd);
        HttpUtils2.login(param, resultCallback);
    }

    @Override
    public String getSave(Context context, String account) {
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

    @Override
    public void saveLoginInfo(Context context, String account, String pwd, boolean isCheck) {
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
    public void fetchStudentInfo(final OnLoginListener listener) {
        HttpUtils2.ResultCallback<String> resultCallback = new HttpUtils2.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                StudentInfo info = DomUtils.getStudentInfo(response);
                Log.d("myd", "fetch success:" + info.toString());
            }

            @Override
            public void onFailure(Exception e) {
                Log.d("myd", "fetch failed:" + e.getMessage());
            }
        };

        HttpUtils2.getStuInfo(resultCallback);
    }

    @Override
    public void saveStudentInfo(StudentInfo info) {

    }

    @Override
    public void fetchCurriculum(String year, String semester, String weeks) {
        HttpUtils2.ResultCallback<String> resultCallback = new HttpUtils2.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                CourseInfo info = new Gson().fromJson(response, CourseInfo.class);
                Log.d("myd", "total = " + info.getRows().size());
                for(int i = 0; i < info.getTotal(); ++i) {
                    Log.d("myd", info.getRows().get(i).toString());
                }
            }

            @Override
            public void onFailure(Exception e) {
                Log.d("myd", "fetchCurriculum failed:" + e.getMessage());
            }
        };

        HttpUtils2.Param param = new HttpUtils2.Param();
        param.addParam("xnxqdm", "201602");
        param.addParam("zc", "");
        param.addParam("page", "1");
        param.addParam("rows", "1000");
        param.addParam("sort", "zc");
        param.addParam("order", "asc");
        HttpUtils2.getCurriculum(param, resultCallback);
    }


    /**
     * 登录回调接口
     */
    public interface OnLoginListener {
        void onLoginSuccess(String msg);
        void onLoginFailure(String msg);

        void onLoadStuInfoSuccess(String msg);
    }
}
