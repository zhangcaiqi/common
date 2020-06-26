package com.xingqi.code.common.mvp.ui;

import android.os.Bundle;
import android.os.Handler;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xingqi.code.common.R;
import com.xingqi.code.common.adapter.RecyclerPersonAdapter;
import com.xingqi.code.common.data.DataProvider;
import com.xingqi.code.commonlib.base.BaseActivity;
import com.xingqi.code.commonlib.mvp.BasePresenter;
import com.xingqi.code.commonlib.paginate.Paginate;

import butterknife.BindView;

public class RecyclePaginateActivity extends BaseActivity implements Paginate.Callbacks {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private RecyclerPersonAdapter adapter;
    private boolean loading = false;
    private int page = 0;
    private Handler handler;
    private Paginate paginate;
    private static final int GRID_SPAN = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        setupPagination();
    }
    protected void setupPagination() {
        // If RecyclerView was recently bound, unbind
        if (paginate != null) {
            paginate.unbind();
        }
        handler.removeCallbacks(fakeCallback);
        adapter = new RecyclerPersonAdapter(DataProvider.getRandomData(20));
        loading = false;
        page = 0;


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        paginate = Paginate.with(recyclerView, this)
                .setLoadingTriggerThreshold(0)
                .setLoadingListItemSpanLookup(()->{
                    return GRID_SPAN;
                })
                .build();
    }
    private Runnable fakeCallback = new Runnable() {
        @Override
        public void run() {
            page++;
            adapter.add(DataProvider.getRandomData(10));
            loading = false;
        }
    };
    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public boolean isRootPage() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_recycle_paginate;
    }

    @Override
    public void release() {

    }

    @Override
    public void initData() {

    }


    @Override
    public boolean hasToolbar() {
        return false;
    }

    @Override
    public String toolbarTitle() {
        return null;
    }

    @Override
    public void onLoadMore() {
        loading = true;
        // Fake asynchronous loading that will generate page of random data after some delay
        handler.postDelayed(fakeCallback, 1000);
    }

    @Override
    public synchronized boolean isLoading() {
        return loading;
    }

    @Override
    public boolean hasLoadedAllItems() {
        return page == 3;
    }
}
