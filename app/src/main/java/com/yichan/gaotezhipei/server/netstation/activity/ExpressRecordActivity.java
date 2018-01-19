package com.yichan.gaotezhipei.server.netstation.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseListActivity;
import com.yichan.gaotezhipei.server.netstation.entity.ExpressRecordedItem;
import com.yichan.gaotezhipei.server.netstation.view.ExpressRecordedAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ckerv on 2018/1/19.
 */

public class ExpressRecordActivity extends BaseListActivity {
    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    private ExpressRecordedAdapter mAdapter;

    private List<ExpressRecordedItem> mList = new ArrayList<>();


    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mTvTitle.setText("快件收录");
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
        return R.layout.activity_express_record;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mAdapter = new ExpressRecordedAdapter(this, mList);
        return mAdapter;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    @Override
    protected int getMultiRecyclerViewLayoutId() {
        return R.id.express_record_rv;
    }

    @Override
    protected void doLoreMore(int currentPage, int size) {

    }

    @Override
    protected void doRefresh(int currentPage, int size) {
        for(int i = 0; i < 10; i++) {
            mList.add(new ExpressRecordedItem());
        }
        mAdapter.notifyDataSetChanged();
        doRefreshFinish(0);
        doLoadMoreFinish(0);
    }
}
