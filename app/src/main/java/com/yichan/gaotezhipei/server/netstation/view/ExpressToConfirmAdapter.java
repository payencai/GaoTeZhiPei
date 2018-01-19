package com.yichan.gaotezhipei.server.netstation.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.changelcai.mothership.view.recycler.MSClickableAdapter;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.server.netstation.entity.ExpressToConfirmItem;

import java.util.List;

/**
 * Created by ckerv on 2018/1/19.
 */

public class ExpressToConfirmAdapter extends MSClickableAdapter<ExpressToConfirmAdapter.ExpressToConfirmViewHolder> {
    private android.content.Context mContext;
    private List<ExpressToConfirmItem> mList;


    public ExpressToConfirmAdapter(Context context, List<ExpressToConfirmItem> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public void onBindVH(ExpressToConfirmViewHolder holder, int position) {

    }

    @Override
    public ExpressToConfirmViewHolder onCreateVH(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_express_to_confirm, parent, false);
        return new ExpressToConfirmViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ExpressToConfirmViewHolder extends RecyclerView.ViewHolder {

        public ExpressToConfirmViewHolder(View itemView) {
            super(itemView);
        }
    }
}
