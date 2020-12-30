package com.xingqi.code.commonlib.base;

import com.xingqi.code.commonlib.mvp.BasePresenter;

public abstract class SimpleActivity<P extends BasePresenter> extends BaseActivity<P> {

    @Override
    public boolean isRootPage() {
        return false;
    }





    @Override
    public void release() {

    }
}
