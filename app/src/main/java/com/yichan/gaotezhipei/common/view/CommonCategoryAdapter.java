//package com.yichan.gaotezhipei.common.view;
//
//import android.content.Context;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.yichan.gaotezhipei.servicecenter.entity.CommonCategoryItem;
//
//import java.util.List;
//
///**
// * Created by ckerv on 2018/1/7.
// */
//
//public abstract class CommonCategoryAdapter extends CategoryAdapter<CommonCategoryItem, CommonCategoryViewHolder> {
//
//    public CommonCategoryAdapter(Context context, List items) {
//        super(context, items);
//    }
//
//    @Override
//    protected CommonCategoryViewHolder onCreateVHAfterFit(View itemView, ViewGroup parent, int viewType) {
//        return new CommonCategoryViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindVH(CommonCategoryViewHolder holder, int position) {
//        CommonCategoryItem categoryItem = mItems.get(position);
//        onBindVH(holder, categoryItem);
//    }
//
//
//    protected abstract void onBindVH(CommonCategoryViewHolder holder, CommonCategoryItem categoryItem);
//}
