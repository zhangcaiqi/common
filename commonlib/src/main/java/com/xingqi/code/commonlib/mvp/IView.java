package com.xingqi.code.commonlib.mvp;

import androidx.annotation.NonNull;

public interface IView {

    void showMessage(@NonNull String message);

    default void showLoading() {

    }

    default void hideLoading() {

    }
}
