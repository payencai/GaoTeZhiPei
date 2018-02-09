package com.yichan.gaotezhipei.trainservice.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.changelcai.mothership.view.recycler.MSClickableAdapter;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.common.constant.Constans;
import com.yichan.gaotezhipei.trainservice.activity.TrainCourseDetailActivity;
import com.yichan.gaotezhipei.trainservice.entity.CourseItem;

import java.util.List;

/**
 * Created by ckerv on 2018/1/20.
 */

public class CourseListAdapter extends MSClickableAdapter<CourseListAdapter.CourseListViewHolder> {

    private Context mContext;
    private List<CourseItem> mList;

    /**
     * 0为线上课程，1为我要报名
     */
    private int mType;

    public CourseListAdapter(Context context, List<CourseItem> list, int type) {
        this.mContext = context;
        this.mList = list;
        this.mType = type;
    }

    @Override
    public void onBindVH(CourseListViewHolder holder, int position) {
        if(mType == 0) {
            holder.tvStatus.setText("初级.168人在学");
            holder.tvType.setText("免费");
            holder.btnApply.setVisibility(View.GONE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Bundle bundle = new Bundle();
//                    bundle.putInt();
                    Intent intent = new Intent();
                    intent.setClass(mContext, TrainCourseDetailActivity.class);
                    intent.putExtra(Constans.CourseModal, Constans.COURSE_MODAL.ONLINE.getIndex());
                    mContext.startActivity(intent);
                }
            });
        } else {
            holder.tvStatus.setText("正在报名");
            holder.tvType.setText("线下课程");
            holder.btnApply.setVisibility(View.VISIBLE);
            holder.btnApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    mContext.startActivity(new Intent(mContext, TrainCourseDetailActivity.class));
                    Intent intent = new Intent();
                    intent.setClass(mContext, TrainCourseDetailActivity.class);
                    intent.putExtra(Constans.CourseModal, Constans.COURSE_MODAL.OFFLINE.getIndex());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public CourseListViewHolder onCreateVH(ViewGroup parent, int viewType) {
        return new CourseListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_course_common, parent, false));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class CourseListViewHolder extends RecyclerView.ViewHolder {

        ImageView ivIcon;
        TextView tvTitle;
        TextView tvStatus;
        TextView tvType;
        Button btnApply;

        public CourseListViewHolder(View itemView) {
            super(itemView);

            ivIcon = (ImageView) itemView.findViewById(R.id.item_course_iv_icon);
            tvTitle = (TextView) itemView.findViewById(R.id.item_course_tv_title);
            tvStatus = (TextView) itemView.findViewById(R.id.item_course_tv_status);
            tvType = (TextView) itemView.findViewById(R.id.item_course_tv_type);
            btnApply = (Button) itemView.findViewById(R.id.item_course_btn_apply);
        }
    }
}
