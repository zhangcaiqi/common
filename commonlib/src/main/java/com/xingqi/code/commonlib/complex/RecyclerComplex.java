package com.xingqi.code.commonlib.complex;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerComplex {
    private RecyclerView recyclerView;

    public RecyclerComplex(Builder builder){
        this.recyclerView = builder.recyclerView;
        List<HeaderItemCreator> headerList = builder.headerList;
        List<FooterItemCreator> footerList = builder.footerList;
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        WrapperAdapter wrapperAdapter = new WrapperAdapter(adapter,headerList,footerList);
        wrapperAdapter.setHasStableIds(true);
        recyclerView.setAdapter(wrapperAdapter);

        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
            GridLayoutManager.SpanSizeLookup spanSizeLookup;
            if(builder.fullSpan){
                spanSizeLookup = new FullSpanSizeLookup(wrapperAdapter,gridLayoutManager);
            }else{
                spanSizeLookup = new CustomSpanSizeLookup(wrapperAdapter,gridLayoutManager);
            }
            gridLayoutManager.setSpanSizeLookup(spanSizeLookup);

        }
    }
    public static class Builder{
        private RecyclerView recyclerView;

        private List<HeaderItemCreator> headerList = new ArrayList<>();

        private List<FooterItemCreator> footerList = new ArrayList<>();

        private boolean fullSpan = true;

        public Builder(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        public Builder addHeader(HeaderItemCreator headerItemCreator){
            headerList.add(headerItemCreator);
            return this;
        }
        public Builder addFooter(FooterItemCreator footerItemCreator){
            footerList.add(footerItemCreator);
            return this;
        }
        public Builder setFullSpan(boolean fullSpan){
            this.fullSpan = fullSpan;
            return this;
        }
        public RecyclerComplex build(){
            return new RecyclerComplex(this);
        }
    }

    public static Builder with(RecyclerView recyclerView){
        return new Builder(recyclerView);
    }
}
