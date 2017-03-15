package com.curiosity.mycalendar.curriculum.presenter;

import com.curiosity.mycalendar.curriculum.model.ICurriculumInfoModel;
import com.curiosity.mycalendar.curriculum.model.OnLoadCurriculumInfoListener;
import com.curiosity.mycalendar.curriculum.model.CurriculumInfoModel;
import com.curiosity.mycalendar.curriculum.view.ICurriculumInfoView;

/**
 * Description :
 * Author : Curiosity
 * Date : 2017-3-12
 * E-mail : 1184581135qq@gmail.com
 */

public class CurriculumInfoPresenter implements ICurriculumInfoPresenter, OnLoadCurriculumInfoListener {

    private ICurriculumInfoModel mCurriculumInfoModel;
    private ICurriculumInfoView mCurriculumInfoView;

    public CurriculumInfoPresenter(ICurriculumInfoView curriculumInfoView) {
        mCurriculumInfoView = curriculumInfoView;
        mCurriculumInfoModel = new CurriculumInfoModel();
    }

    @Override
    public void loadCurriculumInfo(String url) {
        mCurriculumInfoModel.loadCurriculumInfo(url, this);
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailed() {

    }

}
