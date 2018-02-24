package com.yichan.gaotezhipei.server.lcldriver.constant;

/**
 * Created by ckerv on 2018/1/19.
 */

public class LCLDriverConstants {

    public static final String[] LCL_DRIVER_ORDER_NAMES = {"全部","待接货","待送货","待签收","已完成"};


    public static final String URL_GET_AVAILABLE_ORDER = "/pdriverOrder/getPdriverOrderByDriver";

    public static final String URL_GET_ALL_ORDER = "/pdriverOrder/getPdriverOrderAllByDriver";

    public static final String URL_DRIVER_UPDATE_ORDER_STATUS = "/pdriverOrder/updatePdriverOrderByDriver";

    public static final String URL_FINISH_ORDER_BY_USER = "/pdriverOrder/updatePdriverOrderByUser";
}
