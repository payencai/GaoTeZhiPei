package com.yichan.gaotezhipei.server.lcldriver.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseListActivity;
import com.yichan.gaotezhipei.server.lcldriver.entity.NearbyCargoItem;
import com.yichan.gaotezhipei.server.lcldriver.view.NearbyCargoAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ckerv on 2018/1/19.
 */

public class NearbyCargoActivity extends BaseListActivity {
    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;


    private NearbyCargoAdapter mAdapter;

    private List<NearbyCargoItem> mList = new ArrayList<>();

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        initTitleBar();
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

    private void initTitleBar() {
        mTvTitle.setText("货源信息");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_nearby_cargo;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mAdapter = new NearbyCargoAdapter(this, mList);
        return mAdapter;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    @Override
    protected int getMultiRecyclerViewLayoutId() {
        return R.id.nearby_cargo_rv;
    }

    @Override
    protected void doLoreMore(int currentPage, int size) {

    }

    @Override
    protected void doRefresh(int currentPage, int size) {
        for(int i = 0; i < 10; i++ ) {
            mList.add(new NearbyCargoItem());
        }
        mAdapter.notifyDataSetChanged();
        doRefreshFinish(0);
        doLoadMoreFinish(0);
    }
}
