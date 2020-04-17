package com.fuxing.libcommon.global;

import android.app.Application;
import android.content.Context;


/**
 * @author ：Create by lichunfu
 * @Date : 2020-04-07
 * Description:
 **/
public class AppGlobals {
    private static Application sApplication;

    /**
     * 获取int资源
     *
     * @param context
     * @param resId
     * @return
     */
    public static int getIntRes(Context context, int resId) {
        Context mContext = context;
        if (context == null) {
            mContext = getApplication();
        }
        return mContext.getResources().getInteger(resId);
    }

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

    /**
     * 获取string资源
     *
     * @param context
     * @param resId
     * @return
     */
    public static String getStringRes(Context context, int resId) {
        Context mContext = context;

        if (context == null) {
            mContext = getApplication();
        }
        return mContext.getResources().getString(resId);
    }

    /**
     * 获取color资源
     *
     * @param context
     * @param resId
     * @return
     */
    public static int getColorRes(Context context, int resId) {
        Context mContext = context;

        if (context == null) {
            mContext = getApplication();
        }
        return mContext.getResources().getColor(resId);
    }

}
