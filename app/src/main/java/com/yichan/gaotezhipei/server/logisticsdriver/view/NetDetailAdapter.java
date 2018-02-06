package com.yichan.gaotezhipei.server.logisticsdriver.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.changelcai.mothership.view.recycler.MSClickableAdapter;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.server.logisticsdriver.entity.NetDetailItem;

import java.util.List;

/**
 * Created by ckerv on 2018/2/6.
 */

public class NetDetailAdapter extends MSClickableAdapter<NetDetailAdapter.NetDetailViewHolder>{

    private android.content.Context mContext;
    private List<NetDetailItem> mList;


    public NetDetailAdapter(Context context, List<NetDetailItem> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public void onBindVH(NetDetailViewHolder holder, int position) {

    }

    @Override
    public NetDetailViewHolder onCreateVH(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_net_detail, parent, false);
        return new NetDetailViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class NetDetailViewHolder extends RecyclerView.ViewHolder {

        public NetDetailViewHolder(View itemView) {
            super(itemView);
        }
    }
}
