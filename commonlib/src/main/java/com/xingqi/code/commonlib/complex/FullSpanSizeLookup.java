package com.xingqi.code.commonlib.complex;


import androidx.recyclerview.widget.GridLayoutManager;


class FullSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

    private final GridLayoutManager.SpanSizeLookup wrappedSpanSizeLookup;
    private final WrapperAdapter wrapperAdapter;
    private final GridLayoutManager gridLayoutManager;

    public FullSpanSizeLookup(WrapperAdapter wrapperAdapter, GridLayoutManager gridLayoutManager) {
        this.wrapperAdapter = wrapperAdapter;
        this.gridLayoutManager = gridLayoutManager;
        this.wrappedSpanSizeLookup = gridLayoutManager.getSpanSizeLookup();
    }

    @Override
    public int getSpanSize(int position) {
        if (wrapperAdapter.isHeaderRow(position)) {
            return gridLayoutManager.getSpanCount();
        }else if(wrapperAdapter.isFooterRow(position)){
            return gridLayoutManager.getSpanCount();
        } else {
            return wrappedSpanSizeLookup.getSpanSize(position);
        }
    }

    public GridLayoutManager.SpanSizeLookup getWrappedSpanSizeLookup() {
        return wrappedSpanSizeLookup;
    }
}