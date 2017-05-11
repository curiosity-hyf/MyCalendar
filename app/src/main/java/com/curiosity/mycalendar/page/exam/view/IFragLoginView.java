package com.curiosity.mycalendar.page.exam.view;

/**
 * Description :
 * Author : curiosity-hyf
 * Date : 2017-3-15
 * E-mail : curiooosity.h@gmail.com
 */

public interface IFragLoginView {

    void makeToast(String msg);

    void showProgress(boolean show);

    void onLoadSuccess();

    void onLoadFailure();

    void initLoginForm(String account, String pwd);
}