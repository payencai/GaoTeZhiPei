package com.yichan.gaotezhipei.server.netstation.entity;

import android.database.Cursor;

import com.yichan.gaotezhipei.common.constant.Constans;

import cn.ittiger.indexlist.entity.BaseEntity;

/**
 * Created by ckerv on 2018/3/7.
 */

public class ExpressCompanyItem implements BaseEntity {


    /**
     * id : 1
     * name : 顺丰
     * picKey : null
     * createTime : null
     * picUrl : null
     * isCancel : 1
     */

    private String id;
    private String code;
    private String name;
    private String picKey;
    private String createTime;
    private String picUrl;
    private String isCancel;

    @Override
    public String getIndexField() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicKey() {
        return picKey;
    }

    public void setPicKey(String picKey) {
        this.picKey = picKey;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(String isCancel) {
        this.isCancel = isCancel;
    }


    public void initWithCursor(Cursor cursor) {
        this.id = cursor.getString(cursor.getColumnIndex(Constans.TBL_EXPRESS_COMPANY_COLUMNS.ID.toString()));
        this.code = cursor.getString(cursor.getColumnIndex(Constans.TBL_EXPRESS_COMPANY_COLUMNS.CODE.toString()));
        this.name = cursor.getString(cursor.getColumnIndex(Constans.TBL_EXPRESS_COMPANY_COLUMNS.NAME.toString()));
        this.picKey = cursor.getString(cursor.getColumnIndex(Constans.TBL_EXPRESS_COMPANY_COLUMNS.PIC_KEY.toString()));
        this.isCancel = cursor.getString(cursor.getColumnIndex(Constans.TBL_EXPRESS_COMPANY_COLUMNS.IS_CANCEL.toString()));

    }
}
