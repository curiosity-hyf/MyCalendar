package com.curiosity.mycalendar.page.curriculum;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.curiosity.mycalendar.R;
import com.curiosity.mycalendar.adapter.CourseAdapter;
import com.curiosity.mycalendar.bean.Course;
import com.curiosity.mycalendar.bean.CoursesJSON;
import com.curiosity.mycalendar.bean.WeekCourses;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description :
 * Author : curiosity-hyf
 * Date : 17-5-1
 * E-mail : curiooosity.h@gmail.com
 */

public class WeekFragment extends Fragment {

    private CourseAdapter adapter;
    private WeekCourses mData;
    private String year, semester, account;

    private View view;

    @BindView(R.id.week_empty)
    TextView msg;

    @BindView(R.id.curriculum_week_rv)
    RecyclerView rv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.curriculum_week_layout, container, false);
        } else {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if(bundle != null) {
            String m = bundle.getString("msg");
            if("empty".equals(m)) {
                rv.setVisibility(View.GONE);
            } else if("data".equals(m)) {
                msg.setVisibility(View.GONE);
            }
        }
        return view;
    }


    public void setData(WeekCourses data) {
        this.mData = data;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_ACTIVITY_CODE) {
//            if (resultCode == RESULT_OK) {
////                startActivityForResult(new Intent().setClass(getContext(), SettingCurActivity.class), REQUEST_SETTING_CODE);
//            }
//        }
//        if (requestCode == REQUEST_SETTING_CODE) {
//            if (resultCode == RESULT_OK) {
//                Bundle bundle = data.getExtras();
//                year = bundle.getString("year");
//                semester = bundle.getString("semester");
//                account = SharedPreferenceUtil.getSaveAccount(getActivity().getApplicationContext());
//                int size = mData.size();
//                Log.d("mytest", "size2 = ------------" + size);
//                for (int i = size - 1; i >= 0; --i) {
//                    adapter.deleteData(0);
//                }
////                initData(account, year, semester);
////                initView();
//            }
//        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initView();
    }

    private void initView() {

        rv = (RecyclerView) view.findViewById(R.id.curriculum_week_rv);
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

    private void initData() {
        adapter = new CourseAdapter(mData);

    }

    public void newInfo(Course info) {
        int num = mData.getCount();
        adapter.addData(info, num);
        //rv.smoothScrollToPosition(num_msg+1);
    }

}
