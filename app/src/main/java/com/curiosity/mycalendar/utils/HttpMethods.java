package com.curiosity.mycalendar.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.curiosity.mycalendar.R;
import com.curiosity.mycalendar.bean.CourseInfo;
import com.curiosity.mycalendar.bean.FirstgetCurriculum;
import com.curiosity.mycalendar.bean.RestgetCurriculum;
import com.curiosity.mycalendar.bean.StudentInfo;
import com.curiosity.mycalendar.retrofit.Interceptor.FirstCurriculumInfoInterceptor;
import com.curiosity.mycalendar.retrofit.Interceptor.RestCurriculumInfoInterceptor;
import com.curiosity.mycalendar.retrofit.Interceptor.StudentInfoInterceptor;
import com.curiosity.mycalendar.retrofit.Interceptor.VerifyCodeInterceptor;
import com.curiosity.mycalendar.retrofit.converterfactory.FirstCurriculumInfoConverterFactory;
import com.curiosity.mycalendar.retrofit.converterfactory.RestCurriculumInfoConverterFactory;
import com.curiosity.mycalendar.retrofit.converterfactory.StudentInfoConverterFactory;
import com.curiosity.mycalendar.retrofit.converterfactory.VerifyCodeConverterFactory;
import com.curiosity.mycalendar.retrofit.service.FirstCurriculumInfoService;
import com.curiosity.mycalendar.retrofit.service.RestCurriculumInfoService;
import com.curiosity.mycalendar.retrofit.service.StudentInfoService;
import com.curiosity.mycalendar.retrofit.service.VerifyCodeService;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Curiosity on 2016-12-31.
 */

public class HttpMethods {

    public static String HOST = "http://jwgl.gdut.edu.cn/";
    public static String LOGIN_URL = HOST + "/default2.aspx";

    public static String cookie;
    public static String viewState;
    public static StudentInfo studentInfo;
    private VerifyCodeService verifyCodeService;

    public static final int SUCCESS = 0;
    public static final int ACCOUNT_ERROR = 1;
    public static final int PASSWORD_ERROR = 2;
    public static final int VERIFYCODE_ERROR = 3;
    public static final int SOMETHING_ERROR = 4;

    public static final int CONNECT_TIMEOUT = 3;

    public static Context context;

    private HttpMethods() {
    }

    /**
     * 获取验证码
     *
     * @param im 显示验证码的 imageview
     */
    public static void getVerifyCode(final ImageView im) {
        OkHttpClient client = new OkHttpClient.Builder()
                .followRedirects(false)
                .addInterceptor(new VerifyCodeInterceptor())
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(new VerifyCodeConverterFactory())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(HOST)
                .build();

        retrofit.create(VerifyCodeService.class).getVerifyCode()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        Log.d("mytest", "------------load verify code1------------");
                        Log.d("mytest", "success load verify code");
                        Log.d("mytest", cookie);
                        Log.d("mytest", "------------load verify code1------------");
                        im.setImageBitmap(bitmap);
                        //getViewState(im);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d("mytest", "------------load verify code2------------");
                        Log.d("mytest", "fail load verify code");
                        Log.d("mytest", throwable.toString());
                        Log.d("mytest", "------------load verify code2------------");
                        im.setImageResource(R.drawable.refreshcode);
                    }
                });
    }

    /**
     * 获取隐藏字段viewState 及 cookie
     *
     * @param im 显示验证码的imageview
     */
    public static void getViewState(final ImageView im, Context context) {
        HttpMethods.context = context;
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                        .followRedirects(false)
                        .build();

                Request request = new Request.Builder()
                        .url(HttpMethods.HOST)
                        .build();

                okhttp3.Response response = null;
                try {
                    response = client.newCall(request).execute();
                    String responseBody = TextUtils.convertStreamToString(response.body().byteStream(), "gbk");
                    viewState = Jsoup.parse(responseBody)
                            .select("input[name=__VIEWSTATE]").get(0).attr("value");
                    cookie = response.header("Set-Cookie").split(";")[0];
                    subscriber.onNext(viewState);
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                } finally {
                    if (response != null)
                        response.close();
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String msg) {
                        Log.d("mytest", "------------get viewState1------------");
                        Log.d("mytest", msg);
                        viewState = msg;
                        getVerifyCode(im);
                        Log.d("mytest", "------------get viewState1------------");
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d("mytest", "------------get viewState2------------");
                        Log.d("mytest", throwable.toString());
                        im.setImageResource(R.drawable.refreshcode);
                        Log.d("mytest", "------------get viewState2------------");
                    }
                });
    }

    /**
     * 登录
     *
     * @param account    账号
     * @param pwd        密码
     * @param verifyCode 验证码
     */
    public static void getAccess(final String account, final String pwd, final String verifyCode, Subscriber<String> subscriber) {
        Log.d("mytest", "begin getAccess");

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                OkHttpClient client = new OkHttpClient.Builder()
                        .followRedirects(false)
                        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS).build();
                FormBody.Builder builder = new FormBody.Builder();
                builder.add("txtUserName", account);
                builder.add("TextBox2", pwd);
                builder.add("txtSecretCode", verifyCode);
                builder.add("__VIEWSTATE", viewState);
                builder.add("RadioButtonList1", "学生");
                builder.add("Button1", "");
                builder.add("lbLanguage", "");
                builder.add("hidPdrs", "");
                builder.add("hidsc", "");
                RequestBody body = builder.build();
                Request request = new Request.Builder().url(LOGIN_URL)
                        .addHeader("Cookie", cookie).post(body).build();
                Response response1 = null;
                try {
                    response1 = client.newCall(request).execute();
                    Log.d("mytest", "login response code : " + response1.code());

                    String responseBody = TextUtils.convertStreamToString(response1.body().byteStream(), "gbk");

                    if (responseBody.contains("用户名不存在")) {
                        subscriber.onError(new IOException("用户名不存在"));
                    } else if (responseBody.contains("密码错误")) {
                        subscriber.onError(new IOException("密码错误"));
                    } else if (responseBody.contains("验证码不正确")) {
                        subscriber.onError(new IOException("验证码不正确"));
                    } else if (responseBody.contains("aspxerrorpath")) {
                        subscriber.onError(new IOException("服务器异常!请稍后重试1"));
                    } else if (responseBody.contains("xs_main.aspx")) {
                        subscriber.onNext("成功");
                    } else {
                        subscriber.onError(new IOException("服务器异常!请稍后重试2"));
                    }
                } catch (IOException e) {
                    subscriber.onError(e);
                } finally {
                    if (response1 != null)
                        response1.close();
                }

            }
        })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取学生信息
     *
     * @param account 账号
     */
    public static void getStudentInfo(final String account) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new StudentInfoInterceptor(cookie, account))
                .connectTimeout(5, TimeUnit.SECONDS)
                .followRedirects(false)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(new StudentInfoConverterFactory())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(HOST)
                .build();

        retrofit.create(StudentInfoService.class).getStudentInfo(account)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<StudentInfo>() {
                    @Override
                    public void call(StudentInfo studentInfo) {
                        Log.d("mytest", "------------load student info1------------");
                        Log.d("mytest", "success load student info");
                        Log.d("mytest", studentInfo.toString());
                        HttpMethods.studentInfo = studentInfo;
                        Log.d("mytest", "------------load student info1------------");
                        getFirstCurriculumInfo(account);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d("mytest", "------------load student info2------------");
                        Log.d("mytest", "fail load student info");
                        Log.d("mytest", throwable.toString());
                        Log.d("mytest", "------------load student info2------------");
                    }
                });
    }

    public static void getFirstCurriculumInfo(final String account) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new FirstCurriculumInfoInterceptor(cookie, account))
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .followRedirects(false)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(new FirstCurriculumInfoConverterFactory())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(HOST)
                .build();

        retrofit.create(FirstCurriculumInfoService.class).getFirstInfo(account)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<FirstgetCurriculum>() {
                    @Override
                    public void call(FirstgetCurriculum firstInfo) {
                        Log.d("mytest", "------------load first info1------------");
                        Log.d("mytest", "success load first info");
                        Log.d("mytest", firstInfo.toString());
                        Log.d("mytest", "------------load first info1------------");
                        years = firstInfo.getYears();
                        rounte(account, firstInfo.getViewState(), 0, 1, courseInfos);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d("mytest", "------------load first info2------------");
                        Log.d("mytest", "fail load first info");
                        Log.d("mytest", throwable.toString());
                        loginCompletedListener.onLoaded(false);
                        Log.d("mytest", "------------load first info2------------");
                    }
                });
    }

    private static List<String> years;

    private static void rounte(final String account, final String viewState, final int year_index, final int semester_index, final List<CourseInfo> courseInfos) {

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new RestCurriculumInfoInterceptor(cookie, account))
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .followRedirects(false)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(new RestCurriculumInfoConverterFactory())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(HOST)
                .build();

        retrofit.create(RestCurriculumInfoService.class).getAllCurriculumInfo(account, viewState, years.get(year_index), semester_index)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RestgetCurriculum>() {
                    @Override
                    public void call(RestgetCurriculum info) {
                        Log.d("mytest", "------------load curri info1------------");
                        Log.d("mytest", "success load curri info " + years.get(year_index) + " " + semester_index);
                        Log.d("mytest", info.toString());
                        info.setYear(years.get(year_index));
                        info.setSemester(String.valueOf(semester_index));
                        String viewState1 = info.getViewState();

                        courseInfos.addAll(DomUtils.getAllCurriInfo(info));
                        Log.d("mytest", "------------load curri info1------------");

                        if (semester_index == 1) {
                            rounte(account, viewState1, year_index, 2, courseInfos);
                        } else if (semester_index == 2) {
                            if (year_index + 1 < years.size()) {
                                rounte(account, viewState1, year_index + 1, 1, courseInfos);
                            } else {
//                                for(CourseInfo courseInfo : courseInfos) {
//                                    Log.d("mytest", courseInfo.toString());
//                                }
                                SQLiteDatabase db1 = SQLiteHelper.getWritableDatabase(context);
                                for (CourseInfo courseInfo : courseInfos) {
                                    //Log.d("mytest", courseInfo.toString());
                                    ContentValues values = new ContentValues();
                                    values.putNull("id");
                                    values.put("account", account);
                                    values.put("year", courseInfo.getYear());
                                    values.put("semester", courseInfo.getSemester());
                                    values.put("courseName", courseInfo.getCourseName());
                                    values.put("teacherName", courseInfo.getTeacherName());
                                    values.put("classroom", courseInfo.getClassRoom());
                                    SQLiteHelper.executeInsert(db1, "courseInfo", values);
                                }
                                SharedPreferenceUtil.setHasClassFromSys(context, true);
                                SQLiteHelper.closeDatabase(db1);
                                Log.d("mytest", "-----database insert complete-----");

                                loginCompletedListener.onLoaded(true);

                                SQLiteDatabase db2 = SQLiteHelper.getReadableDatabase(context);
                                Cursor cursor = SQLiteHelper.executeQuery(db2, "select * from courseInfo", null);
                                while (cursor.moveToNext()) {
                                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                                    String year = cursor.getString(cursor.getColumnIndex("year"));
                                    String semester = cursor.getString(cursor.getColumnIndex("semester"));
                                    String courseName = cursor.getString(cursor.getColumnIndex("courseName"));
                                    String teacherName = cursor.getString(cursor.getColumnIndex("teacherName"));
                                    String classroom = cursor.getString(cursor.getColumnIndex("classroom"));
//                                    Log.d("mytest", "id=" + id + " year=" + year + " semester=" + semester + " courseName=" + courseName
//                                            + " teacherName=" + teacherName + " classroom=" + classroom);
                                }
                                SQLiteHelper.closeDatabase(db2);
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d("mytest", "------------load curri info2------------");
                        Log.d("mytest", "fail load curri info " + years.get(year_index) + " " + semester_index);
                        throwable.printStackTrace();
                        loginCompletedListener.onLoaded(false);
                        Log.d("mytest", "------------load curri info2------------");
                    }
                });

    }

    private static List<CourseInfo> courseInfos = new ArrayList<>();

    public static void getAllCurriculumInfo(String account, LoginCompletedListener loginCompletedListener) {
        HttpMethods.loginCompletedListener = loginCompletedListener;
        getFirstCurriculumInfo(account);
    }

    private static LoginCompletedListener loginCompletedListener;

    public interface LoginCompletedListener {
        void onLoaded(boolean loaded);
    }
}
