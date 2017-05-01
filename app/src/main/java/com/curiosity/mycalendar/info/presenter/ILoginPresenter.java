package com.curiosity.mycalendar.info.presenter;

/**
 * Description :
 * Author : curiosity-hyf
 * Date : 2017-3-15
 * E-mail : curiooosity.h@gmail.com
 */

public interface ILoginPresenter {

    void login(String account, String pwd, boolean checkPwd, int grade, int semester);

    /**
     * 获取保存的表单
     */
    void initForm();
}
