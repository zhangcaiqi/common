package com.xingqi.code.commonlib.base;

import com.xingqi.code.commonlib.mvp.BasePresenter;

public abstract class SimpleFragment<P extends BasePresenter> extends BaseFragment<P> {

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
    public void release() {

    }
}
