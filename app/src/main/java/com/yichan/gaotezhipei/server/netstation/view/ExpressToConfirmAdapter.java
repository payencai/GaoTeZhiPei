package com.yichan.gaotezhipei.server.netstation.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.changelcai.mothership.view.recycler.MSClickableAdapter;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.listener.OnItemSubviewClickListener;
import com.yichan.gaotezhipei.server.netstation.entity.ExpressToComfirmOrderPage;

import java.util.List;

/**
 * Created by ckerv on 2018/1/19.
 */

public class ExpressToConfirmAdapter extends MSClickableAdapter<ExpressToConfirmAdapter.ExpressToConfirmViewHolder> {
    private android.content.Context mContext;
    private List<ExpressToComfirmOrderPage.ListBean> mList;

    private OnItemSubviewClickListener<ExpressToComfirmOrderPage.ListBean> mSubviewListener;

    public OnItemSubviewClickListener<ExpressToComfirmOrderPage.ListBean> getSubviewListener() {
        return mSubviewListener;
    }

    public void setSubviewListener(OnItemSubviewClickListener<ExpressToComfirmOrderPage.ListBean> mSubviewListener) {
        this.mSubviewListener = mSubviewListener;
    }


    public ExpressToConfirmAdapter(Context context, List<ExpressToComfirmOrderPage.ListBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public void onBindVH(ExpressToConfirmViewHolder holder, final int position) {
        holder.tvDriverName.setText(mList.get(position).getDriverName());
        holder.tvCount.setText("揽货件数:" + mList.get(position).getCount() + "件");
        holder.tvTime.setText("揽货时间:" + mList.get(position).getTakeorderTime());
        holder.rlConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSubviewListener != null) {
                    mSubviewListener.onClick(v, position, mList.get(position));
                }
            }
        });
    }

    @Override
    public ExpressToConfirmViewHolder onCreateVH(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_express_to_confirm, parent, false);
        return new ExpressToConfirmViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ExpressToConfirmViewHolder extends RecyclerView.ViewHolder {

        TextView tvDriverName;
        TextView tvCount;
        TextView tvTime;
        RelativeLayout rlConfirm;

        public ExpressToConfirmViewHolder(View itemView) {
            super(itemView);
            tvDriverName = (TextView) itemView.findViewById(R.id.item_tv_driver_name);
            tvCount = (TextView) itemView.findViewById(R.id.item_tv_count);
            tvTime = (TextView) itemView.findViewById(R.id.item_tv_time);
            rlConfirm = (RelativeLayout) itemView.findViewById(R.id.item_rl_confirm);
        }
    }
}
