package com.yichan.gaotezhipei.trainservice.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.changelcai.mothership.view.recycler.MSClickableAdapter;
import com.ckev.chooseimagelibrary.base.img.assist.CommonImageLoader;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.common.constant.Constans;
import com.yichan.gaotezhipei.trainservice.activity.TrainCourseDetailActivity;
import com.yichan.gaotezhipei.trainservice.entity.OnlineCoursePage;

import java.util.List;

/**
 * Created by ckerv on 2018/3/7.
 */

public class OnlineCourseAdapter extends MSClickableAdapter<OnlineCourseAdapter.CourseListViewHolder> {

    private List<OnlineCoursePage.BeanListBean> mList;
    private Context mContext;

    public OnlineCourseAdapter(List<OnlineCoursePage.BeanListBean> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public void onBindVH(CourseListViewHolder holder, int position) {
        CommonImageLoader.displayImage(mList.get(position).getImage(), holder.ivIcon, CommonImageLoader.NO_CACHE_OPTIONS);
        holder.tvTitle.setText(mList.get(position).getTitle());
        holder.tvStatus.setText(mList.get(position).getContent());
        holder.tvType.setText("免费");
        holder.btnApply.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, TrainCourseDetailActivity.class);
                intent.putExtra(Constans.CourseModal, Constans.COURSE_MODAL.ONLINE.getIndex());
                mContext.startActivity(intent);
            }
        });

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
