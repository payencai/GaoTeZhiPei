package com.yichan.gaotezhipei.logistics.activity;

import android.support.v4.app.Fragment;

import com.yichan.gaotezhipei.logistics.constant.LogisticsContants;
import com.yichan.gaotezhipei.logistics.fragment.LogisticAllOrderFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ckerv on 2018/1/15.
 */

public class LogisticOrderActivity extends CommonOrderActivtiy {
    @Override
    protected String[] getTabNames() {
        return LogisticsContants.LOGISTICS_ORDER_NAMES;
    }

    @Override
    protected String getTitleText() {
        return "物流订单";
    }

    @Override
    protected List<Fragment> initFragmentList() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new LogisticAllOrderFragment());
        fragments.add(new LogisticAllOrderFragment());
        fragments.add(new LogisticAllOrderFragment());
        fragments.add(new LogisticAllOrderFragment());
        fragments.add(new LogisticAllOrderFragment());
        return fragments;
    }
}
