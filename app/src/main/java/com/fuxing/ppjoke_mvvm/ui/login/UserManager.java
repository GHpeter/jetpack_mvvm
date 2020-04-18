package com.fuxing.ppjoke_mvvm.ui.login;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fuxing.libcommon.global.AppGlobals;
import com.fuxing.libnetwork.ApiResponse;
import com.fuxing.libnetwork.ApiService;
import com.fuxing.libnetwork.JsonCallback;
import com.fuxing.libnetwork.cache.CacheManager;
import com.fuxing.ppjoke_mvvm.model.User;

/**
 * @author ：Create by lichunfu
 * @Date : 2020-04-16
 * Description:
 **/
public class UserManager {
    private static final String KEY_CACHE_USER = "cache_user";
    private static UserManager mUserManager = new UserManager();

    private MutableLiveData userLiveData = new MutableLiveData();
    private User mUser;

    private UserManager() {
        User cache = (User) CacheManager.getCache(KEY_CACHE_USER);
        if (cache != null && cache.expires_time > System.currentTimeMillis()) {
            mUser = cache;
        }
    }

    public static UserManager get() {
        return mUserManager;
    }

    public LiveData<User> refresh() {
        if (isLogin()) {
            return login(AppGlobals.getApplication());
        }
        MutableLiveData<User> liveData = new MutableLiveData<>();
        userQuery(liveData);
        return liveData;

    }

    public boolean isLogin() {
        return mUser == null ? false : mUser.expires_time > System.currentTimeMillis();
    }

    public LiveData<User> login(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        return userLiveData;
    }

    private void userQuery(MutableLiveData<User> liveData) {
        ApiService.get("user/query")
                .addParam("userId", getUserId())
                .execute(new JsonCallback<User>() {
                    @Override
                    public void onSuccess(ApiResponse<User> response) {
                        save(response.body);
                        liveData.postValue(getUser());
                    }

                    @Override
                    public void onError(ApiResponse<User> response) {
                        ArchTaskExecutor.getMainThreadExecutor().execute(() -> {

                                    Toast.makeText(AppGlobals.getApplication(), response.message, Toast.LENGTH_LONG).show();
                                }
                        );
                        liveData.postValue(null);

                    }


                });
    }

    public long getUserId() {
        if (mUser==null){
            return  mUser.userId=0;
        }
        return isLogin() ? mUser.userId : 0;
    }

    public void save(User user) {
        mUser = user;
        CacheManager.save(KEY_CACHE_USER, user);
        if (userLiveData.hasObservers()) {
            userLiveData.postValue(user);
        }
    }

    public User getUser() {
        return isLogin() ? mUser : null;
    }

    public void logout() {
        CacheManager.delete(KEY_CACHE_USER, mUser);
        mUser = null;
    }
}
