package com.curiosity.mycalendar.retrofit.converterfactory;

import android.util.Log;

import com.curiosity.mycalendar.utils.TextUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Curiosity on 2017-1-1.
 */

public class LoginConverterFactory extends Converter.Factory {
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (String.class.equals(type)) {
            return new Converter<ResponseBody, String>() {
                @Override
                public String convert(ResponseBody value) throws IOException {
                    try {
                        String responseBody = TextUtils.convertStreamToString(value.byteStream(), "gbk");
                        if (responseBody.contains("用户名不存在")) {
                            throw new IOException("用户名不存在");
                        } else if (responseBody.contains("密码错误")) {
                            throw new IOException("密码错误");
                        } else if (responseBody.contains("验证码不正确")) {
                            throw new IOException("验证码不正确");
                        } else if (responseBody.contains("aspxerrorpath")) {
                            Log.d("mytest", responseBody);
                            throw new IOException("服务器异常!请稍后重试");
                        } else if (responseBody.contains("xs_main.aspx")) {
                            return "成功";
                        } else {
                            throw new IOException("服务器异常!请稍后重试");
                        }
                    } finally {
                        value.close();
                    }
                }
            };
        }
        return null;
    }
}
