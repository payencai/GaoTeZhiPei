package com.yichan.gaotezhipei.server.netstation.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.changelcai.mothership.view.recycler.MSClickableAdapter;
import com.ckev.chooseimagelibrary.base.img.assist.CommonImageLoader;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.server.netstation.entity.ExpressConfirmedItem;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ckerv on 2018/1/19.
 */

public class ExpressConfirmedAdapter extends MSClickableAdapter<ExpressConfirmedAdapter.ExpressConfirmedViewHolder> {

    private android.content.Context mContext;
    private List<ExpressConfirmedItem> mList;


    public ExpressConfirmedAdapter(Context context, List<ExpressConfirmedItem> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public void onBindVH(ExpressConfirmedViewHolder holder, int position) {
        CommonImageLoader.displayImage(mList.get(position).getPortraitUrl(), holder.civHead, CommonImageLoader.NO_CACHE_OPTIONS);
        holder.tvDriverName.setText(mList.get(position).getDriverName());
        holder.tvCount.setText("揽货件数:" + mList.get(position).getCount() + "件");
        holder.tvTime.setText("确认时间:" + mList.get(position).getConfirmTime());
    }

    @Override
    public ExpressConfirmedViewHolder onCreateVH(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_server_net_main, parent, false);
        return new ExpressConfirmedViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ExpressConfirmedViewHolder extends RecyclerView.ViewHolder {

        TextView tvDriverName;
        TextView tvCount;
        TextView tvTime;
        CircleImageView civHead;

        public ExpressConfirmedViewHolder(View itemView) {
            super(itemView);
            civHead = (CircleImageView) itemView.findViewById(R.id.item_net_main_iv_header);
            tvDriverName = (TextView) itemView.findViewById(R.id.item_tv_driver_name);
            tvCount = (TextView) itemView.findViewById(R.id.item_tv_count);
            tvTime = (TextView) itemView.findViewById(R.id.item_tv_time);
        }
    }
}
