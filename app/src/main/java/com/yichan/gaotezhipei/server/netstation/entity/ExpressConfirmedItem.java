package com.yichan.gaotezhipei.server.netstation.entity;

/**
 * Created by ckerv on 2018/1/19.
 */

public class ExpressConfirmedItem {


    /**
     * driverId : 82cd3a2b-d5a4-4280-b2f8-d5423d019d60
     * driverName : 1111
     * portraitKey : null
     * portraitUrl : null
     * confirmTime : 2018-03-08 13:06:01
     * count : 1
     */

    private String driverId;
    private String driverName;
    private String portraitKey;
    private String portraitUrl;
    private String confirmTime;
    private int count;

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getPortraitKey() {
        return portraitKey;
    }

    public void setPortraitKey(String portraitKey) {
        this.portraitKey = portraitKey;
    }

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
    }

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
