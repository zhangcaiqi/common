package com.xingqi.code.common.mvp.ui.fragment;

import androidx.annotation.NonNull;

import com.xingqi.code.common.R;
import com.xingqi.code.common.mvp.contract.HotKeyWordContract;
import com.xingqi.code.common.mvp.model.HotKeyWordModel;
import com.xingqi.code.common.mvp.model.entity.HotKeyWord;
import com.xingqi.code.common.mvp.presenter.HotKeyWordPresenter;
import com.xingqi.code.commonlib.base.BaseFragment;
import com.xingqi.code.commonlib.manager.LoadingDialogManager;

import java.util.List;

public class FragmentFour extends BaseFragment<HotKeyWordPresenter> implements HotKeyWordContract.View {
    @Override
    protected HotKeyWordPresenter initPresenter() {
        return new HotKeyWordPresenter(new HotKeyWordModel(), this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_four;
    }

    @Override
    public void release() {

    }

    @Override
    public void initData() {
        mPresenter.getHotWordList();
    }


    @Override
    public boolean hasToolbar() {
        return false;
    }

    @Override
    public String toolbarTitle() {
        return null;
    }

    @Override
    public void showKeyWord(List<HotKeyWord> hotKeyWordList) {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void showLoading() {
        LoadingDialogManager.getInstance().showLoading(getActivity().getSupportFragmentManager());
    }

    @Override
    public void hideLoading() {
        LoadingDialogManager.getInstance().hideLoading();
    }
}
