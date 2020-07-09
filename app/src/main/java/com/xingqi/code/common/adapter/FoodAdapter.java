package com.xingqi.code.common.adapter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.xingqi.code.common.R;
import com.xingqi.code.common.mvp.model.entity.Food;
import com.xingqi.code.commonlib.base.BaseAdapter;
import com.xingqi.code.commonlib.complex.ViewHolder;

public class FoodAdapter extends BaseAdapter<Food> {
    public FoodAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.person_list_item;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setText(R.id.tv_full_name,getDataList().get(position).name+position);
    }
}
