package com.yichan.gaotezhipei.server.logisticsdriver.entity;

import java.io.Serializable;

/**
 * Created by ckerv on 2018/2/9.
 */

public class LogisticsDriverOrderItem implements Serializable {


    /**
     * driverStatus : 2
     * updateTime : 2018-02-25 10:08:39
     * warehouseName : 高特仓库
     * isConfirm : 1
     * networkName : 1
     * distanceDriver : 871.6
     * networkId : 48ca0583-cfe2-49c8-be53-b9be7d43eec9
     * driverId : 03fbf09e-e373-46c0-a5da-1bd1b3477464
     * networkAdressLatitude : 23.138494
     * networkAdressLongitude : 113.220381
     * networkAdress : 1
     * warehouseAdress : 广州大学城
     * warehouseAdressLatitude : 39.976004
     * warehouseAdressLongitude : 116.611813
     * takeorderTime : 2018-02-24 18:28:45
     * pickTime : null
     * reachwarehouseTime : null
     * count : 1
     * status : null
     * networkPicKey : null
     * networkPic : null
     */

    private String driverStatus;
    private String updateTime;
    private String warehouseName;
    private String isConfirm;
    private String networkName;
    private double distanceDriver;
    private String networkId;
    private String driverId;
    private String networkAdressLatitude;
    private String networkAdressLongitude;
    private String networkAdress;
    private String warehouseAdress;
    private String warehouseAdressLatitude;
    private String warehouseAdressLongitude;
    private String takeorderTime;
    private String pickTime;
    private String reachwarehouseTime;
    private int count;
    private String status;
    private String networkPicKey;
    private String networkPic;

    public String getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(String driverStatus) {
        this.driverStatus = driverStatus;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getIsConfirm() {
        return isConfirm;
    }

    public void setIsConfirm(String isConfirm) {
        this.isConfirm = isConfirm;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public double getDistanceDriver() {
        return distanceDriver;
    }

    public void setDistanceDriver(double distanceDriver) {
        this.distanceDriver = distanceDriver;
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getNetworkAdressLatitude() {
        return networkAdressLatitude;
    }

    public void setNetworkAdressLatitude(String networkAdressLatitude) {
        this.networkAdressLatitude = networkAdressLatitude;
    }

    public String getNetworkAdressLongitude() {
        return networkAdressLongitude;
    }

    public void setNetworkAdressLongitude(String networkAdressLongitude) {
        this.networkAdressLongitude = networkAdressLongitude;
    }

    public String getNetworkAdress() {
        return networkAdress;
    }

    public void setNetworkAdress(String networkAdress) {
        this.networkAdress = networkAdress;
    }

    public String getWarehouseAdress() {
        return warehouseAdress;
    }

    public void setWarehouseAdress(String warehouseAdress) {
        this.warehouseAdress = warehouseAdress;
    }

    public String getWarehouseAdressLatitude() {
        return warehouseAdressLatitude;
    }

    public void setWarehouseAdressLatitude(String warehouseAdressLatitude) {
        this.warehouseAdressLatitude = warehouseAdressLatitude;
    }

    public String getWarehouseAdressLongitude() {
        return warehouseAdressLongitude;
    }

    public void setWarehouseAdressLongitude(String warehouseAdressLongitude) {
        this.warehouseAdressLongitude = warehouseAdressLongitude;
    }

    public String getTakeorderTime() {
        return takeorderTime;
    }

    public void setTakeorderTime(String takeorderTime) {
        this.takeorderTime = takeorderTime;
    }

    public String getPickTime() {
        return pickTime;
    }

    public void setPickTime(String pickTime) {
        this.pickTime = pickTime;
    }

    public String getReachwarehouseTime() {
        return reachwarehouseTime;
    }

    public void setReachwarehouseTime(String reachwarehouseTime) {
        this.reachwarehouseTime = reachwarehouseTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNetworkPicKey() {
        return networkPicKey;
    }

    public void setNetworkPicKey(String networkPicKey) {
        this.networkPicKey = networkPicKey;
    }

    public String getNetworkPic() {
        return networkPic;
    }

    public void setNetworkPic(String networkPic) {
        this.networkPic = networkPic;
    }
}
