package com.yichan.gaotezhipei.trainservice.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.common.activity.CommonMultiTabActivity;
import com.yichan.gaotezhipei.common.view.CommonTabBean;
import com.yichan.gaotezhipei.trainservice.fragment.OnlineCourseDetailFragment;
import com.yichan.gaotezhipei.trainservice.fragment.OnlineCourseSectionsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by simon on 2018/2/9 0009.
 */

public class OnlineCourseLearnActivity extends CommonMultiTabActivity {
    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_online_course_learn;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mTvTitle.setText("");
    }

    @Override
    protected String[] getTabNames() {
        return new String[]{"章节", "详情"};
    }

    @Override
    protected int[] getTabSelectedIcons() {
        return new int[0];
    }

    @Override
    protected int[] getTabUnSelectedIcons() {
        return new int[0];
    }

    @Override
    protected int getTitleBarId() {
        return R.id.common_mt_titlebar;
    }

    @Override
    protected int getViewPagerId() {
        return R.id.common_mt_vp;
    }

    @Override
    protected int getCommonTabLayoutId() {
        return R.id.common_mt_tab_layout;
    }

    @Override
    protected List<Fragment> initFragmentList() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new OnlineCourseSectionsFragment());
        fragments.add(new OnlineCourseDetailFragment());
        return fragments;
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

    @OnClick({R.id.titlebar_btn_left})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.titlebar_btn_left:
                finish();
                break;
            default:
                break;
        }
    }
}
