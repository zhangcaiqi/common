package com.xingqi.code.commonlib.complex;

import android.content.Context;
import android.view.ViewGroup;

import com.xingqi.code.commonlib.base.BaseApplication;

public abstract class BaseHeader implements HeaderItemCreator<ViewHolder> {
    private Context context;

    public BaseHeader(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = ViewHolder.get(context,parent,getLayoutResId());
        return viewHolder;
    }
    protected abstract int getLayoutResId();

}
