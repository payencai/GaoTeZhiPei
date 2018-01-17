package com.yichan.gaotezhipei.logistics.activity;

import android.support.v4.app.Fragment;

import com.yichan.gaotezhipei.logistics.constant.LogisticsContants;
import com.yichan.gaotezhipei.logistics.fragment.LCLAllOrderFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ckerv on 2018/1/12.
 */

public class LCLOrderActivity extends CommonOrderActivtiy {


    @Override
    protected String[] getTabNames() {
        return LogisticsContants.LCL_ORDER_NAMES;
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

    @Override
    protected String getTitleText() {
        return "拼货订单";
    }
}
