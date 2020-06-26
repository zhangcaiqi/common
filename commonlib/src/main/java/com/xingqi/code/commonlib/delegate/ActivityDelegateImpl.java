package com.xingqi.code.commonlib.delegate;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.xingqi.code.commonlib.base.BaseApplication;
import com.xingqi.code.commonlib.base.IActivity;
import com.xingqi.code.commonlib.manager.AppManager;
import com.xingqi.code.commonlib.utils.EventBusUtil;

import java.util.List;



public class ActivityDelegateImpl implements ActivityDelegate{

    private Activity activity;
    private IActivity iActivity;
    private AppManager mAppManager = AppManager.getAppManager();


    public ActivityDelegateImpl(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if(isAppSelfActivity()){
            iActivity = (IActivity) activity;
        }
        if (null != iActivity && iActivity.registerEventBus()) {
            EventBusUtil.register(activity);
        }
        mAppManager.addActivity(activity);
        //放置最后，框架外部的扩展回调
        List<FragmentManager.FragmentLifecycleCallbacks> fragmentLifecycleCallbackList
                = BaseApplication.fragmentLifecycleCallbacksList;
        for(FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks:fragmentLifecycleCallbackList){
            ((AppCompatActivity)activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks,true);
        }
    }




    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {
        mAppManager.setCurrentActivity(activity);
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {
        if (mAppManager.getCurrentActivity() == activity) {
            mAppManager.setCurrentActivity(null);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

    }

    @Override
    public void onDestroy() {
//        List<FragmentManager.FragmentLifecycleCallbacks> fragmentLifecycleCallbackList
//                = BaseApplication.fragmentLifecycleCallbacksList;
//        for(FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks:fragmentLifecycleCallbackList){
//            ((AppCompatActivity)activity).getSupportFragmentManager().unregisterFragmentLifecycleCallbacks(fragmentLifecycleCallbacks);
//        }
        if (null != iActivity && iActivity.registerEventBus()) {
            EventBusUtil.unregister(activity);
        }
        mAppManager.removeActivity(activity);
    }

    private boolean isAppSelfActivity(){
        if(activity instanceof IActivity){
            return true;
        }
        return false;
    }
}
