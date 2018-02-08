package com.yichan.gaotezhipei.trainservice.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/8 0008.
 */

public class Module<T> {
    private String moduleId;
    private Object face;

    private List<T> list = new ArrayList<>();

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public Object getFace() {
        return face;
    }

    public void setFace(Object face) {
        this.face = face;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
