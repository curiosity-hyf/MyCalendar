package com.curiosity.mycalendar.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Description : Toast使用类
 * Author : Curiosity
 * Date : 2016-12-28
 * E-mail : 1184581135qq@gmail.com
 */

public class ToastUtils {
    public static void ToastLong(Context context, int msgId) {
        Toast.makeText(context, msgId, Toast.LENGTH_LONG).show();
    }

    public static void ToastShort(Context context, int msgId) {
        Toast.makeText(context, msgId, Toast.LENGTH_SHORT).show();
    }

    public static void ToastLong(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void ToastShort(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
