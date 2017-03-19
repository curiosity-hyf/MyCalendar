package com.curiosity.mycalendar.sysinfo.presenter;

import android.content.Context;
import android.os.Bundle;

import com.curiosity.mycalendar.sysinfo.FetchInfoActivity;
import com.curiosity.mycalendar.sysinfo.model.FetchModel;
import com.curiosity.mycalendar.sysinfo.model.IFetchModel;
import com.curiosity.mycalendar.sysinfo.view.IFetchView;

/**
 * Description :
 * Author : Curiosity
 * Date : 2017-3-12
 * E-mail : 1184581135qq@gmail.com
 */

public class FetchPresenter implements IFetchPresenter {

    private IFetchModel mLoginModel;
    private IFetchView mLoginView;
    private Context mContext;
    private int currentFragIdx;

    public FetchPresenter(IFetchView loginView, Context context) {
        currentFragIdx = 0;
        mLoginView = loginView;
        mLoginModel = new FetchModel();
        mContext = context;
    }

    @Override
    public void switchNavigation(int id, Bundle bundle) {
        currentFragIdx = id;
        switch (id) {
            case 0:
                mLoginView.switchYearFragment(bundle);
                mLoginView.showNextStep(true);
                break;
            case 1:
                mLoginView.switchLoginFragment(bundle);
                mLoginView.showNextStep(false);
                break;
        }
    }

    @Override
    public boolean navigationBack() {
        mLoginView.showNextStep(true);
        if (currentFragIdx == 0) {
            ((FetchInfoActivity) mContext).onNavigateUp();
            return true;
        } else {
            currentFragIdx = 0;
            mLoginView.switchYearFragment(null);
            return false;
        }
    }
}
