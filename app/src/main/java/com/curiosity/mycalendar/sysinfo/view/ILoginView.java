package com.curiosity.mycalendar.sysinfo.view;

/**
 * Description :
 * Author : curiosity-hyf
 * Date : 2017-3-15
 * E-mail : curiooosity.h@gmail.com
 */

public interface ILoginView {

    void makeToast(String msg);

    void showProgress(boolean show);

    void onLoadSuccess();

    void onLoadFailure();

    void initForm(String account, String pwd, boolean isCheck);
}
