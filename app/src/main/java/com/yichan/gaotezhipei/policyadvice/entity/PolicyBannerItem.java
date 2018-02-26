package com.yichan.gaotezhipei.policyadvice.entity;

/**
 * Created by ckerv on 2018/2/12.
 */

public class PolicyBannerItem {

    /**
     * id : 1
     * name : wqe
     * about : 12
     * imageKey : 上传/2018020911170789
     * image : http://gaotedianshang.oss-cn-shenzhen.aliyuncs.com/%E4%B8%8A%E4%BC%A0/2018020911170789?Expires=1518491836&OSSAccessKeyId=LTAIjs0fwbrMyqNt&Signature=4Ixh393Q4E3oqp2LP2tO6EjufD0%3D
     * createTime : 2018-02-10 14:50:14
     * isUse : 1
     * isDel : 1
     * policyData : {"id":"12","title":"标题","image":"上传/2018020911193445","imageKey":null,"content":"sadasdasdasdasdadas","createTime":"2018-02-10 14:51:12","isUse":1,"isDel":1}
     */

    private String id;
    private String name;
    private String about;
    private String imageKey;
    private String image;
    private String createTime;
    private int isUse;
    private int isDel;
    private PolicyDataBean policyData;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getImageKey() {
        return imageKey;
    }

    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public PolicyDataBean getPolicyData() {
        return policyData;
    }

    public void setPolicyData(PolicyDataBean policyData) {
        this.policyData = policyData;
    }

    public static class PolicyDataBean {
        /**
         * id : 12
         * title : 标题
         * image : 上传/2018020911193445
         * imageKey : null
         * content : sadasdasdasdasdadas
         * createTime : 2018-02-10 14:51:12
         * isUse : 1
         * isDel : 1
         */

        private String id;
        private String title;
        private String image;
        private Object imageKey;
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

        public Object getImageKey() {
            return imageKey;
        }

        public void setImageKey(Object imageKey) {
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
