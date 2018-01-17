package com.yichan.gaotezhipei.mine.entity;

import java.io.Serializable;

/**
 * Created by ckerv on 2018/1/12.
 */

public class AddressItem implements Serializable{

    private String name;
    private String phone;
    private String detailAddr;
    private boolean isDefault;

    public AddressItem() {

    }

    public AddressItem(String name, String phone, String detailAddr, boolean isDefault) {
        this.name = name;
        this.phone = phone;
        this.detailAddr = detailAddr;
        this.isDefault = isDefault;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDetailAddr() {
        return detailAddr;
    }

    public void setDetailAddr(String detailAddr) {
        this.detailAddr = detailAddr;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
