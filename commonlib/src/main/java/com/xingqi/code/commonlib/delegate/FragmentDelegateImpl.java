package com.xingqi.code.commonlib.delegate;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.xingqi.code.commonlib.base.IFragment;
import com.xingqi.code.commonlib.utils.EventBusUtil;
import com.xingqi.code.commonlib.utils.ToolbarUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FragmentDelegateImpl implements FragmentDelegate {
    private FragmentManager fragmentManager;
    private Fragment fragment;
    private IFragment iFragment;
    private Unbinder unbinder;

    public FragmentDelegateImpl(FragmentManager fragmentManager, Fragment fragment) {
        this.fragmentManager = fragmentManager;
        this.fragment = fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if(fragment instanceof IFragment){
            iFragment = (IFragment) fragment;
        }else{
            return;
        }

        if (iFragment.registerEventBus()) {
            EventBusUtil.register(fragment);
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(fragment, view);
        if(null != iFragment){
            iFragment.initData();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroyView() {
        if(null != iFragment){
            iFragment.release();
        }
        //解绑BufferKnife
        if (unbinder != null && unbinder != Unbinder.EMPTY) {
            try {
                unbinder.unbind();
            } catch (IllegalStateException e) {
                e.printStackTrace();
                //fix Bindings already cleared
            }
        }

    }

    @Override
    public void onDestroy() {
        if (null != iFragment && iFragment.registerEventBus()) {
            EventBusUtil.unregister(fragment);
        }
        this.unbinder = null;
        this.fragmentManager = null;
        this.fragment = null;
        this.iFragment = null;

    }

    @Override
    public void onDetach() {

    }
}
