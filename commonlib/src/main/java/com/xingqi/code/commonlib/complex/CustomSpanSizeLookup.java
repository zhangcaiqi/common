package com.xingqi.code.commonlib.complex;


import androidx.recyclerview.widget.GridLayoutManager;


class CustomSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

    private final GridLayoutManager.SpanSizeLookup wrappedSpanSizeLookup;
    private final ComplexAdapter complexAdapter;
    private GridLayoutManager gridLayoutManager;
    public CustomSpanSizeLookup(ComplexAdapter complexAdapter, GridLayoutManager gridLayoutManager) {
        this.complexAdapter = complexAdapter;
        this.gridLayoutManager = gridLayoutManager;
        this.wrappedSpanSizeLookup = gridLayoutManager.getSpanSizeLookup();
    }

    @Override
    public int getSpanSize(int position) {
        if (complexAdapter.isHeaderRow(position)) {
            return complexAdapter.findHeaderByPosition(position).getSpanSize();
        }else if(complexAdapter.isFooterRow(position)){
            return complexAdapter.findFooterByPosition(position).getSpanSize();
        }else if(complexAdapter.isStatusRow(position)){
            return gridLayoutManager.getSpanCount();
        } else {
            return wrappedSpanSizeLookup.getSpanSize(position);
        }
    }

    public GridLayoutManager.SpanSizeLookup getWrappedSpanSizeLookup() {
        return wrappedSpanSizeLookup;
    }
}