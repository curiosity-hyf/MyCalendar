package com.curiosity.mycalendar.main.model;

import android.content.Context;

import com.curiosity.mycalendar.bean.StudentInfo;
import com.curiosity.mycalendar.utils.SQLiteHelper;
import com.curiosity.mycalendar.utils.SharedPreferenceUtil;

/**
 * Description :
 * Author : curiosity-hyf
 * Date : 17-4-28
 * E-mail : curiooosity.h@gmail.com
 */

public class MainModel implements IMainModel {

    @Override
    public String getLoginNum(Context context) {
        String account = SharedPreferenceUtil.getSaveAccount(context);

        return account;
    }

    @Override
    public StudentInfo getStudentInfo(Context context, String stuNum) {
        return SQLiteHelper.getStudentInfo(context, stuNum);
    }
}
