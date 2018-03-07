package com.yichan.gaotezhipei.trainservice.entity;

import java.util.List;

/**
 * Created by ckerv on 2018/3/7.
 */

public class OfflineCoursePage {

    /**
     * beanList : [{"content":"string","createTime":"2018-03-07T10:59:01.685Z","id":"string","image":"string","imageKey":"string","isDel":0,"isUse":0,"joinNum":0,"title":"string"}]
     * pageNum : 0
     * pageSize : 0
     * tp : 0
     * tr : 0
     */

    private int pageNum;
    private int pageSize;
    private int tp;
    private int tr;
    private List<BeanListBean> beanList;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTp() {
        return tp;
    }

    public void setTp(int tp) {
        this.tp = tp;
    }

    public int getTr() {
        return tr;
    }

    public void setTr(int tr) {
        this.tr = tr;
    }

    public List<BeanListBean> getBeanList() {
        return beanList;
    }

    public void setBeanList(List<BeanListBean> beanList) {
        this.beanList = beanList;
    }

    public static class BeanListBean {
        /**
         * content : string
         * createTime : 2018-03-07T10:59:01.685Z
         * id : string
         * image : string
         * imageKey : string
         * isDel : 0
         * isUse : 0
         * joinNum : 0
         * title : string
         */

        private String content;
        private String createTime;
        private String id;
        private String image;
        private String imageKey;
        private int isDel;
        private int isUse;
        private int joinNum;
        private String title;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public int getJoinNum() {
            return joinNum;
        }

        public void setJoinNum(int joinNum) {
            this.joinNum = joinNum;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
