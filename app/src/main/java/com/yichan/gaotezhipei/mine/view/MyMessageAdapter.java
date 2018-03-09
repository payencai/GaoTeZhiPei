package com.yichan.gaotezhipei.mine.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.changelcai.mothership.view.recycler.MSClickableAdapter;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.common.UserManager;
import com.yichan.gaotezhipei.logistics.constant.LogisticsContants;
import com.yichan.gaotezhipei.logistics.entity.LCLOrderPage;
import com.yichan.gaotezhipei.logistics.entity.LogisticsOrderPage;
import com.yichan.gaotezhipei.logistics.view.LCLOrderAdapter;
import com.yichan.gaotezhipei.logistics.view.LogisticOrderAdapter;
import com.yichan.gaotezhipei.mine.entity.MyMessageItem;

import java.util.List;

/**
 * Created by ckerv on 2018/1/15.
 */

public class MyMessageAdapter extends MSClickableAdapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<MyMessageItem> mList;

    public MyMessageAdapter(Context context, List<MyMessageItem> list) {
        this.mContext = context;
        this.mList = list;
    }



    @Override
    public RecyclerView.ViewHolder onCreateVH(ViewGroup parent, int viewType) {
        if(viewType == 1) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_lcl_order_common, parent, false);
            return new LCLOrderAdapter.LCLOrderViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_logistics_order_common, parent, false);
            return new LogisticOrderAdapter.LogisticOrderViewHolder(itemView);
        }
    }


    @Override
    public void onBindVH(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == 1) {
            onBindViewLCLOrder((LCLOrderAdapter.LCLOrderViewHolder)holder, (LCLOrderPage.BeanListBean)mList.get(position).getOrder());
        } else {
            onBindViewLogisOrder((LogisticOrderAdapter.LogisticOrderViewHolder)holder, position, (LogisticsOrderPage.ListBean) mList.get(position).getOrder());
        }
    }

    private void onBindViewLCLOrder(LCLOrderAdapter.LCLOrderViewHolder holder, LCLOrderPage.BeanListBean bean) {
        setLCLOrderStatus(holder, bean);

        setLCLAddressInform(holder, bean);

        setCargoInform(holder, bean);

        setBottomLayout(holder, bean);
    }

    private void onBindViewLogisOrder(LogisticOrderAdapter.LogisticOrderViewHolder holder, int position, LogisticsOrderPage.ListBean bean) {
        setOrderStatus(holder, position, bean);
        setAddressInform(holder, bean);
        setOneLine(holder, position, bean);
        setTwoLine(holder, bean);
        setThreeLine(holder, bean);
        setFourLine(holder, bean);
        setLocationView(holder, position, bean);
        setBottomView(holder, bean);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getType();
    }


    private void setLCLOrderStatus(LCLOrderAdapter.LCLOrderViewHolder holder, LCLOrderPage.BeanListBean bean) {
        holder.tvOrderTime.setText(bean.getOrderTime());
        int type = Integer.valueOf(bean.getType());
        switch (type) {
            case LogisticsContants.TYPE_LCL_ORDER_TO_RECEIVE:
                holder.rlStatus.setBackground(mContext.getDrawable(R.drawable.order_green));
                holder.tvStatus.setText("待接单");
                break;
            case LogisticsContants.TYPE_LCL_ORDER_TO_GET_CARGO:
                holder.rlStatus.setBackground(mContext.getDrawable(R.drawable.order_purple));
//                if(UserManager.getInstance(mContext).isDemand()) {
//                    holder.tvStatus.setText("待接货");
//                } else {
//                    holder.tvStatus.setText("待取货");
//                }
                holder.tvStatus.setText("待接货");
                break;
            case LogisticsContants.TYPE_LCL_ORDER_TO_RECEIVE_CARGO:
                holder.rlStatus.setBackground(mContext.getDrawable(R.drawable.order_purple));
                if(UserManager.getInstance(mContext).isDemand()) {
                    holder.tvStatus.setText("待收货");
                } else {
                    holder.tvStatus.setText("待送货");
                }
                break;
            case LogisticsContants.TYPE_LCL_ORDER_TO_CONFIRM:
                holder.rlStatus.setBackground(mContext.getDrawable(R.drawable.order_orangle));
                holder.tvStatus.setText("待签收");
                break;
            case LogisticsContants.TYPE_LCL_ORDER_TO_FINISH:
                holder.rlStatus.setBackground(mContext.getDrawable(R.drawable.order_blue));
                holder.tvStatus.setText("已完成");
                break;
            default:
                break;
        }
    }

    private void setLCLAddressInform(LCLOrderAdapter.LCLOrderViewHolder holder, LCLOrderPage.BeanListBean bean) {
        holder.tvMailDistrict.setText(bean.getAddress().getArea());
        holder.tvMailProvinceCity.setText(bean.getAddress().getProvince() + " " + bean.getAddress().getCity());
        holder.tvDistance.setText(String.format("%.2f", Double.valueOf(bean.getDistance())) + "km");
        holder.tvPickDistrict.setText(bean.getConsigneeArea());
        holder.tvPickProvinceCity.setText(bean.getConsigneeProvince() + " " + bean.getConsigneeCity());
    }

    private void setCargoInform(LCLOrderAdapter.LCLOrderViewHolder holder, LCLOrderPage.BeanListBean bean) {
        holder.tvCargoName.setText(bean.getArticleName() + ":");
        holder.tvCargoInform.setText(bean.getNum() + "件 " + bean.getWeight() + "kg " + bean.getVolume() + "m³");

        holder.tvGetCargoTime.setText(bean.getAnticipantTime());
        holder.tvGetCargoAddr.setText(bean.getPickupAddress());
    }

    private void setBottomLayout(LCLOrderAdapter.LCLOrderViewHolder holder, final LCLOrderPage.BeanListBean bean) {
        holder.rlBottom.setVisibility(View.GONE);

    }


    private void setOrderStatus(LogisticOrderAdapter.LogisticOrderViewHolder holder, int pos, LogisticsOrderPage.ListBean bean) {
        holder.tvOrderTime.setText(bean.getCreateTime());
        switch (Integer.valueOf(bean.getStatus())) {
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

    private void setAddressInform(LogisticOrderAdapter.LogisticOrderViewHolder holder, LogisticsOrderPage.ListBean bean) {
        holder.tvMailDistrict.setText(bean.getAdressFromDistrict());
        holder.tvMailProvinceCity.setText(bean.getAdressFromProvince() + " " + bean.getAdressFromCity());
        holder.tvDistance.setText(String.format("%.2f", Double.valueOf(bean.getDistanceTotal())) + "km");
        holder.tvPickDistrict.setText(bean.getAdressToDistrict());
        holder.tvPickProvinceCity.setText(bean.getAdressToProvince() + " " + bean.getAdressToCity());
    }

    private void setOneLine(LogisticOrderAdapter.LogisticOrderViewHolder holder, int pos, LogisticsOrderPage.ListBean bean) {
        switch (Integer.valueOf(bean.getStatus())) {
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

    private void setTwoLine(LogisticOrderAdapter.LogisticOrderViewHolder holder, LogisticsOrderPage.ListBean bean) {
        holder.tvTwo.setText("物流公司:" + bean.getLogisticsCompanyName());
    }

    private void setThreeLine(LogisticOrderAdapter.LogisticOrderViewHolder holder, LogisticsOrderPage.ListBean bean) {
        holder.tvThree.setText("物流单号:" + bean.getLogisticsCompanyId());
    }

    private void setFourLine(LogisticOrderAdapter.LogisticOrderViewHolder holder, LogisticsOrderPage.ListBean bean) {
        holder.tvFour.setText(bean.getGoodsName() + ":" + bean.getGoodsQuantity()  + "件  " + bean.getGoodsMass() + "kg  " + bean.getGoodsSize() + "m³");
    }


    private void setLocationView(LogisticOrderAdapter.LogisticOrderViewHolder holder, int pos, LogisticsOrderPage.ListBean bean) {
//        if(getItemViewType(pos) == LogisticsContants.TYPE_LOG_ORDER_TO_WAREHOUSE) {
//            holder.rlLoc.setVisibility(View.VISIBLE);
//        } else {
            holder.rlLoc.setVisibility(View.GONE);
//        }
    }

    private void setBottomView(LogisticOrderAdapter.LogisticOrderViewHolder holder, final LogisticsOrderPage.ListBean bean) {
        holder.rlBottom.setVisibility(View.GONE);
    }
}
