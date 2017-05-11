package com.curiosity.mycalendar.page.exam.presenter;

import android.content.Context;

import com.curiosity.mycalendar.R;
import com.curiosity.mycalendar.bean.StudentInfo;
import com.curiosity.mycalendar.page.exam.model.ILoginModel;
import com.curiosity.mycalendar.page.exam.model.LoginModel;
import com.curiosity.mycalendar.page.exam.view.IFragLoginView;
import com.curiosity.mycalendar.utils.SharedPreferenceUtil;
import com.curiosity.mycalendar.utils.TextUtils;

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
    private int mGrade, mSemester;

    public LoginPresenter(IFragLoginView fragLoginView, Context context) {
        mFragLoginView = fragLoginView;
        mLoginModel = new LoginModel();
        mContext = context;
        curriculumLoadedCount = 0;
    }

    @Override
    public void login(String account, String pwd, int grade, int semester) {
        mGrade = grade;
        mSemester = semester;
        mAccount = account;
        mPwd = pwd;
        mFragLoginView.showProgress(true);
        curriculumLoadedCount = 0;
        mLoginModel.login(mContext, mAccount, mPwd, this);
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
        mLoginModel.fetchAllExam(mContext, admission, this);
    }

    @Override
    public void onLoadStuInfoFailure(String msg) {

    }

    private int curriculumLoadedCount = 0;
    @Override
    public void onLoadExamSuccess(int grade, int semester, int maxWeek) {
        curriculumLoadedCount++;
        if(curriculumLoadedCount < 8)
            return ;
//        mLoginModel.saveLoginInfo(mContext, mAccount, mPwd, mCheckPwd, mGrade, mSemester);
        mFragLoginView.showProgress(false);
        mFragLoginView.onLoadSuccess();
//        mFragLoginView.makeToast(mContext.getResources().getString(R.string.fetch_select));
    }

    @Override
    public void onLoadExamFailure(String msg) {
        mFragLoginView.showProgress(false);
        mFragLoginView.onLoadFailure();
        mFragLoginView.makeToast(msg);
    }

}
