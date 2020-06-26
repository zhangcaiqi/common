package com.xingqi.code.commonlib.base;

import android.content.Context;


public interface IPage {


    int getLayoutId();

    void initData();

    boolean registerEventBus();


    boolean hasToolbar();

    String toolbarTitle();

    int toolbarColor();

    boolean displayNavigateIcon();

    int navigateIconRes();

    boolean darkStatusBarText();

    void onNavigateClick();

    void release();

    void disposable();

    Context getOwnContext();
}
