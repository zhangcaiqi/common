package com.xingqi.code.commonlib.base;

import android.content.Context;


public interface IPage {


    int getLayoutId();

    void initData();

    boolean registerEventBus();

    void release();

    void disposable();


    Context getOwnContext();

}
