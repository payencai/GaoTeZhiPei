package com.yichan.gaotezhipei.server.logisticsdriver.entity;

import java.io.Serializable;

/**
 * Created by ckerv on 2018/2/9.
 */

public class NetOrderItem implements Serializable{

    /**
     * adress : string
     * distance : 0
     * id : string
     * name : string
     */

    private String adress;
    private double distance;
    private String id;
    private String name;

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

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
}
