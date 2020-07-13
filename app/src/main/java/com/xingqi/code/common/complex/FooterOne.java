package com.xingqi.code.common.complex;

import android.content.Context;

import com.xingqi.code.common.R;
import com.xingqi.code.commonlib.complex.BaseFooter;
import com.xingqi.code.commonlib.complex.ViewHolder;

public class FooterOne extends BaseFooter {
    public FooterOne(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_footer;
    }


    @Override
    protected void bindViewHolder(ViewHolder holder, int position) {
        holder.setText(R.id.text_view,"footerOne");
    }

    @Override
    public int getSpanSize() {
        return 2;
    }
}
