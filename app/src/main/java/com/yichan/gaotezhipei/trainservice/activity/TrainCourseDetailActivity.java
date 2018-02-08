package com.yichan.gaotezhipei.trainservice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;
import com.yichan.gaotezhipei.common.constant.Constans;
import com.yichan.gaotezhipei.common.entity.CourseChapterPo;
import com.yichan.gaotezhipei.common.entity.CourseSectionPo;
import com.yichan.gaotezhipei.trainservice.entity.Module;
import com.yichan.gaotezhipei.trainservice.view.CourseOutlineAdapter;
import com.yichan.gaotezhipei.trainservice.view.ListViewForScrollView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by simon on 2018/2/8 0008.
 */

public class TrainCourseDetailActivity extends BaseActivity {

    @BindView(R.id.part_title_bar_simple_rv)
    RelativeLayout mLayTitleBar;
    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;
    @BindView(R.id.course_outline_listview)
    ListViewForScrollView mListView;
    private CourseOutlineAdapter mCourseOutlineAdapter;
    private List<Module> moduleList = new ArrayList<>();
    protected List<CourseChapterPo> mListCourseChapterPos;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_train_course_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
//        initTitleBar();
        initView();
    }

    private void initView() {
        mLayTitleBar.setBackgroundColor(getResources().getColor(R.color.text_blue_color));
        mTvTitle.setText("");
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
        if(Constans.DEMO_MODE) {
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
            courseSectionPo.setNumber(11);
            courseSectionPo.setSectionName("section" + 11);
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
            courseSectionPo.setNumber(21);
            courseSectionPo.setSectionName("section" + 21);
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
                courseSectionPo.setNumber(30+i);
                courseSectionPo.setSectionName("section" + (30+i));
                courseSectionPos.add(courseSectionPo);
            }
            courseChapterPo.setSectionPoList(courseSectionPos);
            courseChapterPos.add(courseChapterPo);
            setForm(courseChapterPos);
        } else {
        }
    }

    private void setForm(List<CourseChapterPo> listResult) {
        mListCourseChapterPos.addAll(listResult);
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
    
    @OnClick({R.id.titlebar_btn_left, R.id.btnApplyForEnroll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_btn_left:
                finish();
                break;
            case R.id.btnApplyForEnroll:
                startActivity(new Intent(TrainCourseDetailActivity.this, TrainCourseEnrollActivity.class));
                break;
            default:
                break;
        }
    }
}
