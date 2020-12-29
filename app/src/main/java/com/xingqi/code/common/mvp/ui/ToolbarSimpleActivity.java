package com.xingqi.code.common.mvp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.xingqi.code.common.R;
import com.xingqi.code.commonlib.base.SimpleActivity;
import com.xingqi.code.commonlib.mvp.BasePresenter;

public class ToolbarSimpleActivity extends SimpleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_toolbar_simple;
    }

    @Override
    public void initData() {

    }
}