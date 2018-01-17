package com.yichan.gaotezhipei.logistics.fragment;

import android.support.v7.widget.RecyclerView;

import com.yichan.gaotezhipei.common.fragment.CommonOrderFragment;
import com.yichan.gaotezhipei.logistics.entity.LogisticOrderItem;
import com.yichan.gaotezhipei.logistics.view.LogisticOrderAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ckerv on 2018/1/15.
 */

public abstract class CommonLogisticOrderFragment extends CommonOrderFragment {
    protected List<LogisticOrderItem> mList = new ArrayList<>();
    private LogisticOrderAdapter mAdapter;

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mAdapter = new LogisticOrderAdapter(getActivity(), mList);
        return mAdapter;
    }
}
