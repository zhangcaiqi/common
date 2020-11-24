package com.xingqi.code.commonlib.mvp;

import androidx.annotation.NonNull;

import io.reactivex.disposables.Disposable;

public interface IView {

    void showMessage(@NonNull String message);

    default void showLoading() {

    }

    default void hideLoading() {

    }

    default void onNoPermission(String msg){

    }

    default void OnAddDisposable(Disposable d){

    }
}
