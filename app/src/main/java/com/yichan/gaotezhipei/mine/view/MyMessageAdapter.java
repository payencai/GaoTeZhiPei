package com.yichan.gaotezhipei.mine.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.mine.entity.MyMessageItem;

import java.util.List;

/**
 * Created by ckerv on 2018/1/15.
 */

public class MyMessageAdapter extends RecyclerView.Adapter<MyMessageAdapter.MyMessageViewHolder>{

    private Context mContext;
    private List<MyMessageItem> mList;

    public MyMessageAdapter(Context context, List<MyMessageItem> list) {
        this.mContext = context;
        this.mList = list;
    }


    @Override
    public MyMessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_my_message, parent, false);
        return new MyMessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyMessageViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyMessageViewHolder extends RecyclerView.ViewHolder{

        public MyMessageViewHolder(View itemView) {
            super(itemView);
        }
    }
}
