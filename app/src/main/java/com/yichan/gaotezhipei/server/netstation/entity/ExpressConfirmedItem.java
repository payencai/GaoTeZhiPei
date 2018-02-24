package com.yichan.gaotezhipei.server.netstation.entity;

/**
 * Created by ckerv on 2018/1/19.
 */

public class ExpressConfirmedItem {

    /**
     * confirmTime : 2018-02-09T08:45:40.225Z
     * count : 0
     * driverId : string
     * driverName : string
     */

    private String confirmTime;
    private int count;
    private String driverId;
    private String driverName;

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
}
