package com.fuxing.libcommon.extention;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.fuxing.libcommon.R;
import com.fuxing.libcommon.global.AppGlobals;

/**
 * @author ：Create by lichunfu
 * @Date : 2020-04-17
 * Description:
 **/
public abstract class AbsPagedViewModel<T> extends AndroidViewModel {
    protected PagedList.Config config;
    protected DataSource dataSource;
    DataSource.Factory factory = new DataSource.Factory() {
        @NonNull
        @Override
        public DataSource create() {
            if (dataSource == null || dataSource.isInvalid()) {
                dataSource = createDatasource();
            }
            return dataSource;

        }
    };
    protected LiveData<PagedList<T>> pageData;
    protected MutableLiveData<Boolean> boundaryPageData = new MutableLiveData<>();
    PagedList.BoundaryCallback callback = new PagedList.BoundaryCallback() {
        @Override
        public void onZeroItemsLoaded() {
            //新提交的pageList中没有数据
            boundaryPageData.postValue(false);
        }

        @Override
        public void onItemAtFrontLoaded(@NonNull Object itemAtFront) {
            //新提交的pageList中的第一条数据被加载到列表上
            boundaryPageData.postValue(true);
        }

        @Override
        public void onItemAtEndLoaded(@NonNull Object itemAtEnd) {

        }
    };
    protected Context mContext;

    public AbsPagedViewModel(@NonNull Application application) {
        super(application);
        mContext=application;
        config = new PagedList.Config.Builder().setPageSize(AppGlobals.getIntRes(application, R.integer.number10))
                .setInitialLoadSizeHint(AppGlobals.getIntRes(application, R.integer.number12))
                .build();

        pageData = new LivePagedListBuilder<>(factory, config)
                .setInitialLoadKey(AppGlobals.getIntRes(application, R.integer.number0))
                .setBoundaryCallback(callback)
                .build();
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public LiveData<PagedList<T>> getPageData() {
        return pageData;
    }

    public MutableLiveData<Boolean> getBoundaryPageData() {
        return boundaryPageData;
    }

    public abstract DataSource createDatasource();

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
