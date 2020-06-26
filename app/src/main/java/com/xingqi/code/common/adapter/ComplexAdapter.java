package com.xingqi.code.common.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xingqi.code.common.R;
import com.xingqi.code.commonlib.complex.ViewHolder;

public class ComplexAdapter extends RecyclerView.Adapter<ViewHolder> {
    private Context context;

    public ComplexAdapter(Context context) {
        this.context = context;
    }

    private String[] array = new String[]{
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "10",
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "10"
    };
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.get(context,parent, R.layout.layout_complex_item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setText(R.id.text_view,array[position]);
    }

    @Override
    public int getItemCount() {
        return array.length;
    }
}
