package com.yichan.gaotezhipei.base.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.changelcai.mothership.view.recycler.MSClickableAdapter;

import java.util.List;

/**
 * Created by ckerv on 2018/1/7.
 */

public abstract class BaseRecyclerListAdapter<D, VH extends RecyclerView.ViewHolder> extends MSClickableAdapter<VH> {

    protected List<D> mItems;
    private Context mContext;

    public BaseRecyclerListAdapter(Context context, List<D> items) {
        this.mItems = items;
        this.mContext = context;
    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }

    protected Context getContext() {
        return mContext;
    }
}
