package com.yichan.gaotezhipei.mine.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseListActivity;
import com.yichan.gaotezhipei.mine.entity.MyMessageItem;
import com.yichan.gaotezhipei.mine.view.MyMessageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ckerv on 2018/1/15.
 */

public class MyMessageActivity extends BaseListActivity {

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;


    private MyMessageAdapter mAdapter;
    private List<MyMessageItem> mMsgList = new ArrayList<>();

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mTvTitle.setText("我的消息");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_my_message;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mAdapter = new MyMessageAdapter(this, mMsgList);
        return mAdapter;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    @Override
    protected int getMultiRecyclerViewLayoutId() {
        return R.id.my_message_rv_list;
    }

    @Override
    protected void doLoreMore(int currentPage, int size) {

    }

    @Override
    protected void doRefresh(int currentPage, int size) {
        initDatas();
        doRefreshFinish(0);
        doLoadMoreFinish(0);
    }

    private void initDatas() {
        for(int i = 0; i < 10; i++) {
            mMsgList.add(new MyMessageItem());
        }
        mAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.titlebar_btn_left})
    public void onCick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_btn_left:
                finish();
                break;
            default:
                break;
        }
    }
}
