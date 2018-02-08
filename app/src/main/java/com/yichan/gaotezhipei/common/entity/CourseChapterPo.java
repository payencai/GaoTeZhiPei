package com.yichan.gaotezhipei.common.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/2/8 0008.
 */

public class CourseChapterPo implements Serializable {
    private Integer id;  //章ID
    //private Integer chapterId;  //章ID
    private String chapterName;    //
    private Integer number; // "number":0  //章编号
    private List<CourseSectionPo> mSectionPoList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<CourseSectionPo> getSectionPoList() {
        return mSectionPoList;
    }

    public void setSectionPoList(List<CourseSectionPo> sectionPoList) {
        mSectionPoList = sectionPoList;
    }
}
