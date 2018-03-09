package com.yichan.gaotezhipei.server.logisticsdriver.entity;

import java.io.Serializable;

/**
 * Created by ckerv on 2018/2/9.
 */

public class NetOrderItem implements Serializable{


    /**
     * id : 48ca0583-cfe2-49c8-be53-b9be7d43eec9
     * name : 1
     * adress : 广东省广州市番禺区地铁4号线,地铁7号线
     * networkPic : http://gaotedianshang.oss-cn-shenzhen.aliyuncs.com/%E4%B8%8A%E4%BC%A0/2018030718104289?Expires=1520516503&OSSAccessKeyId=LTAIjs0fwbrMyqNt&Signature=Bj%2FCbFwM1uTPZsdwbotSNB7Qv0c%3D
     * distance : 86.78
     * latitude : 23.04342
     * longitude : 113.400431
     * telephone : 123
     */

    private String id;
    private String name;
    private String adress;
    private String networkPic;
    private double distance;
    private String latitude;
    private String longitude;
    private String telephone;

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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getNetworkPic() {
        return networkPic;
    }

    public void setNetworkPic(String networkPic) {
        this.networkPic = networkPic;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
