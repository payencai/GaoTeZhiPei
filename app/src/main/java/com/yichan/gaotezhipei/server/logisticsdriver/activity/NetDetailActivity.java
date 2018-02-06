package com.yichan.gaotezhipei.server.logisticsdriver.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseListActivity;
import com.yichan.gaotezhipei.server.logisticsdriver.entity.NetDetailItem;
import com.yichan.gaotezhipei.server.logisticsdriver.view.NetDetailAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ckerv on 2018/2/6.
 */

public class NetDetailActivity extends BaseListActivity{

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    private List<NetDetailItem> mList = new ArrayList<>();
    private NetDetailAdapter mAdapter;


    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initTitleBar();
    }

    private void initTitleBar() {
        mTvTitle.setText("白云永康网点");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_net_detail;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mAdapter = new NetDetailAdapter(NetDetailActivity.this, mList);
        return mAdapter;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    @Override
    protected int getMultiRecyclerViewLayoutId() {
        return R.id.net_detail_rv;
    }

    @Override
    protected void doLoreMore(int currentPage, int size) {

    }

    @Override
    protected void doRefresh(int currentPage, int size) {
        for(int i = 0 ; i < 10; i++) {
            mList.add(new NetDetailItem());
        }
        mAdapter.notifyDataSetChanged();
        doRefreshFinish(0);
        doLoadMoreFinish(0);
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
}
