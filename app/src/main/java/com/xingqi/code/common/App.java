package com.xingqi.code.common;

import androidx.fragment.app.FragmentManager;

import com.xingqi.code.commonlib.base.BaseApplication;

import java.util.List;

import me.jessyan.autosize.AutoSize;

public class App extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        AutoSize.initCompatMultiProcess(this);
        AutoSize.checkAndInit(this);
    }

    @Override
    protected void addActivityCallback(List<ActivityLifecycleCallbacks> activityLifecycleCallbacksList) {

    }

    @Override
    protected void addFragmentCallback(List<FragmentManager.FragmentLifecycleCallbacks> fragmentLifecycleCallbacksList) {

    }
}
