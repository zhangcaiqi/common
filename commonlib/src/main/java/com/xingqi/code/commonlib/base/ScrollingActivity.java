package com.xingqi.code.commonlib.base;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.xingqi.code.commonlib.R;
import com.xingqi.code.commonlib.mvp.BasePresenter;

import butterknife.BindView;

public class ScrollingActivity<P extends BasePresenter> extends BaseActivity<P> {

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
        return R.layout.activity_scrolling;
    }

    @Override
    public void initData() {
        recyclerView = findViewById(R.id.recycler_view);
    }

    @Override
    public void release() {

    }
}