package com.curiosity.mycalendar.retrofit.converterfactory;

import com.curiosity.mycalendar.bean.RestgetCurriculum;
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

public class RestCurriculumInfoConverterFactory extends Converter.Factory {
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new Converter<ResponseBody, RestgetCurriculum>() {
            @Override
            public RestgetCurriculum convert(ResponseBody value) throws IOException {
                try {
                    String responseBody = TextUtils.convertStreamToString(value.byteStream(), "gbk");
                    Document document = Jsoup.parse(responseBody);
                    String viewState = document.select("input[name=__VIEWSTATE]").get(0).attr("value");
                    RestgetCurriculum info = new RestgetCurriculum(viewState, document);
                    return info;
                } finally {
                    value.close();
                }
            }
        };
    }
}
