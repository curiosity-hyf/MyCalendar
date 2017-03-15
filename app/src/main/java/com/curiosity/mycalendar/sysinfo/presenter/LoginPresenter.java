package com.curiosity.mycalendar.sysinfo.presenter;

import android.content.Context;

import com.curiosity.mycalendar.sysinfo.model.FetchModel;
import com.curiosity.mycalendar.sysinfo.model.IFetchModel;
import com.curiosity.mycalendar.sysinfo.model.ILoginModel;
import com.curiosity.mycalendar.sysinfo.model.LoginModel;
import com.curiosity.mycalendar.sysinfo.view.IFetchView;
import com.curiosity.mycalendar.sysinfo.view.ILoginView;
import com.curiosity.mycalendar.utils.SharedPreferenceUtil;
import com.curiosity.mycalendar.utils.TextUtils;

/**
 * Created by red on 17-3-15.
 */

public class LoginPresenter implements ILoginPresenter, LoginModel.OnLoginListener {
    private ILoginModel mLoginModel;
    private ILoginView mLoginView;
    private Context mContext;

    private String mAccount, mPwd;
    private boolean mCheckPwd;

    public LoginPresenter(ILoginView loginView, Context context) {
        mLoginView = loginView;
        mLoginModel = new LoginModel();
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

//        mLoginModel.fetchStudentInfo(this);
        mLoginModel.fetchCurriculum("", "", "");
    }

    @Override
    public void onLoginFailure(String msg) {
        mLoginView.showProgress(false);
        mLoginView.onLoginFailed();
        mLoginView.makeToast(msg);
    }

    @Override
    public void onLoadStuInfoSuccess(String msg) {

    }

}
