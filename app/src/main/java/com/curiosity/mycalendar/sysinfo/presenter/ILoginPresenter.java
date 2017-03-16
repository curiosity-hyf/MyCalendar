package com.curiosity.mycalendar.sysinfo.presenter;

/**
 * Description :
 * Author : Curiosity
 * Date : 2017-3-15
 * E-mail : 1184581135qq@gmail.com
 */

public interface ILoginPresenter {
    void login(String account, String pwd, boolean checkPwd, int grade, int semester);

    /**
     * 获取保存的表单
     */
    void getSaveForm();
}
