package com.curiosity.mycalendar.info.presenter;

import android.content.Context;
import android.os.Bundle;

import com.curiosity.mycalendar.info.LoginActivity;
import com.curiosity.mycalendar.info.view.IFetchView;

/**
 * Description :
 * Author : curiosity-hyf
 * Date : 2017-3-12
 * E-mail : curiooosity.h@gmail.com
 */

public class FetchPresenter implements IFetchPresenter {

    private IFetchView mLoginView;
    private Context mContext;
    private int currentFragIdx;

    public FetchPresenter(IFetchView loginView, Context context) {
        currentFragIdx = 0;
        mLoginView = loginView;
        mContext = context;
    }

    @Override
    public void switchNavigation(int id, Bundle bundle) {
        currentFragIdx = id;
        switch (id) {
            case 0:
                mLoginView.switchLoginFragment(bundle);
                break;
            case 1:
                mLoginView.switchYearFragment(bundle);
                mLoginView.showCompleted(true);
                break;
        }
    }

    @Override
    public boolean navigationBack() {
        mLoginView.showCompleted(false);
        if (currentFragIdx == 0) {
            ((LoginActivity) mContext).onNavigateUp();
            return true;
        } else {
            currentFragIdx = 0;
            mLoginView.switchLoginFragment(null);
            return false;
        }
    }
}
