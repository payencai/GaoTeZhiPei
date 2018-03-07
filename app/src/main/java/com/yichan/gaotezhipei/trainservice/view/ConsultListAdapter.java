package com.yichan.gaotezhipei.trainservice.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ckev.chooseimagelibrary.base.img.assist.CommonImageLoader;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.view.BaseRecyclerListAdapter;
import com.yichan.gaotezhipei.trainservice.entity.ConsultPage;

import java.util.List;

/**
 * Created by ckerv on 2018/1/7.
 */

public class ConsultListAdapter extends BaseRecyclerListAdapter<ConsultPage.BeanListBean, ConsultListAdapter.ConsultListViewHolder> {


    public ConsultListAdapter(Context context, List<ConsultPage.BeanListBean> items) {
        super(context, items);
    }

    @Override
    public void onBindVH(ConsultListViewHolder holder, int position) {
        CommonImageLoader.displayImage(mItems.get(position).getImage(), holder.iconIv, CommonImageLoader.NO_CACHE_OPTIONS);
        holder.titleTv.setText(mItems.get(position).getTitle());
        holder.descTv.setText(mItems.get(position).getContent());
    }

    @Override
    public ConsultListViewHolder onCreateVH(ViewGroup parent, int viewType) {
        return new ConsultListViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_consult, parent, false));
    }

    class ConsultListViewHolder extends RecyclerView.ViewHolder {

        ImageView iconIv;
        TextView titleTv;
        TextView descTv;

        public ConsultListViewHolder(View itemView) {
            super(itemView);
            iconIv = (ImageView) itemView.findViewById(R.id.item_consult_iv_img);
            titleTv = (TextView) itemView.findViewById(R.id.item_consult_tv_title);
            descTv = (TextView) itemView.findViewById(R.id.item_consult_tv_desc);
        }
    }
}
