package com.yichan.gaotezhipei.common.fragment;

import android.os.Bundle;

import com.yichan.gaotezhipei.base.component.BaseMultiTabFragment;
import com.yichan.gaotezhipei.common.view.CommonTabBean;

import java.util.ArrayList;

/**
 * Created by ckerv on 2018/1/8.
 */

public abstract class CommonMultiTabFragment extends BaseMultiTabFragment<CommonTabBean> {
    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
    }

    @Override
    protected ArrayList<CommonTabBean> initTabList() {
        ArrayList<CommonTabBean> tabList = new ArrayList<>();
        String[] tabNames = getTabNames();
        for (int i = 0; i < tabNames.length; i++) {
            tabList.add(new CommonTabBean(tabNames[i], getTabSelectedIcons()[i], getTabUnSelectedIcons()[i]));
        }
        return tabList;
    }

    protected abstract String[] getTabNames();

    protected abstract int[] getTabSelectedIcons();

    protected abstract int[] getTabUnSelectedIcons();


}
