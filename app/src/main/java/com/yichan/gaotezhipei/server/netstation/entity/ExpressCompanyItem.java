package com.yichan.gaotezhipei.server.netstation.entity;

import cn.ittiger.indexlist.entity.BaseEntity;

/**
 * Created by ckerv on 2018/3/7.
 */

public class ExpressCompanyItem implements BaseEntity {


    /**
     * id : string
     * name : string
     */

    private String id;
    private String name;

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

    @Override
    public String getIndexField() {
        return name;
    }
}
