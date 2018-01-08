package com.yichan.gaotezhipei.common.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.changelcai.mothership.view.recycler.MSClickableAdapter;
import com.yichan.gaotezhipei.servicecenter.entity.CommonCategoryItem;

import java.util.List;

/**
 * 类目item自适应recyclerview的高度。
 * Created by ckerv on 2018/1/6.
 */

public abstract class CategoryAdapter<D extends CommonCategoryItem, V extends RecyclerView.ViewHolder> extends MSClickableAdapter<V> {

    protected List<D> mItems;
    protected Context mContext;

    public CategoryAdapter(Context context, List<D> items) {
        this.mContext = context;
        this.mItems = items;
    }


    @Override
    public V onCreateVH(ViewGroup parent, int viewType) {
        //RecyclerView的高度
        int parentHeight = parent.getMeasuredHeight();

        View itemView = LayoutInflater.from(mContext).inflate(getItemLayoutId(), parent, false);

        //得到行数
        int row = mItems.size() / getCategorySpanCount();
        if(mItems.size() % getCategorySpanCount() != 0) {
            row += 1;
        }

        //适应每个item的高度
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        layoutParams.height = parentHeight / row;
        itemView.setLayoutParams(layoutParams);

        return onCreateVHAfterFit(itemView, parent, viewType);
    }



    @Override
    public int getItemCount() {
        return mItems.size();
    }


    protected abstract int getCategorySpanCount();

    protected abstract int getItemLayoutId();

    protected abstract V onCreateVHAfterFit(View itemView, ViewGroup parent, int viewType);


}
