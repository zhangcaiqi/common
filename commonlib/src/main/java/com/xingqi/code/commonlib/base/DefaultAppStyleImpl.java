package com.xingqi.code.commonlib.base;

import android.app.Activity;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.xingqi.code.commonlib.R;
import com.xingqi.code.commonlib.utils.StatusBarUtil;

public class DefaultAppStyleImpl implements IAppStyle{

    private boolean hasToolbar;
    private int toolbarColor ;
    private int statusBarColor;
    private boolean darkStatusBarText ;
    private String toolbarTitle ;
    private boolean displayNavigateIcon;
    private int navigateIconRes;
    private Activity activity;
    private View.OnClickListener listener;

    public DefaultAppStyleImpl(Builder builder){
        this.hasToolbar = builder.hasToolbar;
        this.toolbarColor = builder.toolbarColor;
        this.statusBarColor = builder.statusBarColor;
        this.darkStatusBarText = builder.darkStatusBarText;
        this.toolbarTitle = builder.toolbarTitle;
        this.displayNavigateIcon = builder.displayNavigateIcon;
        this.navigateIconRes = builder.navigateIconRes;
        this.activity = builder.activity;
        this.listener = builder.listener;
    }

    private void setupStatusBar() {
        if(hasToolbar){
            StatusBarUtil.setStatusBarColor(activity,toolbarColor);
        }else{
            StatusBarUtil.setTranslucentStatus(activity);
            StatusBarUtil.setStatusBarColor(activity,statusBarColor);
        }
        if(darkStatusBarText){
            StatusBarUtil.setDarkText(activity);
        }
    }


    private void setupToolbar() {
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        if(null == toolbar){
            return;
        }
        AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
        appCompatActivity.setSupportActionBar(toolbar);
        ((AppCompatActivity) activity).getSupportActionBar().setTitle(toolbarTitle);
        toolbar.setBackground(activity.getDrawable(toolbarColor));
        if(displayNavigateIcon){
            toolbar.setNavigationIcon(navigateIconRes);
            toolbar.setNavigationOnClickListener(listener);
        }

    }

    @Override
    public void setAppBarStyle() {
        setupStatusBar();
        if(hasToolbar){
            //设置工具栏
            setupToolbar();
        }
    }
    public static class Builder{

        public Builder(Activity activity) {
            this.activity = activity;
        }

        private Activity activity;
        private boolean hasToolbar;
        private int statusBarColor;
        private int toolbarColor;
        private boolean darkStatusBarText;
        private String toolbarTitle;
        private boolean displayNavigateIcon;
        private int navigateIconRes;
        private View.OnClickListener listener;

        public Builder hasToolbar(boolean hasToolbar) {
            this.hasToolbar = hasToolbar;
            return this;
        }

        public Builder toolbarColor(int toolbarColor) {
            this.toolbarColor = toolbarColor;
            return this;
        }

        public Builder darkStatusBarText(boolean darkStatusBarText) {
            this.darkStatusBarText = darkStatusBarText;
            return this;
        }

        public Builder toolbarTitle(String toolbarTitle) {
            this.toolbarTitle = toolbarTitle;
            return this;
        }

        public Builder displayNavigateIcon(boolean displayNavigateIcon) {
            this.displayNavigateIcon = displayNavigateIcon;
            return this;
        }

        public Builder navigateIconRes(int navigateIconRes) {
            this.navigateIconRes = navigateIconRes;
            return this;
        }


        public Builder listener(View.OnClickListener listener) {
            this.listener = listener;
            return this;
        }

        public Builder statusBarColor(int statusBarColor) {
            this.statusBarColor = statusBarColor;
            return this;
        }

        public DefaultAppStyleImpl build(){
            return new DefaultAppStyleImpl(this);
        }
    }
}
