package com.yichan.gaotezhipei.servicecenter.view;

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

public class ServiceCenterCatAdapter extends CategoryAdapter<CommonCategoryItem, ServiceCenterCatAdapter.ServiceCenterCatViewHolder> {

    public ServiceCenterCatAdapter(Context context, List<CommonCategoryItem> items) {
        super(context, items);
    }

    @Override
    public int getCategorySpanCount() {
        return 3;
    }


    @Override
    protected int getItemLayoutId() {
        return R.layout.item_service_center_category;
    }

    @Override
    protected ServiceCenterCatViewHolder onCreateVHAfterFit(View itemView, ViewGroup parent, int viewType) {
        return new ServiceCenterCatViewHolder(itemView);
    }

    @Override
    public void onBindVH(ServiceCenterCatViewHolder holder, int position) {
        holder.iconIv.setImageResource(mItems.get(position).getIconResId());
        holder.titleTv.setText(mItems.get(position).getTitle());
    }

    class ServiceCenterCatViewHolder extends RecyclerView.ViewHolder {

        ImageView iconIv;
        TextView titleTv;

        public ServiceCenterCatViewHolder(View itemView) {
            super(itemView);
            iconIv = (ImageView) itemView.findViewById(R.id.item_sc_iv_icon);
            titleTv = (TextView) itemView.findViewById(R.id.item_sc_tv_title);
        }
    }
}
