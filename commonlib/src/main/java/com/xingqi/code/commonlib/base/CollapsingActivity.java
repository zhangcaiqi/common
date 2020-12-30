package com.xingqi.code.commonlib.base;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.xingqi.code.commonlib.R;
import com.xingqi.code.commonlib.mvp.BasePresenter;
import com.xingqi.code.commonlib.utils.StatusBarUtil;

public class CollapsingActivity<P extends BasePresenter> extends BaseActivity<P> {

    protected ImageView iv;
    protected CollapsingToolbarLayout collapsingToolbarLayout;
    protected RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected P initPresenter() {
        return null;
    }

    @Override
    public boolean isRootPage() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_collapsing;
    }

    @Override
    public void initData() {
        StatusBarUtil.setTranslucentStatus(this);

        iv = findViewById(R.id.iv);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_layout);
        recyclerView = findViewById(R.id.recycler_view);
    }

    @Override
    public void release() {

    }
}