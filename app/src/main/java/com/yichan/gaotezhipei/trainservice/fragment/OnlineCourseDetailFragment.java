package com.yichan.gaotezhipei.trainservice.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseFragment;
import com.yichan.gaotezhipei.common.constant.Constans;
import com.yichan.gaotezhipei.trainservice.entity.CourseChapterPo;
import com.yichan.gaotezhipei.trainservice.entity.CourseIntroductionPo;
import com.yichan.gaotezhipei.trainservice.entity.CourseSectionPo;
import com.yichan.gaotezhipei.trainservice.entity.Module;
import com.yichan.gaotezhipei.trainservice.view.CourseIntroductionAdapter;
import com.yichan.gaotezhipei.trainservice.view.CourseOutlineAdapter;
import com.yichan.gaotezhipei.trainservice.view.ListViewForScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by simon on 2018/2/9 0009.
 */

public class OnlineCourseDetailFragment extends BaseFragment {
    @BindView(R.id.tvCourseDescribe)
    TextView mTvCourseDescribe;
    @BindView(R.id.imgArrow)
    ImageView mImgArrow;
    @BindView(R.id.course_introduction_listview)
    ListViewForScrollView mIntroductionListView;
    private CourseIntroductionAdapter mCourseIntroductionAdapter;
    private List<Module> introductionModuleList = new ArrayList<>();
    private List<CourseIntroductionPo> mCourseIntroductionPos;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_online_course_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        mCourseIntroductionPos = new ArrayList<>();
        mCourseIntroductionAdapter = new CourseIntroductionAdapter(getActivity());
        mIntroductionListView.setAdapter(mCourseIntroductionAdapter);
    }

    protected void initData() {
        if(Constans.DEMO_MODE) {
            // introduction
            List<CourseIntroductionPo> courseIntroductionPos = new ArrayList<>();
            List<String> introductionItems;
            CourseIntroductionPo courseIntroductionPo;
            courseIntroductionPo = new CourseIntroductionPo();
            courseIntroductionPo.setId(1);
            courseIntroductionPo.setIntroductionTitle("课程须知");
            introductionItems = new ArrayList<>();
            introductionItems.add("1、有深入体验过移动端产品");
            introductionItems.add("2、对动效设计有大致的了解");
            courseIntroductionPo.setItemList(introductionItems);
            courseIntroductionPos.add(courseIntroductionPo);
            courseIntroductionPo = new CourseIntroductionPo();
            courseIntroductionPo.setIntroductionTitle("老师告诉你能学到什么？");
            introductionItems = new ArrayList<>();
            introductionItems.add("1、动效设计应用的场景");
            introductionItems.add("2、动效设计的价值");
            introductionItems.add("3、新手如何学习动效设计");
            courseIntroductionPo.setItemList(introductionItems);
            courseIntroductionPos.add(courseIntroductionPo);
            setForm(courseIntroductionPos);
        } else {
        }
    }

    private void setForm(List<CourseIntroductionPo> courseIntroductionPos) {
        mCourseIntroductionPos.addAll(courseIntroductionPos);
        if(mCourseIntroductionPos != null && mCourseIntroductionPos.size() > 0) {
            introductionModuleList.clear();
            Module<String> introduction;
            for(CourseIntroductionPo courseIntroductionPo : mCourseIntroductionPos) {
                introduction = new Module<>();
                introduction.setModuleId(courseIntroductionPo.getIntroductionTitle());
                introduction.setList(courseIntroductionPo.getItemList());
                introductionModuleList.add(introduction);
            }
            mCourseIntroductionAdapter.setModuleList(introductionModuleList);
            mCourseIntroductionAdapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.flArrow, R.id.imgArrow})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.flArrow:
            case R.id.imgArrow:
                if(mTvCourseDescribe.getMaxLines() <= 2) {
                    mTvCourseDescribe.setMaxLines(50);
                    mImgArrow.setImageResource(R.drawable.arrowup);
                } else {
                    mTvCourseDescribe.setMaxLines(2);
                    mImgArrow.setImageResource(R.drawable.arrowdown);
                }
                break;
        }
    }
}
