package com.yichan.gaotezhipei.trainservice.fragment;

import android.os.Bundle;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseFragment;
import com.yichan.gaotezhipei.common.constant.Constans;
import com.yichan.gaotezhipei.trainservice.entity.CourseChapterPo;
import com.yichan.gaotezhipei.trainservice.entity.CourseSectionPo;
import com.yichan.gaotezhipei.trainservice.entity.Module;
import com.yichan.gaotezhipei.trainservice.view.CourseOutlineAdapter;
import com.yichan.gaotezhipei.trainservice.view.ListViewForScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by simon on 2018/2/9 0009.
 */

public class OnlineCourseSectionsFragment extends BaseFragment {
    @BindView(R.id.course_outline_listview)
    ListViewForScrollView mListView;
    private CourseOutlineAdapter mCourseOutlineAdapter;
    private List<Module> moduleList = new ArrayList<>();
    private List<CourseChapterPo> mListCourseChapterPos;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_online_course_sections;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        mListCourseChapterPos = new ArrayList<>();
        mCourseOutlineAdapter = new CourseOutlineAdapter(getActivity());
        mCourseOutlineAdapter.setLearnModal(true);
        mListView.setAdapter(mCourseOutlineAdapter);
    }

    protected void initData() {
        if(Constans.DEMO_MODE) {
            //chapter 1
            List<CourseChapterPo> courseChapterPos = new ArrayList<>();
            List<CourseSectionPo> courseSectionPos;
            CourseChapterPo courseChapterPo;
            CourseSectionPo courseSectionPo;
            courseChapterPo = new CourseChapterPo();
            courseChapterPo.setId(1);
            courseChapterPo.setNumber(1);
            courseChapterPo.setChapterName("课程简介");
            courseChapterPo.setChapterName(String.format("第%d章 %s", courseChapterPo.getNumber(), courseChapterPo.getChapterName()));
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
            courseChapterPo.setChapterName(String.format("第%d章 %s", courseChapterPo.getNumber(), courseChapterPo.getChapterName()));
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
            courseChapterPo.setChapterName(String.format("第%d章 %s", courseChapterPo.getNumber(), courseChapterPo.getChapterName()));
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
            setForm(courseChapterPos);
        } else {
        }
    }

    private void setForm(List<CourseChapterPo> courseChapterPos) {
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
}
