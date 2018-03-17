package com.yichan.gaotezhipei.logistics.entity;

import com.esharp.android.common.EJsonMapping;

/**
 * Created by Administrator on 2018/3/15 0015.
 */

public class ExpressTraceItem {
    @EJsonMapping(tagPath = "AcceptStation")
    private String AcceptStation;
    @EJsonMapping(tagPath = "AcceptTime")
    private String AcceptTime;

    public String getAcceptStation() {
        return AcceptStation;
    }

    public void setAcceptStation(String acceptStation) {
        AcceptStation = acceptStation;
    }

    public String getAcceptTime() {
        return AcceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        AcceptTime = acceptTime;
    }
}
