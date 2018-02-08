package com.yichan.gaotezhipei.lcl.fragment;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.lcl.constant.LCLConstants;

/**
 * Created by ckerv on 2018/1/12.
 */

public class LCLSmallTrackFragment extends LCLBaseDetailFragment {
    @Override
    protected int getCarTypeResId() {
        return R.drawable.lcl_xiaohuoche;
    }

    @Override
    protected String getCarSize() {
        return "2.7*1.5*1.7米";
    }

    @Override
    protected String getCarVolume() {
        return "6.9方";
    }

    @Override
    protected String getCarWeight() {
        return "1吨";
    }

    @Override
    protected int getType() {
        return LCLConstants.TYPE_SMALL_TRACK;
    }

    @Override
    protected String getTypeStr() {
        return LCLConstants.TYPE_STR_SMALL_TRACK;
    }
}
