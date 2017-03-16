package com.curiosity.mycalendar.sysinfo.view;

/**
 * Created by red on 17-3-15.
 */

public interface ILoginView {
    void makeToast(String msg);
    void showProgress(boolean show);
    void onLoginFailure();

    void initForm(String account, String pwd, boolean isCheck);
}
