package com.xingqi.code.commonlib.base;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xingqi.code.commonlib.complex.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    private Context context;
    private List<T> dataList = new ArrayList<>();

    public BaseAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.get(context,parent, getLayoutId());
    }
    protected abstract int getLayoutId();

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public List<T> getDataList() {
        return dataList;
    }
}
