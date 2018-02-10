package com.yichan.gaotezhipei.server.logisticsdriver.event;

/**
 * Created by ckerv on 2018/2/9.
 */

public class SelectOrderEvent {

    public boolean isSelected;
    public String orderId;

    public SelectOrderEvent(boolean isSelected, String orderId) {
        this.isSelected = isSelected;
        this.orderId = orderId;
    }
}
