package com.yichan.gaotezhipei.policyadvice;

/**
 * Created by ckerv on 2018/1/10.
 */

public class PolicyAdviceItem {

    private String title;
    private String imgPath;
    private String author;
    private String time;

    public PolicyAdviceItem(String title, String imgPath, String author, String time) {
        this.title = title;
        this.imgPath = imgPath;
        this.author = author;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
