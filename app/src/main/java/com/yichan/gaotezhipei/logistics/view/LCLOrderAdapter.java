package com.yichan.gaotezhipei.logistics.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.logistics.entity.LCLOrderItem;

import java.util.List;

/**
 * Created by ckerv on 2018/1/12.
 */

public class LCLOrderAdapter extends RecyclerView.Adapter<LCLOrderAdapter.LCLOrderViewHolder> {

    private Context mContext;
    private List<LCLOrderItem> mList;

    public LCLOrderAdapter(Context context, List<LCLOrderItem> list) {
        this.mContext = context;
        this.mList = list;
    }


    @Override
    public LCLOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_lcl_order_common, parent, false);
        return new LCLOrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LCLOrderViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class LCLOrderViewHolder extends RecyclerView.ViewHolder {

        public LCLOrderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
