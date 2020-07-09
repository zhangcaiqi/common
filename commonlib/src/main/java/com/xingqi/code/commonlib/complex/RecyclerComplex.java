package com.xingqi.code.commonlib.complex;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xingqi.code.commonlib.base.BaseAdapter;
import com.xingqi.code.commonlib.paginate.LoadingListItemCreator;
import com.xingqi.code.commonlib.paginate.NoDataListItemCreator;
import com.xingqi.code.commonlib.paginate.NoMoreListItemCreator;

import java.util.ArrayList;
import java.util.List;

public class RecyclerComplex<T> {
    private RecyclerView recyclerView;
    private ComplexAdapter complexAdapter;
    private BaseAdapter<T> adapter;

    public RecyclerComplex(Builder builder){
        this.recyclerView = builder.recyclerView;
        List<HeaderItemCreator> headerList = builder.headerList;
        List<FooterItemCreator> footerList = builder.footerList;
        adapter = (BaseAdapter<T>) recyclerView.getAdapter();
        LoadingListItemCreator loadingListItemCreator = builder.loadingListItemCreator;
        NoMoreListItemCreator noMoreListItemCreator = builder.noMoreListItemCreator;
        NoDataListItemCreator noDataListItemCreator = builder.noDataListItemCreator;
        int pageSize = builder.pageSize;

        complexAdapter = new ComplexAdapter(
                                        adapter,
                                        headerList,
                                        footerList,
                                        loadingListItemCreator,
                                        noMoreListItemCreator,
                                        noDataListItemCreator,
                                        pageSize,
                                        builder.callback);
        recyclerView.setAdapter(complexAdapter);

        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
            GridLayoutManager.SpanSizeLookup spanSizeLookup;
            if(builder.fullSpan){
                spanSizeLookup = new FullSpanSizeLookup(complexAdapter,gridLayoutManager);
            }else{
                spanSizeLookup = new CustomSpanSizeLookup(complexAdapter,gridLayoutManager);
            }
            gridLayoutManager.setSpanSizeLookup(spanSizeLookup);

        }
    }
    public static class Builder{
        private RecyclerView recyclerView;

        private List<HeaderItemCreator> headerList = new ArrayList<>();

        private List<FooterItemCreator> footerList = new ArrayList<>();

        private NoDataListItemCreator noDataListItemCreator = NoDataListItemCreator.DEFAULT;
        private LoadingListItemCreator loadingListItemCreator = LoadingListItemCreator.DEFAULT;
        private NoMoreListItemCreator noMoreListItemCreator = NoMoreListItemCreator.DEFAULT;

        private int pageSize;

        private boolean fullSpan = true;

        private PaginateCallback callback;

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
        public Builder fullSpan(boolean fullSpan){
            this.fullSpan = fullSpan;
            return this;
        }


        public Builder noDataListItemCreator(NoDataListItemCreator noDataListItemCreator) {
            this.noDataListItemCreator = noDataListItemCreator;
            return this;
        }

        public Builder loadingListItemCreator(LoadingListItemCreator loadingListItemCreator) {
            this.loadingListItemCreator = loadingListItemCreator;
            return this;
        }

        public Builder noMoreListItemCreator(NoMoreListItemCreator noMoreListItemCreator) {
            this.noMoreListItemCreator = noMoreListItemCreator;
            return this;
        }

        public Builder pageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder callback(PaginateCallback callback) {
            this.callback = callback;
            return this;
        }

        public RecyclerComplex build(){
            return new RecyclerComplex(this);
        }
    }

    public static Builder with(RecyclerView recyclerView){
        return new Builder(recyclerView);
    }

    public void appendData(boolean pullToRefresh,int total,List<T> items){
        List<T> dataList = adapter.getDataList();
        if(pullToRefresh){
            dataList.clear();
        }
        int preEndIndex = dataList.size();
        complexAdapter.setTotal(total);

        dataList.addAll(items);
        if(pullToRefresh){
            complexAdapter.notifyDataSetChanged();
        }else{
            if(!complexAdapter.hasLoadedAllItems()){
                complexAdapter.notifyItemRangeInserted(preEndIndex, items.size());
            }else{
                complexAdapter.notifyItemRangeInserted(preEndIndex, items.size()+1);
            }

        }
        complexAdapter.setShowStatusRow(true);
    }
}
