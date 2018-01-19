package com.yichan.gaotezhipei.server.lcldriver.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
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

public class LCLDriverMainActivity extends BaseListActivity {


    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    @BindView(R.id.titlebar_btn_left)
    ImageButton mIbLeft;

    private NearbyCargoAdapter mAdapter;

    private List<NearbyCargoItem> mList = new ArrayList<>();

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        initTitleBar();
    }

    private void initTitleBar() {
        mTvTitle.setText("拼货司机");
        mIbLeft.setVisibility(View.GONE);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_server_lcl_driver_main;
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
        return R.id.lcl_driver_main_rv;
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

    @OnClick({R.id.lcl_driver_iv_find_cargo, R.id.lcl_driver_iv_myorder, R.id.lcl_driver_iv_driver_inform})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lcl_driver_iv_find_cargo:
                startActivity(new Intent(LCLDriverMainActivity.this, NearbyCargoActivity.class));
                break;
            case R.id.lcl_driver_iv_myorder:
                startActivity(new Intent(LCLDriverMainActivity.this, LCLDriverOrderActivity.class));
                break;
            case R.id.lcl_driver_iv_driver_inform:
                startActivity(new Intent(LCLDriverMainActivity.this, DriverInformActivity.class));
                break;
            default:
                break;
        }
    }
}
