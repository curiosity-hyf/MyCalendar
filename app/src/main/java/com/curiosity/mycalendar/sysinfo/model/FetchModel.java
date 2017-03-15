package com.curiosity.mycalendar.sysinfo.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.curiosity.mycalendar.bean.LoginInfo;
import com.curiosity.mycalendar.utils.HttpUtils2;
import com.curiosity.mycalendar.utils.SQLiteHelper;
import com.curiosity.mycalendar.utils.SharedPreferenceUtil;
import com.google.gson.Gson;

/**
 * Description : 学生登录业务处理类
 * Author : Curiosity
 * Date : 2017-3-12
 * E-mail : 1184581135qq@gmail.com
 */

public class FetchModel implements IFetchModel {

    @Override
    public void login(String account, String pwd, final OnLoginListener listener) {
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
     * 登录回调接口
     */
    public interface OnLoginListener {
        void onLoginSuccess(String msg);
        void onLoginFailure(String msg);
    }
}
