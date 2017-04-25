package com.curiosity.mycalendar.customview;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;

import com.gordonwong.materialsheetfab.AnimatedFab;

/**
 * Description :
 * Author : Curiosity
 * Date : 2016-12-29
 * E-mail : curiooosity.h@gmail.com
 */

public class MenuFAB extends FloatingActionButton implements AnimatedFab {
    public MenuFAB(Context context) {
        super(context);
    }

    public MenuFAB(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MenuFAB(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void show(float translationX, float translationY) {

    }
}
