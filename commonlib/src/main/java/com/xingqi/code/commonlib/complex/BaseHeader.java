package com.xingqi.code.commonlib.complex;

import android.content.Context;
import android.view.ViewGroup;

import com.xingqi.code.commonlib.base.BaseApplication;

public abstract class BaseHeader implements HeaderItemCreator<ViewHolder> {
    private boolean init;
    protected Context context;

    public BaseHeader(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = ViewHolder.get(context,parent,getLayoutResId());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(!init){
            bindViewHolder(holder,position);
        }
        init = true;
    }
    protected abstract void bindViewHolder(ViewHolder holder, int position);

    protected abstract int getLayoutResId();

}
