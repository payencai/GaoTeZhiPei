package com.yichan.gaotezhipei.trainservice.entity;

/**
 * Created by ckerv on 2018/1/7.
 */

public class ConsultItem {

    private String imgPath;
    private String title;
    private String desc;

    public ConsultItem() {

    }


    public ConsultItem(String imgPath, String title, String desc) {
        this.imgPath = imgPath;
        this.title = title;
        this.desc = desc;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
