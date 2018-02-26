package com.yichan.gaotezhipei.server.logisticsdriver.constant;

/**
 * Created by ckerv on 2018/2/5.
 */

public class LogisticsDriverConstants {
    public static final String[] LOGISTICS_DRIVER_ORDER_NAMES = {"全部","待确认","待送达","已完成"};

    public static final String URL_GET_NET_LIST = "/logistics/network/getList";

    public static final String URL_GET_NET_ORDER_LIST = "/wdriver/order/getNetworks";

    public static final String URL_GET_MY_ORDER = "/wdriver/order/getMyOrders";

//    public static final String URL_GET_MY_FINISHED_ORDER = "/wdriver/order/getMyFinishedOrders";

    public static final String URL_CONFIRM_ORDER = "/wdriver/order/pick";

    public static final String URL_DILIVER = "/wdriver/order/arrive";

    public static final String URL_GET_NET_DETAIL_LIST = "/wdriver/order/getNetworkOrder";

    public static final String URL_TAKE_ORDER = "/wdriver/order/takeorder";


    public static final int TYPE_ALL = 1;
    public static final int TYPE_TO_CONFIRM = 2;//待确认
    public static final int TYPE_TO_DELEVER = 3;
    public static final int TYPE_FINISHED = 4;


}
