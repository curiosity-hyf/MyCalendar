package com.curiosity.mycalendar.info.view;

import android.os.Bundle;

/**
 * Description :
 * Author : curiosity-hyf
 * Date : 2017-3-12
 * E-mail : curiooosity.h@gmail.com
 */

public interface IFetchView {

    void showNextStep(boolean show);

    void switchYearFragment(Bundle bundle);

    void switchLoginFragment(Bundle bundle);
}
