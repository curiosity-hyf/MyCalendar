package com.curiosity.mycalendar.main.presenter;

import com.curiosity.mycalendar.bean.StudentInfo;

/**
 * Description :
 * Author : curiosity-hyf
 * Date : 2017-3-16
 * E-mail : curiooosity.h@gmail.com
 */

public interface IMainPresenter {

    boolean getLoginStatus();

    void switchNavigation(int id);

    void login();

    void logout();

    void getStudentInfo();
}