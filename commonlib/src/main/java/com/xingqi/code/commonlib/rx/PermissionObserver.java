package com.xingqi.code.commonlib.rx;

import io.reactivex.disposables.Disposable;

public abstract class PermissionObserver<T> implements io.reactivex.Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {

    }


    @Override
    public void onError(Throwable e) {
        RxErrorHandler rxErrorHandler = provideErrorHandler();
        rxErrorHandler.handleException(e);
    }

    @Override
    public void onComplete() {

    }
    protected abstract RxErrorHandler provideErrorHandler();
}
