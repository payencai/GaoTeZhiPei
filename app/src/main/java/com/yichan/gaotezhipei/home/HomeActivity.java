package com.yichan.gaotezhipei.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.common.activity.CommonMultiTabActivity;
import com.yichan.gaotezhipei.lcl.fragment.LCLFragment;
import com.yichan.gaotezhipei.logistics.fragment.LogisticsFragment;
import com.yichan.gaotezhipei.mine.fragment.MineFragment;
import com.yichan.gaotezhipei.servicecenter.fragment.ServiceCenterFragment;

import java.util.ArrayList;
import java.util.List;



public class HomeActivity extends CommonMultiTabActivity {

    private ViewGroup mTvTitle;
    private ImageView mIvRight;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initTitleBar();
        initViewPagerListener();
    }

    private void initTitleBar() {
        mTvTitle = (ViewGroup) getTitleBar().findViewById(R.id.home_title_bar);
        mIvRight = (ImageView) findViewById(R.id.titlebar_imgbtn_right);
        mIvRight.setVisibility(View.VISIBLE);
        mIvRight.setImageResource(R.drawable.customservice);
    }

    @Override
    protected String[] getTabNames() {
        return HomeConstants.TAB_NAMES;
    }

    @Override
    protected int[] getTabSelectedIcons() {
        return HomeConstants.TAB_SELECTED_ICONS;
    }

    @Override
    protected int[] getTabUnSelectedIcons() {
        return HomeConstants.TAB_UNSELECTED_ICONS;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.home_title_bar;
    }


    private void initViewPagerListener() {
       mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

           }

           @Override
           public void onPageSelected(int position) {
                //setTitleText(getTabNames()[position]);
               if(position == 3) {
                   mTvTitle.setVisibility(View.GONE);
               } else {
                   mTvTitle.setVisibility(View.VISIBLE);
               }
           }

           @Override
           public void onPageScrollStateChanged(int state) {

           }
       });
    }

    @Override
    protected int getViewPagerId() {
        return R.id.home_vp_pager;
    }

    @Override
    protected int getCommonTabLayoutId() {
        return R.id.home_bottom_tab_layout;
    }

    @Override
    protected List<Fragment> initFragmentList() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new ServiceCenterFragment());
        fragments.add(new LCLFragment());
        fragments.add(new LogisticsFragment());
        fragments.add(new MineFragment());
        return fragments;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }


}
