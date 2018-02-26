package com.yichan.gaotezhipei.server.logisticsdriver.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.changelcai.mothership.view.recycler.MSClickableAdapter;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.listener.OnItemSubviewClickListener;
import com.yichan.gaotezhipei.server.logisticsdriver.constant.LogisticsDriverConstants;
import com.yichan.gaotezhipei.server.logisticsdriver.entity.LogisticsDriverOrderItem;

import java.util.List;

/**
 * Created by ckerv on 2018/2/5.
 */

public class LogisticsDriverOrderAdapter extends MSClickableAdapter<LogisticsDriverOrderAdapter.LogisticsDriverOrderViewHolder>{
    private Context mContext;
    private List<LogisticsDriverOrderItem> mList;

    private OnItemSubviewClickListener<LogisticsDriverOrderItem> subviewClickListener;


    public OnItemSubviewClickListener<LogisticsDriverOrderItem> getSubviewClickListener() {
        return subviewClickListener;
    }

    public void setSubviewClickListener(OnItemSubviewClickListener<LogisticsDriverOrderItem> subviewClickListener) {
        this.subviewClickListener = subviewClickListener;
    }

    public LogisticsDriverOrderAdapter(Context context, List<LogisticsDriverOrderItem> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public void onBindVH(LogisticsDriverOrderViewHolder holder, int position) {

        LogisticsDriverOrderItem bean = mList.get(position);

        setOrderStatus(holder, bean);

        setNetInform(holder, bean);

        setCargoCount(holder, bean);

        setTimeInform(holder, bean);

        setAddrInform(holder, bean);

        setBottomLayout(holder, position, bean);
    }

    private void setBottomLayout(LogisticsDriverOrderViewHolder holder, final int pos, final LogisticsDriverOrderItem bean) {
        if(Integer.valueOf(bean.getDriverStatus()) == LogisticsDriverConstants.TYPE_TO_CONFIRM) {
            if(bean.getIsConfirm().equals("1")) { //网点已确认
                holder.rlBottom.setVisibility(View.VISIBLE);
                holder.btnBottom.setText("确认收货");
            } else {
                holder.rlBottom.setVisibility(View.GONE);
            }
        } else if(Integer.valueOf(bean.getDriverStatus()) == LogisticsDriverConstants.TYPE_TO_DELEVER) {
            holder.rlBottom.setVisibility(View.VISIBLE);
            holder.btnBottom.setText("确认送达");
        } else {
            holder.rlBottom.setVisibility(View.GONE);
        }
        holder.btnBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(subviewClickListener != null) {
                    subviewClickListener.onClick(v, pos, bean);
                }
            }
        });
    }

    private void setAddrInform(LogisticsDriverOrderViewHolder holder, LogisticsDriverOrderItem bean) {
        int type = Integer.valueOf(bean.getDriverStatus());
        switch (type)  {
            case 2:
                holder.tvAddress.setText("网点地址:" + bean.getNetworkAdress());
                break;
            case 3:
                holder.tvAddress.setText("仓库地址:" + bean.getWarehouseAdress());
                break;
            default:
                holder.tvAddress.setText("收货仓库:" + bean.getWarehouseAdress());
                break;
        }
    }

    private void setTimeInform(LogisticsDriverOrderViewHolder holder, LogisticsDriverOrderItem bean) {
        int type = Integer.valueOf(bean.getDriverStatus());
        switch (type)  {
            case 2:
                holder.tvTime.setText("揽货时间:" + bean.getUpdateTime());
                break;
            case 3:
                holder.tvTime.setText("派送时间:" + bean.getUpdateTime());
                break;
            default:
                holder.tvTime.setText("收货时间:" + bean.getUpdateTime());
                break;
        }
    }

    private void setCargoCount(LogisticsDriverOrderViewHolder holder, LogisticsDriverOrderItem bean) {
        holder.tvCargoCount.setText(bean.getCount() + "件");
    }

    private void setNetInform(LogisticsDriverOrderViewHolder holder, LogisticsDriverOrderItem bean) {
        holder.tvNetName.setText(bean.getNetworkName());
        holder.tvDistance.setText(String.valueOf(bean.getDistanceDriver() + "m"));
    }

    private void setOrderStatus(LogisticsDriverOrderViewHolder holder, LogisticsDriverOrderItem bean) {
        holder.tvCreateTime.setText(bean.getTakeorderTime());
        int type = Integer.valueOf(bean.getDriverStatus());
        switch (type)  {
            case 2:
                holder.rlStatus.setBackground(mContext.getDrawable(R.drawable.order_green));
                holder.tvStatus.setText("待确认");
                break;
            case 3:
                holder.rlStatus.setBackground(mContext.getDrawable(R.drawable.order_purple));
                holder.tvStatus.setText("待送达");
                break;
            default:
                holder.rlStatus.setBackground(mContext.getDrawable(R.drawable.order_blue));
                holder.tvStatus.setText("已完成");
                break;
        }
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

        TextView tvCreateTime;
        RelativeLayout rlStatus;
        TextView tvStatus;

        TextView tvNetName;
        TextView tvDistance;

        TextView tvCargoCount;

        TextView tvAddress;
        TextView tvTime;

        RelativeLayout rlBottom;
        Button btnBottom;


        public LogisticsDriverOrderViewHolder(View itemView) {
            super(itemView);

            tvCreateTime = (TextView) itemView.findViewById(R.id.item_tv_create_time);
            rlStatus = (RelativeLayout) itemView.findViewById(R.id.item_rl_status);
            tvStatus = (TextView) itemView.findViewById(R.id.item_tv_status);

            tvNetName = (TextView) itemView.findViewById(R.id.item_tv_net_name);
            tvDistance = (TextView) itemView.findViewById(R.id.item_tv_distance);

            tvCargoCount = (TextView) itemView.findViewById(R.id.item_tv_cargo_count);

            tvAddress = (TextView) itemView.findViewById(R.id.item_tv_address);
            tvTime = (TextView) itemView.findViewById(R.id.item_tv_time);

            rlBottom = (RelativeLayout) itemView.findViewById(R.id.item_rl_bottom);
            btnBottom = (Button) itemView.findViewById(R.id.item_btn_bottom);
        }
    }
}
