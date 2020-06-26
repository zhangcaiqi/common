package com.xingqi.code.common.complex;

import android.content.Context;

import com.xingqi.code.common.R;
import com.xingqi.code.commonlib.complex.BaseHeader;
import com.xingqi.code.commonlib.complex.ViewHolder;

public class HeaderOne extends BaseHeader {

    public HeaderOne(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_header;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setText(R.id.text_view,"headerOne");
    }

    @Override
    public int getSpanSize() {
        return 4;
    }
}
