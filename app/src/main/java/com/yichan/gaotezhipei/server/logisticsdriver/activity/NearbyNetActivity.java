package com.yichan.gaotezhipei.server.logisticsdriver.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.changelcai.mothership.view.recycler.MSClickableAdapter;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseListActivity;
import com.yichan.gaotezhipei.server.logisticsdriver.entity.NearbyNetItem;
import com.yichan.gaotezhipei.server.logisticsdriver.view.NearbyNetAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ckerv on 2018/2/5.
 */

public class NearbyNetActivity extends BaseListActivity {
    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;


    private NearbyNetAdapter mAdapter;

    private List<NearbyNetItem> mList = new ArrayList<>();

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
        mTvTitle.setText("附近网点");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_nearby_net;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mAdapter = new NearbyNetAdapter(this, mList);
        mAdapter.setOnItemClickListener(new MSClickableAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(NearbyNetActivity.this, NetDetailActivity.class));
            }
        });
        return mAdapter;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    @Override
    protected int getMultiRecyclerViewLayoutId() {
        return R.id.nearby_net_rv;
    }

    @Override
    protected void doLoreMore(int currentPage, int size) {

    }

    @Override
    protected void doRefresh(int currentPage, int size) {
        for(int i = 0; i < 10; i++ ) {
            mList.add(new NearbyNetItem());
        }
        mAdapter.notifyDataSetChanged();
        doRefreshFinish(0);
        doLoadMoreFinish(0);
    }
}
