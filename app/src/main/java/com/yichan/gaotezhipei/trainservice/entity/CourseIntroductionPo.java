package com.yichan.gaotezhipei.trainservice.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/2/9 0009.
 */

public class CourseIntroductionPo implements Serializable {
    private Integer id;  //ID
    private String introductionTitle;
    private List<String> mItemList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIntroductionTitle() {
        return introductionTitle;
    }

    public void setIntroductionTitle(String introductionTitle) {
        this.introductionTitle = introductionTitle;
    }

    public List<String> getItemList() {
        return mItemList;
    }

    public void setItemList(List<String> itemList) {
        mItemList = itemList;
    }
}
