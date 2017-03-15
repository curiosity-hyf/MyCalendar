package com.curiosity.mycalendar.presenter.impl;

import com.curiosity.mycalendar.R;
import com.curiosity.mycalendar.presenter.IMainPresenter;
import com.curiosity.mycalendar.view.IMainView;

/**
 * Created by Curiosity on 2017-3-11.
 */

public class MainPresenter implements IMainPresenter {

    private IMainView mIMainView;

    public MainPresenter(IMainView mIMainView) {
        this.mIMainView = mIMainView;
    }

    @Override
    public void switchNavigation(int id) {
        switch (id) {
            case R.id.calendar_info:
                mIMainView.switch2Calender();
                break;
            case R.id.test:
                mIMainView.switch2Test();
                break;
            case R.id.curriculum_info:
                mIMainView.switch2Curriculum();
                break;
        }
    }

    @Override
    public void login() {
        mIMainView.login();
    }
}
