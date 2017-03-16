package com.curiosity.mycalendar.sysinfo.view;

/**
 * Description :
 * Author : Curiosity
 * Date : 2017-3-15
 * E-mail : 1184581135qq@gmail.com
 */

public interface ILoginView {
    void makeToast(String msg);
    void showProgress(boolean show);

    void onLoadSuccess();
    void onLoadFailure();

    void initForm(String account, String pwd, boolean isCheck);
}
