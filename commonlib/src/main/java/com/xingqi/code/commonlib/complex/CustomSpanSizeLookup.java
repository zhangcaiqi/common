package com.xingqi.code.commonlib.complex;


import androidx.recyclerview.widget.GridLayoutManager;


class CustomSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

    private final GridLayoutManager.SpanSizeLookup wrappedSpanSizeLookup;
    private final WrapperAdapter wrapperAdapter;
    private GridLayoutManager gridLayoutManager;
    public CustomSpanSizeLookup(WrapperAdapter wrapperAdapter, GridLayoutManager gridLayoutManager) {
        this.wrapperAdapter = wrapperAdapter;
        this.gridLayoutManager = gridLayoutManager;
        this.wrappedSpanSizeLookup = gridLayoutManager.getSpanSizeLookup();
    }

    @Override
    public int getSpanSize(int position) {
        if (wrapperAdapter.isHeaderRow(position)) {
            return wrapperAdapter.findHeaderByPosition(position).getSpanSize();
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