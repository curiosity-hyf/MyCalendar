package com.curiosity.mycalendar.sysinfo.presenter;

import android.content.Context;

import com.curiosity.mycalendar.sysinfo.model.IFetchModel;
import com.curiosity.mycalendar.sysinfo.model.FetchModel;
import com.curiosity.mycalendar.sysinfo.view.IFetchView;
import com.curiosity.mycalendar.utils.SharedPreferenceUtil;
import com.curiosity.mycalendar.utils.TextUtils;

/**
 * Description :
 * Author : Curiosity
 * Date : 2017-3-12
 * E-mail : 1184581135qq@gmail.com
 */

public class FetchPresenter implements IFetchPresenter, FetchModel.OnLoginListener {

    private IFetchModel mLoginModel;
    private IFetchView mLoginView;
    private Context mContext;

    private String mAccount, mPwd;
    private boolean mCheckPwd;

    public FetchPresenter(IFetchView loginView, Context context) {
        mLoginView = loginView;
        mLoginModel = new FetchModel();
        mContext = context;
    }

    @Override
    public void login(String account, String pwd, boolean checkPwd) {
        mAccount = account;
        mPwd = pwd;
        mCheckPwd = checkPwd;
        mLoginView.showProgress(true);
        mLoginModel.login(account, pwd, this);
    }

    @Override
    public void getSaveForm() {
        String account = SharedPreferenceUtil.getSaveAccount(mContext);
        boolean isCheckPwd = SharedPreferenceUtil.getCheckPwd(mContext);
        String pwd = "";
        if (!TextUtils.isEmpty(account) && isCheckPwd) {
            pwd = mLoginModel.getSave(mContext, account);
        }
        mLoginView.initForm(account, pwd, true); // 策略：不论上次是否记住密码，初始化时都默认选择记住密码
    }

    @Override
    public void onLoginSuccess(String msg) {
        mLoginModel.saveLoginInfo(mContext, mAccount, mPwd, mCheckPwd);
        mLoginView.showProgress(false);
        mLoginView.makeToast(msg);


    }

    @Override
    public void onLoginFailure(String msg) {
        mLoginView.showProgress(false);
        mLoginView.onLoginFailed();
        mLoginView.makeToast(msg);
    }
}
