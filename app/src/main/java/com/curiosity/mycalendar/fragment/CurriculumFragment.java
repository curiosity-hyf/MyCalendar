package com.curiosity.mycalendar.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.curiosity.mycalendar.sysinfo.FetchInfoActivity;
import com.curiosity.mycalendar.R;
import com.curiosity.mycalendar.SettingCurActivity;
import com.curiosity.mycalendar.adapter.CourseAdapter;
import com.curiosity.mycalendar.bean.CourseInfo;
import com.curiosity.mycalendar.config.FieldDefine;
import com.curiosity.mycalendar.utils.SQLiteHelper;
import com.curiosity.mycalendar.utils.SharedPreferenceUtil;
import com.curiosity.mycalendar.customview.MenuFAB;
import com.gordonwong.materialsheetfab.MaterialSheetFab;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Curiosity on 2016-12-30.
 */

public class CurriculumFragment extends Fragment {
    private RecyclerView rv;
    private CourseAdapter adapter;
    private List<CourseInfo> mData;
    MaterialSheetFab<MenuFAB> materialSheetFab;
    public int REQUEST_ACTIVITY_CODE = 1;
    public int REQUEST_SETTING_CODE = 2;

    @BindView(R.id.empty_msg)
    TextView empty_msg;

    @BindView(R.id.btn_getCurriculum)
    TextView btn_getCurriculum;

    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("mytest", "Curri -- > onCreateView");
        view = inflater.inflate(R.layout.curriculum_list_layout, container, false);
        ButterKnife.bind(this, view);

        year = SharedPreferenceUtil.getCurYear(getContext());
        semester = SharedPreferenceUtil.getCurSemester(getContext());
        account = SharedPreferenceUtil.getSaveAccount(getContext());
        return view;
    }

    @OnClick(R.id.btn_getCurriculum)
    public void onGetCurriculum() {
        Log.d("mytest", "click");
        startActivityForResult(new Intent().setClass(getContext(), FetchInfoActivity.class), REQUEST_ACTIVITY_CODE);
        if(materialSheetFab.isSheetVisible())
            materialSheetFab.hideSheet();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_ACTIVITY_CODE) {
            if(resultCode == RESULT_OK) {
                startActivityForResult(new Intent().setClass(getContext(), SettingCurActivity.class), REQUEST_SETTING_CODE);
            }
        }
        if(requestCode == REQUEST_SETTING_CODE) {
            if(resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                year = bundle.getString("year");
                semester = bundle.getString("semester");
                account = SharedPreferenceUtil.getSaveAccount(getContext());
                int size = mData.size();
                Log.d("mytest", "size2 = ------------" + size);
                for(int i = size - 1; i >= 0; --i) {
                    adapter.deleteData(0);
                }
//                initData(account, year, semester);
//                initView();
            }
        }
    }

    private void initView() {

        rv = (RecyclerView) view.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        rv.setAdapter(adapter);
        rv.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Toast.makeText(getActivity().getApplicationContext(), adapter.getData(position).getCourseName(),
//                        Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onItemLongClick(View view, int position) {
//                Toast.makeText(getActivity().getApplicationContext(), adapter.getData(position).getCourseName(),
//                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData(String account, String year, String semester) {
        mData = new ArrayList<>();
        adapter = new CourseAdapter(mData);
        boolean hasCourse = SharedPreferenceUtil.getHasClassFromSys(getContext()) | SharedPreferenceUtil.getHasClassCustom(getContext());
        if(hasCourse) {
            empty_msg.setVisibility(View.GONE);
            SQLiteDatabase db = SQLiteHelper.getReadableDatabase(getContext());
            Cursor cursor = SQLiteHelper.executeQuery(db,
                    "select * from " + SQLiteHelper.COURSE_INFO_TABLE +
                    " where account = ? and year = ? and semester = ?", new String[]{account, year, semester});
            Log.d("mytest", "----------load data!!!----------------");
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("courseName"));
                String teacher_name = cursor.getString(cursor.getColumnIndex("teacherName"));
                CourseInfo info = new CourseInfo();
//                info.setCourseName(name);
//                info.setId(id);
//                info.setTeacherName(teacher_name);
                newInfo(info);
                //Log.d("mytest", "" + id + " " + name + " " + teacher_name);
            }
        }

//        while(cursor.moveToNext()) {
//            int id = cursor.getInt(cursor.getColumnIndex("id"));
//            String year = cursor.getString(cursor.getColumnIndex("year"));
//            String semester = cursor.getString(cursor.getColumnIndex("semester"));
//            String courseName = cursor.getString(cursor.getColumnIndex("courseName"));
//            String teacherName = cursor.getString(cursor.getColumnIndex("teacherName"));
//            String classroom = cursor.getString(cursor.getColumnIndex("classroom"));
//            Log.d("mytest", "id=" + id + " year=" + year + " semester=" + semester + " courseName=" + courseName
//                    + " teacherName=" + teacherName + " classroom=" + classroom);
//        }
    }

    public void newInfo(CourseInfo info) {
        int num = mData.size();
        adapter.addData(info, num);
        //rv.smoothScrollToPosition(num_msg+1);
    }

    static MenuFAB fab;
    View sheetView;
    View overlay;
    private void initFAB() {
        fab = (MenuFAB) view.findViewById(R.id.fab);
        sheetView = view.findViewById(R.id.fab_sheet);
        overlay = view.findViewById(R.id.dim_overlay);
        int sheetColor = getResources().getColor(R.color.cardview_light_background);
        int fabColor = getResources().getColor(R.color.colorAccent);

        // Initialize material sheet FAB
        materialSheetFab = new MaterialSheetFab<>(fab, sheetView, overlay,
                sheetColor, fabColor);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("mytest", "Curriculum Frag-->onPause");
        if(materialSheetFab.isSheetVisible())
            materialSheetFab.hideSheet();
    }

    private  String year, semester, account;
    @Override
    public void onStart() {
        super.onStart();
        Log.d("mytest", "Curriculum Frag-->onStart");

        Log.d("mytest", year + " " + semester);
        if(mData != null) {
            int size = mData.size();
            Log.d("mytest", "size1 = ------------" + size);
            for (int i = size - 1; i >= 0; --i) {
                adapter.deleteData(0);
            }
        }
//            initData(account, year, semester);
//            initView();


        Bundle bundle = getArguments();
        String emptyMsg = bundle.getString(FieldDefine.EMPTY_MSG);
        Log.d("mytest", emptyMsg);
        initFAB();
    }
}
