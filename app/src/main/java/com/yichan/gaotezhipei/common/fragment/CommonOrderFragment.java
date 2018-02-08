package com.yichan.gaotezhipei.common.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseListFragment;

/**
 * Created by ckerv on 2018/1/12.
 */

public abstract class CommonOrderFragment extends BaseListFragment {

    protected View mViewNodata;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mViewNodata = findViewById(R.id.common_order_nodata);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_comon_order;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override
    protected int getMultiRecyclerViewLayoutId() {
        return R.id.common_order_rv;
    }

}
