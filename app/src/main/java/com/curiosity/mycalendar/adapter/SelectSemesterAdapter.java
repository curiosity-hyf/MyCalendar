package com.curiosity.mycalendar.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.curiosity.mycalendar.R;
import com.curiosity.mycalendar.SettingCurActivity;

import java.util.List;

public class SelectSemesterAdapter extends RecyclerView.Adapter<SelectSemesterAdapter.MyViewHolder> {

    private List<SettingCurActivity.SelectSemester> mData;

    public SelectSemesterAdapter(List<SettingCurActivity.SelectSemester> data) {
        mData = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_semester, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final SettingCurActivity.SelectSemester semester = mData.get(position);
        holder.getSemester().setText(semester.format());
        if(mListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(semester, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(SettingCurActivity.SelectSemester semester, int position) {
        mData.add(position, semester);
        notifyItemInserted(position);

    }

    public SettingCurActivity.SelectSemester getData(int position) {
        return mData.get(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView select_semester;

        public MyViewHolder(View itemView) {
            super(itemView);
            select_semester = (TextView) itemView.findViewById(R.id.select_semester);
        }

        public TextView getSemester() {
            return select_semester;
        }

    }


    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(SettingCurActivity.SelectSemester semester, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }
}
