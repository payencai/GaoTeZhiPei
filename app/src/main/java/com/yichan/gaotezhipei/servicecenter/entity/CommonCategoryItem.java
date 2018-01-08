package com.yichan.gaotezhipei.servicecenter.entity;

/**
 * Created by ckerv on 2018/1/6.
 */

public class CommonCategoryItem {

    private int iconResId;
    private String title;
    private String subTitle;

    public CommonCategoryItem(int iconResId, String title) {
        this(iconResId, title, "");
    }

    public CommonCategoryItem(int iconResId, String title, String subTitle) {
        this.iconResId = iconResId;
        this.title = title;
        this.subTitle = subTitle;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
}
