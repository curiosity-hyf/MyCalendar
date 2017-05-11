package com.curiosity.mycalendar.page.exam.presenter;

/**
 * Description :
 * Author : curiosity-hyf
 * Date : 2017-3-15
 * E-mail : curiooosity.h@gmail.com
 */

public interface ILoginPresenter {

    void login(String account, String pwd, int grade, int semester);
    void onLoginSuccess(String msg);
    void onLoginFailure(String msg);
}
