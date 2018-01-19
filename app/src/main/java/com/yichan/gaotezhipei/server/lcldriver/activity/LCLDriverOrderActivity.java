package com.yichan.gaotezhipei.server.lcldriver.activity;

import android.support.v4.app.Fragment;

import com.yichan.gaotezhipei.logistics.activity.CommonOrderActivtiy;
import com.yichan.gaotezhipei.logistics.fragment.LCLAllOrderFragment;
import com.yichan.gaotezhipei.server.lcldriver.constant.LCLDriverConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ckerv on 2018/1/19.
 */

public class LCLDriverOrderActivity extends CommonOrderActivtiy {
    @Override
    protected String[] getTabNames() {
        return LCLDriverConstants.LCL_DRIVER_ORDER_NAMES;
    }

    @Override
    protected String getTitleText() {
        return "我的订单";
    }

    @Override
    protected List<Fragment> initFragmentList() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new LCLAllOrderFragment());
        fragments.add(new LCLAllOrderFragment());
        fragments.add(new LCLAllOrderFragment());
        fragments.add(new LCLAllOrderFragment());
        fragments.add(new LCLAllOrderFragment());
        return fragments;
    }
}
