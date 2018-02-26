package com.yichan.gaotezhipei.policyadvice.entity;

import java.util.List;

/**
 * Created by ckerv on 2018/2/12.
 */

public class PolicyArticlePageList {

    /**
     * pageNum : 1
     * tr : 1
     * pageSize : 8
     * beanList : [{"id":"12","title":"标题","image":"http://gaotedianshang.oss-cn-shenzhen.aliyuncs.com/%E4%B8%8A%E4%BC%A0/2018020911193445?Expires=1518491836&OSSAccessKeyId=LTAIjs0fwbrMyqNt&Signature=O8%2BaxY7%2FTjRJOxOmiFe%2B%2B62foVk%3D","imageKey":"上传/2018020911193445","content":"sadasdasdasdasdadas","createTime":"2018-02-10 14:51:12","isUse":1,"isDel":1}]
     * tp : 1
     */

    private int pageNum;
    private int tr;
    private int pageSize;
    private int tp;
    private List<BeanListBean> beanList;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getTr() {
        return tr;
    }

    public void setTr(int tr) {
        this.tr = tr;
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

    public List<BeanListBean> getBeanList() {
        return beanList;
    }

    public void setBeanList(List<BeanListBean> beanList) {
        this.beanList = beanList;
    }

    public static class BeanListBean {
        /**
         * id : 12
         * title : 标题
         * image : http://gaotedianshang.oss-cn-shenzhen.aliyuncs.com/%E4%B8%8A%E4%BC%A0/2018020911193445?Expires=1518491836&OSSAccessKeyId=LTAIjs0fwbrMyqNt&Signature=O8%2BaxY7%2FTjRJOxOmiFe%2B%2B62foVk%3D
         * imageKey : 上传/2018020911193445
         * content : sadasdasdasdasdadas
         * createTime : 2018-02-10 14:51:12
         * isUse : 1
         * isDel : 1
         */

        private String id;
        private String title;
        private String image;
        private String imageKey;
        private String content;
        private String createTime;
        private int isUse;
        private int isDel;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public int getIsUse() {
            return isUse;
        }

        public void setIsUse(int isUse) {
            this.isUse = isUse;
        }

        public int getIsDel() {
            return isDel;
        }

        public void setIsDel(int isDel) {
            this.isDel = isDel;
        }
    }
}
