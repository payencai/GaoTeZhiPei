package com.yichan.gaotezhipei.agriculturalservice.entity;

import java.util.List;

/**
 * Created by ckerv on 2018/3/6.
 */

public class AgriculturalShopPage {

    /**
     * beanList : [{"address":"string","area":"string","city":"string","createTime":"2018-03-04T10:57:35.904Z","id":"string","image":"string","imageKey":"string","isDel":0,"isUse":0,"name":"string","principal":"string","province":"string","telephone":"string"}]
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
         * address : string
         * area : string
         * city : string
         * createTime : 2018-03-04T10:57:35.904Z
         * id : string
         * image : string
         * imageKey : string
         * isDel : 0
         * isUse : 0
         * name : string
         * principal : string
         * province : string
         * telephone : string
         */

        private String address;
        private String area;
        private String city;
        private String createTime;
        private String id;
        private String image;
        private String imageKey;
        private int isDel;
        private int isUse;
        private String name;
        private String principal;
        private String province;
        private String telephone;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
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

        public String getPrincipal() {
            return principal;
        }

        public void setPrincipal(String principal) {
            this.principal = principal;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }
    }
}
