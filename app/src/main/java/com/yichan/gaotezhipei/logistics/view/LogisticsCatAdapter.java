package com.yichan.gaotezhipei.logistics.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.common.view.CategoryAdapter;
import com.yichan.gaotezhipei.servicecenter.entity.CommonCategoryItem;

import java.util.List;


/**
 * Created by ckerv on 2018/1/7.
 */

public class LogisticsCatAdapter extends CategoryAdapter<CommonCategoryItem, LogisticsCatAdapter.LogisticsCatViewHolder> {


    public LogisticsCatAdapter(Context context, List<CommonCategoryItem> items) {
        super(context, items);
    }

    @Override
    protected int getCategorySpanCount() {
        return 2;
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_logistic_category;
    }

    @Override
    protected LogisticsCatViewHolder onCreateVHAfterFit(View itemView, ViewGroup parent, int viewType) {
        return new LogisticsCatViewHolder(itemView);
    }

    @Override
    public void onBindVH(LogisticsCatViewHolder holder, int position) {
        CommonCategoryItem categoryItem = mItems.get(position);
        holder.iconIv.setImageResource(categoryItem.getIconResId());
        holder.titleTv.setText(categoryItem.getTitle());
        holder.subTitleTv.setText(categoryItem.getSubTitle());
    }

    class LogisticsCatViewHolder extends RecyclerView.ViewHolder {

        ImageView iconIv;
        TextView titleTv;
        TextView subTitleTv;

        public LogisticsCatViewHolder(View itemView) {
            super(itemView);
            iconIv = (ImageView) itemView.findViewById(R.id.item_logis_iv_icon);
            titleTv = (TextView) itemView.findViewById(R.id.item_logis_tv_title);
            subTitleTv = (TextView) itemView.findViewById(R.id.item_logis_tv_subtitle);
        }
    }


}
