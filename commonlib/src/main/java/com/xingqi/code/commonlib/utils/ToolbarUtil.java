package com.xingqi.code.commonlib.utils;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.xingqi.code.commonlib.R;
import com.xingqi.code.commonlib.annotation.ToolbarConfig;

public class ToolbarUtil {

    public static Toolbar initToolbar(Class<?> clazz, AppCompatActivity activity){
        ToolbarConfig toolbarConfig = getToolbarConfig(clazz);
        if(null != toolbarConfig){
            Toolbar toolbar = activity.findViewById(R.id.toolbar);
            if(null != toolbar){
                toolbar.setTitle(toolbarConfig.title());
                activity.setSupportActionBar(toolbar);
                activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                toolbar.setOnMenuItemClickListener((Toolbar.OnMenuItemClickListener) activity);
                return toolbar;
            }

        }
        return null;
    }

    public static Toolbar initFragmentToolbar(Class<?> clazz, AppCompatActivity activity, View view){
        ToolbarConfig toolbarConfig = getToolbarConfig(clazz);
        if(null != toolbarConfig){
            Toolbar toolbar = view.findViewById(R.id.toolbar);
            if(null != toolbar){
                toolbar.setTitle(toolbarConfig.title());
                activity.setSupportActionBar(toolbar);
                activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                toolbar.setOnMenuItemClickListener((Toolbar.OnMenuItemClickListener) activity);
                return toolbar;
            }

        }
        return null;
    }

    public static ToolbarConfig getToolbarConfig(Class<?> clazz){
        ToolbarConfig toolbarConfig = clazz.getAnnotation(ToolbarConfig.class);
        return toolbarConfig;
    }
}
