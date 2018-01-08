package com.yichan.gaotezhipei.servicetrain.activity;

import android.support.v4.app.Fragment;

import com.yichan.gaotezhipei.common.activity.CommonMultiTabOnlyTextActivity;
import com.yichan.gaotezhipei.servicetrain.fragment.ConsultFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 培训服务Activity。
 * Created by ckerv on 2018/1/7.
 */

public class TrainServiceActivity extends CommonMultiTabOnlyTextActivity{

    @Override
    protected String[] getTabNames() {
        return new String[]{"学校概况", "咨询", "线上课程", "我要报名"};
    }


    @Override
    protected List<Fragment> initFragmentList() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new Fragment());
        fragments.add(new ConsultFragment());
        fragments.add(new Fragment());
        fragments.add(new Fragment());
        return fragments;
    }

}
