package com.curiosity.mycalendar.main.presenter;

import android.content.Context;

import com.curiosity.mycalendar.R;
import com.curiosity.mycalendar.bean.StudentInfo;
import com.curiosity.mycalendar.main.model.IMainModel;
import com.curiosity.mycalendar.main.model.MainModel;
import com.curiosity.mycalendar.main.view.IMainView;

/**
 * Description :
 * Author : curiosity-hyf
 * Date : 2017-3-11
 * E-mail : curiooosity.h@gmail.com
 */

public class MainPresenter implements IMainPresenter {

    private IMainView mIMainView;
    private Context mContext;

    private IMainModel mIMainModel;

    public MainPresenter(IMainView mIMainView, Context context) {
        this.mIMainView = mIMainView;

        mContext = context;
        mIMainModel = new MainModel();
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

    @Override
    public void getStudentInfo() {
        String account = mIMainModel.getLoginNum(mContext);
        StudentInfo studentInfo = mIMainModel.getStudentInfo(mContext, account);
        if(studentInfo != null) {
            mIMainView.setStudentInfo(studentInfo.getName(),
                    studentInfo.getInstitute(),
                    studentInfo.getMajor(),
                    studentInfo.getClas());
        }
    }
}
