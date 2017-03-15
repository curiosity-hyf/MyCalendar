package com.curiosity.mycalendar.sysinfo.view;

/**
 * Description :
 * Author : Curiosity
 * Date : 2017-3-12
 * E-mail : 1184581135qq@gmail.com
 */

public interface IFetchView {
    void makeToast(String msg);
    void showProgress(boolean show);
    void onLoginFailed();

    void initForm(String account, String pwd, boolean isCheck);
}
