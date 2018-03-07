package com.yichan.gaotezhipei.server.lcldriver.entity;

/**
 * Created by ckerv on 2018/3/7.
 */

public class DriverInform {

    /**
     * size : 1.8*1.3*1.1米
     * name : ta
     * count : null
     * plateNum : 哈哈
     * type : 小面包车
     * portrait : http://gaotedianshang.oss-cn-shenzhen.aliyuncs.com/%E4%B8%8A%E4%BC%A0/2018030718322542?Expires=1520421581&OSSAccessKeyId=LTAIjs0fwbrMyqNt&Signature=%2Bvhs3JsOyQMAZCa1Uw6%2FijrnItI%3D
     * telnum : 17628285613
     */

    private String size;
    private String name;
    private Integer count;
    private String plateNum;
    private String type;
    private String portrait;
    private String telnum;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getTelnum() {
        return telnum;
    }

    public void setTelnum(String telnum) {
        this.telnum = telnum;
    }
}
