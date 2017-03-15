package com.curiosity.mycalendar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.curiosity.mycalendar.adapter.SelectSemesterAdapter;
import com.curiosity.mycalendar.utils.SQLiteHelper;
import com.curiosity.mycalendar.utils.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Curiosity on 2017-1-3.
 */

public class SettingCurActivity extends AppCompatActivity {

    @BindView(R.id.set_cur_rv)
    RecyclerView set_cur_rv;


    private SelectSemesterAdapter adapter;

    private List<SelectSemester> semester_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_cur_layout);
        ButterKnife.bind(this);

        initData();
        initView();
    }

    private void initView() {
        set_cur_rv.setHasFixedSize(true);
        set_cur_rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        set_cur_rv.setAdapter(adapter);
        set_cur_rv.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(new SelectSemesterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SelectSemester semester, int position) {
                Toast.makeText(getApplicationContext(),
                        adapter.getData(position).getYear() + " " + adapter.getData(position).getSemester(),
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("year", adapter.getData(position).getYear());
                bundle.putString("semester", adapter.getData(position).getSemester());
                SharedPreferenceUtil.setCurYear(SettingCurActivity.this, adapter.getData(position).getYear());
                SharedPreferenceUtil.setCurSemester(SettingCurActivity.this, adapter.getData(position).getSemester());
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void initData() {
        semester_list = new ArrayList<>();
        adapter = new SelectSemesterAdapter(semester_list);
        String account = SharedPreferenceUtil.getSaveAccount(this);
        SQLiteDatabase db = SQLiteHelper.getReadableDatabase(this);
        Cursor cursor = SQLiteHelper.executeQuery(db,
                "select distinct year, semester from " + SQLiteHelper.COURSE_INFO_TABLE +
                " where account = ?", new String[] {account});
        Log.d("mytest", "SettingCurActivity -- > " + cursor.getCount());
        while(cursor.moveToNext()) {
            String year = cursor.getString(cursor.getColumnIndex("year"));
            String semester = cursor.getString(cursor.getColumnIndex("semester"));
            SelectSemester selectSemester = new SelectSemester(year, semester);

            newSemester(selectSemester);
        }
        cursor.close();
        SQLiteHelper.closeDatabase(db);
        //默认选择最后的一个学期
        SharedPreferenceUtil.setCurYear(this, semester_list.get(semester_list.size()-1).getYear());
        SharedPreferenceUtil.setCurSemester(this, semester_list.get(semester_list.size()-1).getSemester());
    }

    public void newSemester(SelectSemester s) {
        int num = semester_list.size();
        adapter.addData(s, num);
        //rv.smoothScrollToPosition(num_msg+1);
    }

    public class SelectSemester {
        private String year, semester;
        SelectSemester(String year, String semester) {
            this.year = year;
            this.semester = semester;
        }

        public String format() {
            return year + "学年 第" + (semester.equals("1") ? "一" : "二") + "学期";
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getSemester() {
            return semester;
        }

        public void setSemester(String semester) {
            this.semester = semester;
        }
    }

}
