package com.yichan.gaotezhipei.lcl.fragment;

import android.support.v4.app.Fragment;

import com.yichan.gaotezhipei.common.fragment.CommonMultiTabOnlyTextFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ckerv on 2018/1/8.
 */

public class LCLFragment extends CommonMultiTabOnlyTextFragment {

    private LCLDetailFragment mLCLDetailFragment1;
    private LCLDetailFragment mLCLDetailFragment2;
    private LCLDetailFragment mLCLDetailFragment3;
    private LCLDetailFragment mLCLDetailFragment4;

    @Override
    protected String[] getTabNames() {
        return new String[]{"小面包车", "中面包车", "小货车", "中货车"};
    }

    @Override
    protected List<Fragment> initFragmentList() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        mLCLDetailFragment1 = new LCLDetailFragment();
        mLCLDetailFragment2 = new LCLDetailFragment();
        mLCLDetailFragment3 = new LCLDetailFragment();
        mLCLDetailFragment4 = new LCLDetailFragment();
        fragments.add(mLCLDetailFragment1);
        fragments.add(mLCLDetailFragment2);
        fragments.add(mLCLDetailFragment3);
        fragments.add(mLCLDetailFragment4);
        return fragments;
    }

    @Override
    protected void onResumeLazy() {
        super.onResumeLazy();
        mLCLDetailFragment1.setUserVisibleHint(true);
    }
}
