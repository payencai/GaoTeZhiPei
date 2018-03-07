package com.yichan.gaotezhipei.server.netstation.event;

/**
 * Created by ckerv on 2018/3/7.
 */

public class ChooseExpressCompanyEvent {

    public String companyName;
    public String companyId;

    public ChooseExpressCompanyEvent(String companyName, String companyId) {
        this.companyName = companyName;
        this.companyId = companyId;
    }
}
