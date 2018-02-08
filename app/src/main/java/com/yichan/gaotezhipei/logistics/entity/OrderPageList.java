package com.yichan.gaotezhipei.logistics.entity;

import com.yichan.gaotezhipei.mine.entity.AddressItem;

import java.util.List;

/**
 * Created by ckerv on 2018/2/8.
 */

public class OrderPageList {

    /**
     * beanList : [{"address":{"address":"string","area":"string","city":"string","defaultAddress":0,"id":"string","isCancel":0,"lat1":"string","lng1":"string","name":"string","province":"string","telephone":"string","userId":"string"},"anticipantCar":"string","anticipantTime":"2018-02-07T12:18:33.240Z","articleName":"string","consignee":"string","consigneeAddress":"string","consigneeArea":"string","consigneeCity":"string","consigneeProvince":"string","consigneeTelephone":"string","distance":"string","driverId":"string","driverTelephone":"string","endTime":"2018-02-07T12:18:33.240Z","getTime":"2018-02-07T12:18:33.240Z","id":"string","isDel":"string","num":0,"orderTime":"2018-02-07T12:18:33.240Z","pickupAddress":"string","pickupTime":"2018-02-07T12:18:33.240Z","receiverAddressId":"string","sendTime":"2018-02-07T12:18:33.240Z","type":"string","userId":"string","volume":0,"weight":0}]
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
         * address : {"address":"string","area":"string","city":"string","defaultAddress":0,"id":"string","isCancel":0,"lat1":"string","lng1":"string","name":"string","province":"string","telephone":"string","userId":"string"}
         * anticipantCar : string
         * anticipantTime : 2018-02-07T12:18:33.240Z
         * articleName : string
         * consignee : string
         * consigneeAddress : string
         * consigneeArea : string
         * consigneeCity : string
         * consigneeProvince : string
         * consigneeTelephone : string
         * distance : string
         * driverId : string
         * driverTelephone : string
         * endTime : 2018-02-07T12:18:33.240Z
         * getTime : 2018-02-07T12:18:33.240Z
         * id : string
         * isDel : string
         * num : 0
         * orderTime : 2018-02-07T12:18:33.240Z
         * pickupAddress : string
         * pickupTime : 2018-02-07T12:18:33.240Z
         * receiverAddressId : string
         * sendTime : 2018-02-07T12:18:33.240Z
         * type : string
         * userId : string
         * volume : 0
         * weight : 0
         */

        private AddressItem address;
        private String anticipantCar;
        private String anticipantTime;
        private String articleName;
        private String consignee;
        private String consigneeAddress;
        private String consigneeArea;
        private String consigneeCity;
        private String consigneeProvince;
        private String consigneeTelephone;
        private String distance;
        private String driverId;
        private String driverTelephone;
        private String endTime;
        private String getTime;
        private String id;
        private String isDel;
        private int num;
        private String orderTime;
        private String pickupAddress;
        private String pickupTime;
        private String receiverAddressId;
        private String sendTime;
        private String type;
        private String userId;
        private int volume;
        private int weight;

        public AddressItem getAddress() {
            return address;
        }

        public void setAddress(AddressItem address) {
            this.address = address;
        }

        public String getAnticipantCar() {
            return anticipantCar;
        }

        public void setAnticipantCar(String anticipantCar) {
            this.anticipantCar = anticipantCar;
        }

        public String getAnticipantTime() {
            return anticipantTime;
        }

        public void setAnticipantTime(String anticipantTime) {
            this.anticipantTime = anticipantTime;
        }

        public String getArticleName() {
            return articleName;
        }

        public void setArticleName(String articleName) {
            this.articleName = articleName;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public String getConsigneeAddress() {
            return consigneeAddress;
        }

        public void setConsigneeAddress(String consigneeAddress) {
            this.consigneeAddress = consigneeAddress;
        }

        public String getConsigneeArea() {
            return consigneeArea;
        }

        public void setConsigneeArea(String consigneeArea) {
            this.consigneeArea = consigneeArea;
        }

        public String getConsigneeCity() {
            return consigneeCity;
        }

        public void setConsigneeCity(String consigneeCity) {
            this.consigneeCity = consigneeCity;
        }

        public String getConsigneeProvince() {
            return consigneeProvince;
        }

        public void setConsigneeProvince(String consigneeProvince) {
            this.consigneeProvince = consigneeProvince;
        }

        public String getConsigneeTelephone() {
            return consigneeTelephone;
        }

        public void setConsigneeTelephone(String consigneeTelephone) {
            this.consigneeTelephone = consigneeTelephone;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getDriverId() {
            return driverId;
        }

        public void setDriverId(String driverId) {
            this.driverId = driverId;
        }

        public String getDriverTelephone() {
            return driverTelephone;
        }

        public void setDriverTelephone(String driverTelephone) {
            this.driverTelephone = driverTelephone;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getGetTime() {
            return getTime;
        }

        public void setGetTime(String getTime) {
            this.getTime = getTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIsDel() {
            return isDel;
        }

        public void setIsDel(String isDel) {
            this.isDel = isDel;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public String getPickupAddress() {
            return pickupAddress;
        }

        public void setPickupAddress(String pickupAddress) {
            this.pickupAddress = pickupAddress;
        }

        public String getPickupTime() {
            return pickupTime;
        }

        public void setPickupTime(String pickupTime) {
            this.pickupTime = pickupTime;
        }

        public String getReceiverAddressId() {
            return receiverAddressId;
        }

        public void setReceiverAddressId(String receiverAddressId) {
            this.receiverAddressId = receiverAddressId;
        }

        public String getSendTime() {
            return sendTime;
        }

        public void setSendTime(String sendTime) {
            this.sendTime = sendTime;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getVolume() {
            return volume;
        }

        public void setVolume(int volume) {
            this.volume = volume;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

    }
}
