package com.xingqi.code.commonlib.complex;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xingqi.code.commonlib.base.BaseAdapter;
import com.xingqi.code.commonlib.paginate.LoadingListItemCreator;
import com.xingqi.code.commonlib.paginate.NoDataListItemCreator;
import com.xingqi.code.commonlib.paginate.NoMoreListItemCreator;

import java.util.ArrayList;
import java.util.List;

public class HeaderPage<T> {
    private RecyclerView recyclerView;
    private HeaderPageAdapter headerPageAdapter;
    private BaseAdapter<T> adapter;

    public HeaderPage(Builder builder){
        this.recyclerView = builder.recyclerView;
        List<HeaderItemCreator> headerList = builder.headerList;
        adapter = (BaseAdapter<T>) recyclerView.getAdapter();
        LoadingListItemCreator loadingListItemCreator = builder.loadingListItemCreator;
        NoMoreListItemCreator noMoreListItemCreator = builder.noMoreListItemCreator;
        NoDataListItemCreator noDataListItemCreator = builder.noDataListItemCreator;
        int pageSize = builder.pageSize;

        headerPageAdapter = new HeaderPageAdapter(
                adapter,
                headerList,
                loadingListItemCreator,
                noMoreListItemCreator,
                noDataListItemCreator,
                pageSize,
                builder.callback);
        recyclerView.setAdapter(headerPageAdapter);

        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
            GridLayoutManager.SpanSizeLookup spanSizeLookup;
            if(builder.fullSpan){
                spanSizeLookup = new HeaderPageFullSpanSizeLookup(headerPageAdapter,gridLayoutManager);
            }else{
                spanSizeLookup = new HeaderPageCustomSpanSizeLookup(headerPageAdapter,gridLayoutManager);
            }
            gridLayoutManager.setSpanSizeLookup(spanSizeLookup);

        }
    }
    public static class Builder{
        private RecyclerView recyclerView;

        private List<HeaderItemCreator> headerList = new ArrayList<>();


        private NoDataListItemCreator noDataListItemCreator = NoDataListItemCreator.DEFAULT;
        private LoadingListItemCreator loadingListItemCreator = LoadingListItemCreator.DEFAULT;
        private NoMoreListItemCreator noMoreListItemCreator = NoMoreListItemCreator.DEFAULT;

        private int pageSize;

        private boolean fullSpan = true;

        private PaginateCallback callback;

        public Builder(RecyclerView recyclerView,PaginateCallback callback) {
            this.recyclerView = recyclerView;
            this.callback = callback;
        }

        public Builder addHeader(HeaderItemCreator headerItemCreator){
            headerList.add(headerItemCreator);
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


        public HeaderPage build(){
            return new HeaderPage(this);
        }
    }

    public static Builder with(RecyclerView recyclerView,PaginateCallback callback){
        return new Builder(recyclerView,callback);
    }

    public void appendData(boolean pullToRefresh,int total,List<T> items){
        List<T> dataList = adapter.getDataList();
        if(pullToRefresh){
            dataList.clear();
            headerPageAdapter.resetPage();
            headerPageAdapter.setItemType(HeaderPageAdapter.ITEM_TYPE_HAS_MORE);
        }
        int preEndIndex = dataList.size();
        headerPageAdapter.setTotal(total);
        if(total == 0){
            headerPageAdapter.setItemType(HeaderPageAdapter.ITEM_TYPE_NO_DATA);
        }
        dataList.addAll(items);
        if(pullToRefresh){
            headerPageAdapter.notifyDataSetChanged();
        }else{
            if(!headerPageAdapter.hasLoadedAllItems()){
                headerPageAdapter.setItemType(HeaderPageAdapter.ITEM_TYPE_HAS_MORE);

            }else{
                headerPageAdapter.setItemType(HeaderPageAdapter.ITEM_TYPE_NO_DATA);
            }
            headerPageAdapter.notifyItemRangeChanged(preEndIndex, items.size()+1);

        }
        headerPageAdapter.setShowStatusRow(true);
    }
}
