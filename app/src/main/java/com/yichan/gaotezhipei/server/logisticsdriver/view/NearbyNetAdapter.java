package com.yichan.gaotezhipei.server.logisticsdriver.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.changelcai.mothership.view.recycler.MSClickableAdapter;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.server.logisticsdriver.entity.NearbyNetItem;

import java.util.List;

/**
 * Created by ckerv on 2018/2/5.
 */

public class NearbyNetAdapter extends MSClickableAdapter<NearbyNetAdapter.NearbyNetViewHolder> {
    private Context mContext;
    private List<NearbyNetItem> mList;


    public NearbyNetAdapter(Context context, List<NearbyNetItem> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public void onBindVH(NearbyNetViewHolder holder, int position) {

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

        public NearbyNetViewHolder(View itemView) {
            super(itemView);
        }
    }
}
