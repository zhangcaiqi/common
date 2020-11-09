package com.xingqi.code.common;

import androidx.fragment.app.FragmentManager;

import com.xingqi.code.common.app.GlobalHttpHandlerImpl;
import com.xingqi.code.commonlib.base.BaseApplication;
import com.xingqi.code.commonlib.http.GlobalHttpHandler;
import com.xingqi.code.commonlib.imageloader.BaseImageLoaderStrategy;
import com.xingqi.code.commonlib.imageloader.glide.GlideImageLoaderStrategy;
import com.xingqi.code.commonlib.imageloader.glide.ImageConfigImpl;

import java.util.List;

import butterknife.ButterKnife;
import me.jessyan.autosize.AutoSize;
import okhttp3.Interceptor;
import timber.log.Timber;

public class App extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        AutoSize.initCompatMultiProcess(this);
        AutoSize.checkAndInit(this);
        if (BuildConfig.LOG_DEBUG) {//Timber初始化
            //Timber 是一个日志框架容器,外部使用统一的Api,内部可以动态的切换成任何日志框架(打印策略)进行日志打印
            //并且支持添加多个日志框架(打印策略),做到外部调用一次 Api,内部却可以做到同时使用多个策略
            //比如添加三个策略,一个打印日志,一个将日志保存本地,一个将日志上传服务器
            Timber.plant(new Timber.DebugTree());
            // 如果你想将框架切换为 Logger 来打印日志,请使用下面的代码,如想切换为其他日志框架请根据下面的方式扩展
//                    Logger.addLogAdapter(new AndroidLogAdapter());
//                    Timber.plant(new Timber.DebugTree() {
//                        @Override
//                        protected void log(int priority, String tag, String message, Throwable t) {
//                            Logger.log(priority, tag, message, t);
//                        }
//                    });
            ButterKnife.setDebug(true);
        }
    }

    @Override
    protected void addActivityCallback(List<ActivityLifecycleCallbacks> activityLifecycleCallbacksList) {

    }

    @Override
    protected void addFragmentCallback(List<FragmentManager.FragmentLifecycleCallbacks> fragmentLifecycleCallbacksList) {

    }

    @Override
    protected int unifyStatusBarColor() {
        return R.color.red;
    }

    @Override
    protected BaseImageLoaderStrategy<ImageConfigImpl> imageLoaderStrategy() {
        return new GlideImageLoaderStrategy();
    }


    @Override
    protected GlobalHttpHandler providerGlobalHttpHandler() {
        return new GlobalHttpHandlerImpl(this);
    }
}
