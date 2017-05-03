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

import com.curiosity.mycalendar.R;
import com.curiosity.mycalendar.adapter.CourseAdapter;
import com.curiosity.mycalendar.bean.Courses;

import java.util.ArrayList;
import java.util.List;

/**
 * Description :
 * Author : curiosity-hyf
 * Date : 17-5-1
 * E-mail : curiooosity.h@gmail.com
 */

public class WeekFragment extends Fragment {


    private RecyclerView rv;
    private CourseAdapter adapter;
    private List<Courses> mData;
    private String year, semester, account;

    private View view;

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
        return view;
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
        initData(account, year, semester);
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
        rv.setVisibility(View.GONE);
    }

    private void initData(String account, String year, String semester) {
        mData = new ArrayList<>();
        adapter = new CourseAdapter(mData);

    }

    public void newInfo(Courses info) {
        int num = mData.size();
        adapter.addData(info, num);
        //rv.smoothScrollToPosition(num_msg+1);
    }

}
