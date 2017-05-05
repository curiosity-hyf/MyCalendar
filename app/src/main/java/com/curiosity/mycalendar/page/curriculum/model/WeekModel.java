package com.curiosity.mycalendar.page.curriculum.model;

import android.content.Context;

import com.curiosity.mycalendar.bean.Curriculum;
import com.curiosity.mycalendar.bean.WeekCourses;
import com.curiosity.mycalendar.utils.SQLiteHelper;
import com.curiosity.mycalendar.utils.SharedPreferenceUtil;

import java.util.List;

/**
 * Description :
 * Author : curiosity-hyf
 * Date : 17-5-4
 * E-mail : curiooosity.h@gmail.com
 */

public class WeekModel implements IWeekModel {
    public WeekModel(){}
    @Override
    public Curriculum getCurriculum(Context context) {
        int grade = SharedPreferenceUtil.getSelectGrade(context);
        int semester = SharedPreferenceUtil.getSelectSemester(context);
        if(grade == 0 || semester == 0)
            return null;
        return SQLiteHelper.getCourse(context, grade, semester);
    }
}
