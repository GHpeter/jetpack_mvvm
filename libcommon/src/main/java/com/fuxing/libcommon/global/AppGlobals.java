package com.fuxing.libcommon.global;

import android.app.Application;


/**
 * @author ：Create by lichunfu
 * @Date : 2020-04-07
 * Description:
 **/
public class AppGlobals {
    private static Application sApplication;

    public static Application getApplication() {
        if (sApplication == null) {

            try {
                sApplication = (Application) Class.forName("android.app.ActivityThread").
                        getMethod("currentApplication").invoke(null, (Object[]) null);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return sApplication;
    }
}
