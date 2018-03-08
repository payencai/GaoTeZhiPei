package com.yichan.gaotezhipei.server.logisticsdriver.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.changelcai.mothership.view.recycler.MSClickableAdapter;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.server.logisticsdriver.entity.NetOrderItem;

import java.util.List;

/**
 * Created by ckerv on 2018/2/5.
 */

public class NearbyNetAdapter extends MSClickableAdapter<NearbyNetAdapter.NearbyNetViewHolder> {
    private Context mContext;
    private List<NetOrderItem> mList;


    public NearbyNetAdapter(Context context, List<NetOrderItem> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public void onBindVH(NearbyNetViewHolder holder, int position) {
        holder.tvName.setText(mList.get(position).getName());
        holder.tvAddress.setText(mList.get(position).getAdress());
        holder.tvDistance.setText(String.valueOf(mList.get(position).getDistance()) + "km");
    }

    @Override
    public NearbyNetViewHolder onCreateVH(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_nearyby_net, parent, false);
        return new NearbyNetViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class NearbyNetViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvAddress;
        TextView tvDistance;

        public NearbyNetViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.item_tv_name);
            tvAddress = (TextView) itemView.findViewById(R.id.item_tv_address);
            tvDistance = (TextView) itemView.findViewById(R.id.item_tv_distance);
        }
    }
}
