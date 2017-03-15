package com.curiosity.mycalendar.sysinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.curiosity.mycalendar.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description : 年级和学期选择界面
 * Author : Curiosity
 * Date : 2017-3-13
 * E-mail : 1184581135qq@gmail.com
 */

public class YearSelectActivity extends AppCompatActivity {

    @BindView(R.id.grade)
    AppCompatSpinner gradeSpinner;

    @BindView(R.id.semester)
    AppCompatSpinner semesterSpinner;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private int gradePos;
    private int semesterPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.year_select_layout);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        //设置是否有返回箭头
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initListener();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.year_select_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.next_step:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("grade", getGradeValue());
                bundle.putString("semester", getSemesterValue());
                intent.putExtra("selected", bundle);
                intent.setClass(YearSelectActivity.this, CurriLoginActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
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
}
