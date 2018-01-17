package com.yichan.gaotezhipei.logistics.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.common.activity.CommonMultiTabActivity;
import com.yichan.gaotezhipei.common.view.CommonTabBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ckerv on 2018/1/12.
 */

public abstract class CommonOrderActivtiy extends CommonMultiTabActivity {


    @BindView(R.id.order_titlebar_tv_title)
    TextView mTvTitle;


    @Override
    protected int[] getTabUnSelectedIcons() {
        return new int[0];
    }

    @Override
    protected int[] getTabSelectedIcons() {
        return new int[0];
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mTvTitle.setText(getTitleText());
    }

    /**
     * 重写
     *
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
        return R.id.order_common_mt_vp;
    }

    @Override
    protected int getCommonTabLayoutId() {
        return R.id.order_common_mt_tab_layout;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_order_common;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.order_common_titlebar;
    }

    @OnClick({R.id.order_titlebar_btn_left})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.order_titlebar_btn_left:
                finish();
                break;
            default:
                break;
        }

    }

    protected abstract String getTitleText();
}
