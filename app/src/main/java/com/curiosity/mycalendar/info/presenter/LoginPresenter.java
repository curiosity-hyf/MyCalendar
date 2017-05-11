package com.curiosity.mycalendar.info.presenter;

import android.content.Context;
import android.util.Log;

import com.curiosity.mycalendar.R;
import com.curiosity.mycalendar.bean.StudentInfo;
import com.curiosity.mycalendar.info.model.ILoginModel;
import com.curiosity.mycalendar.info.model.LoginModel;
import com.curiosity.mycalendar.info.view.IFragLoginView;
import com.curiosity.mycalendar.utils.SharedPreferenceUtil;
import com.curiosity.mycalendar.utils.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Description :
 * Author : curiosity-hyf
 * Date : 2017-3-12
 * E-mail : curiooosity.h@gmail.com
 */

public class LoginPresenter implements ILoginPresenter, LoginModel.OnLoginListener {

    private static final String TAG = "myd";
    private ILoginModel mLoginModel;
    private IFragLoginView mFragLoginView;
    private Context mContext;

    private String mAccount, mPwd;
    private boolean mCheckPwd;
    private int mGrade, mSemester;

    public LoginPresenter(IFragLoginView fragLoginView, Context context) {
        mFragLoginView = fragLoginView;
        mLoginModel = new LoginModel();
        mContext = context;
        curriculumLoadedCount = 0;
    }

    @Override
    public void login(String account, String pwd, boolean checkPwd, int grade, int semester) {
        mGrade = grade;
        mSemester = semester;
        mAccount = account;
        mPwd = pwd;
        mCheckPwd = checkPwd;
        mFragLoginView.showProgress(true);
        curriculumLoadedCount = 0;
        mLoginModel.login(mContext, mAccount, mPwd, mCheckPwd, this);
    }

    @Override
    public void initForm() {
        String account = SharedPreferenceUtil.getSaveAccount(mContext);
        boolean isCheckPwd = SharedPreferenceUtil.getCheckPwd(mContext);
        String pwd = "";
        if (!TextUtils.isEmpty(account) && isCheckPwd) {
            pwd = mLoginModel.getLoginInfo(mContext, account);
        }
        mFragLoginView.initLoginForm(account, pwd, true); // 策略：不论上次是否记住密码，初始化时都默认选择记住密码
    }

    @Override
    public void onLoginSuccess(String msg) {
        mFragLoginView.makeToast(mContext.getResources().getString(R.string.login_fetch_curriculum));
        mLoginModel.fetchStudentInfo(mContext, this);
    }

    @Override
    public void onLoginFailure(String msg) {
        mFragLoginView.showProgress(false);
        mFragLoginView.onLoadFailure();
        mFragLoginView.makeToast(msg);
    }

    @Override
    public void onLoadStuInfoSuccess(StudentInfo info) {
        String admission = info.getAdmission();
        mLoginModel.fetchAllCurriculum(mContext, admission, this);
//        mLoginModel.fetchCurriculum(mContext, admission, mGrade, mSemester, this);
    }

    @Override
    public void onLoadStuInfoFailure(String msg) {
        mFragLoginView.showProgress(false);
        mFragLoginView.onLoadFailure();
        mFragLoginView.makeToast(msg);
    }

    private int curriculumLoadedCount = 0;
    @Override
    public void onLoadCurriculumSuccess(int grade, int semester, int maxWeek) {
        curriculumLoadedCount++;
        if(curriculumLoadedCount < 8)
            return ;
        mLoginModel.saveLoginInfo(mContext, mAccount, mPwd, mCheckPwd);
//        mLoginModel.saveLoginInfo(mContext, mAccount, mPwd, mCheckPwd, mGrade, mSemester);
        mFragLoginView.showProgress(false);
        mFragLoginView.onLoadSuccess();
        mFragLoginView.makeToast(mContext.getResources().getString(R.string.fetch_select));
    }

    @Override
    public void onLoadCurriculumFailure(String msg) {
        mFragLoginView.showProgress(false);
        mFragLoginView.onLoadFailure();
        mFragLoginView.makeToast(msg);
    }

}
