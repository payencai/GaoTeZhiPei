package com.yichan.gaotezhipei.mine.event;

/**
 * Created by ckerv on 2018/2/10.
 */

public class ChooseCarTypeEvent {

    public String carType;
    public String carSize;
    public String carWeight;
    public String carVolume;

    public ChooseCarTypeEvent(String carType,String carSize,String carWeight,String carVolume) {
        this.carType = carType;
        this.carSize = carSize;
        this.carWeight = carWeight;
        this.carVolume = carVolume;
    }
}
