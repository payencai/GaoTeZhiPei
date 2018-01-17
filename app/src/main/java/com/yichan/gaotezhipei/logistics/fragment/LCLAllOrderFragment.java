package com.yichan.gaotezhipei.logistics.fragment;

import com.yichan.gaotezhipei.logistics.entity.LCLOrderItem;

/**
 * Created by ckerv on 2018/1/12.
 */

public class LCLAllOrderFragment extends CommonLCLOrderFragment {

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
        for(int i = 0 ; i < 10; i++) {
            mList.add(new LCLOrderItem());
        }
    }
}
