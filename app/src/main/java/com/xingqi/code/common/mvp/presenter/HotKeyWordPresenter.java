package com.xingqi.code.common.mvp.presenter;

import com.xingqi.code.common.mvp.contract.HotKeyWordContract;
import com.xingqi.code.common.mvp.model.entity.HotKeyWord;
import com.xingqi.code.commonlib.mvp.BasePresenter;
import com.xingqi.code.commonlib.mvp.Observer;
import com.xingqi.code.commonlib.rx.ResponseException;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HotKeyWordPresenter extends BasePresenter<HotKeyWordContract.Model,HotKeyWordContract.View> {

    public HotKeyWordPresenter(HotKeyWordContract.Model mModel, HotKeyWordContract.View mRootView) {
        super(mModel, mRootView);
    }

    public HotKeyWordPresenter(HotKeyWordContract.View mRootView) {
        super(mRootView);
    }

    public HotKeyWordPresenter() {
    }

    public void getHotWordList(){
        mModel.getHotKeyWordList()
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<HotKeyWord>>() {
                    @Override
                    public void OnSuccess(List<HotKeyWord> hotKeyWordList) {
                        mView.showKeyWord(hotKeyWordList);
                    }

                    @Override
                    public void OnFail(ResponseException e) {
                        mView.hideLoading();
                        mView.showMessage(e.message);
                    }

                    @Override
                    public void OnCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void OnAddDisposable(Disposable d) {
                        addDispose(d);
                    }

                    @Override
                    public void onStart() {
                        mView.showLoading();
                    }
                });
//        mModel.getHotKeyWordList().compose(RxUtil.applySchedulers(mView)).
//                subscribe(new Observer<List<HotKeyWord>>() {
//                    @Override
//                    public void OnSuccess(List<HotKeyWord> hotKeyWords) {
//                        mView.showKeyWord(hotKeyWords);
//                    }
//
//                    @Override
//                    public void OnFail(ResponseException e) {
//
//                    }
//
//                    @Override
//                    public void OnCompleted() {
//
//                    }
//
//                    @Override
//                    public void OnAddDisposable(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onStart() {
//
//                    }
//                });
    }

}
