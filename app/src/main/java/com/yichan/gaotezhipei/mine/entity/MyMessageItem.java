package com.yichan.gaotezhipei.mine.entity;

/**
 * Created by ckerv on 2018/1/15.
 */

public class MyMessageItem<T> {

    /**
     * createTime : 2018-03-08T09:42:50.435Z
     * order : {}
     * type : 0
     */

    private String createTime;
    private T order;
    private int type;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public T getOrder() {
        return order;
    }

    public void setOrder(T order) {
        this.order = order;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


}
