package com.curiosity.mycalendar.info.presenter;

import android.os.Bundle;

/**
 * Description :
 * Author : curiosity-hyf
 * Date : 2017-3-12
 * E-mail : curiooosity.h@gmail.com
 */

public interface IFetchPresenter {
    /**
     * 切换 Fragment 页面
     * @param id 页面 id
     * @param bundle
     */
    void switchNavigation(int id, Bundle bundle);

    /**
     * 回退
     * @return
     */
    boolean navigationBack();
}
