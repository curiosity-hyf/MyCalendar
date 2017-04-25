package com.curiosity.mycalendar.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.curiosity.mycalendar.R;
import com.curiosity.mycalendar.bean.CourseInfo;

import java.util.List;

/**
 * Description :
 * Author : Curiosity
 * Date : 2016-12-29
 * E-mail : curiooosity.h@gmail.com
 */

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.MyViewHolder> {

    private List<CourseInfo> mData;
    private OnItemClickListener mListener;

    public CourseAdapter(List<CourseInfo> infoBean) {
        mData = infoBean;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_course, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        CourseInfo infoBean = mData.get(position);
//        holder.getId().setText(""+infoBean.getId());
//        holder.getName().setText(infoBean.getCourseName());
//        holder.getTeacher().setText(infoBean.getTeacherName());
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
        return mData.size();
    }

    public void addData(CourseInfo infoBean, int position) {
        mData.add(position, infoBean);
        notifyItemInserted(position);
    }

    public void deleteData(int position) {
        mData.remove(position);
        notifyDataSetChanged();
        notifyItemRemoved(position);
        if (position != mData.size()) {
            notifyItemRangeChanged(position, mData.size() - position);
        }
    }

    public CourseInfo getData(int position) {
        return mData.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView course_id;
        private TextView course_name;
        private TextView course_teacher;

        public MyViewHolder(View itemView) {
            super(itemView);
            course_id = (TextView) itemView.findViewById(R.id.course_id);
            course_name = (TextView) itemView.findViewById(R.id.course_name);
            course_teacher = (TextView) itemView.findViewById(R.id.course_teacher);
        }

        public TextView getId() {
            return course_id;
        }

        public TextView getName() {
            return course_name;
        }

        public TextView getTeacher() {
            return course_teacher;
        }

    }
}
