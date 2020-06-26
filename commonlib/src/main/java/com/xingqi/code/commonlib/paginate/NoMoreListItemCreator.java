package com.xingqi.code.commonlib.paginate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.xingqi.code.commonlib.R;


public interface NoMoreListItemCreator {

    RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    void onBindViewHolder(RecyclerView.ViewHolder holder, int position);

    NoMoreListItemCreator DEFAULT = new NoMoreListItemCreator() {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.no_more_row, parent, false);
            return new RecyclerView.ViewHolder(view) {
            };
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        }
    };
}