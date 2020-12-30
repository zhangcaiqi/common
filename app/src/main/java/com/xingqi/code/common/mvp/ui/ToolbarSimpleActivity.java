package com.xingqi.code.common.mvp.ui;

import android.os.Bundle;
import android.view.Menu;

import com.xingqi.code.common.R;
import com.xingqi.code.commonlib.annotation.ToolbarConfig;
import com.xingqi.code.commonlib.base.SimpleActivity;
import com.xingqi.code.commonlib.mvp.BasePresenter;

@ToolbarConfig(title = "测试")
public class ToolbarSimpleActivity extends SimpleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
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