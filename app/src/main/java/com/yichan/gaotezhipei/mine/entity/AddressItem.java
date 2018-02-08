package com.yichan.gaotezhipei.mine.entity;

import java.io.Serializable;

/**
 * Created by ckerv on 2018/1/12.
 */

public class AddressItem implements Serializable{


    /**
     * address : string
     * area : string
     * city : string
     * defaultAddress : 0
     * id : string
     * isCancel : 0
     * lat1 : string
     * lng1 : string
     * name : string
     * province : string
     * telephone : string
     * userId : string
     */

    private String address;
    private String area;
    private String city;
    private int defaultAddress;
    private String id;
    private int isCancel;
    private String lat1;
    private String lng1;
    private String name;
    private String province;
    private String telephone;
    private String userId;

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

    public int getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(int defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(int isCancel) {
        this.isCancel = isCancel;
    }

    public String getLat1() {
        return lat1;
    }

    public void setLat1(String lat1) {
        this.lat1 = lat1;
    }

    public String getLng1() {
        return lng1;
    }

    public void setLng1(String lng1) {
        this.lng1 = lng1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
