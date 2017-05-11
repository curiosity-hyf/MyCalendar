package com.curiosity.mycalendar.info.view;

import java.util.HashMap;
import java.util.Map;

/**
 * Description :
 * Author : curiosity-hyf
 * Date : 2017-3-15
 * E-mail : curiooosity.h@gmail.com
 */

public interface IFragLoginView {

    void makeToast(String msg);

    void showProgress(boolean show);

    void onLoadSuccess(HashMap<String, Integer> curriculumMaxWeek);

    void onLoadFailure();

    void initLoginForm(String account, String pwd, boolean isCheck);
}
