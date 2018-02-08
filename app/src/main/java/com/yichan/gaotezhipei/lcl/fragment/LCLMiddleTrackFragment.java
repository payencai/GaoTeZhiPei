package com.yichan.gaotezhipei.lcl.fragment;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.lcl.constant.LCLConstants;

/**
 * Created by ckerv on 2018/1/12.
 */

public class LCLMiddleTrackFragment extends LCLBaseDetailFragment {

    @Override
    protected boolean isLastFragment() {
        return true;
    }

    @Override
    protected int getCarTypeResId() {
        return R.drawable.lcl_zhonghuoche;
    }

    @Override
    protected String getCarSize() {
        return "4.2*1.8*1.8米";
    }

    @Override
    protected String getCarVolume() {
        return "13.6方";
    }

    @Override
    protected String getCarWeight() {
        return "1.8吨";
    }

    @Override
    protected int getType() {
        return LCLConstants.TYPE_MIDDLE_TRACK;
    }

    @Override
    protected String getTypeStr() {
        return LCLConstants.TYPE_STR_MIDDLE_TRACK;
    }
}
