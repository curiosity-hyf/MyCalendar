package com.curiosity.mycalendar.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import rx.Observable;
import rx.Subscriber;

import static com.curiosity.mycalendar.utils.HttpUtils.REQUIRE_CODE_ERROR;

/**
 * Created by Curiosity on 2016-12-31.
 */

public class CurriculumUtils {

    private static final int TIME_OUT = 5 * 1000;

    public static final String VERIFY_CODE_URL = "http://jwgl.gdut.edu.cn/CheckCode.aspx";

    public static final String USER_AGENT_KEY = "User-Agent";
    public static final String USER_AGENT_VALUE = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36";


    private static String cookie;


    private static Bitmap getBitmap() throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS).build();
        Request request = new Request.Builder().header(USER_AGENT_KEY, USER_AGENT_VALUE).url(VERIFY_CODE_URL).build();
        okhttp3.Response response1 = okHttpClient.newCall(request).execute();
        if(response1.code() == 200) {
            Log.d("mytest", "" + response1.header("Set-Cookie"));

            Log.d("mytest", "" + "completed");
            cookie = response1.header("Set-Cookie");
            response1.body().string();
            byte [] bb = TextUtils.convertStreamToBytes(response1.body().byteStream());
            return BitmapFactory.decodeByteArray(bb, 0, bb.length);
        } else {
            throw new IOException(REQUIRE_CODE_ERROR);
        }
    }

    public static Observable<Bitmap> getCodeBitmap() {
        return Observable.create(new Observable.OnSubscribe<Bitmap>() {

            @Override
            public void call(final Subscriber<? super Bitmap> subscriber) {
                try {
                    subscriber.onNext(getBitmap());
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
