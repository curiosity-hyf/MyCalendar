package com.curiosity.mycalendar.retrofit.converterfactory;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.curiosity.mycalendar.utils.TextUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Curiosity on 2016-12-31.
 */

public class VerifyCodeConverterFactory extends Converter.Factory {
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (Bitmap.class.equals(type)) {
            return new Converter<ResponseBody, Bitmap>() {

                @Override
                public Bitmap convert(ResponseBody value) throws IOException {
                    try {
                        byte[] bb = TextUtils.convertStreamToBytes(value.byteStream());
                        return BitmapFactory.decodeByteArray(bb, 0, bb.length);
                    } finally {
                        value.close();
                    }
                }
            };
        }
        return null;
    }
}
