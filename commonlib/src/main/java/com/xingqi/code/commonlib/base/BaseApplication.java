package com.xingqi.code.commonlib.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;


import androidx.fragment.app.FragmentManager;

import com.xingqi.code.commonlib.config.GlobalConfig;
import com.xingqi.code.commonlib.delegate.ActivityDelegate;
import com.xingqi.code.commonlib.delegate.FragmentDelegate;
import com.xingqi.code.commonlib.http.GlobalHttpHandler;
import com.xingqi.code.commonlib.http.log.DefaultFormatPrinter;
import com.xingqi.code.commonlib.http.log.FormatPrinter;
import com.xingqi.code.commonlib.http.log.RequestInterceptor;
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
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;


public abstract class BaseApplication extends Application {
    private static Context context;
    private static Handler handler;
    public static List<ActivityLifecycleCallbacks> activityLifecycleCallbacksList = new ArrayList<>();
    public static List<FragmentManager.FragmentLifecycleCallbacks> fragmentLifecycleCallbacksList = new ArrayList<>();

    public final static Map<Integer, ActivityDelegate> activityDelegateMap = new HashMap<>();
    public final static Map<Integer, FragmentDelegate> fragmentDelegateMap = new HashMap<>();

    public static GlobalConfig globalConfig;
    protected OkHttpClient.Builder okHttpClientBuilder;
    private static OkHttpClient okHttpClient;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        activityLifecycleCallbacksList.add(new SysActivityLifecycleCallback());
        activityLifecycleCallbacksList.add(new ActivityLifecycleForRxLifecycle());
        fragmentLifecycleCallbacksList.add(new SysFragmentLifecycleCallback());
        buildGlobalConfig();
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

    public static OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    protected abstract void addActivityCallback(List<ActivityLifecycleCallbacks> activityLifecycleCallbacksList);

    protected abstract void addFragmentCallback(List<FragmentManager.FragmentLifecycleCallbacks> fragmentLifecycleCallbacksList);

    protected void buildGlobalConfig(){
        okHttpClientBuilder = new OkHttpClient.Builder();
        GlobalHttpHandler globalHttpHandler = providerGlobalHttpHandler();
        Interceptor interceptor = providerInterceptor();
        okHttpClient = buildOkHttpClient(interceptor,globalHttpHandler);
        globalConfig = GlobalConfig.with(this)
                .cacheDir(new File(StorageDirUtil.getPublicExternalPicDir()))
                .imageLoaderStrategy(new GlideImageLoaderStrategy())
                .okHttpClient(okHttpClient)
                .build();
    }

    protected OkHttpClient buildOkHttpClient(Interceptor interceptor,GlobalHttpHandler httpHandler){

        okHttpClientBuilder
                .connectTimeout(GlobalConfig.TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(GlobalConfig.TIME_OUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(interceptor);

        if (httpHandler != null) {
            okHttpClientBuilder.addInterceptor(chain -> chain.proceed(httpHandler.onHttpRequestBefore(chain, chain.request())));
        }
        return okHttpClientBuilder.build();
    }

    protected  Interceptor providerInterceptor(){
        GlobalHttpHandler httpHandler = providerGlobalHttpHandler();
        FormatPrinter formatPrinter = providerFormatPrinter();
        RequestInterceptor.Level level = providerPrintLevel();
        return new RequestInterceptor(httpHandler,formatPrinter,level);
    }

    protected FormatPrinter providerFormatPrinter(){
        return new DefaultFormatPrinter();
    }
    protected RequestInterceptor.Level providerPrintLevel(){
        return RequestInterceptor.Level.ALL;
    }
    protected abstract GlobalHttpHandler providerGlobalHttpHandler();

}
