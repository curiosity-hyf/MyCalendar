package com.curiosity.mycalendar.retrofit.converterfactory;

import com.curiosity.mycalendar.bean.StudentInfo;
import com.curiosity.mycalendar.utils.DomUtils;
import com.curiosity.mycalendar.utils.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Curiosity on 2017-1-1.
 */

public class StudentInfoConverterFactory extends Converter.Factory {
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new Converter<ResponseBody, StudentInfo>() {
            @Override
            public StudentInfo convert(ResponseBody value) throws IOException {
                try {
                    String responseBody = TextUtils.convertStreamToString(value.byteStream(), "gbk");
                    Document document = Jsoup.parse(responseBody);
//                    StudentInfo info = DomUtils.getStuInfo(document);
//                    return info;
                    return null;
                } finally {
                    value.close();
                }
            }
        };
    }
}
