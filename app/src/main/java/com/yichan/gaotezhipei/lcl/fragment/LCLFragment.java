package com.yichan.gaotezhipei.lcl.fragment;

import android.support.v4.app.Fragment;

import com.yichan.gaotezhipei.common.fragment.CommonMultiTabOnlyTextFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ckerv on 2018/1/8.
 */

public class LCLFragment extends CommonMultiTabOnlyTextFragment {

    @Override
    protected String[] getTabNames() {
        return new String[]{"小面包车", "中面包车", "小货车", "中货车"};
    }

    @Override
    protected List<Fragment> initFragmentList() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new LCLMiniBusFragment());
        fragments.add(new LCLMiddleBusFragment());
        fragments.add(new LCLSmallTrackFragment());
        fragments.add(new LCLMiddleTrackFragment());
        return fragments;
    }

}
