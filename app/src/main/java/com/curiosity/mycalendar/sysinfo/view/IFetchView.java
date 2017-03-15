package com.curiosity.mycalendar.sysinfo.view;

import android.os.Bundle;

/**
 * Description :
 * Author : Curiosity
 * Date : 2017-3-12
 * E-mail : 1184581135qq@gmail.com
 */

public interface IFetchView {
    void showNextStep(boolean show);

    void switchYearFragment(Bundle bundle);
    void switchLoginFragment(Bundle bundle);
}
