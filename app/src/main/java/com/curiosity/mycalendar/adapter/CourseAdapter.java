package com.curiosity.mycalendar.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.curiosity.mycalendar.R;
import com.curiosity.mycalendar.bean.Course;
import com.curiosity.mycalendar.bean.CoursesJSON;
import com.curiosity.mycalendar.bean.WeekCourses;
import com.curiosity.mycalendar.utils.TextUtils;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Description :
 * Author : curiosity-hyf
 * Date : 2016-12-29
 * E-mail : curiooosity.h@gmail.com
 */

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.MyViewHolder> {

    private WeekCourses mData;
    private OnItemClickListener mListener;

    public CourseAdapter(WeekCourses data) {
        mData = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_course, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Course course = mData.getCoursesAt(position);
        holder.getFullTime().setText(TextUtils.formatDate(course.getFullTime()));
        String cls_time = TextUtils.formatNumString(TextUtils.spiltString(course.getClsNum(), 2), "、");
        holder.getClsNum().setText(" 第 " + cls_time + " 节");
        holder.getName().setText(course.getName());
        holder.getTeacher().setText(course.getTeacher());
        if (mListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(holder.itemView, position);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mListener.onItemLongClick(holder.itemView, position);
                    return true;
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return mData.getCount();
    }

    public void addData(Course course, int position) {
        mData.add(course, position);
        notifyItemInserted(position);
    }

    public void deleteData(int position) {
        mData.remove(position);
        notifyDataSetChanged();
        notifyItemRemoved(position);
        if (position != mData.getCount()) {
            notifyItemRangeChanged(position, mData.getCount() - position);
        }
    }

    public Course getData(int position) {
        return mData.getCoursesAt(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView course_full_time;
        private TextView course_cls_num;
        private TextView course_name;
        private TextView course_teacher;

        public MyViewHolder(View itemView) {
            super(itemView);
            course_full_time = ButterKnife.findById(itemView, R.id.course_full_time);
            course_cls_num = ButterKnife.findById(itemView, R.id.course_cls_num);
            course_name = ButterKnife.findById(itemView, R.id.course_name);
            course_teacher = ButterKnife.findById(itemView, R.id.course_teacher);
        }

        public TextView getFullTime() {
            return course_full_time;
        }

        public TextView getClsNum() {
            return course_cls_num;
        }

        public TextView getName() {
            return course_name;
        }

        public TextView getTeacher() {
            return course_teacher;
        }

    }
}
