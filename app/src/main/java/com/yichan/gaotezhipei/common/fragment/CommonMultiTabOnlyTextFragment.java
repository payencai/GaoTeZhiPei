package com.yichan.gaotezhipei.common.fragment;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.common.view.CommonTabBean;

import java.util.ArrayList;

/**
 * Created by ckerv on 2018/1/8.
 */

public abstract class CommonMultiTabOnlyTextFragment extends CommonMultiTabFragment {
    @Override
    protected int[] getTabUnSelectedIcons() {
        return new int[0];
    }

    @Override
    protected int[] getTabSelectedIcons() {
        return new int[0];
    }

    /**
     * 重写
     * @return
     */
    @Override
    protected ArrayList<CommonTabBean> initTabList() {
        ArrayList<CommonTabBean> tabList = new ArrayList<>();
        String[] tabNames = getTabNames();
        for (int i = 0; i < tabNames.length; i++) {
            tabList.add(new CommonTabBean(tabNames[i]));
        }
        return tabList;
    }

    @Override
    protected int getViewPagerId() {
        return R.id.fg_common_mt_vp;
    }

    @Override
    protected int getCommonTabLayoutId() {
        return R.id.fg_common_mt_tab_layout;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_common_multi_tab_only_text;
    }

}
