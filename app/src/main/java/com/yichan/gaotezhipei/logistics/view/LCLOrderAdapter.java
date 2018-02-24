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
import com.yichan.gaotezhipei.common.UserManager;
import com.yichan.gaotezhipei.logistics.constant.LogisticsContants;
import com.yichan.gaotezhipei.logistics.entity.LCLOrderPage;

import java.util.List;

/**
 * Created by ckerv on 2018/1/12.
 */

public class LCLOrderAdapter extends MSClickableAdapter<LCLOrderAdapter.LCLOrderViewHolder> {

    private Context mContext;
    private List<LCLOrderPage.BeanListBean> mList;

    public LCLOrderAdapter(Context context, List<LCLOrderPage.BeanListBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    private OnItemSubviewClickListener<LCLOrderPage.BeanListBean> mOnItemSubviewClickListener;

    public void setOnItemSubviewClickListener(OnItemSubviewClickListener<LCLOrderPage.BeanListBean> listener) {
        this.mOnItemSubviewClickListener = listener;
    }


    @Override
    public LCLOrderViewHolder onCreateVH(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_lcl_order_common, parent, false);
        return new LCLOrderViewHolder(itemView);
    }


    @Override
    public void onBindVH(LCLOrderViewHolder holder, int position) {
        LCLOrderPage.BeanListBean bean = mList.get(position);

        setOrderStatus(holder, bean);

        setAddressInform(holder, bean);

        setCargoInform(holder, bean);

        setBottomLayout(holder, position,  bean);
    }

    private void setOrderStatus(LCLOrderViewHolder holder, LCLOrderPage.BeanListBean bean) {
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

    private void setAddressInform(LCLOrderViewHolder holder, LCLOrderPage.BeanListBean bean) {
        holder.tvMailDistrict.setText(bean.getAddress().getArea());
        holder.tvMailProvinceCity.setText(bean.getAddress().getProvince() + " " + bean.getAddress().getCity());
        holder.tvDistance.setText(String.format("%.2f", Double.valueOf(bean.getDistance()) / 1000) + "km");
        holder.tvPickDistrict.setText(bean.getConsigneeArea());
        holder.tvPickProvinceCity.setText(bean.getConsigneeProvince() + " " + bean.getConsigneeCity());
    }

    private void setCargoInform(LCLOrderViewHolder holder, LCLOrderPage.BeanListBean bean) {
        holder.tvCargoName.setText(bean.getArticleName() + ":");
        holder.tvCargoInform.setText(bean.getNum() + "件 " + bean.getWeight() + "kg " + bean.getVolume() + "m³");

        holder.tvGetCargoTime.setText(bean.getAnticipantTime());
        holder.tvGetCargoAddr.setText(bean.getPickupAddress());
    }

    private void setBottomLayout(LCLOrderViewHolder holder, final int pos, final LCLOrderPage.BeanListBean bean) {
        int type = Integer.valueOf(bean.getType());
        if(UserManager.getInstance(mContext).isDemand()) {//需求方
//            if(type == LogisticsContants.TYPE_LCL_ORDER_TO_RECEIVE) {
//                holder.rlBottom.setVisibility(View.VISIBLE);
//                holder.btnRight.setVisibility(View.GONE);
//                holder.btnLeft.setText("取消订单");
//            }
            if (type == LogisticsContants.TYPE_LCL_ORDER_TO_GET_CARGO) {//待接货
                holder.rlBottom.setVisibility(View.VISIBLE);
            } else if (type == LogisticsContants.TYPE_LCL_ORDER_TO_RECEIVE_CARGO) {//待收货
                holder.rlBottom.setVisibility(View.VISIBLE);
                holder.btnRight.setVisibility(View.GONE);
                holder.btnLeft.setText("查看物流");
            } else if (type == LogisticsContants.TYPE_LCL_ORDER_TO_CONFIRM) {//待签收
                holder.rlBottom.setVisibility(View.VISIBLE);
                holder.btnLeft.setVisibility(View.GONE);
                holder.btnRight.setText("确认签收");
            } else {//已完成
                holder.rlBottom.setVisibility(View.GONE);
            }
        } else  {
            if (type == LogisticsContants.TYPE_LCL_ORDER_TO_GET_CARGO) {//待接货
                holder.rlBottom.setVisibility(View.VISIBLE);
                holder.btnRight.setText("确认接货");
            } else if (type == LogisticsContants.TYPE_LCL_ORDER_TO_RECEIVE_CARGO) {//待送货
                holder.rlBottom.setVisibility(View.VISIBLE);
                holder.btnLeft.setVisibility(View.GONE);
                holder.btnRight.setText("确认送达");
            } else {
                holder.rlBottom.setVisibility(View.GONE);
            }
        }
        holder.btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemSubviewClickListener != null) {
                    mOnItemSubviewClickListener.onClick(v, pos, bean);
                }
            }
        });

        holder.btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemSubviewClickListener != null) {
                    mOnItemSubviewClickListener.onClick(v, pos, bean);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    class LCLOrderViewHolder extends RecyclerView.ViewHolder {

        TextView tvOrderTime;
        RelativeLayout rlStatus;
        TextView tvStatus;

        TextView tvMailDistrict;
        TextView tvMailProvinceCity;
        TextView tvDistance;
        TextView tvPickDistrict;
        TextView tvPickProvinceCity;

        TextView tvCargoName;
        TextView tvCargoInform;

        TextView tvGetCargoTime;

        TextView tvGetCargoAddr;

        RelativeLayout rlBottom;
        Button btnLeft;
        Button btnRight;



        public LCLOrderViewHolder(View itemView) {
            super(itemView);

            tvOrderTime = (TextView) itemView.findViewById(R.id.item_tv_order_time);
            rlStatus = (RelativeLayout) itemView.findViewById(R.id.item_rl_status);
            tvStatus = (TextView) itemView.findViewById(R.id.item_tv_status);

            tvMailDistrict = (TextView) itemView.findViewById(R.id.item_tv_mail_district);
            tvMailProvinceCity = (TextView) itemView.findViewById(R.id.item_tv_mail_province_city);
            tvDistance = (TextView) itemView.findViewById(R.id.item_tv_distance);
            tvPickDistrict = (TextView) itemView.findViewById(R.id.item_tv_pick_district);
            tvPickProvinceCity = (TextView) itemView.findViewById(R.id.item_tv_pick_province_city);

            tvCargoName = (TextView) itemView.findViewById(R.id.item_tv_cargo_name);
            tvCargoInform = (TextView) itemView.findViewById(R.id.item_tv_cargo_inform);

            tvGetCargoTime = (TextView) itemView.findViewById(R.id.item_tv_get_cargo_time);
            tvGetCargoAddr = (TextView) itemView.findViewById(R.id.item_tv_get_cargo_addr);

            rlBottom = (RelativeLayout) itemView.findViewById(R.id.item_rl_bottom);
            btnLeft = (Button) itemView.findViewById(R.id.item_btn_left);
            btnRight = (Button) itemView.findViewById(R.id.item_btn_right);
        }
    }
}
