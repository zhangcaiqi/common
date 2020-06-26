package com.xingqi.code.commonlib.lifecycle;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.xingqi.code.commonlib.base.BaseApplication;
import com.xingqi.code.commonlib.delegate.FragmentDelegate;
import com.xingqi.code.commonlib.delegate.FragmentDelegateImpl;

public class SysFragmentLifecycleCallback extends FragmentManager.FragmentLifecycleCallbacks {
    private FragmentDelegate fragmentDelegate;
    @Override
    public void onFragmentAttached(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Context context) {
        super.onFragmentAttached(fm, f, context);
        fragmentDelegate = fetchDelegate(f);
        if(null == fragmentDelegate){
            fragmentDelegate = new FragmentDelegateImpl(fm,f);
            int delegateKey = f.hashCode();
            BaseApplication.fragmentDelegateMap.put(delegateKey,fragmentDelegate);
        }


    }

    @Override
    public void onFragmentCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
        super.onFragmentCreated(fm, f, savedInstanceState);
        fragmentDelegate = fetchDelegate(f);
        if(null != fragmentDelegate){
            fragmentDelegate.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onFragmentActivityCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
        super.onFragmentActivityCreated(fm, f, savedInstanceState);
        fragmentDelegate = fetchDelegate(f);
        if(null != fragmentDelegate){
            fragmentDelegate.onActivityCreated(savedInstanceState);
        }

    }

    @Override
    public void onFragmentViewCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onFragmentViewCreated(fm, f, v, savedInstanceState);
        fragmentDelegate = fetchDelegate(f);
        if(null != fragmentDelegate){
            fragmentDelegate.onViewCreated(v,savedInstanceState);
        }

    }

    @Override
    public void onFragmentStarted(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentStarted(fm, f);
        fragmentDelegate = fetchDelegate(f);
        if(null != fragmentDelegate){
            fragmentDelegate.onStart();
        }

    }

    @Override
    public void onFragmentResumed(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentResumed(fm, f);
        fragmentDelegate = fetchDelegate(f);
        if(null != fragmentDelegate){
            fragmentDelegate.onResume();
        }

    }

    @Override
    public void onFragmentPaused(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentPaused(fm, f);
        fragmentDelegate = fetchDelegate(f);
        if(null != fragmentDelegate){
            fragmentDelegate.onPause();
        }

    }

    @Override
    public void onFragmentStopped(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentStopped(fm, f);
        fragmentDelegate = fetchDelegate(f);
        if(null != fragmentDelegate){
            fragmentDelegate.onStop();
        }

    }

    @Override
    public void onFragmentSaveInstanceState(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Bundle outState) {
        super.onFragmentSaveInstanceState(fm, f, outState);
        fragmentDelegate = fetchDelegate(f);
        if(null != fragmentDelegate){
            fragmentDelegate.onSaveInstanceState(outState);
        }

    }

    @Override
    public void onFragmentViewDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentViewDestroyed(fm, f);
        fragmentDelegate = fetchDelegate(f);
        if(null != fragmentDelegate){
            fragmentDelegate.onDestroyView();
        }

    }

    @Override
    public void onFragmentDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentDestroyed(fm, f);
        fragmentDelegate = fetchDelegate(f);
        if(null != fragmentDelegate){
            fragmentDelegate.onDestroy();
        }

    }

    @Override
    public void onFragmentDetached(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentDetached(fm, f);
        fragmentDelegate = fetchDelegate(f);
        if(null != fragmentDelegate){
            fragmentDelegate.onDetach();
        }
        BaseApplication.fragmentDelegateMap.remove(f.hashCode());

    }

    private FragmentDelegate fetchDelegate(Fragment fragment){
        int delegateKey = fragment.hashCode();
        FragmentDelegate fragmentDelegate = BaseApplication.fragmentDelegateMap.get(delegateKey);
        return fragmentDelegate;
    }
}
