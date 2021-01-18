package com.xingqi.code.common.mvp.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.xingqi.code.common.R;
import com.xingqi.code.commonlib.annotation.ToolbarConfig;
import com.xingqi.code.commonlib.base.BaseActivity;
import com.xingqi.code.commonlib.mvp.BasePresenter;

import butterknife.BindView;

@ToolbarConfig(title = "DrawerLayout测试")
public class DrawerNavActivity extends BaseActivity {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isOpen()){
            drawerLayout.close();
        }else{
            super.onBackPressed();
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_drawer_nav;
    }

    @Override
    public void initData() {
        Toolbar toolbar = getToolbar();

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerToggle.syncState();
        drawerLayout.addDrawerListener(mDrawerToggle);
    }
}