package com.yichan.gaotezhipei.lcl.fragment;

import com.yichan.gaotezhipei.R;

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
}
