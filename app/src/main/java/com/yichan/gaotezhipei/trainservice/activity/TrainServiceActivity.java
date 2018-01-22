package com.yichan.gaotezhipei.trainservice.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.common.activity.CommonMultiTabOnlyTextActivity;
import com.yichan.gaotezhipei.trainservice.fragment.ApplyCourseFragment;
import com.yichan.gaotezhipei.trainservice.fragment.ConsultFragment;
import com.yichan.gaotezhipei.trainservice.fragment.OnlineCourseFragment;
import com.yichan.gaotezhipei.trainservice.fragment.SchoolIntroFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 培训服务Activity。
 * Created by ckerv on 2018/1/7.
 */

public class TrainServiceActivity extends CommonMultiTabOnlyTextActivity{

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;


    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initTitleBar();
    }

    private void initTitleBar() {
        mTvTitle.setText("红河州高特电商职业培训学校");
    }

    @Override
    protected String[] getTabNames() {
        return new String[]{"学校概况", "咨询", "线上课程", "我要报名"};
    }


    @Override
    protected List<Fragment> initFragmentList() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new SchoolIntroFragment());
        fragments.add(new ConsultFragment());
        fragments.add(new OnlineCourseFragment());
        fragments.add(new ApplyCourseFragment());
        return fragments;
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
