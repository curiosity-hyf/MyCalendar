package com.curiosity.mycalendar;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Description :
 * Author : Curiosity
 * Date : 2017-3-13
 * E-mail : 1184581135qq@gmail.com
 */

public class TestButton extends android.support.v7.widget.AppCompatButton {
    public TestButton(Context context) {
        super(context);
    }

    public TestButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("myd", "onTouchEvent");
        return true;
    }
}
