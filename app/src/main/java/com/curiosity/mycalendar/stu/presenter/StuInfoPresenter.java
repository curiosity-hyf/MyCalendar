package com.curiosity.mycalendar.stu.presenter;

import com.curiosity.mycalendar.stu.model.IStuInfoModel;
import com.curiosity.mycalendar.stu.model.OnLoadStuInfoListener;
import com.curiosity.mycalendar.stu.model.StuInfoModel;
import com.curiosity.mycalendar.stu.view.IStuInfoView;

/**
 * Description :
 * Author : Curiosity
 * Date : 2017-3-12
 * E-mail : 1184581135qq@gmail.com
 */

public class StuInfoPresenter implements IStuInfoPresenter, OnLoadStuInfoListener {

    private IStuInfoModel mStuInfoModel;
    private IStuInfoView mStuInfoView;

    public StuInfoPresenter(IStuInfoView stuInfoView) {
        mStuInfoView = stuInfoView;
        mStuInfoModel = new StuInfoModel();
    }

    @Override
    public void loadStuInfo(String url) {
        mStuInfoModel.loadStuInfo(url, this);
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure() {

    }
}
