package com.yichan.gaotezhipei.common.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseListFragment;

/**
 * 只包含一个线性布局的RecyclerView的通用fragment。
 * Created by ckerv on 2018/1/7.
 */

public abstract class CommonLinearListFragment<E> extends BaseListFragment<E> {

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_common_list;
    }


    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override
    protected int getMultiRecyclerViewLayoutId() {
        return R.id.common_rv_list;
    }

}
