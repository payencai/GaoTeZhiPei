package com.yichan.gaotezhipei.server.netstation.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.changelcai.mothership.view.recycler.MSClickableAdapter;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.listener.OnItemSubviewClickListener;
import com.yichan.gaotezhipei.server.netstation.entity.ExpressRecordedOrderPage;

import java.util.List;

/**
 * Created by ckerv on 2018/1/19.
 */

public class ExpressRecordedAdapter extends MSClickableAdapter<ExpressRecordedAdapter.ExpressRecordedViewHolder> {

    private android.content.Context mContext;
    private List<ExpressRecordedOrderPage.ListBean> mList;

    private OnItemSubviewClickListener<ExpressRecordedOrderPage.ListBean> mSubviewListener;

    public OnItemSubviewClickListener<ExpressRecordedOrderPage.ListBean> getSubviewListener() {
        return mSubviewListener;
    }

    public void setSubviewListener(OnItemSubviewClickListener<ExpressRecordedOrderPage.ListBean> mSubviewListener) {
        this.mSubviewListener = mSubviewListener;
    }

    public ExpressRecordedAdapter(Context context, List<ExpressRecordedOrderPage.ListBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public void onBindVH(ExpressRecordedViewHolder holder, final int position) {
        holder.tvNumber.setText("运单编号:" + mList.get(position).getOrderNumber());

        holder.tvTakeOn.setOnClickListener(null);

        if(mList.get(position).getStatus().equals("4")) {
            holder.tvTakeOn.setText("确认\n收录");
            holder.tvTakeOn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mSubviewListener != null) {
                        mSubviewListener.onClick(v, position, mList.get(position));
                    }
                }
            });
        } else if(mList.get(position).getStatus().equals("5")){
            holder.tvTakeOn.setText("已经\n收录");
        }

    }

    @Override
    public ExpressRecordedViewHolder onCreateVH(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_express_record, parent, false);
        return new ExpressRecordedViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ExpressRecordedViewHolder extends RecyclerView.ViewHolder {

        TextView tvNumber;
        TextView tvTakeOn;
        Button btnDetail;

        public ExpressRecordedViewHolder(View itemView) {
            super(itemView);

            tvNumber = (TextView) itemView.findViewById(R.id.item_tv_number);
            tvTakeOn = (TextView) itemView.findViewById(R.id.item_tv_takeon);
            btnDetail = (Button) itemView.findViewById(R.id.item_btn_detail);
        }
    }
}
