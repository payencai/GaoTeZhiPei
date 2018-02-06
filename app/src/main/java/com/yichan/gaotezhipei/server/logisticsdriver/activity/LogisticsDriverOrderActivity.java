package com.yichan.gaotezhipei.server.logisticsdriver.activity;

import android.support.v4.app.Fragment;

import com.yichan.gaotezhipei.logistics.activity.CommonOrderActivtiy;
import com.yichan.gaotezhipei.server.logisticsdriver.constant.LogisticsDriverConstants;
import com.yichan.gaotezhipei.server.logisticsdriver.fragment.CommonLogisticsDriverOrderFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ckerv on 2018/2/5.
 */

public class LogisticsDriverOrderActivity extends CommonOrderActivtiy {
    @Override
    protected String[] getTabNames() {
        return LogisticsDriverConstants.LOGISTICS_DRIVER_ORDER_NAMES;
    }

    @Override
    protected String getTitleText() {
        return "我的订单";
    }

    @Override
    protected List<Fragment> initFragmentList() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new CommonLogisticsDriverOrderFragment());
        fragments.add(new CommonLogisticsDriverOrderFragment());
        fragments.add(new CommonLogisticsDriverOrderFragment());
        fragments.add(new CommonLogisticsDriverOrderFragment());
        return fragments;
    }
}
