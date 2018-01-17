package com.yichan.gaotezhipei.lcl.fragment;

import com.yichan.gaotezhipei.R;

/**
 * 小面包车
 * Created by ckerv on 2018/1/12.
 */

public class LCLMiniBusFragment extends LCLBaseDetailFragment{
    @Override
    protected int getCarTypeResId() {
        return R.drawable.lcl_xiaomianbaoche;
    }

    @Override
    protected String getCarSize() {
        return "1.8*1.3*1.1米";
    }

    @Override
    protected String getCarVolume() {
        return "2.6方";
    }

    @Override
    protected boolean isFistFragment() {
        return true;
    }

    @Override
    protected String getCarWeight() {
        return "500公斤";
    }
}
