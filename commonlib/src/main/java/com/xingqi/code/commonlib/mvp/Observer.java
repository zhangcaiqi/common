package com.xingqi.code.commonlib.mvp;


import com.xingqi.code.commonlib.rx.DefaultRxErrorHandler;
import com.xingqi.code.commonlib.rx.ResponseException;

import io.reactivex.disposables.Disposable;



public abstract class Observer<T> implements io.reactivex.Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {
        //添加订阅关系
        OnAddDisposable(d);
        onStart();
    }

    @Override
    public void onNext(T t) {
        OnSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        //自定义异常的传递
        OnFail(new DefaultRxErrorHandler().handleException(e));
    }

    @Override
    public void onComplete() {
        OnCompleted();
    }

    public abstract void OnSuccess(T t);

    public abstract void OnFail(ResponseException e);

    public abstract void OnCompleted();

    public abstract void OnAddDisposable(Disposable d);

    public abstract void onStart();
}






































