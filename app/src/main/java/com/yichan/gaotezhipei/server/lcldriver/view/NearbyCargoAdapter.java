package com.yichan.gaotezhipei.server.lcldriver.view;

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
import com.yichan.gaotezhipei.logistics.entity.LCLOrderPage;

import java.util.List;

/**
 * Created by ckerv on 2018/1/19.
 */

public class NearbyCargoAdapter extends MSClickableAdapter<NearbyCargoAdapter.NearbyCargoViewHolder> {


    private Context mContext;
    private List<LCLOrderPage.BeanListBean> mList;
    private OnItemSubviewClickListener<LCLOrderPage.BeanListBean> mOnItemSubviewClickListener;

    public void setOnItemSubviewClickListener(OnItemSubviewClickListener<LCLOrderPage.BeanListBean> listener) {
        this.mOnItemSubviewClickListener = listener;
    }


    public NearbyCargoAdapter(Context context, List<LCLOrderPage.BeanListBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public void onBindVH(NearbyCargoViewHolder holder, final int position) {
        final LCLOrderPage.BeanListBean bean = mList.get(position);

        setOrderStatus(holder, bean);

        setAddressInform(holder, bean);

        setCargoInform(holder, bean);

        holder.rlRob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemSubviewClickListener != null) {
                    mOnItemSubviewClickListener.onClick(v, position, bean);
                }
            }
        });
    }

    private void setOrderStatus(NearbyCargoViewHolder holder, LCLOrderPage.BeanListBean bean) {
        holder.tvOrderTime.setText(bean.getOrderTime());
    }

    private void setAddressInform(NearbyCargoViewHolder holder, LCLOrderPage.BeanListBean bean) {
        holder.tvMailDistrict.setText(bean.getAddress().getArea());
        holder.tvMailProvinceCity.setText(bean.getAddress().getProvince() + " " + bean.getAddress().getCity());
        holder.tvDistance.setText(String.format("%.2f", Double.valueOf(bean.getDistance()) / 1000) + "km");
        holder.tvPickDistrict.setText(bean.getConsigneeArea());
        holder.tvPickProvinceCity.setText(bean.getConsigneeProvince() + " " + bean.getConsigneeCity());
    }

    private void setCargoInform(NearbyCargoViewHolder holder, LCLOrderPage.BeanListBean bean) {
        holder.tvCargoName.setText(bean.getArticleName() + ":");
        holder.tvCargoInform.setText(bean.getNum() + "件 " + bean.getWeight() + "kg " + bean.getVolume() + "m³");
        holder.tvCar.setText(bean.getAnticipantCar());
        holder.tvGetCargoTime.setText(bean.getAnticipantTime());
        holder.tvGetCargoAddr.setText(bean.getPickupAddress());
    }

    @Override
    public NearbyCargoViewHolder onCreateVH(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_nearby_cargo, parent, false);
        return new NearbyCargoViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class NearbyCargoViewHolder extends RecyclerView.ViewHolder {

        TextView tvOrderTime;


        TextView tvMailDistrict;
        TextView tvMailProvinceCity;
        TextView tvDistance;
        TextView tvPickDistrict;
        TextView tvPickProvinceCity;

        TextView tvCargoName;
        TextView tvCargoInform;

        TextView tvCar;
        TextView tvGetCargoTime;

        TextView tvGetCargoAddr;

        RelativeLayout rlRob;


        public NearbyCargoViewHolder(View itemView) {
            super(itemView);

            tvOrderTime = (TextView) itemView.findViewById(R.id.item_tv_order_time);


            tvMailDistrict = (TextView) itemView.findViewById(R.id.item_tv_mail_district);
            tvMailProvinceCity = (TextView) itemView.findViewById(R.id.item_tv_mail_province_city);
            tvDistance = (TextView) itemView.findViewById(R.id.item_tv_distance);
            tvPickDistrict = (TextView) itemView.findViewById(R.id.item_tv_pick_district);
            tvPickProvinceCity = (TextView) itemView.findViewById(R.id.item_tv_pick_province_city);

            tvCargoName = (TextView) itemView.findViewById(R.id.item_tv_cargo_name);
            tvCargoInform = (TextView) itemView.findViewById(R.id.item_tv_cargo_inform);

            tvCar = (TextView) itemView.findViewById(R.id.item_tv_need_car);
            tvGetCargoTime = (TextView) itemView.findViewById(R.id.item_tv_get_cargo_time);
            tvGetCargoAddr = (TextView) itemView.findViewById(R.id.item_tv_get_cargo_addr);

            rlRob = (RelativeLayout) itemView.findViewById(R.id.nearby_cargo_rl_rob);
        }
    }
}
