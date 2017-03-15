package com.curiosity.mycalendar.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Curiosity on 2016-12-28.
 * Toast使用类
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
