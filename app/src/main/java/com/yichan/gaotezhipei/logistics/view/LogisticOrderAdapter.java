package com.yichan.gaotezhipei.logistics.view;

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
import com.yichan.gaotezhipei.logistics.constant.LogisticsContants;
import com.yichan.gaotezhipei.logistics.entity.LogisticsOrderPage;

import java.util.List;

/**
 * Created by ckerv on 2018/1/15.
 */

public class LogisticOrderAdapter extends MSClickableAdapter<LogisticOrderAdapter.LogisticOrderViewHolder> {


    private Context mContext;
    private List<LogisticsOrderPage.ListBean> mList;

    private OnItemSubviewClickListener<LogisticsOrderPage.ListBean> subviewClickListener;

    public OnItemSubviewClickListener<LogisticsOrderPage.ListBean> getSubviewClickListener() {
        return subviewClickListener;
    }

    public void setSubviewClickListener(OnItemSubviewClickListener<LogisticsOrderPage.ListBean> subviewClickListener) {
        this.subviewClickListener = subviewClickListener;
    }

    public LogisticOrderAdapter(Context context, List<LogisticsOrderPage.ListBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    

    @Override
    public LogisticOrderViewHolder onCreateVH(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_logistics_order_common, parent, false);
        return new LogisticOrderViewHolder(itemView);
    }
    

    @Override
    public void onBindVH(LogisticOrderViewHolder holder, int position) {

        LogisticsOrderPage.ListBean bean = mList.get(position);
        
        setOrderStatus(holder, position, bean);
        setAddressInform(holder, bean);
        setOneLine(holder, position, bean);
        setTwoLine(holder, bean);
        setThreeLine(holder, bean);
        setFourLine(holder, bean);
        setLocationView(holder, position, bean);
        setBottomView(holder, position, bean);
    }

    private void setOrderStatus(LogisticOrderViewHolder holder, int pos, LogisticsOrderPage.ListBean bean) {
        holder.tvOrderTime.setText(bean.getCreateTime());
        switch (getItemViewType(pos)) {
            case LogisticsContants.TYPE_LOG_ORDER_TO_RECEIVER:
                holder.rlStatus.setBackground(mContext.getDrawable(R.drawable.order_green));
                holder.tvStatus.setText("待接单");
                break;
            case LogisticsContants.TYPE_LOG_ORDER_TO_GET_CARGO:
                holder.rlStatus.setBackground(mContext.getDrawable(R.drawable.order_red));
                holder.tvStatus.setText("待接货");
                break;
            case LogisticsContants.TYPE_LOG_ORDER_TO_WAREHOUSE:
                holder.rlStatus.setBackground(mContext.getDrawable(R.drawable.order_purple));
                holder.tvStatus.setText("待发往");
                break;
            case LogisticsContants.TYPE_LOG_ORDER_TRANSITING:
                holder.rlStatus.setBackground(mContext.getDrawable(R.drawable.order_light_blue));
                holder.tvStatus.setText("运输中");
                break;
            case LogisticsContants.TYPE_LOG_ORDER_TO_CONFIRM:
                holder.rlStatus.setBackground(mContext.getDrawable(R.drawable.order_orangle));
                holder.tvStatus.setText("待签收");
                break;
            case LogisticsContants.TYPE_LOG_ORDER_FINISH:
                holder.rlStatus.setBackground(mContext.getDrawable(R.drawable.order_blue));
                holder.tvStatus.setText("已完成");
                break;
            default:
                break;
        }
    }

    private void setAddressInform(LogisticOrderViewHolder holder, LogisticsOrderPage.ListBean bean) {
        holder.tvMailDistrict.setText(bean.getAdressFromDistrict());
        holder.tvMailProvinceCity.setText(bean.getAdressFromProvince() + " " + bean.getAdressFromCity());
        holder.tvDistance.setText(String.format("%.2f", Double.valueOf(bean.getDistanceTotal())) + "km");
        holder.tvPickDistrict.setText(bean.getAdressToDistrict());
        holder.tvPickProvinceCity.setText(bean.getAdressToProvince() + " " + bean.getAdressToCity());
    }

    private void setOneLine(LogisticOrderViewHolder holder, int pos, LogisticsOrderPage.ListBean bean) {
        switch (getItemViewType(pos)) {
            case LogisticsContants.TYPE_LOG_ORDER_TO_RECEIVER:
                holder.tvOne.setText("快件录入时间:" + bean.getCreateTime());
                break;
            case LogisticsContants.TYPE_LOG_ORDER_TO_GET_CARGO:
                holder.tvOne.setText("服务录入网点:" + bean.getNetworkName());
                break;
            case LogisticsContants.TYPE_LOG_ORDER_TO_WAREHOUSE:
                holder.tvOne.setText("司机接货时间:" + bean.getPickTime());
                break;
            case LogisticsContants.TYPE_LOG_ORDER_TRANSITING:
                holder.tvOne.setText("收货仓库:" + bean.getWarehouseName());
                break;
            case LogisticsContants.TYPE_LOG_ORDER_TO_CONFIRM:
                holder.tvOne.setText("到达仓库时间:" + bean.getReachwarehouseTime());
                break;
            case LogisticsContants.TYPE_LOG_ORDER_FINISH:
                holder.tvOne.setText("用户签收时间:" + bean.getSignTime());
                break;
            default:
                break;

        }
    }

    private void setTwoLine(LogisticOrderViewHolder holder, LogisticsOrderPage.ListBean bean) {
        holder.tvTwo.setText("物流公司:" + bean.getLogisticsCompanyName());
    }

    private void setThreeLine(LogisticOrderViewHolder holder, LogisticsOrderPage.ListBean bean) {
        holder.tvThree.setText("物流单号:" + bean.getLogisticsCompanyId());
    }

    private void setFourLine(LogisticOrderViewHolder holder, LogisticsOrderPage.ListBean bean) {
        holder.tvFour.setText(bean.getGoodsName() + ":" + bean.getGoodsQuantity()  + "件  " + bean.getGoodsMass() + "kg  " + bean.getGoodsSize() + "m³");
    }


    private void setLocationView(LogisticOrderViewHolder holder, int pos, LogisticsOrderPage.ListBean bean) {
//        if(getItemViewType(pos) == LogisticsContants.TYPE_LOG_ORDER_TO_WAREHOUSE) {
//            holder.rlLoc.setVisibility(View.VISIBLE);
//        } else {
//            holder.rlLoc.setVisibility(View.GONE);
//        }
        //TODO 司机位置 功能待开发
        holder.rlLoc.setVisibility(View.GONE);
    }

    private void setBottomView(LogisticOrderViewHolder holder, final int pos, final LogisticsOrderPage.ListBean bean) {
        if(getItemViewType(pos) == LogisticsContants.TYPE_LOG_ORDER_TO_CONFIRM) {
            holder.rlBottom.setVisibility(View.VISIBLE);
            holder.btnRight.setVisibility(View.VISIBLE);
        } else {
            holder.rlBottom.setVisibility(View.VISIBLE);
            holder.btnRight.setVisibility(View.GONE);
        }

        holder.btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(subviewClickListener != null) {
                    subviewClickListener.onClick(v, pos, bean);
                }
            }
        });
        holder.btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(subviewClickListener != null) {
                    subviewClickListener.onClick(v, pos, bean);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class LogisticOrderViewHolder extends RecyclerView.ViewHolder {

        public TextView tvOrderTime;
        public RelativeLayout rlStatus;
        public TextView tvStatus;

        public TextView tvMailDistrict;
        public TextView tvMailProvinceCity;
        public TextView tvDistance;
        public TextView tvPickDistrict;
        public TextView tvPickProvinceCity;

        public RelativeLayout rlLoc;

        public TextView tvOne;
        public TextView tvTwo;
        public TextView tvThree;
        public TextView tvFour;

        public RelativeLayout rlBottom;
        public Button btnLeft;
        public Button btnRight;

        public LogisticOrderViewHolder(View itemView) {
            super(itemView);

            tvOrderTime = (TextView) itemView.findViewById(R.id.item_tv_order_time);
            rlStatus = (RelativeLayout) itemView.findViewById(R.id.item_rl_status);
            tvStatus = (TextView) itemView.findViewById(R.id.item_tv_status);

            tvMailDistrict = (TextView) itemView.findViewById(R.id.item_tv_mail_district);
            tvMailProvinceCity = (TextView) itemView.findViewById(R.id.item_tv_mail_province_city);
            tvDistance = (TextView) itemView.findViewById(R.id.item_tv_distance);
            tvPickDistrict = (TextView) itemView.findViewById(R.id.item_tv_pick_district);
            tvPickProvinceCity = (TextView) itemView.findViewById(R.id.item_tv_pick_province_city);

            rlLoc = (RelativeLayout) itemView.findViewById(R.id.item_rl_loc);

            tvOne = (TextView) itemView.findViewById(R.id.item_tv_one);
            tvTwo = (TextView) itemView.findViewById(R.id.item_tv_two);
            tvThree = (TextView) itemView.findViewById(R.id.item_tv_three);
            tvFour = (TextView) itemView.findViewById(R.id.item_tv_four);

            rlBottom = (RelativeLayout) itemView.findViewById(R.id.item_rl_bottom);
            btnLeft = (Button) itemView.findViewById(R.id.item_btn_left);
            btnRight = (Button) itemView.findViewById(R.id.item_btn_right);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return Integer.valueOf(mList.get(position).getStatus());
    }
}
