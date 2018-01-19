package com.yichan.gaotezhipei.server.lcldriver.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.changelcai.mothership.view.recycler.MSClickableAdapter;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.server.lcldriver.entity.NearbyCargoItem;

import java.util.List;

/**
 * Created by ckerv on 2018/1/19.
 */

public class NearbyCargoAdapter extends MSClickableAdapter<NearbyCargoAdapter.NearbyCargoViewHolder>{


    private Context mContext;
    private List<NearbyCargoItem> mList;


    public NearbyCargoAdapter(Context context, List<NearbyCargoItem> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public void onBindVH(NearbyCargoViewHolder holder, int position) {

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

        public NearbyCargoViewHolder(View itemView) {
            super(itemView);
        }
    }
}
