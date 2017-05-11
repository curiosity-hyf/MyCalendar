package com.curiosity.mycalendar.page.exam.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.curiosity.mycalendar.R;
import com.curiosity.mycalendar.config.FieldDefine;
import com.curiosity.mycalendar.page.exam.presenter.ILoginPresenter;
import com.curiosity.mycalendar.page.exam.presenter.LoginPresenter;
import com.curiosity.mycalendar.page.exam.view.IFragLoginView;
import com.curiosity.mycalendar.utils.TextUtils;
import com.curiosity.mycalendar.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description :
 * Author : curiosity-hyf
 * Date : 2017-3-15
 * E-mail : curiooosity.h@gmail.com
 */

public class LoginFragment extends Fragment implements IFragLoginView {
    private static final String TAG = "mytest";
    @BindView(R.id.accountWrapper)
    TextInputLayout accountWrapper;

    @BindView(R.id.psdWrapper)
    TextInputLayout psdWrapper;

    @BindView(R.id.login_account)
    EditText login_account;

    @BindView(R.id.login_pwd)
    EditText login_pwd;

    @BindView(R.id.login_btn)
    Button login_btn;


    @BindView(R.id.loading_progress)
    ContentLoadingProgressBar clp;

    private ILoginPresenter mLoginPresenter;

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "LoginFragment onCreateView: ");
        if (view == null) {
            view = inflater.inflate(R.layout.exam_login_layout, container, false);
        } else {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }
        ButterKnife.bind(this, view);

        login_account.addTextChangedListener(new LoginFragment.MyTextWatcher(login_account));
        login_pwd.addTextChangedListener(new LoginFragment.MyTextWatcher(login_pwd));
        clp.hide();
        mLoginPresenter = new LoginPresenter(this, getActivity().getApplicationContext());

        return view;
    }

    private void setEnabled(boolean enabled) {
        login_account.setEnabled(enabled);
        login_pwd.setEnabled(enabled);
        login_account.setClickable(enabled);
        login_btn.setEnabled(enabled);
    }

    private int grade, semester;

    /**
     * 点击确认
     */
    @OnClick(R.id.login_btn)
    void confirm() {
        if (isValid()) {
            setEnabled(false);
            mLoginPresenter.login(TextUtils.getText(login_account),
                    TextUtils.getText(login_pwd),
                    grade,
                    semester);
        }
    }

    public void setGradeSemester(Bundle bundle) {
        if (bundle != null) {
            grade = bundle.getInt(FieldDefine.L_GRADE);
            semester = bundle.getInt(FieldDefine.L_SEMESTER);

            if (grade != 0 && semester != 0) {
                Log.d("myd", "grade = " + grade + " semester = " + semester);
            }
        }
    }

    @Override
    public void makeToast(String msg) {
        ToastUtils.ToastShort(getActivity().getApplicationContext(), msg);
    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            clp.show();
        } else {
            clp.hide();
        }
    }

    @Override
    public void onLoadSuccess() {
        activity.onLoadSuccess();
        setEnabled(true);
    }

    @Override
    public void onLoadFailure() {
        setEnabled(true);
    }

    @Override
    public void initLoginForm(String account, String pwd) {
        login_account.setText(account);
        login_pwd.setText(pwd);
    }

    /**
     * 账号、密码、验证码合法性
     *
     * @return 是否合法
     */
    private boolean isValid() {
        return isAccountValid() && isPwdValid();
    }

    /**
     * 账号的合法性
     *
     * @return 是否合法
     */
    private boolean isAccountValid() {
        String account = login_account.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            accountWrapper.setErrorEnabled(true);
            accountWrapper.setError(getString(R.string.input_curri_account_empty));
            accountWrapper.requestFocus();
            return false;
        }
        accountWrapper.setErrorEnabled(false);
        return true;
    }

    /**
     * 密码的合法性
     *
     * @return 是否合法
     */
    private boolean isPwdValid() {
        String password = login_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            psdWrapper.setErrorEnabled(true);
            psdWrapper.setError(getString(R.string.input_curri_password_empty));
            psdWrapper.requestFocus();
            return false;
        }
        psdWrapper.setErrorEnabled(false);
        return true;
    }

    /**
     * TextView 输入监听器
     */
    private class MyTextWatcher implements TextWatcher {

        private View view;

        public MyTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            switch (view.getId()) {
                case R.id.login_account:
                    accountWrapper.setErrorEnabled(true);
                    accountWrapper.setError("");
                    accountWrapper.setErrorEnabled(false);
                    break;
                case R.id.login_pwd:
                    psdWrapper.setErrorEnabled(true);
                    psdWrapper.setError("");
                    psdWrapper.setErrorEnabled(false);
                    break;
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }

    private OnLoadListener activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "LoginFragment onAttach: ");
        try {
            activity = (OnLoadListener) context;
        } catch (ClassCastException e) {

        }
    }

    public interface OnLoadListener {
        void onLoadSuccess();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "LoginFragment onDestroy: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "LoginFragment onStop: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "LoginFragment onPause: ");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
