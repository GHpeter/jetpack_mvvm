package com.fuxing.libcommon.utils;

import java.util.Calendar;

/**
 * @author ：Create by lichunfu
 * @Date : 2020-04-16
 * Description:
 **/
public class TimeUtils {
    public static String calculate(long time) {
        long timeInMills = Calendar.getInstance().getTimeInMillis();
        String valueOf = String.valueOf(time);

        if (valueOf.length() < 13) {
            time = time * 1000;
        }
        long diff = (timeInMills - time) / 1000;
        if (diff <= 5) {
            return "刚刚";
        } else if (diff < 60) {
            return diff + "秒前";
        } else if (diff < 3600) {
            return diff / 60 + "分钟前";
        } else if (diff < 3600 * 24) {
            return diff / 3600 + "小时前";
        } else {
            return diff / (3600 * 24) + "天前";
        }


    }
}
