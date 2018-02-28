package com.yichan.gaotezhipei.trainservice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;
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
 * Created by simon on 2018/2/8 0008.
 */

public class TrainCourseDetailActivity extends BaseActivity {

    @BindView(R.id.part_title_bar_simple_rv)
    RelativeLayout mLayTitleBar;
    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;
    @BindView(R.id.course_introduction_listview)
    ListViewForScrollView mIntroductionListView;
    @BindView(R.id.course_outline_listview)
    ListViewForScrollView mListView;
    @BindView(R.id.btnBeginToLearn)
    TextView mBtnBeginToLearn;
    @BindView(R.id.btnApplyForEnroll)
    TextView mBtnApplyForEnroll;

    private CourseOutlineAdapter mCourseOutlineAdapter;
    private List<Module> moduleList = new ArrayList<>();
    private List<CourseChapterPo> mListCourseChapterPos;
    private CourseIntroductionAdapter mCourseIntroductionAdapter;
    private List<Module> introductionModuleList = new ArrayList<>();
    private List<CourseIntroductionPo> mCourseIntroductionPos;
    private Integer mCourseModal;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_train_course_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mCourseModal = getIntent().getIntExtra(Constans.CourseModal, 0);
        initView();
    }

    private void initView() {

        mLayTitleBar.setBackgroundColor(getResources().getColor(R.color.text_blue_color));
        mTvTitle.setText("");
        if(mCourseModal == Constans.COURSE_MODAL.ONLINE.getIndex()) {
            mBtnBeginToLearn.setVisibility(View.VISIBLE);
            mBtnApplyForEnroll.setVisibility(View.GONE);
        } else if(mCourseModal == Constans.COURSE_MODAL.OFFLINE.getIndex()) {
            mBtnApplyForEnroll.setVisibility(View.VISIBLE);
            mBtnBeginToLearn.setVisibility(View.GONE);
        }
        mCourseIntroductionPos = new ArrayList<>();
        mCourseIntroductionAdapter = new CourseIntroductionAdapter(this);
        mIntroductionListView.setAdapter(mCourseIntroductionAdapter);
        mListCourseChapterPos = new ArrayList<>();
        mCourseOutlineAdapter = new CourseOutlineAdapter(this);
        mListView.setAdapter(mCourseOutlineAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    protected void initData() {
        if(mCourseIntroductionPos != null)
            mCourseIntroductionPos.clear();
        if(mListCourseChapterPos != null)
            mListCourseChapterPos.clear();

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

            //chapter 1
            List<CourseChapterPo> courseChapterPos = new ArrayList<>();
            List<CourseSectionPo> courseSectionPos;
            CourseChapterPo courseChapterPo;
            CourseSectionPo courseSectionPo;
            courseChapterPo = new CourseChapterPo();
            courseChapterPo.setId(1);
            courseChapterPo.setNumber(1);
            courseChapterPo.setChapterName("课程简介");
            courseSectionPos = new ArrayList<>();
            courseSectionPo = new CourseSectionPo();
            courseSectionPo.setId(11);
            courseSectionPo.setChapterId(1);
            courseSectionPo.setNumber(1);
            courseSectionPo.setSectionName("课程介绍");
            courseSectionPos.add(courseSectionPo);
            courseChapterPo.setSectionPoList(courseSectionPos);
            courseChapterPos.add(courseChapterPo);
            //chapter 2
            courseChapterPo = new CourseChapterPo();
            courseChapterPo.setId(2);
            courseChapterPo.setNumber(2);
            courseChapterPo.setChapterName("动效设计简介");
            courseSectionPos = new ArrayList<>();
            courseSectionPo = new CourseSectionPo();
            courseSectionPo.setId(21);
            courseSectionPo.setChapterId(2);
            courseSectionPo.setNumber(1);
            courseSectionPo.setSectionName("什么是动效设计");
            courseSectionPos.add(courseSectionPo);
            courseChapterPo.setSectionPoList(courseSectionPos);
            courseChapterPos.add(courseChapterPo);
            //chapter 3
            courseChapterPo = new CourseChapterPo();
            courseChapterPo.setId(3);
            courseChapterPo.setNumber(3);
            courseChapterPo.setChapterName("动效设计的优势");
            courseSectionPos = new ArrayList<>();
            for(int i=1; i<8; i++) {
                courseSectionPo = new CourseSectionPo();
                courseSectionPo.setId(30+i);
                courseSectionPo.setChapterId(3);
                courseSectionPo.setNumber(i);
                String sectionName = "";
                switch (i) {
                    case 1 : sectionName = "动效设计在体验时代的必要性";break;
                    case 2 : sectionName = "情感化的设计提升用户体验";break;
                    case 3 : sectionName = "增加产品趣味性";break;
                    case 4 : sectionName = "减少用户等待的焦虑感";break;
                    case 5 : sectionName = "减少沟通成本&提高团队效率";break;
                    case 6 : sectionName = "作品包装与展示";break;
                    case 7 : sectionName = "在未来设计领域扮演重要角色";break;
                }
                courseSectionPo.setSectionName(sectionName);
                courseSectionPos.add(courseSectionPo);
            }
            courseChapterPo.setSectionPoList(courseSectionPos);
            courseChapterPos.add(courseChapterPo);
            setForm(courseIntroductionPos, courseChapterPos);
        } else {
        }
    }

    private void setForm(List<CourseIntroductionPo> courseIntroductionPos, List<CourseChapterPo> courseChapterPos) {
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
        mListCourseChapterPos.addAll(courseChapterPos);
        //setData();
        if(mListCourseChapterPos != null && mListCourseChapterPos.size() >0) {
            moduleList.clear();
            Module<CourseSectionPo> chapter;
            for(CourseChapterPo courseChapterPo : mListCourseChapterPos) {
                chapter = new Module<>();
                //String moduleTitleStr = String.format("%d-%d %s")
                chapter.setModuleId(courseChapterPo.getChapterName());
                chapter.setList(courseChapterPo.getSectionPoList());
                moduleList.add(chapter);
            }
            mCourseOutlineAdapter.setModuleList(moduleList);
            mCourseOutlineAdapter.notifyDataSetChanged();
        }
    }
    
    @OnClick({R.id.titlebar_btn_left, R.id.btnBeginToLearn, R.id.btnApplyForEnroll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_btn_left:
                finish();
                break;
            case R.id.btnBeginToLearn:
                startActivity(new Intent(TrainCourseDetailActivity.this, OnlineCourseLearnActivity.class));
                break;
            case R.id.btnApplyForEnroll:
                startActivity(new Intent(TrainCourseDetailActivity.this, TrainCourseEnrollActivity.class));
                break;
            default:
                break;
        }
    }
}
