package com.curiosity.mycalendar.utils;

import android.util.Log;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by dev on 2017/5/11.
 */

public class TimeUtil {

    /**
     * 返回当前是一周的第几天
     * 1 星期一
     * 2 星期二
     * 3 星期三
     * 4 星期四
     * 5 星期五
     * 6 星期六
     * 7 星期日
     * @return
     */
    public static int getDayOfWeek() {
        int i = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        return i == 1 ? 7 : i-1;
    }

    public static int getDayOfWeek(Calendar calendar) {
        int i = calendar.get(Calendar.DAY_OF_WEEK);
        return i == 1 ? 7 : i -1;
    }

    public static void getSundayTime() {
        Calendar instance = Calendar.getInstance();
        long time1 = instance.getTimeInMillis();
        int d = getDayOfWeek(instance);
        instance.add(Calendar.DAY_OF_WEEK, 7-d);
        long time2 = instance.getTimeInMillis();
        Log.d("myAA", "" + getDayOfWeek(instance) + " " + dayDiff(time1, time2));
    }

    public static long dayDiff(long timeStart, long timeEnd) {
        return (timeEnd-timeStart)/(1000*3600*24);
    }

    public static int weekDiff(long timeStart) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date(timeStart));
        int d = getDayOfWeek(instance);
        instance.add(Calendar.DAY_OF_WEEK, 7-d);
        long timeS = instance.getTimeInMillis();

        instance.setTime(new Date());
        instance.set(Calendar.DAY_OF_MONTH, 11);
        long timeE = instance.getTimeInMillis();

        int diff = Integer.valueOf(String.valueOf(dayDiff(timeS, timeE)));
        int wdiff = diff / 7;
        int wwdiff = diff % 7;
        if(wwdiff == 0) {
            Log.d("myAA", "" + wdiff);
        } else {
            Log.d("myAA", "" + (wdiff+1));
        }
        return 0;
    }

    public static String getTime() {
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.YEAR, 2017);
        instance.set(Calendar.MONTH, Calendar.MARCH);
        instance.set(Calendar.DAY_OF_MONTH, 7);
        Date date1 = new Date();
        instance.setTime(date1);
        instance.set(Calendar.DAY_OF_MONTH, 10);
        long time1 = instance.getTimeInMillis();
        Date date2 = new Date();
        instance.setTime(date2);
        long time2 = instance.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);
        Log.d("myAA", "" + between_days);
        Log.d("myAA", "" + instance.get(Calendar.DAY_OF_WEEK));
        Log.d("myAA", "" + instance.get(Calendar.DAY_OF_WEEK_IN_MONTH));
        Log.d("myAA", "" + instance.get(Calendar.WEEK_OF_MONTH));
        Log.d("myAA", "" + instance.get(Calendar.WEEK_OF_YEAR));
        return "";
    }
}
