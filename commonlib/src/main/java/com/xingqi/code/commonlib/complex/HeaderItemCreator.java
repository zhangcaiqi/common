package com.xingqi.code.commonlib.complex;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public interface HeaderItemCreator<T extends RecyclerView.ViewHolder> {

    T onCreateViewHolder(ViewGroup parent, int viewType);

    void onBindViewHolder(T holder, int position);

    int getSpanSize();
}
