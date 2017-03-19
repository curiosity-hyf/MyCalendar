package com.curiosity.mycalendar.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.curiosity.mycalendar.R;
import com.curiosity.mycalendar.config.FieldDefine;
import com.curiosity.mycalendar.utils.SQLiteHelper;
import com.curiosity.mycalendar.utils.SharedPreferenceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description :
 * Author : Curiosity
 * Date : 2016-12-29
 * E-mail : 1184581135qq@gmail.com
 */

public class EmptyFragment extends Fragment {

    @BindView(R.id.empty_msg)
    TextView msg;

    @BindView(R.id.login_code)
    EditText code;

    @BindView(R.id.login_im)
    ImageView im;

    @BindView(R.id.clear)
    Button clear;

    @BindView(R.id.show)
    Button show;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.empty_fragment, container, false);
        ButterKnife.bind(this, view);
        Log.d("mytest", "Empty Frag-->onCreateView");

        return view;
    }

    @OnClick(R.id.clear)
    public void onClear() {
        Log.d("mytest", "onClear");
        SharedPreferenceUtil.setHasClassFromSys(getContext(), false);
        SQLiteDatabase db = SQLiteHelper.getWritableDatabase(getContext());
        SQLiteHelper.executeDelete(db, "courseInfo", null, null);
        SQLiteHelper.closeDatabase(db);
    }

    @OnClick(R.id.show)
    public void onShow() {
        Log.d("mytest", "onShow");
        SQLiteDatabase db = SQLiteHelper.getReadableDatabase(getContext());
        Cursor cursor = SQLiteHelper.executeQuery(db, "select * from courseInfo", null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String account = cursor.getString(cursor.getColumnIndex("account"));
            String year = cursor.getString(cursor.getColumnIndex("year"));
            String semester = cursor.getString(cursor.getColumnIndex("semester"));
            String courseName = cursor.getString(cursor.getColumnIndex("courseName"));
            String teacherName = cursor.getString(cursor.getColumnIndex("teacherName"));
            String classroom = cursor.getString(cursor.getColumnIndex("classroom"));
            Log.d("mytest", "id=" + id + " account=" + account + " year=" + year + " semester=" + semester + " courseName=" + courseName
                    + " teacherName=" + teacherName + " classroom=" + classroom);
        }
        SQLiteHelper.closeDatabase(db);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("mytest", "Empty Frag-->onStart");
        Bundle bundle = getArguments();
        String emptyMsg = bundle.getString(FieldDefine.EMPTY_MSG);
//        Log.d("mytest", emptyMsg);
        msg.setText(emptyMsg);
        final ViewGroup.LayoutParams lp = im.getLayoutParams();
        ViewTreeObserver vto2 = code.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                code.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                Log.d("mytest", "after init :" + " height = " + code.getHeight() + " width = " + code.getWidth());
                lp.height = code.getHeight();
                im.setLayoutParams(lp);
            }
        });
        im.setImageResource(R.drawable.requirecode);
        //getCode();

        //test();
    }

    @OnClick(R.id.login_im)
    void refreshCode() {
        //ToastUtils.ToastShort(getContext(), R.string.text_curri_loading);
        //getCode();
        test();
    }

    @OnClick(R.id.looo)
    void login() {
        //HttpMethods.getAccess("3114006092", "hyf627507", code.getText().toString());
    }

    public void test() {
        im.setImageResource(R.drawable.requirecode);
//        HttpMethods.getVerifyCode(im);
    }

}
