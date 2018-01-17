package com.yichan.gaotezhipei.logistics.fragment;

import android.support.v7.widget.RecyclerView;

import com.yichan.gaotezhipei.common.fragment.CommonOrderFragment;
import com.yichan.gaotezhipei.logistics.entity.LCLOrderItem;
import com.yichan.gaotezhipei.logistics.view.LCLOrderAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 拼货订单fragment公共基类。与物流订单隔开。
 * Created by ckerv on 2018/1/12.
 */

public abstract class CommonLCLOrderFragment extends CommonOrderFragment {

    protected List<LCLOrderItem> mList = new ArrayList<>();
    private LCLOrderAdapter mAdapter;

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mAdapter = new LCLOrderAdapter(getActivity(), mList);
        return mAdapter;
    }
}
