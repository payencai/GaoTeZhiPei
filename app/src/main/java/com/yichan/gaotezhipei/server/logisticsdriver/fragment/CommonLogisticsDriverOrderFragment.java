package com.yichan.gaotezhipei.server.logisticsdriver.fragment;

import android.support.v7.widget.RecyclerView;

import com.yichan.gaotezhipei.common.fragment.CommonOrderFragment;
import com.yichan.gaotezhipei.server.logisticsdriver.entity.LogisticsDriverOrderItem;
import com.yichan.gaotezhipei.server.logisticsdriver.view.LogisticsDriverOrderAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ckerv on 2018/2/5.
 */

public class CommonLogisticsDriverOrderFragment extends CommonOrderFragment {

    private List<LogisticsDriverOrderItem> mList = new ArrayList<>();
    private LogisticsDriverOrderAdapter mAdapter;

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mAdapter = new LogisticsDriverOrderAdapter(getActivity(), mList);
        return mAdapter;
    }

    @Override
    protected void doLoreMore(int currentPage, int size) {

    }

    @Override
    protected void doRefresh(int currentPage, int size) {
        for(int i = 0; i < 10; i ++) {
            mList.add(new LogisticsDriverOrderItem());
        }
        mAdapter.notifyDataSetChanged();
        doRefreshFinish(0);
        doLoadMoreFinish(0);
    }
}
