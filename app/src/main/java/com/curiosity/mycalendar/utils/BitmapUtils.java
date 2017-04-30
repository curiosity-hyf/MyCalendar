package com.curiosity.mycalendar.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Description : 图片处理相关类
 * Author : curiosity-hyf
 * Date : 17-4-30
 * E-mail : curiooosity.h@gmail.com
 */

public class BitmapUtils {

    /**
     * 降采样
     * @param resources 资源
     * @param imgId 图片id
     * @param inSampleSize 采样跨度
     * @return 图片
     */
    public static Bitmap getBitmapWithInSample(Resources resources, int imgId, int inSampleSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeResource(resources, imgId, options);
    }
}
