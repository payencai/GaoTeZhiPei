package com.yichan.gaotezhipei.agriculturalservice.entity;

/**
 * Created by ckerv on 2018/1/12.
 */

public class AgriculturalShopItem {

    private String shopIcon;
    private String shopName;
    private String shopAddr;
    private String shopPhone;

    public AgriculturalShopItem(String shopIcon, String shopName, String shopAddr, String shopPhone) {
        this.shopIcon = shopIcon;
        this.shopName = shopName;
        this.shopAddr = shopAddr;
        this.shopPhone = shopPhone;
    }

    public String getShopIcon() {
        return shopIcon;
    }

    public void setShopIcon(String shopIcon) {
        this.shopIcon = shopIcon;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddr() {
        return shopAddr;
    }

    public void setShopAddr(String shopAddr) {
        this.shopAddr = shopAddr;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }
}

