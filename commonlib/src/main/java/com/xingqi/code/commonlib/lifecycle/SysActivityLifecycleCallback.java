package com.xingqi.code.commonlib.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xingqi.code.commonlib.base.BaseApplication;
import com.xingqi.code.commonlib.delegate.ActivityDelegate;
import com.xingqi.code.commonlib.delegate.ActivityDelegateImpl;

public class SysActivityLifecycleCallback implements Application.ActivityLifecycleCallbacks {
    private ActivityDelegate activityDelegate;
    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

        activityDelegate = fetchDelegate(activity);
        if(null == activityDelegate){
            activityDelegate = new ActivityDelegateImpl(activity);
            int delegateKey = activity.hashCode();
            BaseApplication.activityDelegateMap.put(delegateKey,activityDelegate);
        }

        activityDelegate.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        activityDelegate = fetchDelegate(activity);
        if(null != activityDelegate){
            activityDelegate.onStart();
        }
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        activityDelegate = fetchDelegate(activity);
        if(null != activityDelegate){
            activityDelegate.onResume();
        }
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        activityDelegate = fetchDelegate(activity);
        if(null != activityDelegate){
            activityDelegate.onPause();
        }

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        activityDelegate = fetchDelegate(activity);
        if(null != activityDelegate){
            activityDelegate.onStop();
        }
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

        activityDelegate = fetchDelegate(activity);
        if(null != activityDelegate){
            activityDelegate.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        activityDelegate = fetchDelegate(activity);
        if(null != activityDelegate){
            activityDelegate.onDestroy();
        }
        BaseApplication.activityDelegateMap.remove(activity.hashCode());
    }

    private ActivityDelegate fetchDelegate(Activity activity){
        ActivityDelegate activityDelegate = BaseApplication.activityDelegateMap.get(activity.hashCode());
        return activityDelegate;
    }
}
