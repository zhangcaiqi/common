package com.xingqi.code.commonlib.complex;

import android.content.Context;

public abstract class LinearHeader extends BaseHeader {
    public LinearHeader(Context context) {
        super(context);
    }


    @Override
    public int getSpanSize() {
        return 1;
    }
}
