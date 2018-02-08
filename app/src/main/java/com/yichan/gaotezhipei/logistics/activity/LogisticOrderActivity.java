package com.yichan.gaotezhipei.logistics.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;
import com.yichan.gaotezhipei.common.view.CommonFragmentPagerAdapter;
import com.yichan.gaotezhipei.logistics.constant.LogisticsContants;
import com.yichan.gaotezhipei.logistics.fragment.LogisticAllOrderFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ckerv on 2018/1/15.
 */

public class LogisticOrderActivity extends BaseActivity {

    @BindView(R.id.order_titlebar_tv_title)
    TextView mTvTitle;

    @BindView(R.id.order_tab_layout)
    SlidingTabLayout mTabLayout;

    @BindView(R.id.order_vp)
    ViewPager mViewPager;

    private List<Fragment> mFragments;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mTvTitle.setText("物流订单");

        initFragments();
        initViewPager();
        initTablayout();
    }

    private void initFragments() {
        mFragments = new ArrayList<>();
        mFragments.add(new LogisticAllOrderFragment());
        mFragments.add(new LogisticAllOrderFragment());
        mFragments.add(new LogisticAllOrderFragment());
        mFragments.add(new LogisticAllOrderFragment());
        mFragments.add(new LogisticAllOrderFragment());
        mFragments.add(new LogisticAllOrderFragment());
    }

    private void initViewPager() {
        CommonFragmentPagerAdapter commonFragmentPagerAdapter = new CommonFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(commonFragmentPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initTablayout() {
        mTabLayout.setViewPager(mViewPager, LogisticsContants.LOGISTICS_ORDER_NAMES);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
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



    @Override
    protected int getContentViewId() {
        return R.layout.activity_common_order;
    }
}
