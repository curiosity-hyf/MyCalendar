package com.curiosity.mycalendar.sysinfo.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.curiosity.mycalendar.R;
import com.curiosity.mycalendar.sysinfo.FetchInfoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by red on 17-3-15.
 */

public class YearSelectFragment extends Fragment {

    @BindView(R.id.grade)
    AppCompatSpinner gradeSpinner;

    @BindView(R.id.semester)
    AppCompatSpinner semesterSpinner;

    private int gradePos;
    private int semesterPos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.year_select_layout, container, false);
        ButterKnife.bind(this, view);
        initListener();
        return view;
    }

    private void initListener() {

        gradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gradePos = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        semesterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                semesterPos = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public String getGradeValue() {
        switch (gradePos) {
            case 0:
                return "1";
            case 1:
                return "2";
            case 2:
                return "3";
            case 3:
                return "4";
            default:
                return "1";
        }
    }

    public String getSemesterValue() {
        switch (semesterPos) {
            case 0:
                return "01";
            case 1:
                return "02";
            default:
                return "01";
        }
    }

    public String[] getSelect() {
        String[] selection = new String[]{getGradeValue(), getSemesterValue()};
        return selection;
    }
}