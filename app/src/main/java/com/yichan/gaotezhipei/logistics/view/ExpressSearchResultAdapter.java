package com.yichan.gaotezhipei.logistics.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.logistics.entity.ExpressTraceItem;

import java.util.List;

/**
 * Created by Administrator on 2018/3/15 0015.
 */

public class ExpressSearchResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<ExpressTraceItem> mList;

    public ExpressSearchResultAdapter(Context context, List<ExpressTraceItem> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_express_search_result, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        ExpressTraceItem expressTraceItem = mList.get(position);
        viewHolder.tvTraceTime.setText(expressTraceItem.getAcceptTime());
        viewHolder.tvStatus.setText(expressTraceItem.getAcceptStation());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTraceTime;
        public TextView tvStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTraceTime = (TextView) itemView.findViewById(R.id.item_tv_trace_time);
            tvStatus = (TextView) itemView.findViewById(R.id.item_tv_status);
        }
    }
}
