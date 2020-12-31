package com.xingqi.code.common.mvp.ui.fragment;

import androidx.annotation.NonNull;

import com.xingqi.code.common.R;
import com.xingqi.code.common.mvp.contract.HotKeyWordContract;
import com.xingqi.code.common.mvp.model.HotKeyWordModel;
import com.xingqi.code.common.mvp.model.entity.HotKeyWord;
import com.xingqi.code.common.mvp.presenter.HotKeyWordPresenter;
import com.xingqi.code.commonlib.base.BaseLazyLoadFragment;
import com.xingqi.code.commonlib.utils.LoadingDialogUtil;

import java.util.List;

public class FragmentTwo extends BaseLazyLoadFragment<HotKeyWordPresenter> implements HotKeyWordContract.View {
    @Override
    protected HotKeyWordPresenter initPresenter() {
        return new HotKeyWordPresenter(new HotKeyWordModel(), this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_two;
    }

    @Override
    public void release() {

    }

    @Override
    public void initData() {
    }



    @Override
    public void showKeyWord(List<HotKeyWord> hotKeyWordList) {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void showLoading() {
        LoadingDialogUtil.showLoading(getActivity().getSupportFragmentManager());
    }

    @Override
    public void hideLoading() {
        LoadingDialogUtil.hideLoading(getActivity().getSupportFragmentManager());
    }

    @Override
    public void lazyLoad() {
        mPresenter.getHotWordList();
    }
}
