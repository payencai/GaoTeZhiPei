package com.yichan.gaotezhipei.servicecenter.entity;

/**
 * Created by ckerv on 2018/2/12.
 */

public class BannerImageItem {

    /**
     * about : string
     * category : string
     * createTime : 2018-02-10T16:18:53.526Z
     * id : string
     * image : string
     * imageKey : string
     * isDel : 0
     * isUse : 0
     * name : string
     */

    private String about;
    private String category;
    private String createTime;
    private String id;
    private String image;
    private String imageKey;
    private int isDel;
    private int isUse;
    private String name;

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageKey() {
        return imageKey;
    }

    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public int getIsUse() {
        return isUse;
    }

    public void setIsUse(int isUse) {
        this.isUse = isUse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
