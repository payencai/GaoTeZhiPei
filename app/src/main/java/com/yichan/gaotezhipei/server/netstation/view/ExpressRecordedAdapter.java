package com.yichan.gaotezhipei.server.netstation.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.changelcai.mothership.view.recycler.MSClickableAdapter;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.server.netstation.entity.ExpressRecordedItem;

import java.util.List;

/**
 * Created by ckerv on 2018/1/19.
 */

public class ExpressRecordedAdapter extends MSClickableAdapter<ExpressRecordedAdapter.ExpressRecordedViewHolder> {

    private android.content.Context mContext;
    private List<ExpressRecordedItem> mList;


    public ExpressRecordedAdapter(Context context, List<ExpressRecordedItem> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public void onBindVH(ExpressRecordedViewHolder holder, int position) {

    }

    @Override
    public ExpressRecordedViewHolder onCreateVH(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_express_record, parent, false);
        return new ExpressRecordedViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ExpressRecordedViewHolder extends RecyclerView.ViewHolder {

        public ExpressRecordedViewHolder(View itemView) {
            super(itemView);
        }
    }
}
