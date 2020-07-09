package com.xingqi.code.common.mvp.presenter;

import com.xingqi.code.common.mvp.contract.FoodContract;
import com.xingqi.code.common.mvp.model.entity.BasePageResult;
import com.xingqi.code.common.mvp.model.entity.Food;
import com.xingqi.code.commonlib.mvp.BasePresenter;
import com.xingqi.code.commonlib.mvp.Observer;
import com.xingqi.code.commonlib.rx.ResponseException;
import com.xingqi.code.commonlib.utils.RxLifecycleUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FoodPresenter extends BasePresenter<FoodContract.Model,FoodContract.View> {

    public FoodPresenter(FoodContract.Model mModel, FoodContract.View mView) {
        super(mModel, mView);
    }

    public FoodPresenter(FoodContract.View mRootView) {
        super(mRootView);
    }

    public FoodPresenter() {
    }

    public void findFoodPage(boolean pullToRefresh,int page,int pageSize){
        mModel.findFoodPage(page,pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mView))
                .subscribe(new Observer<BasePageResult<Food>>() {
                    @Override
                    public void OnSuccess(BasePageResult<Food> basePageResult) {
                        mView.appendData(pullToRefresh,basePageResult.rows);
                    }

                    @Override
                    public void OnFail(ResponseException e) {

                    }

                    @Override
                    public void OnCompleted() {

                    }

                    @Override
                    public void OnAddDisposable(Disposable d) {

                    }

                    @Override
                    public void onStart() {

                    }
                });
    }
}
