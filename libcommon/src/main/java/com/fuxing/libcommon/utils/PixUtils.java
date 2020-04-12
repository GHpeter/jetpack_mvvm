package com.fuxing.libcommon.utils;

import android.util.DisplayMetrics;

import com.fuxing.libcommon.global.AppGloabls;

/**
 * @author ：Create by lichunfu
 * @Date : 2020-04-12
 * Description:
 **/
public class PixUtils {
    public static int dp2px(int dpValue) {
        DisplayMetrics metrics = AppGloabls.getApplication().getResources().getDisplayMetrics();
        return (int) (metrics.density * dpValue + 0.5f);
    }

    public static int getScreenWidth() {
        DisplayMetrics metrics = AppGloabls.getApplication().getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }


    public static int getScreenHeight() {
        DisplayMetrics metrics = AppGloabls.getApplication().getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }

}