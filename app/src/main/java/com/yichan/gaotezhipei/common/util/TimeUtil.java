package com.yichan.gaotezhipei.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ckerv on 2018/2/7.
 */

public class TimeUtil {


    public static String getStringByFormat(Date date, String format) {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
        String strDate = null;
        try {
            strDate = mSimpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

}
