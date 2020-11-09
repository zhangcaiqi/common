package com.xingqi.code.commonlib.mvp;

public class CommonPresenter<T> extends BasePresenter<CommonContract.Model<T>,CommonContract.View<T>> {

    public CommonPresenter(CommonContract.Model<T> mModel, CommonContract.View<T> mView) {
        super(mModel, mView);
    }

}
