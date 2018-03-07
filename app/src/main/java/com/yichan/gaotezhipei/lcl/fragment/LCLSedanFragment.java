package com.yichan.gaotezhipei.lcl.fragment;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.lcl.constant.LCLConstants;

/**
 * Created by ckerv on 2018/3/6.
 */

public class LCLSedanFragment extends LCLBaseDetailFragment {
    @Override
    protected int getCarTypeResId() {
        return R.drawable.jiaoche;
    }

    @Override
    protected String getCarSize() {
        return "3.5*1.5*1.5米";
    }

    @Override
    protected String getCarVolume() {
        return "1.6方";
    }

    @Override
    protected String getCarWeight() {
        return "450公斤";
    }

    @Override
    protected int getType() {
        return LCLConstants.TYPE_SEDAN;
    }

    @Override
    protected String getTypeStr() {
        return LCLConstants.TYPE_STR_SEDAN;
    }
}
