package com.xingqi.code.commonlib.complex;


import androidx.recyclerview.widget.GridLayoutManager;


class FullSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

    private final GridLayoutManager.SpanSizeLookup wrappedSpanSizeLookup;
    private final ComplexAdapter complexAdapter;
    private final GridLayoutManager gridLayoutManager;

    public FullSpanSizeLookup(ComplexAdapter complexAdapter, GridLayoutManager gridLayoutManager) {
        this.complexAdapter = complexAdapter;
        this.gridLayoutManager = gridLayoutManager;
        this.wrappedSpanSizeLookup = gridLayoutManager.getSpanSizeLookup();
    }

    @Override
    public int getSpanSize(int position) {
        if (complexAdapter.isHeaderRow(position)) {
            return gridLayoutManager.getSpanCount();
        }else if(complexAdapter.isFooterRow(position)){
            return gridLayoutManager.getSpanCount();
        }else if(complexAdapter.isStatusRow(position)){
            return gridLayoutManager.getSpanCount();
        } else {
            return wrappedSpanSizeLookup.getSpanSize(position);
        }
    }

}