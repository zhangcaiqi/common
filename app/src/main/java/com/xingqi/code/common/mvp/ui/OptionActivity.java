package com.xingqi.code.common.mvp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.xingqi.code.common.R;
import com.xingqi.code.commonlib.base.BaseActivity;
import com.xingqi.code.commonlib.mvp.BasePresenter;

public class OptionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public boolean isRootPage() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_option;
    }

    @Override
    public void initData() {

    }


    @Override
    public void release() {

    }
}
