package com.curiosity.mycalendar.info.fragment;

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
import com.curiosity.mycalendar.config.FieldDefine;
import com.curiosity.mycalendar.utils.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.HashMap;

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

    @BindView(R.id.week)
    AppCompatSpinner weekSpinner;

    private int gradePos = 0;
    private int semesterPos = 0;
    private int weekPos = 0;

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "YearSelect onCreateView: ");
        if (view == null) {
            view = inflater.inflate(R.layout.year_select_layout, container, false);
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

    private HashMap<String, Integer> curriculumMaxWeek;
    private ArrayAdapter<String> arrayAdapter;
    public void setWeekMax(HashMap<String, Integer> curriculumMaxWeek) {
        this.curriculumMaxWeek = curriculumMaxWeek;
        resetWeekList("0101");
    }

    private void initListener() {

        arrayAdapter = new ArrayAdapter<>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, new ArrayList<String>());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weekSpinner.setAdapter(arrayAdapter);

        gradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gradePos = position;
                semesterSpinner.setSelection(0);
                semesterPos = 0;
                resetWeekList("0"+(gradePos+1)+"0"+(semesterPos+1));
                weekSpinner.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        semesterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                semesterPos = position;
                resetWeekList("0"+(gradePos+1)+"0"+(semesterPos+1));
                weekSpinner.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        weekSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                weekPos = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void resetWeekList(String yearCode) {
        Log.d("myA", "resetWeekList: " + yearCode);
        int num = arrayAdapter.getCount();
        for(int i = 0; i < num; ++i) {
            arrayAdapter.remove(arrayAdapter.getItem(0));
        }

        num = curriculumMaxWeek.get(yearCode);
        for(int i = 1; i <= num; ++i) {
            arrayAdapter.add("第" + i + "周");
        }
        arrayAdapter.notifyDataSetChanged();
        weekPos = 0;
    }

    private int getGradeValue() {
        return gradePos + 1;
    }

    private int getSemesterValue() {
        return semesterPos + 1;
    }

    private int getWeekValue() {
        return weekPos + 1;
    }

    public void saveSelect() {
        SharedPreferenceUtil.setSelectGrade(getActivity().getApplicationContext(),  getGradeValue());
        SharedPreferenceUtil.setSelectSemester(getActivity().getApplicationContext(), getSemesterValue());
        SharedPreferenceUtil.setWeekOrder(getActivity().getApplicationContext(), getWeekValue());
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
