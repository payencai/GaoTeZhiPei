package com.yichan.gaotezhipei.common.entity;

import java.io.Serializable;

/**
 * Created by simon on 2018/2/8 0008.
 */

public class CourseSectionPo implements Serializable {
    private Integer id;  //节ID
    private Integer chapterId;  //章ID
    private String sectionName;    //
    private Integer number; // "number":0  //节编号

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChapterId() {
        return chapterId;
    }

    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
