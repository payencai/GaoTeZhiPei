package com.yichan.gaotezhipei.agriculturalservice.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.changelcai.mothership.view.recycler.MSClickableAdapter;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.agriculturalservice.entity.AgriculturalShopItem;

import java.util.List;

/**
 * Created by ckerv on 2018/1/12.
 */

public class AgriculturalShopAdapter extends MSClickableAdapter<AgriculturalShopAdapter.AgriculturalShopViewHolder> {


    private Context mContext;
    private List<AgriculturalShopItem> mList;

    public AgriculturalShopAdapter(Context context, List<AgriculturalShopItem> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public void onBindVH(AgriculturalShopViewHolder holder, int position) {
        //holder.shopIconIv.
        holder.shopNameTv.setText(mList.get(position).getShopName());
        holder.shopPhoneTv.setText(mList.get(position).getShopPhone());
        holder.shopAddrTv.setText(mList.get(position).getShopAddr());
    }

    @Override
    public AgriculturalShopViewHolder onCreateVH(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_agricultural_shop, parent, false);
        return new AgriculturalShopViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class AgriculturalShopViewHolder extends RecyclerView.ViewHolder {

        ImageView shopIconIv;
        TextView shopNameTv;
        TextView shopAddrTv;
        TextView shopPhoneTv;

        public AgriculturalShopViewHolder(View itemView) {
            super(itemView);

            shopIconIv = (ImageView) itemView.findViewById(R.id.item_agri_iv_shopicon);
            shopNameTv = (TextView) itemView.findViewById(R.id.item_agri_tv_shopname);
            shopAddrTv = (TextView) itemView.findViewById(R.id.item_agri_tv_shopaddr);
            shopPhoneTv = (TextView) itemView.findViewById(R.id.item_agri_tv_shopphone);
        }
    }
}
