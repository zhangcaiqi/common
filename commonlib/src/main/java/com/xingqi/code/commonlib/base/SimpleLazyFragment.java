package com.xingqi.code.commonlib.base;

import com.xingqi.code.commonlib.mvp.BasePresenter;

public abstract class SimpleLazyFragment<P extends BasePresenter> extends BaseLazyLoadFragment<P>{

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
