package com.yichan.gaotezhipei.server.netstation.entity;

/**
 * Created by ckerv on 2018/1/19.
 */

public class ExpressToConfirmItem {


    /**
     * count : 0
     * driverId : string
     * driverName : string
     * takeorderTime : 2018-02-09T08:45:40.229Z
     */

    private int count;
    private String driverId;
    private String driverName;
    private String takeorderTime;

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

    public String getTakeorderTime() {
        return takeorderTime;
    }

    public void setTakeorderTime(String takeorderTime) {
        this.takeorderTime = takeorderTime;
    }
}
