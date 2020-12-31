package com.xingqi.code.common.mvp.ui;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.xingqi.code.common.R;
import com.xingqi.code.common.mvp.contract.HotKeyWordContract;
import com.xingqi.code.common.mvp.model.HotKeyWordModel;
import com.xingqi.code.common.mvp.model.entity.HotKeyWord;
import com.xingqi.code.common.mvp.presenter.HotKeyWordPresenter;
import com.xingqi.code.commonlib.base.BaseActivity;
import com.xingqi.code.commonlib.utils.CommonUtils;
import com.xingqi.code.commonlib.utils.LoadingDialogUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Main2Activity extends BaseActivity<HotKeyWordPresenter> implements HotKeyWordContract.View {

    @BindView(R.id.main_button)
    Button mainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
    }

    @Override
    protected HotKeyWordPresenter initPresenter() {
        return new HotKeyWordPresenter(new HotKeyWordModel(), this);
    }


    @Override
    public boolean isRootPage() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main2;
    }

    @Override
    public void initData() {
        mPresenter.getHotWordList();
    }



    @Override
    public void release() {

    }

    @OnClick(R.id.main_button)
    public void onViewClicked() {
        CommonUtils.startActivity(MainActivity.class);
    }

    @Override
    public void showKeyWord(List<HotKeyWord> hotKeyWordList) {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }
    @Override
    public void showLoading() {
        LoadingDialogUtil.showLoading(getSupportFragmentManager());
    }

    @Override
    public void hideLoading() {
        LoadingDialogUtil.hideLoading(getSupportFragmentManager());
    }
}
