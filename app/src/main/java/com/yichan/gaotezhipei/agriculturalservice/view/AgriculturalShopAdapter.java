package com.yichan.gaotezhipei.agriculturalservice.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.changelcai.mothership.view.recycler.MSClickableAdapter;
import com.ckev.chooseimagelibrary.base.img.assist.CommonImageLoader;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.agriculturalservice.entity.AgriculturalShopPage;
import com.yichan.gaotezhipei.base.listener.OnItemSubviewClickListener;

import java.util.List;

/**
 * Created by ckerv on 2018/1/12.
 */

public class AgriculturalShopAdapter extends MSClickableAdapter<AgriculturalShopAdapter.AgriculturalShopViewHolder> {


    private Context mContext;
    private List<AgriculturalShopPage.BeanListBean> mList;

    public OnItemSubviewClickListener<AgriculturalShopPage.BeanListBean> subviewClickListener;

    public OnItemSubviewClickListener<AgriculturalShopPage.BeanListBean> getSubviewClickListener() {
        return subviewClickListener;
    }

    public void setSubviewClickListener(OnItemSubviewClickListener<AgriculturalShopPage.BeanListBean> subviewClickListener) {
        this.subviewClickListener = subviewClickListener;
    }

    public AgriculturalShopAdapter(Context context, List<AgriculturalShopPage.BeanListBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public void onBindVH(AgriculturalShopViewHolder holder, final int position) {
        CommonImageLoader.displayImage(mList.get(position).getImage(), holder.shopIconIv, CommonImageLoader.NO_CACHE_OPTIONS);
        holder.shopNameTv.setText(mList.get(position).getName());
        holder.shopPhoneTv.setText(mList.get(position).getTelephone());
        holder.shopAddrTv.setText(mList.get(position).getAddress());
        if(subviewClickListener != null) {
            holder.dialBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    subviewClickListener.onClick(v, position, mList.get(position));
                }
            });
        }
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
        Button dialBtn;

        public AgriculturalShopViewHolder(View itemView) {
            super(itemView);

            shopIconIv = (ImageView) itemView.findViewById(R.id.item_agri_iv_shopicon);
            shopNameTv = (TextView) itemView.findViewById(R.id.item_agri_tv_shopname);
            shopAddrTv = (TextView) itemView.findViewById(R.id.item_agri_tv_shopaddr);
            shopPhoneTv = (TextView) itemView.findViewById(R.id.item_agri_tv_shopphone);
            dialBtn = (Button) itemView.findViewById(R.id.item_agri_btn_dial);
        }
    }
}
