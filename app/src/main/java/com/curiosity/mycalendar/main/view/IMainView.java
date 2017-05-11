package com.curiosity.mycalendar.main.view;

/**
 * Description :
 * Author : curiosity-hyf
 * Date : 2017-3-11
 * E-mail : curiooosity.h@gmail.com
 */

public interface IMainView {

    void login();

    void logout();

    void switch2Exam();

    void switch2Curriculum();

    void switch2Calender();

    void setStudentInfo(String stuName, String stuInstitute, String stuMajor, String stuClass);
}
