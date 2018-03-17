package com.yichan.gaotezhipei.logistics.entity;

import com.esharp.android.common.EJsonMapping;

import java.util.List;

/**
 * Created by Simon on 2018/3/15 0015.
 */

public class ExpressSearchResp {
    @EJsonMapping(tagPath = "LogisticCode")
    private String LogisticCode;
    @EJsonMapping(tagPath = "ShipperCode")
    private String ShipperCode;
    @EJsonMapping(tagPath = "State")
    private String State;
    @EJsonMapping(tagPath = "EBusinessID")
    private String EBusinessID;
    @EJsonMapping(tagPath = "Reason")
    private String Reason;
    @EJsonMapping(tagPath = "Success", genericType = "Boolean")
    private Boolean Success;
    @EJsonMapping(tagPath = "Traces", genericType = "com.yichan.gaotezhipei.logistics.entity.ExpressTraceItem", type = EJsonMapping.TYPE_COLLECTION)
    private List<ExpressTraceItem> Traces;

    public String getLogisticCode() {
        return LogisticCode;
    }

    public void setLogisticCode(String logisticCode) {
        LogisticCode = logisticCode;
    }

    public String getShipperCode() {
        return ShipperCode;
    }

    public void setShipperCode(String shipperCode) {
        ShipperCode = shipperCode;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getEBusinessID() {
        return EBusinessID;
    }

    public void setEBusinessID(String EBusinessID) {
        this.EBusinessID = EBusinessID;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public Boolean getSuccess() {
        return Success;
    }

    public void setSuccess(Boolean success) {
        Success = success;
    }

    public List<ExpressTraceItem> getTraces() {
        return Traces;
    }

    public void setTraces(List<ExpressTraceItem> traces) {
        Traces = traces;
    }
}
