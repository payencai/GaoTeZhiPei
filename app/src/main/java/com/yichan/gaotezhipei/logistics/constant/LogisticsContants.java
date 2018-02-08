package com.yichan.gaotezhipei.logistics.constant;

/**
 * Created by ckerv on 2018/1/8.
 */

public class LogisticsContants {

    public static final String[] LCL_ORDER_NAMES = {"全部","待接单","待接货","待收货", "待签收","已完成"};

    public static final String[] LOGISTICS_ORDER_NAMES = {"全部","待接货","运输中","待发往仓库","待签收","已完成"};

    public static final int TYPE_LCL_ORDER_ALL = 0;
    public static final int TYPE_LCL_ORDER_TO_RECEIVE = 1;//待接单
    public static final int TYPE_LCL_ORDER_TO_GET_CARGO = 2;//待接货
    public static final int TYPE_LCL_ORDER_TO_RECEIVE_CARGO = 3;//待收货
    public static final int TYPE_LCL_ORDER_TO_CONFIRM = 4;//待确认
    public static final int TYPE_LCL_ORDER_TO_FINISH = 5;//已完成


    public static final String URL_DEMAND_GET_ORDER = "/pdriverOrder/getPdriverOrderByUser";
}
