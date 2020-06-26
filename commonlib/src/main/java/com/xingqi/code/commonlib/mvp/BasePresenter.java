package com.xingqi.code.commonlib.mvp;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenter<M extends IModel,V extends IView> implements IPresenter, LifecycleObserver {
    protected M mModel;
    protected V mView;

    protected CompositeDisposable mCompositeDisposable;

    public BasePresenter(M mModel, V mView) {
        this.mModel = mModel;
        this.mView = mView;
        onStart();
    }

    public BasePresenter(V mRootView){
        this.mView = mRootView;
        onStart();
    }
    public BasePresenter(){
        onStart();
    }
    @Override
    public void onStart() {
        if (mView != null && mView instanceof LifecycleOwner) {
            ((LifecycleOwner) mView).getLifecycle().addObserver(this);
        }
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    @Override
    public void onDestroy() {
        unDispose();//解除订阅
        if (mModel != null) {
            mModel.onDestroy();
        }
        this.mModel = null;
        this.mView = null;
        this.mCompositeDisposable = null;
    }
    public void addDispose(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);//将所有 Disposable 放入容器集中处理
    }
    public void unDispose() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();//保证 Activity 结束时取消所有正在执行的订阅
        }
    }
}
