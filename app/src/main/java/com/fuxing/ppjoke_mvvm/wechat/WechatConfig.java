package com.fuxing.ppjoke_mvvm.wechat;

import android.app.Activity;

import com.fuxing.ppjoke_mvvm.wechat.callbacks.IWeChatSignInCallback;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WechatConfig {
    public static final String APP_ID = "";
    public static final String APP_SECRET = "";
    private final IWXAPI WXAPI;
    private IWeChatSignInCallback mSignInCallback = null;
    private static Activity activity;


    private static final class Holder {
        private static final WechatConfig INSTANCE = new WechatConfig(activity);
    }

    public static WechatConfig getInstance(Activity act) {

        return Holder.INSTANCE;
    }

    private WechatConfig(Activity activity) {

//        final Activity activity = Latte.getConfiguration(ConfigKeys.ACTIVITY);
        WXAPI = WXAPIFactory.createWXAPI(activity, APP_ID, true);
        WXAPI.registerApp(APP_ID);
    }

    public final IWXAPI getWXAPI() {
        return WXAPI;
    }

    public WechatConfig onSignSuccess(IWeChatSignInCallback callback) {
        this.mSignInCallback = callback;
        return this;
    }

    public IWeChatSignInCallback getSignInCallback() {
        return mSignInCallback;
    }

    public final void signIn() {
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "random_state";
        WXAPI.sendReq(req);
    }

}
