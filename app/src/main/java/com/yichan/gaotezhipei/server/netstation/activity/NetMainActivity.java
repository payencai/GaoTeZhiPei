package com.yichan.gaotezhipei.server.netstation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseListActivity;
import com.yichan.gaotezhipei.server.netstation.entity.ExpressConfirmedItem;
import com.yichan.gaotezhipei.server.netstation.view.ExpressConfirmedAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ckerv on 2018/1/19.
 */

public class NetMainActivity extends BaseListActivity {

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    @BindView(R.id.titlebar_btn_left)
    ImageButton mIbLeft;

    private ExpressConfirmedAdapter mAdapter;

    private List<ExpressConfirmedItem> mList = new ArrayList<>();

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        initTitleBar();
    }

    private void initTitleBar() {
        mTvTitle.setText("服务网点");
        mIbLeft.setVisibility(View.GONE);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_server_net_main;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mAdapter = new ExpressConfirmedAdapter(this, mList);
        return mAdapter;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    @Override
    protected int getMultiRecyclerViewLayoutId() {
        return R.id.net_main_rv;
    }

    @Override
    protected void doLoreMore(int currentPage, int size) {

    }

    @Override
    protected void doRefresh(int currentPage, int size) {
        initDataList();
        doRefreshFinish(0);
        doLoadMoreFinish(0);
    }

    private void initDataList() {
        for (int i = 0; i < 10; i++) {
            mList.add(new ExpressConfirmedItem());
        }
        mAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.net_main_iv_new_address, R.id.net_main_iv_express_to_confirm, R.id.net_main_iv_express_record})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.net_main_iv_new_address:
                startActivity(new Intent(NetMainActivity.this, NewExpressActivity.class));
                break;
            case R.id.net_main_iv_express_to_confirm:
                startActivity(new Intent(NetMainActivity.this, ExpressToConfirmActivity.class));
                break;
            case R.id.net_main_iv_express_record:
                startActivity(new Intent(NetMainActivity.this, ExpressRecordActivity.class));
                break;
            default:
                break;
        }
    }
}
