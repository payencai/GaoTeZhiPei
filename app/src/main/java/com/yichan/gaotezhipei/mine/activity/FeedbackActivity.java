package com.yichan.gaotezhipei.mine.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.changelcai.mothership.view.recycler.MSClickableAdapter;
import com.ckev.chooseimagelibrary.base.img.listener.OnImageSelectedChangeListener;
import com.ckev.chooseimagelibrary.base.img.listener.OnImageSelectedFinishedListener;
import com.ckev.chooseimagelibrary.base.img.view.ChooseImageActivity;
import com.ckev.chooseimagelibrary.base.img.view.ImageDetailActivity;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;
import com.yichan.gaotezhipei.mine.view.FeedbackDisplayImageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ckerv on 2018/1/17.
 */

public class FeedbackActivity extends BaseActivity implements OnImageSelectedFinishedListener, OnImageSelectedChangeListener {


    @BindView(R.id.feed_back_rv_display)
    RecyclerView mRvDisplay;

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    private FeedbackDisplayImageAdapter mAdapter;

    private List<String> mDatas;

    /**
     * 可选择的图片数量
     */
    private int mSelectNum;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initTitleBar();
        initVariables();
        initDisplayImageViews();
    }

    private void initVariables() {
        mSelectNum = 9;
        mDatas = new ArrayList<>();
        mDatas.add("android.resource://com.yichan.gaotezhipei/drawable/mine_add");
    }

    private void initDisplayImageViews() {
        mAdapter = new FeedbackDisplayImageAdapter(this, R.layout.item_feed_back_display_img, mDatas, mSelectNum);
        mRvDisplay.setLayoutManager(new GridLayoutManager(this, 3));
        mRvDisplay.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MSClickableAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(mDatas.get(position).equals("android.resource://com.yichan.gaotezhipei/drawable/mine_add")) {
                    ChooseImageActivity.startActivity(FeedbackActivity.this, mSelectNum);
                } else {
                    ImageDetailActivity.startActvitiy(FeedbackActivity.this, mDatas.get(position));
                }
            }
        });
    }

    private void initTitleBar() {
        mTvTitle.setText("意见反馈");
    }

    @OnClick({R.id.titlebar_btn_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_btn_left:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_feed_back;
    }

    @Override
    public void onFinish(List<String> selectedImages) {

        mDatas.addAll(0, selectedImages);
        //处理已选择的图片数量超过可选择图片数量的逻辑
        if(mDatas.size() - 1 >= mSelectNum) {
            for(int i = mDatas.size() - 1; i >= mSelectNum; i--)
                mDatas.remove(i);
        }
        mRvDisplay.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onImageSelectedChange(List<String> selectedImages) {

    }
}
