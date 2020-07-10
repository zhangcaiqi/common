package com.xingqi.code.commonlib.complex;


import androidx.recyclerview.widget.GridLayoutManager;


class HeaderPageCustomSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

    private final GridLayoutManager.SpanSizeLookup wrappedSpanSizeLookup;
    private final HeaderPageAdapter headerPageAdapter;
    private GridLayoutManager gridLayoutManager;
    public HeaderPageCustomSpanSizeLookup(HeaderPageAdapter headerPageAdapter, GridLayoutManager gridLayoutManager) {
        this.headerPageAdapter = headerPageAdapter;
        this.gridLayoutManager = gridLayoutManager;
        this.wrappedSpanSizeLookup = gridLayoutManager.getSpanSizeLookup();
    }

    @Override
    public int getSpanSize(int position) {
        if (headerPageAdapter.isHeaderRow(position)) {
            return headerPageAdapter.findHeaderByPosition(position).getSpanSize();
        }else if(headerPageAdapter.isStatusRow(position)){
            return gridLayoutManager.getSpanCount();
        } else {
            return wrappedSpanSizeLookup.getSpanSize(position);
        }
    }

    public GridLayoutManager.SpanSizeLookup getWrappedSpanSizeLookup() {
        return wrappedSpanSizeLookup;
    }
}