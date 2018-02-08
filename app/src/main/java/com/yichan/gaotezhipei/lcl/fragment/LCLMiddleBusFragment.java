package com.yichan.gaotezhipei.lcl.fragment;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.lcl.constant.LCLConstants;

/**
 * Created by ckerv on 2018/1/12.
 */

public class LCLMiddleBusFragment extends LCLBaseDetailFragment {
    @Override
    protected int getCarTypeResId() {
        return R.drawable.lcl_zhongmianbaoche;
    }

    @Override
    protected String getCarSize() {
        return "2.7*1.4*1.2米";
    }

    @Override
    protected String getCarVolume() {
        return "4.5方";
    }

    @Override
    protected String getCarWeight() {
        return "1吨";
    }

    @Override
    protected int getType() {
        return LCLConstants.TYPE_MIDDLE_BUS;
    }

    @Override
    protected String getTypeStr() {
        return LCLConstants.TYPE_STR_MIDDLE_BUS;
    }
}
