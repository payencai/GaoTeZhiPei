package com.yichan.gaotezhipei.servicecenter.constant;

import com.yichan.gaotezhipei.R;

import java.util.HashMap;

/**
 * Created by ckerv on 2018/1/8.
 */

public class ServiceCenterConstants {

    public static final int CATEGORY_NUMS = 9;

    public static final HashMap<Integer, Class<?>> CATEGORY_MAP = new HashMap<>();

    public static final int TYPE_ENTERPRISE = 0;
    public static final int TYPE_FINACE = 1;
    public static final int TYPE_ZHENGCEZIXUN = 2;
    public static final int TYPE_FUHUA = 3;
    public static final int TYPE_PEIXUN = 4;
    public static final int TYPE_PRODUCT = 5;
    public static final int TYPE_GAOYTEZHIPEI = 6;
    public static final int TYPE_NONGZI = 7;
    public static final int TYPE_DATA = 8;

    public static final int[] BANNER_IMAGES = new int[]{R.drawable.service_center_banner1,
    R.drawable.service_center_banner2,
    R.drawable.service_center_banner3};

    public static final String URL_GET_BANNER_IMAGES = "/banner/getBannerUse";




}
