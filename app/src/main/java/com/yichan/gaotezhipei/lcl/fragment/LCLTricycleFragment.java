package com.yichan.gaotezhipei.lcl.fragment;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.lcl.constant.LCLConstants;

/**
 * Created by ckerv on 2018/3/6.
 */

public class LCLTricycleFragment extends LCLBaseDetailFragment {
    @Override
    protected int getCarTypeResId() {
        return R.drawable.sanlunche;
    }

    @Override
    protected String getCarSize() {
        return "3.5*1.2*1.8米";
    }

    @Override
    protected String getCarVolume() {
        return "5.8方";
    }

    @Override
    protected String getCarWeight() {
        return "370公斤";
    }

    @Override
    protected int getType() {
        return LCLConstants.TYPE_TRICYCLE;
    }

    @Override
    protected String getTypeStr() {
        return LCLConstants.TYPE_STR_TRICYCLE;
    }
}
