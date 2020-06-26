package com.xingqi.code.commonlib.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;


import androidx.fragment.app.FragmentManager;

import com.xingqi.code.commonlib.config.GlobalConfig;
import com.xingqi.code.commonlib.delegate.ActivityDelegate;
import com.xingqi.code.commonlib.delegate.FragmentDelegate;
import com.xingqi.code.commonlib.imageloader.glide.GlideImageLoaderStrategy;
import com.xingqi.code.commonlib.lifecycle.SysActivityLifecycleCallback;
import com.xingqi.code.commonlib.lifecycle.SysFragmentLifecycleCallback;
import com.xingqi.code.commonlib.rxlifecycle.ActivityLifecycleForRxLifecycle;
import com.xingqi.code.commonlib.utils.StorageDirUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;

public abstract class BaseApplication extends Application {
    private static Context context;
    private static Handler handler;
    public static List<ActivityLifecycleCallbacks> activityLifecycleCallbacksList = new ArrayList<>();
    public static List<FragmentManager.FragmentLifecycleCallbacks> fragmentLifecycleCallbacksList = new ArrayList<>();

    public final static Map<Integer, ActivityDelegate> activityDelegateMap = new HashMap<>();
    public final static Map<Integer, FragmentDelegate> fragmentDelegateMap = new HashMap<>();

    public static GlobalConfig globalConfig;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        activityLifecycleCallbacksList.add(new SysActivityLifecycleCallback());
        activityLifecycleCallbacksList.add(new ActivityLifecycleForRxLifecycle());
        fragmentLifecycleCallbacksList.add(new SysFragmentLifecycleCallback());
        globalConfig = GlobalConfig.with(this)
                .cacheDir(new File(StorageDirUtil.getPublicExternalPicDir()))
                .imageLoaderStrategy(new GlideImageLoaderStrategy())
                .okHttpClient(new OkHttpClient.Builder().build())
                .build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        handler = new Handler();
        for(Application.ActivityLifecycleCallbacks activityLifecycleCallbacks:activityLifecycleCallbacksList){
            registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
        }

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        for(Application.ActivityLifecycleCallbacks activityLifecycleCallbacks:activityLifecycleCallbacksList){
            unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks);
        }
    }

    public static Context getContext(){
        return context;
    }

    public static Handler getHandler(){
        return handler;
    }

    public static GlobalConfig getGlobalConfig() {
        return globalConfig;
    }

    protected abstract void addActivityCallback(List<ActivityLifecycleCallbacks> activityLifecycleCallbacksList);

    protected abstract void addFragmentCallback(List<FragmentManager.FragmentLifecycleCallbacks> fragmentLifecycleCallbacksList);


}
