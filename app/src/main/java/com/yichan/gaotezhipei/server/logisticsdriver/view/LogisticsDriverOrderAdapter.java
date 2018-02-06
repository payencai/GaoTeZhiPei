package com.yichan.gaotezhipei.server.logisticsdriver.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.changelcai.mothership.view.recycler.MSClickableAdapter;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.server.logisticsdriver.entity.LogisticsDriverOrderItem;

import java.util.List;

/**
 * Created by ckerv on 2018/2/5.
 */

public class LogisticsDriverOrderAdapter extends MSClickableAdapter<LogisticsDriverOrderAdapter.LogisticsDriverOrderViewHolder>{
    private Context mContext;
    private List<LogisticsDriverOrderItem> mList;


    public LogisticsDriverOrderAdapter(Context context, List<LogisticsDriverOrderItem> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public void onBindVH(LogisticsDriverOrderViewHolder holder, int position) {

    }

    @Override
    public LogisticsDriverOrderViewHolder onCreateVH(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_logistics_driver_order, parent, false);
        return new LogisticsDriverOrderViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class LogisticsDriverOrderViewHolder extends RecyclerView.ViewHolder {

        public LogisticsDriverOrderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
