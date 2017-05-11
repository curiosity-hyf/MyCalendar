package com.curiosity.mycalendar.info.presenter;

import android.content.Context;
import android.util.Log;

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
        curriculumWeekMap.clear();
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
        curriculumWeekMap.clear();
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
        Log.d(TAG, "onLoginSuccess: ");
        mFragLoginView.makeToast(msg);
        mLoginModel.fetchStudentInfo(mContext, this);
    }

    @Override
    public void onLoginFailure(String msg) {
        Log.d(TAG, "onLoadFailure: ");
        mFragLoginView.showProgress(false);
        mFragLoginView.onLoadFailure();
        mFragLoginView.makeToast(msg);
    }

    @Override
    public void onLoadStuInfoSuccess(StudentInfo info) {
        Log.d(TAG, "onLoadStuInfoSuccess: ");
        String admission = info.getAdmission();
        mLoginModel.fetchAllCurriculum(mContext, admission, this);
//        mLoginModel.fetchCurriculum(mContext, admission, mGrade, mSemester, this);
    }

    @Override
    public void onLoadStuInfoFailure(String msg) {
        Log.d(TAG, "onLoadStuInfoFailure: ");
        mFragLoginView.showProgress(false);
        mFragLoginView.onLoadFailure();
        mFragLoginView.makeToast(msg);
    }

    private int curriculumLoadedCount = 0;
    private HashMap<String, Integer> curriculumWeekMap = new HashMap<>();
    @Override
    public void onLoadCurriculumSuccess(int grade, int semester, int maxWeek) {
        curriculumLoadedCount++;
        curriculumWeekMap.put("0" + grade + "0" + semester, maxWeek);
        Log.d("myA", "onLoadCurriculumSuccess: " + curriculumLoadedCount);
        if(curriculumLoadedCount < 8)
            return ;
        Log.d("myA", "onLoadCurriculumSuccess: Completed");
        mLoginModel.saveLoginInfo(mContext, mAccount, mPwd, mCheckPwd);
//        mLoginModel.saveLoginInfo(mContext, mAccount, mPwd, mCheckPwd, mGrade, mSemester);
        mFragLoginView.showProgress(false);
        mFragLoginView.onLoadSuccess(curriculumWeekMap);
        mFragLoginView.makeToast("Load Curriculum Success");
    }

    @Override
    public void onLoadCurriculumFailure(String msg) {
        Log.d(TAG, "onLoadCurriculumFailure: ");
        mFragLoginView.showProgress(false);
        mFragLoginView.onLoadFailure();
        mFragLoginView.makeToast(msg);
    }

}
