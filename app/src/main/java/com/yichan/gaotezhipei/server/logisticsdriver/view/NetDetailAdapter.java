package com.yichan.gaotezhipei.server.logisticsdriver.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.changelcai.mothership.view.recycler.MSClickableAdapter;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.common.util.EventBus;
import com.yichan.gaotezhipei.server.logisticsdriver.entity.NetDetailOrderPage;
import com.yichan.gaotezhipei.server.logisticsdriver.event.SelectOrderEvent;

import java.util.List;

/**
 * Created by ckerv on 2018/2/6.
 */

public class NetDetailAdapter extends MSClickableAdapter<NetDetailAdapter.NetDetailViewHolder>{

    private android.content.Context mContext;
    private List<NetDetailOrderPage.ListBean> mList;




    public NetDetailAdapter(Context context, List<NetDetailOrderPage.ListBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public void onBindVH(NetDetailViewHolder holder, int position) {

        NetDetailOrderPage.ListBean bean = mList.get(position);
        setOrderStatus(holder, bean);
        setAddressInform(holder, bean);
        setCargoInform(holder, bean);
        setMailInform(holder, bean);
        setPickInform(holder, bean);
    }

    private void setCargoInform(NetDetailViewHolder holder, NetDetailOrderPage.ListBean bean) {
        holder.tvCargoInform.setText("货物详情    " + bean.getGoodsName() + "   " + bean.getGoodsQuantity() +
                        "件  " + bean.getGoodsMass() + "kg  " + bean.getGoodsSize() + "m³");
    }


    private void setPickInform(NetDetailViewHolder holder, NetDetailOrderPage.ListBean bean) {
        holder.pickNamePhone.setText(bean.getNameTo() + "  " + bean.getReceiverTelnum());
        holder.pickAddress.setText(bean.getAdressTo());
    }

    private void setMailInform(NetDetailViewHolder holder, NetDetailOrderPage.ListBean bean) {
        holder.mailNamePhone.setText(bean.getNameFrom() + "  " + bean.getDemanderTelnum());
        holder.mailAddress.setText(bean.getAdressFrom());
    }

    private void setAddressInform(NetDetailViewHolder holder, NetDetailOrderPage.ListBean bean) {
        holder.tvMailDistrict.setText(bean.getAdressFromDistrict());
        holder.tvMailProvinceCity.setText(bean.getAdressFromProvince() + " " + bean.getAdressFromCity());
        holder.tvDistance.setText(String.format("%.2f", Double.valueOf(bean.getDistanceTotal()) / 1000) + "km");
        holder.tvPickDistrict.setText(bean.getAdressToDistrict());
        holder.tvPickProvinceCity.setText(bean.getAdressToProvince() + " " + bean.getAdressToCity());
    }

    private void setOrderStatus(NetDetailViewHolder holder, final NetDetailOrderPage.ListBean bean) {
        holder.tvNumber.setText("运单编号:" + bean.getOrderNumber());
        holder.cbSelect.setOnCheckedChangeListener(null);
        holder.cbSelect.setChecked(bean.isCheck());
        holder.cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    EventBus.getInstance().post(new SelectOrderEvent(true, bean.getId()));
                } else {
                    EventBus.getInstance().post(new SelectOrderEvent(false, bean.getId()));
                }

            }
        });

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

        CheckBox cbSelect;
        TextView tvNumber;

        TextView tvMailDistrict;
        TextView tvMailProvinceCity;
        TextView tvDistance;
        TextView tvPickDistrict;
        TextView tvPickProvinceCity;

        TextView tvCargoInform;

        TextView mailNamePhone;
        TextView mailAddress;

        TextView pickNamePhone;
        TextView pickAddress;

        public NetDetailViewHolder(View itemView) {
            super(itemView);

            cbSelect = (CheckBox) itemView.findViewById(R.id.item_cb_select);
            tvNumber = (TextView) itemView.findViewById(R.id.item_tv_number);

            tvMailDistrict = (TextView) itemView.findViewById(R.id.item_tv_mail_district);
            tvMailProvinceCity = (TextView) itemView.findViewById(R.id.item_tv_mail_province_city);
            tvDistance = (TextView) itemView.findViewById(R.id.item_tv_distance);
            tvPickDistrict = (TextView) itemView.findViewById(R.id.item_tv_pick_district);
            tvPickProvinceCity = (TextView) itemView.findViewById(R.id.item_tv_pick_province_city);

            tvCargoInform = (TextView) itemView.findViewById(R.id.item_tv_cargo_inform);

            mailNamePhone = (TextView) itemView.findViewById(R.id.item_tv_mail_name_phone);
            mailAddress = (TextView) itemView.findViewById(R.id.item_tv_mail_address);

            pickNamePhone = (TextView) itemView.findViewById(R.id.item_tv_pick_name_phone);
            pickAddress = (TextView) itemView.findViewById(R.id.item_tv_pick_address);
        }
    }
}
