package com.yichan.gaotezhipei.logistics.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.logistics.entity.LogisticOrderItem;

import java.util.List;

/**
 * Created by ckerv on 2018/1/15.
 */

public class LogisticOrderAdapter extends RecyclerView.Adapter<LogisticOrderAdapter.LogisticOrderViewHolder> {


    private Context mContext;
    private List<LogisticOrderItem> mList;

    public LogisticOrderAdapter(Context context, List<LogisticOrderItem> list) {
        this.mContext = context;
        this.mList = list;
    }


    @Override
    public LogisticOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_logistics_order_common, parent, false);
        return new LogisticOrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LogisticOrderViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    class LogisticOrderViewHolder extends RecyclerView.ViewHolder {

        public LogisticOrderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
