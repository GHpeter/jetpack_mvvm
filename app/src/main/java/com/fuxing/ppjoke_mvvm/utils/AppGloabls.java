package com.fuxing.ppjoke_mvvm.utils;

import android.app.Application;

import java.lang.reflect.Method;

/**
 * @author ：Create by lichunfu
 * @Date : 2020-04-07
 * Description:
 **/
public class AppGloabls {
    private static Application sApplication;

    public static Application getApplication() {
        if (sApplication == null) {
            Method method = null;
            try {
                method = Class.forName("android.app.activityThread").getDeclaredMethod("currentApplication");
                sApplication= (Application) method.invoke(null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return sApplication;
    }
}
