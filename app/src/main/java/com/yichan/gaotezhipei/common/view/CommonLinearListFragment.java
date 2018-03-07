package com.yichan.gaotezhipei.common.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseListFragment;

import butterknife.BindView;

/**
 * 只包含一个线性布局的RecyclerView的通用fragment。
 * Created by ckerv on 2018/1/7.
 */

public abstract class CommonLinearListFragment<E> extends BaseListFragment<E> {

    @BindView(R.id.view_no_data)
    View nodataView;

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

    protected void toggleNodataView(boolean isShow) {
        if(isShow) {
            mMultiLayout.setVisibility(View.GONE);
            nodataView.setVisibility(View.VISIBLE);
        } else {
            mMultiLayout.setVisibility(View.VISIBLE);
            nodataView.setVisibility(View.GONE);
        }
    }

}
