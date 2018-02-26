package com.yichan.gaotezhipei.policyadvice.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.changelcai.mothership.view.recycler.MSClickableAdapter;
import com.ckev.chooseimagelibrary.base.img.assist.CommonImageLoader;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.policyadvice.entity.PolicyArticlePageList;

import java.util.List;

/**
 * Created by ckerv on 2018/1/10.
 */

public class PolicyAdviceAdapter extends MSClickableAdapter<PolicyAdviceAdapter.PolicyAdviceViewHolder> {

    private Context mContext;
    private List<PolicyArticlePageList.BeanListBean> mList;


    public PolicyAdviceAdapter(Context context, List<PolicyArticlePageList.BeanListBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public void onBindVH(PolicyAdviceAdapter.PolicyAdviceViewHolder holder, int position) {
        holder.imgIv.setImageResource(R.drawable.pa_image1);
        CommonImageLoader.displayImage(mList.get(position).getImage(),holder.imgIv, CommonImageLoader.DOUBLE_CACHE_OPTIONS);
        holder.titleTv.setText(mList.get(position).getContent());
        holder.authorTv.setText(mList.get(position).getTitle());
        holder.timeTv.setText(mList.get(position).getCreateTime().substring(0, 10));
    }

    @Override
    public PolicyAdviceAdapter.PolicyAdviceViewHolder onCreateVH(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_policy_advice, parent, false);
        return new PolicyAdviceViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class PolicyAdviceViewHolder extends RecyclerView.ViewHolder {

        ImageView imgIv;
        TextView titleTv;
        TextView authorTv;
        TextView timeTv;

        public PolicyAdviceViewHolder(View itemView) {
            super(itemView);
            imgIv = (ImageView) itemView.findViewById(R.id.item_pa_iv_img);
            titleTv = (TextView) itemView.findViewById(R.id.item_pa_tv_title);
            authorTv = (TextView) itemView.findViewById(R.id.item_pa_tv_author);
            timeTv = (TextView) itemView.findViewById(R.id.item_pa_tv_time);
        }
    }
}
