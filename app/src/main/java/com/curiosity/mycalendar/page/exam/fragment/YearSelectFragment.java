package com.curiosity.mycalendar.page.exam.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.curiosity.mycalendar.R;
import com.curiosity.mycalendar.utils.SQLiteHelper;
import com.curiosity.mycalendar.utils.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description :
 * Author : curiosity-hyf
 * Date : 2017-3-15
 * E-mail : curiooosity.h@gmail.com
 */

public class YearSelectFragment extends Fragment {
    private static final String TAG = "mytest";
    @BindView(R.id.grade)
    AppCompatSpinner gradeSpinner;

    @BindView(R.id.semester)
    AppCompatSpinner semesterSpinner;

    private int gradePos = 0;
    private int semesterPos = 0;

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "YearSelect onCreateView: ");
        if (view == null) {
            view = inflater.inflate(R.layout.exam_select_layout, container, false);
        } else {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }
        ButterKnife.bind(this, view);

        initListener();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void initListener() {

        gradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gradePos = position;
                semesterSpinner.setSelection(0);
                semesterPos = 0;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        semesterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                semesterPos = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private int getGradeValue() {
        return gradePos + 1;
    }

    private int getSemesterValue() {
        return semesterPos + 1;
    }

    public void saveSelect() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "YearSelect onAttach: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "YearSelect onDestroy: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "YearSelect onStop: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "YearSelect onPause: ");
    }
}
