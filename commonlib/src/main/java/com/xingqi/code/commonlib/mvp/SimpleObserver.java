package com.xingqi.code.commonlib.mvp;

import com.xingqi.code.commonlib.rx.ResponseException;

import io.reactivex.disposables.Disposable;

public abstract class SimpleObserver<T> extends Observer<T> {

    @Override
    public void OnCompleted() {

    }

    @Override
    public void OnAddDisposable(Disposable d) {

    }

    @Override
    public void onStart() {

    }
}
