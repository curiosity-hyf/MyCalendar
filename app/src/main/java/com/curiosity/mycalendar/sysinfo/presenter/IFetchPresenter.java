package com.curiosity.mycalendar.sysinfo.presenter;

import android.os.Bundle;

/**
 * Description :
 * Author : Curiosity
 * Date : 2017-3-12
 * E-mail : 1184581135qq@gmail.com
 */

public interface IFetchPresenter {
    void switchNavigation(int id, Bundle bundle);

    boolean navigationBack();
}
