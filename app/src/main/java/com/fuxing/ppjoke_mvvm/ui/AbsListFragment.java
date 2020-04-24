package com.fuxing.ppjoke_mvvm.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fuxing.libcommon.view.EmptyView;
import com.fuxing.ppjoke_mvvm.R;
import com.fuxing.ppjoke_mvvm.databinding.LayoutRefreshViewBinding;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author ：Create by lichunfu
 * @Date : 2020-04-17
 * Description:
 **/
public abstract class AbsListFragment<T, M extends AbsPagedViewModel<T>> extends Fragment implements OnRefreshListener, OnLoadMoreListener {
    protected LayoutRefreshViewBinding binding;
    protected EmptyView mEmptyView;

    protected RecyclerView mRecyclerView;
    protected SmartRefreshLayout mRefreshLayout;
    protected PagedListAdapter<T, RecyclerView.ViewHolder> adapter;
    protected M mViewModel;
    protected DividerItemDecoration decoration;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = LayoutRefreshViewBinding.inflate(inflater, container, false);
        binding.getRoot().setFitsSystemWindows(true);
        mRecyclerView = binding.recyclerView;
        mRefreshLayout = binding.refreshLayout;
        mEmptyView = binding.emptyView;
        initRefreshConfig();

        initAdapterConfig();
        genericViewModel();

        return binding.getRoot();
    }

    private void initRefreshConfig() {
        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(true);
        mRefreshLayout.setOnRefreshListener(this::onRefresh);
        mRefreshLayout.setOnRefreshListener(this::onLoadMore);
    }

    private void initAdapterConfig() {
        adapter = getAdapter();
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(null);

        decoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        decoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.list_divider));

    }


    public abstract PagedListAdapter getAdapter();

    private void genericViewModel() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        Type[] arguments = type.getActualTypeArguments();
        //T, M extends AbsPagedViewModel<T>
        if (arguments.length > 1) {
            Type argument = arguments[1];
            Class modelClaz = ((Class) argument).asSubclass(AbsPagedViewModel.class);
            mViewModel = (M) ViewModelProviders.of(this).get(modelClaz);
            //触发页面初始化数据加载的逻辑
            mViewModel.getPageData().observe(this, pagedList -> submitList(pagedList));
            //监听分页时有无更多数据，以决定是否关闭上拉加载的动画
            mViewModel.getBoundaryPageData().observe(this, hasdata -> finishRefresh(hasdata));
        }
    }

    public void submitList(PagedList<T> result) {
        if (result.size() > 0) {
            adapter.submitList(result);
        }
        finishRefresh(result.size() > 0);
    }

    public void finishRefresh(boolean hasData) {
        PagedList<T> currentList = adapter.getCurrentList();
        hasData = hasData || currentList != null && currentList.size() > 0;
        RefreshState state = mRefreshLayout.getState();
        if (state.isFooter && state.isOpening) {
            mRefreshLayout.finishLoadMore();
        } else if (state.isHeader && state.isOpening) {
            mRefreshLayout.finishRefresh();
        }

        if (hasData) {
            mEmptyView.setVisibility(View.GONE);
        } else {
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }
}
