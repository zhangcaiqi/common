package com.xingqi.code.commonlib.rx;

import com.xingqi.code.commonlib.mvp.IView;
import com.xingqi.code.commonlib.utils.RxLifecycleUtils;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxUtil {


    public static <T> ObservableTransformer<T, T> applySchedulers(final IView view) {
        return observable -> observable.subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    view.showLoading();//显示进度条
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    view.hideLoading();//隐藏进度条
                }).compose(RxLifecycleUtils.bindToLifecycle(view));
    }
}
