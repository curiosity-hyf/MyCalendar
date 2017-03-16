package com.curiosity.mycalendar.sysinfo.presenter;

import android.content.Context;
import android.util.Log;

import com.curiosity.mycalendar.bean.CourseInfo;
import com.curiosity.mycalendar.bean.StudentInfo;
import com.curiosity.mycalendar.sysinfo.model.ILoginModel;
import com.curiosity.mycalendar.sysinfo.model.LoginModel;
import com.curiosity.mycalendar.sysinfo.view.ILoginView;
import com.curiosity.mycalendar.utils.SharedPreferenceUtil;
import com.curiosity.mycalendar.utils.TextUtils;

/**
 * Created by red on 17-3-15.
 */

public class LoginPresenter implements ILoginPresenter, LoginModel.OnLoginListener {

    private static final String TAG = "myd";
    private ILoginModel mLoginModel;
    private ILoginView mLoginView;
    private Context mContext;

    private String mAccount, mPwd;
    private boolean mCheckPwd;
    private int mGrade, mSemester;

    public LoginPresenter(ILoginView loginView, Context context) {
        mLoginView = loginView;
        mLoginModel = new LoginModel();
        mContext = context;
    }

    @Override
    public void login(String account, String pwd, boolean checkPwd, int grade, int semester) {
        mGrade = grade;
        mSemester = semester;
        mAccount = account;
        mPwd = pwd;
        mCheckPwd = checkPwd;
        mLoginView.showProgress(true);
        mLoginModel.login(mContext, mAccount, mPwd, mCheckPwd, this);
    }

    @Override
    public void getSaveForm() {
        String account = SharedPreferenceUtil.getSaveAccount(mContext);
        boolean isCheckPwd = SharedPreferenceUtil.getCheckPwd(mContext);
        String pwd = "";
        if (!TextUtils.isEmpty(account) && isCheckPwd) {
            pwd = mLoginModel.getLoginInfo(mContext, account);
        }
        mLoginView.initForm(account, pwd, true); // 策略：不论上次是否记住密码，初始化时都默认选择记住密码
    }

    @Override
    public void onLoginSuccess(String msg) {
        Log.d(TAG, "onLoginSuccess: ");
        mLoginView.makeToast(msg);
        mLoginModel.fetchStudentInfo(mContext, this);
    }

    @Override
    public void onLoginFailure(String msg) {
        Log.d(TAG, "onLoginFailure: ");
        mLoginView.showProgress(false);
        mLoginView.onLoginFailed();
        mLoginView.makeToast(msg);
    }

    @Override
    public void onLoadStuInfoSuccess(StudentInfo info) {
        Log.d(TAG, "onLoadStuInfoSuccess: ");
        String admission = mLoginModel.getStudentInfo(mContext, "stuNum", info.getStuNum(), "admission");
        mLoginModel.fetchCurriculum(mContext, admission, mGrade, mSemester, this);
    }

    @Override
    public void onLoadStuInfoFailure(String msg) {
        Log.d(TAG, "onLoadStuInfoFailure: ");
        mLoginView.showProgress(false);
        mLoginView.onLoginFailed();
        mLoginView.makeToast(msg);
    }

    @Override
    public void onLoadCurriculumSuccess() {
        Log.d(TAG, "onLoadCurriculumSuccess: ");
        mLoginView.showProgress(false);
        mLoginView.makeToast("Load Curriculum Success");
    }

    @Override
    public void onLoadCurriculumFailure(String msg) {
        Log.d(TAG, "onLoadCurriculumFailure: ");
        mLoginView.showProgress(false);
        mLoginView.onLoginFailed();
        mLoginView.makeToast(msg);
    }

}
