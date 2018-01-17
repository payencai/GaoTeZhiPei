package com.yichan.gaotezhipei.agriculturalservice.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.agriculturalservice.entity.AgriculturalShopItem;
import com.yichan.gaotezhipei.agriculturalservice.view.AgriculturalShopAdapter;
import com.yichan.gaotezhipei.base.component.BaseListActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ckerv on 2018/1/12.
 */

public class AgriculturalServiceActivity extends BaseListActivity {


    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    private AgriculturalShopAdapter mAdapter;

    private List<AgriculturalShopItem> mList = new ArrayList<>();

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initTitleBar();
    }

    private void initTitleBar() {
        mTvTitle.setText("农资服务");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_agricultural;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mAdapter = new AgriculturalShopAdapter(AgriculturalServiceActivity.this, mList);
        return mAdapter;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(AgriculturalServiceActivity.this);
    }

    @Override
    protected int getMultiRecyclerViewLayoutId() {
        return R.id.agricultural_rv_list;
    }

    @Override
    protected void doLoreMore(int currentPage, int size) {

    }

    @Override
    protected void doRefresh(int currentPage, int size) {
        mockData();
        doRefreshFinish(0);
        doLoadMoreFinish(0);
    }

    private void mockData() {
        for(int i = 0; i < 10; i++) {
            mList.add(new AgriculturalShopItem(null, "云南供销农资连锁店（弥勒店）", "云南省弥勒市红河区前卫西路世纪半岛橄榄谷7-6号", "0766-8532468"));
        }
    }

    @OnClick({R.id.titlebar_btn_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_btn_left:
                finish();
                break;
        }
    }
}
