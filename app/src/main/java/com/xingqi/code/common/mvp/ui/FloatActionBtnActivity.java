package com.xingqi.code.common.mvp.ui;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.xingqi.code.common.R;
import com.xingqi.code.commonlib.annotation.ToolbarConfig;
import com.xingqi.code.commonlib.base.BaseActivity;
import com.xingqi.code.commonlib.mvp.BasePresenter;
import com.xingqi.code.commonlib.utils.SnackbarUtils;
import com.xingqi.code.commonlib.utils.StatusBarUtil;
import com.xingqi.code.commonlib.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class FloatActionBtnActivity extends BaseActivity {

    @BindView(R.id.float_action_button)
    FloatingActionButton floatActionButton;

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
        return R.layout.activity_float_action_btn;
    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.float_action_button)
    public void onFabClick() {
//        SnackbarUtils.longShow(floatActionButton,"测试长时间显示");
        Snackbar snackbar = SnackbarUtils.makeLong(floatActionButton,"显示Action");
        SnackbarUtils.setAction(snackbar,"取消",v -> {
            ToastUtil.toast(FloatActionBtnActivity.this,"已取消");
        }).show();
    }
}