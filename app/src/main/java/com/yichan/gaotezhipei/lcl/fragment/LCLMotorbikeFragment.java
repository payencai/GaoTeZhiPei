package com.yichan.gaotezhipei.lcl.fragment;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.lcl.constant.LCLConstants;

/**
 * Created by ckerv on 2018/3/6.
 */

public class LCLMotorbikeFragment extends LCLBaseDetailFragment {
    @Override
    protected int getCarTypeResId() {
        return R.drawable.motuoche;
    }

    @Override
    protected String getCarSize() {
        return "1.9*0.8*1.1米";
    }

    @Override
    protected String getCarVolume() {
        return "0.8方";
    }

    @Override
    protected String getCarWeight() {
        return "200公斤";
    }

    @Override
    protected int getType() {
        return LCLConstants.TYPE_MOTORBIKE;
    }

    @Override
    protected String getTypeStr() {
        return LCLConstants.TYPE_STR_MOTORBIKE;
    }

    @Override
    protected boolean isFistFragment() {
        return true;
    }
}
