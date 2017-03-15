package com.curiosity.mycalendar.sysinfo.presenter;

/**
 * Description :
 * Author : Curiosity
 * Date : 2017-3-12
 * E-mail : 1184581135qq@gmail.com
 */

public interface IFetchPresenter {
    void login(String account, String pwd, boolean checkPwd);

    /**
     * 获取保存的表单
     */
    void getSaveForm();
}
