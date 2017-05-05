package com.curiosity.mycalendar.page.curriculum.presenter;

import android.content.Context;

import com.curiosity.mycalendar.bean.Curriculum;
import com.curiosity.mycalendar.page.curriculum.model.IWeekModel;
import com.curiosity.mycalendar.page.curriculum.model.WeekModel;
import com.curiosity.mycalendar.page.curriculum.view.IWeekView;

/**
 * Description :
 * Author : curiosity-hyf
 * Date : 17-5-4
 * E-mail : curiooosity.h@gmail.com
 */

public class WeekPresenter implements IWeekPresenter {

    private IWeekView weekView;
    private Context context;
    private IWeekModel model;

    public WeekPresenter(Context context, IWeekView weekView) {
        this.context = context;
        this.weekView = weekView;
        model = new WeekModel();
    }

    @Override
    public Curriculum getCurriculum() {
        return model.getCurriculum(context);
    }
}
