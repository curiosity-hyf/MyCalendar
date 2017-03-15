package com.curiosity.mycalendar.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.curiosity.mycalendar.bean.CourseInfo;
import com.curiosity.mycalendar.bean.StudentInfo;
import com.curiosity.mycalendar.config.FieldDefine;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;

import static com.curiosity.mycalendar.config.FieldDefine.DATABASE_VERSION;

/**
 * Created by Curiosity on 2016-12-25.
 */

public class HttpUtils {

    private static final int TIME_OUT = 20 * 1000;

    private static String codeUrl = "http://jwgl.gdut.edu.cn/CheckCode.aspx";
    private static String referUrl = "http://jwgl.gdut.edu.cn/default2.aspx";
    private static String mainUrl = "http://jwgl.gdut.edu.cn/xs_main.aspx?xh=";
    private static String user = "3114006092";
    private static String pwd = "hyf627507";
    private static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36";
    private static String cookie;
    private static String viewState;
    private static final String COOKIE_KEY = "ASP.NET_SessionId";


    private static final String HOST = "http://222.200.98.147";
    private static final String LOGIN_URL = "/login!doLogin.action";
    private static final String CURRI_CODE_URL = FieldDefine.HOST + "/verifycode";
    private static final String CURRI_LOGIN_URL = FieldDefine.HOST + "/curriculum_login";

    public static final int SUCCESS = 0;
    public static final String REQUIRE_CODE_ERROR = "REQUIRE_CODE_ERROR";
    public static final String ACCOUNT_ERROR = "ACCOUNT_ERROR";
    public static final String PASSWORD_ERROR = "PASSWORD_ERROR";
    public static final String VERIFYCODE_ERROR = "VERIFYCODE_ERROR";
    public static final String SOMETHING_ERROR = "SOMETHING_ERROR";

    public static final String APP_LOGIN_URL = FieldDefine.HOST + "/app_login";
    public static final String GET_STU_URL = FieldDefine.HOST + "/get_stu";
    public static final String GET_CURRI_URL = FieldDefine.HOST + "/get_curri";


    private static Bitmap getBitmap() throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS).build();
        Request request = new Request.Builder().url(CURRI_CODE_URL).build();
        okhttp3.Response response1 = okHttpClient.newCall(request).execute();
        if(response1.code() == 200) {
            Log.d("mytest", "" + response1.header("Set-Cookie"));

            Log.d("mytest", "" + "completed");
            cookie = response1.header("Set-Cookie");
            byte [] bb = TextUtils.convertStreamToBytes(response1.body().byteStream());
            return BitmapFactory.decodeByteArray(bb, 0, bb.length);
        } else {
            throw new IOException(REQUIRE_CODE_ERROR);
        }
    }

    /**
     * 验证码获取调用接口
     * @return
     */
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

    /**
     * 登录调用接口
     * @param account
     * @param pwd
     * @param code
     * @return
     */
    public static Observable<String> login(final String account, final String pwd, final String code) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    Log.d("mytest", "begin login");
                    Map<String, String> postData = new HashMap<>();
                    postData.put("txtUserName", account);
                    postData.put("TextBox2", pwd);
                    postData.put("txtSecretCode", code);
                    okhttp3.Response response = getAccess(postData);
                    Log.d("mytest", "end login");
                    int statusCode = response.code();
                    //Log.d("mytest", response.body().string());
                    Log.d("mytest", "" + statusCode);
                    if(statusCode == 302) {
                        subscriber.onNext(cookie);
//                        Gson gson = new Gson();
//                        subscriber.onNext(gson.fromJson(response.body().string(), StudentInfo.class));
                    } else if(statusCode == 201){
                        subscriber.onError(new IOException(ACCOUNT_ERROR));
                    } else if(statusCode == 202) {
                        subscriber.onError(new IOException(PASSWORD_ERROR));
                    } else if(statusCode == 203) {
                        subscriber.onError(new IOException(VERIFYCODE_ERROR));
                    } else {
                        subscriber.onError(new IOException(SOMETHING_ERROR));
                    }
                } catch (IOException e) {
                    Log.d("mytest", e.toString());

                    subscriber.onError(new IOException(SOMETHING_ERROR));
                }
            }
        });
    }

    public static Observable<StudentInfo> getStuInfo(final String account, final String cookie) {
        return Observable.create(new Observable.OnSubscribe<StudentInfo>() {
            @Override
            public void call(Subscriber<? super StudentInfo> subscriber) {
                Log.d("mytest", "begin getStuInfo");
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS).build();
                FormBody.Builder builder = new FormBody.Builder();
                builder.add("account", account);
                RequestBody body = builder.build();
                Request request = new Request.Builder().url(GET_STU_URL).addHeader("Cookie", cookie).post(body).build();
                okhttp3.Response response1 = null;
                try {
                    response1 = client.newCall(request).execute();
                    Gson gson = new Gson();
                    String bo = response1.body().string();
                    Log.d("mytest", bo);
                    subscriber.onNext(gson.fromJson(bo, StudentInfo.class));
                } catch (IOException e) {
                    subscriber.onError(new IOException("GET_STU_ERROR"));
                    e.printStackTrace();
                }
                //Log.d("mytest", response1.body().string());

            }
        });
    }


    /**
     * 验证登录
     *
     * @return 状态码是否为 302  是则验证成功 否则验证失败
     * @throws IOException
     */
    private static okhttp3.Response getAccess(Map<String, String> postData) throws IOException {
        Log.d("mytest", "begin getAccess");
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS).build();
        FormBody.Builder builder = new FormBody.Builder();
        for(Map.Entry<String, String> entry : postData.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        RequestBody body = builder.build();
        Request request = new Request.Builder().url(CURRI_LOGIN_URL).addHeader("Cookie", cookie).post(body).build();
        okhttp3.Response response1 = client.newCall(request).execute();
        //Log.d("mytest", response1.body().string());
        Log.d("mytest", "" + response1.code());
        return response1;
    }

    //打开App时自动检查
    public static Observable<String> checkUserAccess(final Context context) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                boolean isFirstIn = SharedPreferenceUtil.isFirstIn(context);
                if(isFirstIn) {//首次使用
                    subscriber.onNext(FieldDefine.ISFIRSTIN);
                } else {
                    //本地检查
                    String account = SharedPreferenceUtil.getSaveAccount(context);
                    if(account == null || account.equals("")) {
                        Log.d("mytest", "1");
                        subscriber.onNext(FieldDefine.ACCOUNT_EXCEPTION);
                        return;
                    }
                    Log.d("mytest", "11");
                    SQLiteHelper helper = new SQLiteHelper(context, "DB", null, DATABASE_VERSION);
                    Cursor cursor = helper
                            .getWritableDatabase()
                            .query("user_login", new String[]{"pwd"}, "account = ?", new String[]{account}, null, null, null);
                    if(cursor.getCount() == 0) {
                        Log.d("mytest", "2");
                        subscriber.onNext(FieldDefine.ACCOUNT_EXCEPTION);
                        cursor.close();
                        return;
                    }
                    Log.d("mytest", "22");
                    String pwd = null;
                    if(cursor.moveToNext()) {
                        pwd = cursor.getString(cursor.getColumnIndex("pwd"));
                    }
                    cursor.close();
                    if(pwd == null || pwd.equals("")) {
                        Log.d("mytest", "3");
                        subscriber.onNext(FieldDefine.ACCOUNT_EXCEPTION);
                        return;
                    }
                    Log.d("mytest", "33");

                    //以下为服务器检查部分
                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS).build();
                    FormBody.Builder builder = new FormBody.Builder();
                    builder.add("account", account);
                    builder.add("pwd", pwd);
                    RequestBody body = builder.build();
                    Request request = new Request.Builder().url(APP_LOGIN_URL).post(body).build();
                    okhttp3.Response response1 = null;
                    try {
                        response1 = client.newCall(request).execute();
                        Log.d("mytest", "auto login --> " + "code = " + response1.code());
                        if(response1.code() == 200) {//正确
                            subscriber.onNext(FieldDefine.LOGIN_PASS);
                        } else if(response1.code() == 201){//密码错误
                            subscriber.onNext(FieldDefine.LOGIN_PSW_ERROR);
                        } else if(response1.code() == 202){ //不存在
                            subscriber.onNext(FieldDefine.LOGIN_NO_EXISTS);
                        } else { //账号异常
                            Log.d("mytest", "4");
                            subscriber.onNext(FieldDefine.ACCOUNT_EXCEPTION);
                        }
                    } catch (IOException e) {
                        //Log.d("mytest", "44");
                        //Log.d("mytest", e.toString());
                        //e.printStackTrace();
                        subscriber.onError(new IOException(SOMETHING_ERROR));
                    }
                }
            }
        });
    }

    public static Observable<List<CourseInfo>> getCourse(final String cookie, final String account) {
        return Observable.create(new Observable.OnSubscribe<List<CourseInfo>>() {
            @Override
            public void call(Subscriber<? super List<CourseInfo>> subscriber) {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS).build();
                FormBody.Builder builder = new FormBody.Builder();
                builder.add("account", account);
                RequestBody body = builder.build();
                Request request = new Request.Builder().url(GET_CURRI_URL).addHeader("Cookie", cookie).post(body).build();
                okhttp3.Response response1 = null;
                try {
                    response1 = client.newCall(request).execute();
                    Log.d("mytest", "catch!");
                    InputStream is = response1.body().byteStream();
                    String co = TextUtils.convertStreamToString(is);
                    Gson gson = new Gson();
                    List<CourseInfo> rev = gson.fromJson(co, new TypeToken<List<CourseInfo>>(){}.getType());
                    for(CourseInfo info : rev) {
                        Log.d("mytest", info.toString());
                    }
                    subscriber.onNext(rev);
                } catch (IOException e) {
                    subscriber.onError(new IOException("1"));
                    e.printStackTrace();
                }
                subscriber.onError(new IOException("2"));
            }
        });
    }
}
