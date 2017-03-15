package com.curiosity.mycalendar.sysinfo.presenter;

/**
 * Created by red on 17-3-15.
 */

public interface ILoginPresenter {
    void login(String account, String pwd, boolean checkPwd);

    /**
     * 获取保存的表单
     */
    void getSaveForm();
}
