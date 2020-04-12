package com.fuxing.ppjoke_mvvm;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.fuxing.libnetwork.ApiService;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

/**
 * @author ：Create by lichunfu
 * @Date : 2020-04-08
 * Description:
 **/
public class MyApp extends Application {
    private static final String HOSTURL = "http://123.56.232.18:8080/serverdemo";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        installTinkerConfig(base);

    }

    private void installTinkerConfig(Context base) {
        MultiDex.install(base);
        // 安装tinker
        Beta.installTinker();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initNetHost();
        initBuglyWithTinkerConfig();
    }


    private void initNetHost() {
        ApiService.init(HOSTURL, null);
    }

    private void initBuglyWithTinkerConfig() {

        // 调试时，将第三个参数改为true
        Bugly.init(this, "25d98ca2b0", true);
    }
}
