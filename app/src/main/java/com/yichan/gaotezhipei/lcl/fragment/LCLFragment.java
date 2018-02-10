package com.yichan.gaotezhipei.lcl.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.yichan.gaotezhipei.common.fragment.CommonMultiTabOnlyTextFragment;
import com.yichan.gaotezhipei.common.util.EventBus;
import com.yichan.gaotezhipei.lcl.event.SwitchToNextEvent;
import com.yichan.gaotezhipei.lcl.event.SwitchToPreEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ckerv on 2018/1/8.
 */

public class LCLFragment extends CommonMultiTabOnlyTextFragment {

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        EventBus.getInstance().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getInstance().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceivePre(SwitchToPreEvent switchToPreEvent) {
        int pre = mViewPager.getCurrentItem() - 1;
        if(pre >= 0) {
            mViewPager.setCurrentItem(pre);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveNext(SwitchToNextEvent switchToNextEvent) {
        int next = mViewPager.getCurrentItem() + 1;
        if(next <= mViewPager.getAdapter().getCount() - 1) {
            mViewPager.setCurrentItem(next);
        }
    }

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
