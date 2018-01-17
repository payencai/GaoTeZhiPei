package com.yichan.gaotezhipei.mine.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.changelcai.mothership.view.recycler.MSClickableAdapter;
import com.ckev.chooseimagelibrary.base.img.assist.CommonImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.yichan.gaotezhipei.R;

import java.util.List;

/**
 * Created by ckerv on 2018/1/17.
 */

public class FeedbackDisplayImageAdapter extends MSClickableAdapter<FeedbackDisplayImageAdapter.FeedbackDisplayImageViewHolder> {


    private List<String> mDatas;

    private Context mContext;

    private int mItemLayoutId;

    private int mSelectedNum;

    public FeedbackDisplayImageAdapter(Context context, int itemLayoutId, List<String> datas, int selectedNum) {
        this.mContext = context;
        this.mItemLayoutId = itemLayoutId;
        this.mDatas = datas;
        this.mSelectedNum = selectedNum;
    }


    @Override
    public FeedbackDisplayImageViewHolder onCreateVH(ViewGroup parent, int viewType) {
        return new FeedbackDisplayImageViewHolder(LayoutInflater.from(mContext).inflate(mItemLayoutId, parent, false));

    }

    @Override
    public void onBindVH(FeedbackDisplayImageViewHolder holder, int position) {
        holder.itemView.setTag(mDatas.get(position));
        //显示最后一个添加图片按钮
        if (mDatas.get(position).equals("android.resource://com.yichan.gaotezhipei/drawable/mine_add")) {
            holder.ivCover.setImageResource(R.drawable.mine_add);
        }
        CommonImageLoader.displayImage(ImageDownloader.Scheme.FILE.wrap(mDatas.get(position)), holder.ivCover, CommonImageLoader.NO_CACHE_OPTIONS);
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class FeedbackDisplayImageViewHolder extends RecyclerView.ViewHolder {

        ImageView ivCover;

        public FeedbackDisplayImageViewHolder(View itemView) {
            super(itemView);
            ivCover = (ImageView) itemView.findViewById(R.id.feed_back_iv_display);
        }
    }
}
